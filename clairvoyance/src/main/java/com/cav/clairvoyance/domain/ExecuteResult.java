package com.cav.clairvoyance.domain;
/**
 * ExecuteResult.java
 */
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExecuteResult {
    private int exitCode;
    private String executeOut;

    public ExecuteResult(int exitCode, String executeOut) {
        this.exitCode = exitCode;
        this.executeOut = executeOut;
    }
}