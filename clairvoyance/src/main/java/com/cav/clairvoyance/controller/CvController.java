package com.cav.clairvoyance.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cav.clairvoyance.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
public class CvController {
    @Autowired
    FileService fileService;
    @RequestMapping(value = "/analyse", method = RequestMethod.POST)
    public void analyse(@RequestBody String data, HttpServletResponse response) throws IOException {
        // 查看用户输入的代码
        System.out.println(data);
        // 把用户输入的代码保存到e:\reentrancy.sol文件中
        try {
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("e:\\reentrancy.sol"), "utf-8"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/mingliang/files/reentrancy.sol"), "utf-8"));
            out.write(data);
            out.close();
        } catch (IOException e) {

        }
        //拼接检测命令
        //String cmdStr = "slither --detect ICfgReentrancy e:\\reentrancy.sol";
        String cmdStr = "slither --detect ICfgReentrancy /home/mingliang/files/reentrancy.sol";
        // 查看即将执行的cmd 命令
        System.out.println(cmdStr);

        //开始检测
        StringBuffer sb = new StringBuffer();
        try {
            Process process = Runtime.getRuntime().exec(cmdStr);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                sb.append(content+"\n");
                System.out.println(content);
                content = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> ans = new HashMap<>();
        ans.put("results", sb.toString());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        writer.print(JSONObject.toJSONString(ans));
        //return "";
    }

    /**
     * 文件上传
     */

    @PostMapping(value = "/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file) throws Exception {
        return fileService.upload(file);
    }

}
