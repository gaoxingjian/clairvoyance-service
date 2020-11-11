package com.cav.clairvoyance.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable {

    private String id;

    private String name;

    private String description;

    private Integer status;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;


}
