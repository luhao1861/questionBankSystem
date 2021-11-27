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
package me.zhengjie.module.business.service.impl;

import me.zhengjie.module.business.domain.TestStrategy;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.module.business.repository.TestStrategyRepository;
import me.zhengjie.module.business.service.TestStrategyService;
import me.zhengjie.module.business.service.dto.TestStrategyDto;
import me.zhengjie.module.business.service.dto.TestStrategyQueryCriteria;
import me.zhengjie.module.business.service.mapstruct.TestStrategyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author KunYi
* @date 2021-11-26
**/
@Service
@RequiredArgsConstructor
public class TestStrategyServiceImpl implements TestStrategyService {

    private final TestStrategyRepository testStrategyRepository;
    private final TestStrategyMapper testStrategyMapper;

    @Override
    public Map<String,Object> queryAll(TestStrategyQueryCriteria criteria, Pageable pageable){
        Page<TestStrategy> page = testStrategyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testStrategyMapper::toDto));
    }

    @Override
    public List<TestStrategyDto> queryAll(TestStrategyQueryCriteria criteria){
        return testStrategyMapper.toDto(testStrategyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TestStrategyDto findById(Long id) {
        TestStrategy testStrategy = testStrategyRepository.findById(id).orElseGet(TestStrategy::new);
        ValidationUtil.isNull(testStrategy.getId(),"TestStrategy","id",id);
        return testStrategyMapper.toDto(testStrategy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestStrategyDto create(TestStrategy resources) {
        return testStrategyMapper.toDto(testStrategyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestStrategy resources) {
        TestStrategy testStrategy = testStrategyRepository.findById(resources.getId()).orElseGet(TestStrategy::new);
        ValidationUtil.isNull( testStrategy.getId(),"TestStrategy","id",resources.getId());
        testStrategy.copy(resources);
        testStrategyRepository.save(testStrategy);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            testStrategyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TestStrategyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestStrategyDto testStrategy : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("name", testStrategy.getName());
            map.put("multiple choices amount", testStrategy.getMAmount());
            map.put("q&a amount", testStrategy.getQAmount());
            map.put("fill in blank amount", testStrategy.getFAmount());
            map.put("status", testStrategy.getEnabled());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}