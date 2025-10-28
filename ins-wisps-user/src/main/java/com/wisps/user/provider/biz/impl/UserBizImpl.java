package com.wisps.user.provider.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.wisps.user.api.consts.UserState;
import com.wisps.user.api.req.*;
import com.wisps.user.provider.biz.RealNameAuthBiz;
import com.wisps.user.provider.biz.UserBiz;
import com.wisps.user.provider.consts.Consts;
import com.wisps.user.provider.entity.UserEntity;
import com.wisps.user.provider.exception.UserErrorCode;
import com.wisps.user.provider.exception.UserException;
import com.wisps.user.provider.mapping.dao.UserDao;
import com.wisps.user.provider.vo.resp.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.wisps.user.provider.exception.UserErrorCode.*;

@Service
public class UserBizImpl implements UserBiz{

    @Autowired
    private UserDao userDao;
    @Autowired
    private RealNameAuthBiz realNameAuthBiz;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RBloomFilter<String> nickNameBloomFilter;
    @Autowired
    private RBloomFilter<String> inviteCodeBloomFilter;
    @Autowired
    private RScoredSortedSet<String> inviteRank;

    @Override
    public UserVo register(RegisterReq registerReq) {
        String password = registerReq.getPassword();
        String mobile = registerReq.getMobile();
        String defaultNickName;
        String defaultInviteCode;
        do {
            defaultInviteCode = RandomUtil.randomString(6).toUpperCase();
            //前缀 + 6位随机数 + 手机号后四位
            defaultNickName = Consts.DEFAULT_NICK_NAME_PREFIX + defaultInviteCode + mobile.substring(mobile.length() - 4);
        } while (nickNameBloomFilter.contains(defaultNickName) || inviteCodeBloomFilter.contains(defaultInviteCode));

        if (userDao.getByMobile(mobile) != null) {
            throw new UserException(DUPLICATE_TELEPHONE_NUMBER);
        }
        // 注册
        UserEntity inviter = userDao.getByInviteCode(registerReq.getInviteCode());
        Long inviterId = inviter == null? null : inviter.getId();
        UserEntity userEntity = userDao.register(mobile, defaultNickName,
                StringUtils.isBlank(password) ? mobile : password, defaultInviteCode, inviterId);
        Assert.notNull(userEntity, UserErrorCode.USER_OPERATE_FAILED.getCode());
        //更新布隆过滤器 和 排行
        nickNameBloomFilter.add(defaultNickName);
        inviteCodeBloomFilter.add(defaultInviteCode);
        updateInviteRank(inviterId);
        //返回结果
        return BeanUtil.copyProperties(userEntity, UserVo.class);
    }

