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
package me.zhengjie.module.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.module.system.domain.SysClass;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author KunYi
* @date 2021-11-26
**/
@Entity
@Data
@Table(name="business_test_paper")
public class TestPaper extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "paper name")
    private String name;

//    @Column(name = "tsid",nullable = false)
//    @NotNull
//    @ApiModelProperty(value = "test strategy id")
//    private Integer tsid;

    @OneToOne
    @JoinColumn(name = "tsid")
    @ApiModelProperty(value = "test strategy info")
    private TestStrategy tsids;

//    @Column(name = "cid",nullable = false)
//    @NotNull
//    @ApiModelProperty(value = "course id")
//    private Integer cid;

    @OneToOne
    @JoinColumn(name = "cid")
    @ApiModelProperty(value = "course info")
    private SysClass cids;

    @Column(name = "description")
    @ApiModelProperty(value = "description")
    private String description;

//    @Column(name = "create_by")
//    @ApiModelProperty(value = "created user")
//    private String createBy;
//
//    @Column(name = "update_by")
//    @ApiModelProperty(value = "updated user")
//    private String updateBy;
//
//    @Column(name = "create_time")
//    @ApiModelProperty(value = "createDate")
//    private Timestamp createTime;
//
//    @Column(name = "update_time")
//    @ApiModelProperty(value = "updateDate")
//    private Timestamp updateTime;

    public void copy(TestPaper source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}