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
import me.zhengjie.module.business.domain.Question;
import me.zhengjie.module.business.domain.TestPaper;
import me.zhengjie.module.business.repository.QuestionRepository;
import me.zhengjie.module.business.repository.TestPaperRepository;
import me.zhengjie.module.business.service.TestPaperService;
import me.zhengjie.module.business.service.TestStrategyService;
import me.zhengjie.module.business.service.dto.TestPaperDto;
import me.zhengjie.module.business.service.dto.TestPaperQueryCriteria;
import me.zhengjie.module.business.service.dto.TestStrategyDto;
import me.zhengjie.module.business.service.dto.TestStrategyQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @author KunYi
 * @website https://el-admin.vip
 * @date 2021-11-26
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "试卷管理管理")
@RequestMapping("/api/testPaper")
public class TestPaperController {

    private final TestPaperService testPaperService;
    private final QuestionRepository questionRepository;
    private final TestStrategyService testStrategyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('testPaper:list')")
    public void download(HttpServletResponse response, TestPaperQueryCriteria criteria) throws IOException {
        testPaperService.download(testPaperService.queryAll(criteria), response);
    }

    @Log("导出试卷")
    @ApiOperation("导出试卷")
    @GetMapping(value = "/downloadpaper")
    @PreAuthorize("@el.check('testPaper:list')")
    public void downloadpaper(HttpServletResponse response, @RequestParam("id") String id) throws IOException {
        //get test paper info
        id = id.substring(0, id.length() - 1);
        TestPaperDto testPaperDto = testPaperService.findById(Long.valueOf(id));
        String title = testPaperDto.getName();//get paper name->title
        Integer cid = testPaperDto.getCids().getId();//get course id->query questions
        Integer tsid = testPaperDto.getTsids().getId();//get testStrategy id->get amounts of each type, query questions

        //get amounts of each 3 types
        // 1:multiple options, 2:q&a, 3:fill in blank
        TestStrategyDto testStrategyDto = testStrategyService.findById(tsid.longValue());
        Map<String, Integer> map = new HashMap<>();
        map.put("1", testStrategyDto.getMAmount());
        map.put("2", testStrategyDto.getQAmount());
        map.put("3", testStrategyDto.getFAmount());

        //query questions by types
        final int typeAmount = 3;
        List<Question> all = new ArrayList<>();
        for (int tid = 1; tid <= typeAmount; tid++) {
            int amount = map.get(Integer.toString(tid));
            if (amount != 0) {
                List<Question> list = questionRepository.RandomQuestionsByTypeAndCourse(tid, cid, amount);
                all.addAll(list);
            }
        }

        //download paper
        testPaperService.downloadpaper(all, title, response);
    }

    @GetMapping
    @Log("查询试卷管理")
    @ApiOperation("查询试卷管理")
    @PreAuthorize("@el.check('testPaper:list')")
    public ResponseEntity<Object> query(TestPaperQueryCriteria criteria, Pageable pageable) {
        if (!ObjectUtils.isEmpty(criteria.getCid())) {
            criteria.getCids().add(criteria.getCid());
        }
        if (!ObjectUtils.isEmpty(criteria.getTsid())) {
            criteria.getTsids().add(criteria.getTsid());
        }
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(testPaperService.queryAll(criteria, pageable), HttpStatus.OK);
        //return new ResponseEntity<>(testPaperService.queryAll(criteria,pageable),HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping
    @Log("新增试卷管理")
    @ApiOperation("新增试卷管理")
    @PreAuthorize("@el.check('testPaper:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TestPaper resources) {
        return new ResponseEntity<>(testPaperService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改试卷管理")
    @ApiOperation("修改试卷管理")
    @PreAuthorize("@el.check('testPaper:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TestPaper resources) {
        testPaperService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除试卷管理")
    @ApiOperation("删除试卷管理")
    @PreAuthorize("@el.check('testPaper:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        testPaperService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}