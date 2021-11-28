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
package me.zhengjie.module.business.service.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author KunYi
* @date 2021-11-26
**/
@Data
public class TestPaperQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private Integer tsid;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private Integer cid;

    /** classes */
    @Query(propName = "id", type = Query.Type.IN, joinName = "cids")
    private Set<Integer> cids = new HashSet<>();

    /** test strategies */
    @Query(propName = "id", type = Query.Type.IN, joinName = "tsids")
    private Set<Integer> tsids = new HashSet<>();
}