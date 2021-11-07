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
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.module.system.domain.SysClass;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Objects;

/**
* @website https://el-admin.vip
* @description /
* @author KunYi
* @date 2021-11-05
**/
@Entity
@Getter
@Setter
@Data
@Table(name="business_question")
public class Question extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "content",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "questionContent")
    private String content;

    @Column(name = "tid")
    @ApiModelProperty(value = "typeId")
    private Integer tid;

//    @Column(name = "cid")
//    @ApiModelProperty(value = "classId")
//    private Long cid;

    @Column(name = "enabled",nullable = false)
    @NotNull
    @ApiModelProperty(value = "status")
    private Boolean enabled;

    @Column(name = "answer")
    @ApiModelProperty(value = "answer")
    private String answer;

    @OneToOne
    @JoinColumn(name = "cid")
    @ApiModelProperty(value = "course info")
    private SysClass cids;


//    @Column(name = "create_by")
//    @CreationTimestamp
//    @ApiModelProperty(value = "userName")
//    private String createBy;
//
//    @Column(name = "create_time")
//    @ApiModelProperty(value = "createDate")
//    private Timestamp createTime;
//
//    @Column(name = "update_time")
//    @ApiModelProperty(value = "updateDate")
//    private Timestamp updateTime;

    public void copy(Question source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(content, question.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}