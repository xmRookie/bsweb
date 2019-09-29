package com.example.demo.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.lang.Runtime;

@Controller
public class ScanServlet extends HttpServlet {
    /* fft数据个数为4096 */
    private float num[] = new float[4096];

    public void init() {
        /* filePath的路径应与底层代码保存fft数据的路径一致*/
        String filePath = "/tmp/data.dat";
        try {
            File file = new File(filePath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                int count = 0;
                while ((lineTxt = br.readLine()) != null) {
                    //System.out.println(lineTxt);
                    num[count] = Float.parseFloat(lineTxt);
                    count += 1;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }

    }

    @RequestMapping(value = "/Servlet")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String str = req.getParameter("input");
        double freq = Float.parseFloat(str);
        /*调用底层进程返回状态值*/
        int error_status ;
        System.out.println(freq);

        /*调用底层可执行程序，cmd的参数1为可执行程序路径，参数2为传入的中心频率值*/
        Process p = null;
        String[] cmd = {"/usr/local/share/starnet/scan/src",str};
        try{
            /*获取root权限*/
            p = Runtime.getRuntime().exec("su");
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (Exception e){
            e.printStackTrace();
        }
        /*获取底层进程返回值，底层程序返回0表示正常，其他值均不正常*/
        error_status = p.exitValue();


        System.out.println(error_status);
        System.out.println("rx end");

        if( error_status == 0){
            //设置响应内容类型
            init();
            resp.setContentType("text/html");
            //设置逻辑实现
            PrintWriter out = resp.getWriter();
            out.write(error_status+",");
            for(int i = 0; i < num.length; i++){
                out.write(num[i] + ",");
            }
            System.out.println("write data end");
            out.close();
        }else{
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.write(error_status + ",");
        }

    }

    @Override
    public void destroy() {

        super.destroy();
    }
}