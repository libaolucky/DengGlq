package com.hp.dao;

import com.hp.bean.Customer;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao {
    //1.带参数的全查（2个表的）
    //t_user  t_customer           where 后面要带参数的
    //select * from t_customer c join t_user u on c.user_id=u.id

    //思考1： 问题： 多个表联查  ，返回值是什么？？？？  Map
    //思考2 ：问题：多个联查，后面要带参数 肯定是 多个表中的 属性都有，那么用什么传参？ Map
    public List<Map> selectAllByParam(Map map){
        List lists = new ArrayList();
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");

        String cname= (String) map.get("cname");
        String lookTime= (String) map.get("lookTime");
        String sex= (String) map.get("sex");
        String uname= (String) map.get("uname");
        String phoneNumber= (String) map.get("phoneNumber");


        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select c.* ,t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";
        //select * from c.*,t.id as user_id ,t.useranme as username, t.password as password, t.real_name as real_name, t.type as type from  t_customer c  join t_user  t  on c.user_id  = t.id where 1=1 and c.cust_birth='1977-11-15' and t.is_del=1;

        sql = sql + " and t.is_del=1   ";
        if (null!=cname&&cname.length()>0){
            sql = sql + " and c.cust_name   like  '%"+cname+"%'   ";
        }
        if (null!=lookTime&&lookTime.length()>0){
            sql = sql + " and c.modify_time   =  "+lookTime+"   ";
        }
        if (null!=phoneNumber&&phoneNumber.length()>0){
            sql = sql + " and c.cust_phone   =  "+phoneNumber+"   ";
        }
        if (null!=sex&&sex.length()>0){
            sql = sql + " and c.cust_sex   =  "+sex+"   ";
        }
        if (null!=uname&&uname.length()>0){
            sql = sql + " and t.username   like  '%"+uname+"%'   ";
        }

        sql = sql + " limit  ? ,  ?";
        System.out.println(" dao de limit sql = " + sql);

        //3.使用连接对象 获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));//因为第一个需要?求出来
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));

            //4.执行sql
            rs = ps.executeQuery();
            while(rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("cust_company",rs.getString("cust_company"));
                dataMap.put("cust_position",rs.getString("cust_position"));
                dataMap.put("cust_phone",rs.getString("cust_phone"));
                dataMap.put("cust_birth",rs.getString("cust_birth"));
                dataMap.put("cust_sex",rs.getInt("cust_sex"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("modify_time",rs.getString("modify_time"));
                dataMap.put("username",rs.getString("username"));
                dataMap.put("password",rs.getString("password"));
                dataMap.put("real_name",rs.getString("real_name"));
                dataMap.put("type",rs.getInt("type"));

                lists.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }


    //2.带参数的查总条数
    //select count(*) from t_customer c join t_user u on c.user_id=u.id
    public int selectAllByParamCount(Map map){
        int total=0;
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String  sql = "select count(*) as total from t_customer c join t_user u on c.user_id=u.id where 1=1";
        //3.预编译
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            //4.执行
            rs=ps.executeQuery();
            if(rs.next()){
                total=rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    //增加
    public int insertCustomer(Customer customer){
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String  sql = "insert into t_customer values (null,?,?,?,?,?,?,?,?,?,?)";
        //预编译
        PreparedStatement ps=null;
        int i=0;
        try {
            ps=connection.prepareStatement(sql);
            ps.setString(1,customer.getCust_name());
            ps.setString(2,customer.getCust_company());
            ps.setString(3,customer.getCust_position());
            ps.setString(4,customer.getCust_phone());
            ps.setString(5,customer.getCust_birth());
            ps.setInt(6,customer.getCust_sex());
            ps.setString(7,customer.getCust_desc());
            ps.setInt(8,customer.getUser_id());
            ps.setString(9,customer.getCreate_time());
            ps.setString(10,customer.getModify_time());
            //4.执行预编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;


    }
    
    //根据 主键id  来删除
    public int deleteByCustomerId(Integer id) {
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String sql = "delete from t_customer where id=?";
        //预编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            //执行
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;

        }
    }


    //测试
    public static void main(String[] args) {
        CustomerDao customerDao=new CustomerDao();
//        Customer customer=new Customer();
//        customer.setCust_name("小黑客");
//        customer.setCust_company("腾讯");
//        customer.setCust_position("java开发");
//        customer.setCust_phone("12345362765");
//        customer.setCust_birth("2012-09-12");
//        customer.setCust_sex(2);
//        customer.setCust_desc("的记录卡啦啦啦");
//        customer.setUser_id(7);
//        customer.setCreate_time("2012-09-12");
//        customer.setModify_time("2012-12-12");
//        int a=customerDao.insertCustomer(customer);
//        System.out.println("a = " + a);
            int a=customerDao.deleteByCustomerId(24);
            System.out.println("a = " + a);
        
        
//        Map paramMap=new HashMap();
//        paramMap.put("page","1");
//        paramMap.put("limit","5");
//        paramMap.put("cname","李小花");

//
     
//        List<Map> maps = customerDao.selectAllByParam(paramMap);
//        System.out.println("maps = " + maps);
//        System.out.println("maps.size() = " + maps.size());
//       int i=customerDao.selectAllByParamCount(paramMap);
//        System.out.println("i = " + i);


    }

}
