package com.cav.clairvoyance.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cav.clairvoyance.domain.Result;
import com.cav.clairvoyance.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
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

    @PostMapping(value = "/upload") //
    public Result upload(MultipartFile file) throws Exception {
        return fileService.upload(file);
    }

    @PostMapping(value = "/bachAnalyse")
    public void analyseFile(HttpServletResponse response) throws IOException {
        // 获得目录绝对路径
        String filePath = new File("files").getAbsolutePath();
        System.out.println(filePath);
        // 得到目录
        File file = new File(filePath);
        // 得到目录下的所有东西（包括文件和目录）
        File[] tempList = file.listFiles();
        //得到刚才上传的文件
        String targetFile = tempList[tempList.length-1].toString();

        //拼接好cmd命令
        String cmdStr = "slither --detect ICfgReentrancy "+targetFile;
        //声明出报告存放路径
        String reportsPath = new File("reports").getAbsolutePath();
        File reportsDir = new File(reportsPath);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs(); //造出一个文件夹，用来存放检测报告
        }
        //声明出报告文件，等待写入
        File reportFile = new File(reportsPath + "/detectionReport.txt");
        //声明一个文件写入者
        FileWriter writer = null;
        try {
            if (!reportFile.exists()) {
                reportFile.createNewFile();// 创建目标文件
            }
            //得到文件写入者
            writer = new FileWriter(reportFile, true);
            //得到命令行进程
            Process process = Runtime.getRuntime().exec(cmdStr);
            //得到命令行进程的输入流
            InputStream is = process.getInputStream();
            //得到进程输入流读者
            InputStreamReader isr = new InputStreamReader(is, "gbk");
            //得到输入流读者的缓冲读者
            BufferedReader br = new BufferedReader(isr);
            //缓冲读者按行读取内容
            String content = br.readLine();
            while (content != null) {
                //文件写者去append内容
                writer.append(content+"\n");
                System.out.println(content);
                content = br.readLine();
            }
            download(reportFile, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //文件读者关闭
            writer.close();
        }
        System.out.println("mamamfmmamammamammamamamam");

    }

    private void download(File file, HttpServletResponse response) throws IOException{

        InputStream ins = new FileInputStream(file);
        /* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
        response.setContentType("multipart/form-data");
        /* 设置文件头：最后一个参数是设置下载文件名 */
        response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        try{
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while((len = ins.read(b)) > 0){
                os.write(b,0,len);
            }
            os.flush();
            os.close();
            ins.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
