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
import me.zhengjie.module.system.service.dto.SysClassSmallDto;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author KunYi
* @date 2021-11-26
**/
@Data
public class TestPaperDto implements Serializable {

    /** id */
    private Long id;

    /** paper name */
    private String name;

    /** test strategy id */
//    private Integer tsid;
    private TestStrategySmallDto tsids;

    /** course id */
//    private Integer cid;

    /** classes list */
    private SysClassSmallDto cids;

    /** description */
    private String description;

    /** created user */
    private String createBy;

    /** updated user */
    private String updateBy;

    /** createDate */
    private Timestamp createTime;

    /** updateDate */
    private Timestamp updateTime;
}