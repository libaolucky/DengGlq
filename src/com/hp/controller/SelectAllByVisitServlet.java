package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SelectAllByVisitServlet",urlPatterns = "/SelectAllByVisitServlet")
public class SelectAllByVisitServlet extends HttpServlet {
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


        String user_id = req.getParameter("user_id");
        System.out.println("user_id = " + user_id);
        String cust_id = req.getParameter("cust_id");
        System.out.println("cust_id = " + cust_id);
        String cust_name = req.getParameter("cust_name");
        System.out.println("cust_name = " + cust_name);
        String username=req.getParameter("username");
        String visit_time=req.getParameter("visit_time");

        System.out.println("username = " + username);
        System.out.println("visit_time = " + visit_time);


        Map paramMap=new HashMap<>();
        paramMap.put("page",page);
        paramMap.put("limit",limit);
        paramMap.put("user_id",user_id);
        paramMap.put("cust_id",cust_id);
        paramMap.put("cust_name",cust_name);
        paramMap.put("username",username);
        paramMap.put("visit_time",visit_time);

        VisitService visitService=new VisitService();
        Map map= visitService.selectByParam(paramMap);

        PrintWriter writer = resp.getWriter();
        String jsonString = JSONObject.toJSONString(map);
        writer.print(jsonString);
        writer.close();
    }
}
