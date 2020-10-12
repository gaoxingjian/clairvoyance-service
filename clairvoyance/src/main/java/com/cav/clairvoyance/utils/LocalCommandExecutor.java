package com.cav.clairvoyance.utils;

import com.cav.clairvoyance.domain.ExecuteResult;

/**
 * LocalCommandExecutor.java
 */
public interface LocalCommandExecutor {
    ExecuteResult executeCommand(String command, long timeout);
}