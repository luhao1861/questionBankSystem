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
import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.module.system.service.SysClassService;
import me.zhengjie.module.system.service.dto.SysClassQueryCriteria;
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
@Api(tags = "课程管理管理")
@RequestMapping("/api/sysClass")
public class SysClassController {

    private final SysClassService sysClassService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysClass:list')")
    public void download(HttpServletResponse response, SysClassQueryCriteria criteria) throws IOException {
        sysClassService.download(sysClassService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询课程管理")
    @ApiOperation("查询课程管理")
    @PreAuthorize("@el.check('sysClass:list')")
    public ResponseEntity<Object> query(SysClassQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysClassService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增课程管理")
    @ApiOperation("新增课程管理")
    @PreAuthorize("@el.check('sysClass:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SysClass resources){
        return new ResponseEntity<>(sysClassService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改课程管理")
    @ApiOperation("修改课程管理")
    @PreAuthorize("@el.check('sysClass:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SysClass resources){
        sysClassService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除课程管理")
    @ApiOperation("删除课程管理")
    @PreAuthorize("@el.check('sysClass:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        sysClassService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}