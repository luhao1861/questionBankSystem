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
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author KunYi
* @date 2021-11-26
**/
@Entity
@Data
@Table(name="business_test_strategy")
public class TestStrategy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "name")
    private String name;

    @Column(name = "m_amount")
    @ApiModelProperty(value = "multiple choices amount")
    private Integer mAmount;

    @Column(name = "q_amount")
    @ApiModelProperty(value = "q&a amount")
    private Integer qAmount;

    @Column(name = "f_amount")
    @ApiModelProperty(value = "fill in blank amount")
    private Integer fAmount;

    @Column(name = "enabled",nullable = false)
    @NotNull
    @ApiModelProperty(value = "status")
    private Boolean enabled;

    public void copy(TestStrategy source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}