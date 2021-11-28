package me.zhengjie.module.business.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestStrategySmallDto implements Serializable {

    private Integer id;

    private String name;

    private Integer mAmount;

    private Integer qAmount;

    private Integer fAmount;

}
