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
package me.zhengjie.module.system.service.impl;

import me.zhengjie.module.system.domain.SysQuestionType;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.module.system.repository.SysQuestionTypeRepository;
import me.zhengjie.module.system.service.SysQuestionTypeService;
import me.zhengjie.module.system.service.dto.SysQuestionTypeDto;
import me.zhengjie.module.system.service.dto.SysQuestionTypeQueryCriteria;
import me.zhengjie.module.system.service.mapstruct.SysQuestionTypeMapper;
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
* @date 2021-11-05
**/
@Service
@RequiredArgsConstructor
public class SysQuestionTypeServiceImpl implements SysQuestionTypeService {

    private final SysQuestionTypeRepository sysQuestionTypeRepository;
    private final SysQuestionTypeMapper sysQuestionTypeMapper;

    @Override
    public Map<String,Object> queryAll(SysQuestionTypeQueryCriteria criteria, Pageable pageable){
        Page<SysQuestionType> page = sysQuestionTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysQuestionTypeMapper::toDto));
    }

    @Override
    public List<SysQuestionTypeDto> queryAll(SysQuestionTypeQueryCriteria criteria){
        return sysQuestionTypeMapper.toDto(sysQuestionTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysQuestionTypeDto findById(Long id) {
        SysQuestionType sysQuestionType = sysQuestionTypeRepository.findById(id).orElseGet(SysQuestionType::new);
        ValidationUtil.isNull(sysQuestionType.getId(),"SysQuestionType","id",id);
        return sysQuestionTypeMapper.toDto(sysQuestionType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysQuestionTypeDto create(SysQuestionType resources) {
        return sysQuestionTypeMapper.toDto(sysQuestionTypeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysQuestionType resources) {
        SysQuestionType sysQuestionType = sysQuestionTypeRepository.findById(resources.getId()).orElseGet(SysQuestionType::new);
        ValidationUtil.isNull( sysQuestionType.getId(),"SysQuestionType","id",resources.getId());
        sysQuestionType.copy(resources);
        sysQuestionTypeRepository.save(sysQuestionType);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sysQuestionTypeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysQuestionTypeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysQuestionTypeDto sysQuestionType : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("questionType", sysQuestionType.getType());
            map.put("status", sysQuestionType.getEnabled());
            map.put("create_date", sysQuestionType.getCreateDate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}