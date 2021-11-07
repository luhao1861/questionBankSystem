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

import cn.hutool.core.util.ObjectUtil;
import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.module.system.repository.SysClassRepository;
import me.zhengjie.module.system.service.SysClassService;
import me.zhengjie.module.system.service.dto.SysClassDto;
import me.zhengjie.module.system.service.dto.SysClassQueryCriteria;
import me.zhengjie.module.system.service.mapstruct.SysClassMapper;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
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
public class SysClassServiceImpl implements SysClassService {

    private final SysClassRepository sysClassRepository;
    private final SysClassMapper sysClassMapper;

    @Override
    public Map<String,Object> queryAll(SysClassQueryCriteria criteria, Pageable pageable){
        Page<SysClass> page = sysClassRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysClassMapper::toDto));
    }

    @Override
    public List<SysClassDto> queryAll(SysClassQueryCriteria criteria){
        //Sort sort = Sort.by(Sort.Direction.ASC, "sysClassSort");
        //List<SysClassDto> list = sysClassMapper.toDto(sysClassRepository.findAllEnabled((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        return sysClassMapper.toDto(sysClassRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
//        return list;
    }


    @Override
    @Transactional
    public SysClassDto findById(Long id) {
        SysClass sysClass = sysClassRepository.findById(id).orElseGet(SysClass::new);
        ValidationUtil.isNull(sysClass.getId(),"SysClass","id",id);
        return sysClassMapper.toDto(sysClass);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysClassDto create(SysClass resources) {
        return sysClassMapper.toDto(sysClassRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysClass resources) {
        SysClass sysClass = sysClassRepository.findById(resources.getId()).orElseGet(SysClass::new);
        ValidationUtil.isNull( sysClass.getId(),"SysClass","id",resources.getId());
        sysClass.copy(resources);
        sysClassRepository.save(sysClass);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sysClassRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysClassDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysClassDto sysClass : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("className", sysClass.getName());
            map.put("createDate", sysClass.getCreateDate());
            map.put("updatedate", sysClass.getUpdateDate());
            map.put("enabled", sysClass.getEnabled());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}