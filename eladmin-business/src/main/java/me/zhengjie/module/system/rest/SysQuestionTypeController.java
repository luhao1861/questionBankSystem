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
package me.zhengjie.module.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.module.system.domain.SysQuestionType;
import me.zhengjie.module.system.service.SysQuestionTypeService;
import me.zhengjie.module.system.service.dto.SysQuestionTypeQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author KunYi
* @date 2021-11-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "题型管理管理")
@RequestMapping("/api/sysQuestionType")
public class SysQuestionTypeController {

    private final SysQuestionTypeService sysQuestionTypeService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysQuestionType:list')")
    public void download(HttpServletResponse response, SysQuestionTypeQueryCriteria criteria) throws IOException {
        sysQuestionTypeService.download(sysQuestionTypeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询题型管理")
    @ApiOperation("查询题型管理")
    @PreAuthorize("@el.check('sysQuestionType:list')")
    public ResponseEntity<Object> query(SysQuestionTypeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysQuestionTypeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增题型管理")
    @ApiOperation("新增题型管理")
    @PreAuthorize("@el.check('sysQuestionType:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SysQuestionType resources){
        return new ResponseEntity<>(sysQuestionTypeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改题型管理")
    @ApiOperation("修改题型管理")
    @PreAuthorize("@el.check('sysQuestionType:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SysQuestionType resources){
        sysQuestionTypeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除题型管理")
    @ApiOperation("删除题型管理")
    @PreAuthorize("@el.check('sysQuestionType:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        sysQuestionTypeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}