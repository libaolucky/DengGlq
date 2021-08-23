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

@WebServlet(name = "CustomerSelectServlet",urlPatterns = "/CustomerSelectServlet")
public class CustomerSelectAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //2.接收 2个参数  page limit
        String page=req.getParameter("page");
        String limit=req.getParameter("limit");
        System.out.println("page = " + page);
        System.out.println("limit = " + limit);

        String cname = req.getParameter("cname");
        String lookTime = req.getParameter("lookTime");
        String sex = req.getParameter("sex");
        String uname=req.getParameter("uname");
        String phoneNumber=req.getParameter("phoneNumber");

        System.out.println("cname = " + cname);
        System.out.println("lookTime = " + lookTime);
        System.out.println("uname = " + uname);
        System.out.println("phoneNumber = " + phoneNumber);
        System.out.println("sex = " + sex);

        Map paramMap=new HashMap<>();
        paramMap.put("page",page);
        paramMap.put("limit",limit);

        paramMap.put("cname",cname);
        paramMap.put("lookTime",lookTime);
        paramMap.put("uname",uname);
        paramMap.put("phoneNumber",phoneNumber);
        paramMap.put("sex",sex);

        CustomerService customerService=new CustomerService();
       Map map= customerService.selectByParam(paramMap);

        PrintWriter writer = resp.getWriter();
        String jsonString = JSONObject.toJSONString(map);
        writer.print(jsonString);
        writer.close();


    }
}
