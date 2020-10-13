package com.cav.clairvoyance.service.impl;

import com.cav.clairvoyance.domain.ExecuteResult;
import com.cav.clairvoyance.service.AnalyseService;
import com.cav.clairvoyance.utils.LocalCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Value("${cmd.str}")
    String cmdStr;

    @Value("${cmd.timeout}")
    long timeout;


    @Autowired
    private LocalCommandExecutor localCommandExecutor;

    @Override
    public String analyseFile(String filePath) throws IOException, InterruptedException {
        ExecuteResult executeResult = localCommandExecutor.executeCommand(cmdStr + " " + filePath, timeout);
        if (executeResult.getExitCode() == 0) {
            return executeResult.getExecuteOut();
        }
        return "Process finished with exit code " + executeResult.getExitCode()+"\nCompilation failure or other problems, please check the code.";

//        StringBuffer sb = new StringBuffer();
//        Process process = Runtime.getRuntime().exec(cmdStr + " " + filePath);
//        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//        BufferedReader br = new BufferedReader(isr);
//        String content = br.readLine();
//        while (content != null) {
//            sb.append(content + "\n");
//            //System.out.println(content);
//            content = br.readLine();
//        }
//        br.close();
//        process.waitFor();
//        return sb.toString();
    }
}
