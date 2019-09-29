package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class MyController {

    @Autowired
    private UserService userservice;


    @RequestMapping(value = "/loginverify")
    public void loginverify(HttpServletRequest req, HttpServletResponse resp, HttpSession session)throws ServletException, IOException{
        String username = req.getParameter("uname");
        String userpasswd = req.getParameter("upasswd");

        //用户信息需要核对数据库
        //System.out.println(username);
        //System.out.println(userpasswd);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userpasswd)) {
            //查找数据库，如果用户不存在，直接警告用户名错误；用户存在则获取数据库对应密码，核对密码
            User user = userservice.getByName(username);
            if(user != null){
                String login_passwd = new String();
                login_passwd = user.getPasswd();
                if (login_passwd.equals(userpasswd)){
                    // 登录成功，就跳转到下一个页面，主页面
                    session.setAttribute("username",username);
                    out.write("true");
                }else {
                    // 登入失败，返回登入页面
                    out.write("false");
                }
            }else{
                out.write("false");
            }
        }
    }

    @RequestMapping("home")
    public String home(){
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /*
    @RequestMapping("scan")
    public String scan(){
        return "scan";
    }

     */

    @RequestMapping(value = "/reset")
    public void reset(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String username = req.getParameter("uname");
        String userpasswd = req.getParameter("upasswd");
        //System.out.println("reset"+username+userpasswd);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userpasswd)){
            //查找数据库，如果用户名不存在，则可以注册，否则不可以注册
            User user = userservice.getByName(username);
            if(user == null){
                //map.put("msg", "用户不存在");
                out.write("none");
            }else{
                userservice.updateUser(username,userpasswd);
                //map.put("msg", "修改密码成功");
                out.write("true");
            }
        }else {
            out.write("false");
        }
    }

    @RequestMapping(value = "/register")
    public void register(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String username = req.getParameter("uname");
        String userpasswd = req.getParameter("upasswd");
        //System.out.println("register"+username+userpasswd);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userpasswd)){
            //查找数据库，如果用户名不存在，则可以注册，否则不可以注册
            User user = userservice.getByName(username);
            if(user == null){
                userservice.insert(username,userpasswd);
                out.write("true");
                //map.put("msg", "注册成功");
            }else{
                out.write("none");
                //map.put("msg", "用户名已经存在");
            }
        }else {
            out.write("false");
        }
    }
}
