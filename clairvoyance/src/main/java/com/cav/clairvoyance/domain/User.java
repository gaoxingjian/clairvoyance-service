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

    private String salt;

    private String password;

    private String email;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
