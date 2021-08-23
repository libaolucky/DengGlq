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
import java.util.Map;

@WebServlet(name = "DelByIdVisitServlet",urlPatterns = "/DelByIdVisitServlet")
public class DelByIdVisitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
      //2.获取参数
        String id=req.getParameter("id");
        System.out.println("id = " + id);
        //调用 service 层
        VisitService visitService =new VisitService();
        Map map = visitService.VisitdeleteById(Integer.parseInt(id));
        System.out.println("map = " + map);

        String s = JSONObject.toJSONString(map);
        System.out.println("s = " + s);
        //5.使用 流输出
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