    @Override
    public UserVo modify(ModifyReq modifyReq) {
        UserEntity userEntity = userDao.selectById(modifyReq.getUid());
        Assert.notNull(userEntity, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(userEntity.canModifyInfo(), () -> new UserException(USER_STATUS_CANT_OPERATE));
        // 重名昵称
        if (StringUtils.isNotBlank(modifyReq.getNickName()) && nickNameBloomFilter.contains(modifyReq.getNickName())) {
            throw new UserException(NICK_NAME_EXIST);
        }
        // 修改
        userDao.updateById(userEntity.modify(modifyReq));
        return BeanUtil.copyProperties(userEntity, UserVo.class);
    }

    @Override
    public void realNameAuth(RealNameAuthReq authReq) {
        UserEntity userEntity = userDao.selectById(authReq.getUid());
        Assert.notNull(userEntity, () -> new UserException(USER_NOT_EXIST));
        if (userEntity.getState() == UserState.AUTH || userEntity.getState() == UserState.ACTIVE) {
            return;
        }
        Assert.isTrue(userEntity.getState() == UserState.INIT, () -> new UserException(USER_STATUS_IS_NOT_INIT));
        // 实名认证
        Assert.isTrue(realNameAuthBiz.checkRealName(authReq.getRealName(), authReq.getIdCard()), () -> new UserException(USER_AUTH_FAIL));
        // 更新认证状态
        userDao.updateById(userEntity.realNameAuth(authReq.getRealName(), authReq.getIdCard()));
    }

    @Override
    public void active(ActiveReq activeReq) {
        UserEntity userEntity = userDao.selectById(activeReq.getUid());
        Assert.notNull(userEntity, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(userEntity.getState() == UserState.AUTH, () -> new UserException(USER_STATUS_IS_NOT_AUTH));
        userDao.updateById(userEntity.active(activeReq.getBlockChainUrl(), activeReq.getBlockChainPlatform()));
    }

    @Override
    public void freeze(Long uid) {
        UserEntity userEntity = userDao.selectById(uid);
        Assert.notNull(userEntity, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(userEntity.getState() == UserState.ACTIVE, () -> new UserException(USER_STATUS_IS_NOT_ACTIVE));
        if (userEntity.getState() == UserState.FROZEN) {
            return;
        }
        userEntity.setState(UserState.FROZEN);
        userDao.updateById(userEntity);
    }

    @Override
    public void unfreeze(Long uid) {
        UserEntity userEntity = userDao.selectById(uid);
        Assert.notNull(userEntity, () -> new UserException(USER_NOT_EXIST));
        if (userEntity.getState() == UserState.ACTIVE) {
            return;
        }
        userEntity.setState(UserState.ACTIVE);
        userDao.updateById(userEntity);
    }

    @Override
    public UserVo queryOne(UserQueryReq queryReq) {
        String mobile = queryReq.getMobile();
        Long uid = queryReq.getUid();
        String password = queryReq.getPassword();
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(password) && uid == null) return null;
        UserEntity userEntity = userDao.conditionQuery(uid, mobile, password);
        return userEntity != null ? BeanUtil.copyProperties(userEntity, UserVo.class) : null;
    }

    @Override
    public UserVo getById(Long id) {
        UserEntity userEntity = userDao.selectById(id);
        return userEntity != null ? BeanUtil.copyProperties(userEntity, UserVo.class) : null;
    }

    @Override
    public List<UserVo> getList(List<Long> ids) {
        List<UserEntity> list = userDao.getList(ids);
        return CollectionUtils.isEmpty(list)? Lists.newArrayList() :
                list.stream().map(userEntity -> BeanUtil.copyProperties(userEntity, UserVo.class)).toList();
    }

    /**
     * 更新排名，排名规则：
     * <pre>
     *     1、优先按照分数排，分数越大的，排名越靠前
     *     2、分数相同，则按照上榜时间排，上榜越早的排名越靠前
     * </pre>
     *
     * @param inviterId
     */
    private void updateInviteRank(Long inviterId) {
        if (inviterId == null) {
            return;
        }
        //1、这里因为是一个私有方法，无法通过注解方式实现分布式锁。
        //2、register方法已经加了锁，这里需要二次加锁的原因是register锁的是注册人，这里锁的是邀请人
        RLock rLock = redissonClient.getLock(inviterId + "");
        rLock.lock();
        try {
            //获取当前用户的积分
            Double score = inviteRank.getScore(inviterId + "");
            if (score == null) {
                score = 0.0;
            }

            //获取最近一次上榜时间
            long currentTimeStamp = System.currentTimeMillis();
            //把上榜时间转成小数(时间戳13位，所以除以10000000000000能转成小数)，并且倒序排列（用1减），即上榜时间越早，分数越大（时间越晚，时间戳越大，用1减一下，就反过来了）
            double timePartScore = 1 - (double) currentTimeStamp / 10000000000000L;

            //1、当前积分保留整数，即移除上一次的小数位
            //2、当前积分加100，表示新邀请了一个用户
            //3、加上“最近一次上榜时间的倒序小数位“作为score
            inviteRank.add(score.intValue() + 100.0 + timePartScore, inviterId + "");
        } finally {
            rLock.unlock();
        }
    }
}