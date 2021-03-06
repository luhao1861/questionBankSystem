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
package me.zhengjie.module.system.repository;

import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.module.system.service.dto.SysClassDto;
import me.zhengjie.module.system.service.dto.SysClassQueryCriteria;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.Root;
import java.util.List;

/**
* @website https://el-admin.vip
* @author KunYi
* @date 2021-11-05
**/
public interface SysClassRepository extends JpaRepository<SysClass, Long>, JpaSpecificationExecutor<SysClass> {

    @Query(value= "select c.id, c.name from sys_class c where enabled = 1", nativeQuery = true)
    List<SysClass> findAllEnabled(@Nullable Specification<T> var1);
}