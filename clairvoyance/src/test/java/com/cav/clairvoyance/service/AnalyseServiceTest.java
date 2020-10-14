package com.cav.clairvoyance.service;

import com.cav.clairvoyance.utils.LocalCommandExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyseServiceTest {

    @Autowired
    private AnalyseService analyseService;

    @Autowired
    private LocalCommandExecutor localCommandExecutor;

    @Test
    public void analyseFile() throws IOException, InterruptedException {
        // String result = analyseService.analyseFile("/Users/jason/develop/clairvoyance-service/files/6b79c44d37394dc3b6967fe4d977753120201012153629/test/test13.sol");
        String result = analyseService.analyseFile("/Users/jason/files/test2/test2.sol");
        System.out.println("result:"+result);
        //System.out.println("isEmpty?:"+result.isEmpty());
    }

    @Test
    public void analyseFiles() throws IOException, InterruptedException {
        // String cmdStr = "ls";
        File[] files = {
                new File("/Users/jason/files/test/test11.sol"),
                new File("/Users/jason/files/test/test12.sol"),
                new File("/Users/jason/files/reentrancy.sol")
        };
        String result;
        for (File file: files ) {
            //result = analyseService.analyseFile(file.getAbsolutePath());
             // result = localCommandExecutor.executeCommand(cmdStr+" "+file.getAbsolutePath(),3000).toString();
            result = analyseService.analyseFile(file.getAbsolutePath());
            System.out.println("result:"+result);
        }



    }
}