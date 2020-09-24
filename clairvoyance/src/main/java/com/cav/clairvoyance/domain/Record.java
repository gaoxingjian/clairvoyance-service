package com.cav.clairvoyance.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//@Table(name = "record")
public class Record implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private String srcCode;
    private String hash;
    private String detectResult;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDetectResult() {
        return detectResult;
    }

    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", srcCode='" + srcCode + '\'' +
                ", hash='" + hash + '\'' +
                ", detectResult='" + detectResult + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
