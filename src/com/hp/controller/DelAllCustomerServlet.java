package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DelAllCustomerServlet",urlPatterns = "/DelAllCustomerServlet")
public class DelAllCustomerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        //重点：  servlet 收取数组的参数
        String [] parameterValues=req.getParameterValues("ids[]");
        System.out.println("parameterValues = " + parameterValues);
        CustomerService service=new CustomerService();
        for (String idStr : parameterValues) {
            //遍历获取每一个id
            System.out.println("idStr = " + idStr);
           int i= service.deleteByCustomerId(Integer.parseInt(idStr));
            System.out.println("i = " + i);
        }
        Map codeMap=new HashMap<>();
        codeMap.put("code",0);
        codeMap.put("msg","ok");

        String s = JSONObject.toJSONString(codeMap);
        System.out.println("s = " + s);
        //5.使用 流输出
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
