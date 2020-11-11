package com.cav.clairvoyance.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class User implements Serializable {

    @Id
    private String id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer deleted;

    private static final long serialVersionUID = 1L;

}
