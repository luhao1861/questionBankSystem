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

import me.zhengjie.annotation.Log;
import me.zhengjie.module.business.domain.TestStrategy;
import me.zhengjie.module.business.service.TestStrategyService;
import me.zhengjie.module.business.service.dto.TestStrategyDto;
import me.zhengjie.module.business.service.dto.TestStrategyQueryCriteria;
import me.zhengjie.module.system.service.dto.SysClassDto;
import me.zhengjie.module.system.service.dto.SysClassQueryCriteria;
import me.zhengjie.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author KunYi
* @date 2021-11-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "试卷策略管理管理")
@RequestMapping("/api/testStrategy")
public class TestStrategyController {

    private final TestStrategyService testStrategyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('testStrategy:list')")
    public void download(HttpServletResponse response, TestStrategyQueryCriteria criteria) throws IOException {
        testStrategyService.download(testStrategyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询试卷策略管理")
    @ApiOperation("查询试卷策略管理")
    @PreAuthorize("@el.check('testStrategy:list')")
    public ResponseEntity<Object> query(TestStrategyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testStrategyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/queryAll")
    @Log("查询课程管理(不分页）")
    @ApiOperation("查询课程管理（不分页）")
    @PreAuthorize("@el.check('testStrategy:list')")
    public ResponseEntity<Object> query(TestStrategyQueryCriteria criteria){
        List<TestStrategyDto> testStrategyDtos = testStrategyService.queryAll(criteria);
        return new ResponseEntity<>(PageUtil.toPage(testStrategyDtos, testStrategyDtos.size()),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增试卷策略管理")
    @ApiOperation("新增试卷策略管理")
    @PreAuthorize("@el.check('testStrategy:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TestStrategy resources){
        return new ResponseEntity<>(testStrategyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改试卷策略管理")
    @ApiOperation("修改试卷策略管理")
    @PreAuthorize("@el.check('testStrategy:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TestStrategy resources){
        testStrategyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除试卷策略管理")
    @ApiOperation("删除试卷策略管理")
    @PreAuthorize("@el.check('testStrategy:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        testStrategyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}