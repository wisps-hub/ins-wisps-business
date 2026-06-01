## 1、三个核心实体的关系：

SsoAccount (登录凭证)        ←→     SsoUser (用户身份)        ←→    Organization
- id                              - uid                            - oid
- loginIdentity (手机/邮箱)        - accountId
- identityType                    - oid

         1 ←————— N (一个手机号可在多个组织中有身份) ————→ N

