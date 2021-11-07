/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.module.business.rest;

import cn.hutool.core.collection.CollectionUtil;
import me.zhengjie.annotation.Log;
import me.zhengjie.module.business.domain.Question;
import me.zhengjie.module.business.service.QuestionService;
import me.zhengjie.module.business.service.dto.QuestionQueryCriteria;
import me.zhengjie.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author KunYi
* @date 2021-11-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "题目管理管理")
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('question:list')")
    public void download(HttpServletResponse response, QuestionQueryCriteria criteria) throws IOException {
        questionService.download(questionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询题目管理")
    @ApiOperation("查询题目管理")
    @PreAuthorize("@el.check('question:list')")
    public ResponseEntity<Object> query(QuestionQueryCriteria criteria, Pageable pageable){
        if (!ObjectUtils.isEmpty(criteria.getCid())) {
            criteria.getCids().add(criteria.getCid());
        }
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(questionService.queryAll(criteria,pageable),HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    @Log("新增题目管理")
    @ApiOperation("新增题目管理")
    @PreAuthorize("@el.check('question:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Question resources){
        questionService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改题目管理")
    @ApiOperation("修改题目管理")
    @PreAuthorize("@el.check('question:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Question resources){
        questionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除题目管理")
    @ApiOperation("删除题目管理")
    @PreAuthorize("@el.check('question:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        questionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}