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

import me.zhengjie.module.business.domain.Question;
import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.module.system.repository.SysClassRepository;
import me.zhengjie.module.system.service.dto.SysClassDto;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.module.business.repository.QuestionRepository;
import me.zhengjie.module.business.service.QuestionService;
import me.zhengjie.module.business.service.dto.QuestionDto;
import me.zhengjie.module.business.service.dto.QuestionQueryCriteria;
import me.zhengjie.module.business.service.mapstruct.QuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.ObjectUtils;

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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public Map<String,Object> queryAll(QuestionQueryCriteria criteria, Pageable pageable){
       Page<Question> page = questionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
       return PageUtil.toPage(page.map(questionMapper::toDto));
    }

    @Override
    public List<QuestionDto> queryAll(QuestionQueryCriteria criteria){
        return questionMapper.toDto(questionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QuestionDto findById(Long id) {
        Question question = questionRepository.findById(id).orElseGet(Question::new);
        ValidationUtil.isNull(question.getId(),"Question","id",id);
        return questionMapper.toDto(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Question resources) {
        questionRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Question resources) {
        Question question = questionRepository.findById(resources.getId()).orElseGet(Question::new);
        ValidationUtil.isNull( question.getId(),"Question","id",resources.getId());
        question.copy(resources);
        questionRepository.save(question);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            questionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QuestionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuestionDto question : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("questionContent", question.getContent());
            map.put("typeId", question.getTid());
//            map.put("classId", question.getCid());
            map.put("course", question.getCids().getName());
            map.put("status", question.getEnabled());
            map.put("answer", question.getAnswer());
            map.put("userName", question.getCreateBy());
            map.put("createDate", question.getCreateTime());
            map.put("updateDate", question.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}