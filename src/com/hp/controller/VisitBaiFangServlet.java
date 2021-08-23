package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.bean.Visit;
import com.hp.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "VisitBaiFangServlet",urlPatterns = "/VisitBaiFangServlet")
public class VisitBaiFangServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        System.out.println(" =========================");
        //2.收参数
       String user_id= req.getParameter("user_id");
        System.out.println("user_id = " + user_id);
        String cust_id=req.getParameter("id");
        System.out.println("cust_id = " + cust_id);
        String visit_time=req.getParameter("visit_time");
        System.out.println("visit_time = " + visit_time);
        String visit_desc=req.getParameter("visit_desc");
        System.out.println("visit_desc = " + visit_desc);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String create_time=simpleDateFormat.format(new Date());


        Visit visit=new Visit();
        visit.setUser_id(Integer.parseInt(user_id));
        visit.setCust_id(Integer.parseInt(cust_id));
        visit.setVisit_desc(visit_desc);
        visit.setVisit_time(visit_time);
        visit.setCreate_time(create_time);

        //调用 service 层
        VisitService visitService=new VisitService();
       Map map=visitService.insertVisit(visit);
        System.out.println("map = " + map);
        //4.把map 变成json
        String s = JSONObject.toJSONString(map);
        System.out.println("s = " + s);
        //5.使用 流输出
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();

    }
}
