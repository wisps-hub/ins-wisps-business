package com.wisps.article.provider.controller;

import com.wisps.controller.BaseController;
import com.wisps.resp.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/file")
public class FileController extends BaseController {

    @PostMapping("/upload")
    public Result create(@RequestBody @Validated MultipartFile image){
        Long uid = Long.valueOf(getUid());
        return Result.success(UUID.randomUUID());
    }

}
