package util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DBUtils {
    private static String driver;
    private static String url;
    private static String name;
    private static String pass;

    static {
        InputStream is = DBUtils.class.getResourceAsStream("/jdbc.properties");
        Properties properties=new Properties();
        try {
            properties.load(is);
            //开始读取数据
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            name=properties.getProperty("username");
            pass=properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建连接数据库对象
     * @return
     */
    public static synchronized Connection getCon() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 统一关闭资源的方法
     * @param rs
     * @param st
     * @param conn
     */
    public static void getClose(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if(st!=null){
                st.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一增，删，改的专门的方法
     * @param sql
     * @param objects
     * @return
     */
    public static int execUpdate(String sql,Object...objects){
        Connection conn=null;
        PreparedStatement ps=null;
        int index=-1;
        try {
            conn=getCon();
            //sql=insert into bb(username)value(?);
            //Object [] obj={"ccc"};
            ps= conn.prepareStatement(sql);
            if(Objects.nonNull(objects)){
                for (int i = 0; i < objects.length; i++) {
                    ps.setObject(i+1,objects[i]);
                }
            }
            index= ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getClose(null,ps,conn);
        }
        return index;
    }

    /**
     * 封装通用的单个数据查询方法
     * @param t
     * @param sql
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> T querySingle(Class<T> t,String sql,Object...objects){
        List<Map<String,Object>> list= queyrListMap(sql,objects);
        if(list.size()>0){
            Map<String,Object> map= list.get(0);
            return mapToBeans(map,t);
        }
       return null;
    }
    /**
     * 封装通用的多条数据查询方法
     */
    public static <T> List<T> queryList(Class<T> t,String sql,Object...objects){
        List<T> list=new ArrayList<>();
        List<Map<String,Object>> lists= queyrListMap(sql,objects);
        /*lists.forEach(o->{
            T m=mapToBeans(o,t);
            list.add(m);
        });*/
        for (Map<String, Object> stringObjectMap : lists) {
            T m=mapToBeans(stringObjectMap,t);
            list.add(m);
        }
       return list;
    }

    /**
     * 把Map集合转换成指定的单个数据类
     * @param map
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T mapToBeans(Map<String,Object> map,Class<T> t){
       T obj=null;
        try {
           //创建对象
            obj=t.newInstance(); //User u=new User();
            //得到当前指定类对象的所有属性
             Field[] fields= t.getDeclaredFields();
            //循环得出所有属性
            for (Field field : fields) {
                //System.out.println("----"+field.getName());
                //得到所有属性的名字
                String fieldname=field.getName();
                //发现属性的名字和map集合中的key的名字一一对应的
                //那么我们可以把属性名做为key来读值
                Object value=map.get(fieldname);
                //System.out.println("KEY:=="+fieldname+",VALUE:--"+value);
                if(Objects.nonNull(value)) {
                    //设置属性可以访问性
                    field.setAccessible(true);
                    field.set(obj, value);//u.setId(1001);
                }
            }
            /*map.forEach((k, v) -> {
                try {
                    // 根据Field名称获取字段对象
                    Field field = t.getDeclaredField(k);
                    // 设置字段的可访问性
                    field.setAccessible(true);
                    // 为字段设置值
                    if(Objects.nonNull(v)) {
                        field.set(obj, v);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });*/
            return  obj;
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 1,专门做查询
     * 2.查询结果可以得出一个集合
     * 3.查询结果可以得出一个对象
     * @param sql
     * @param objects
     * @return
     */
    //select * from user where id=1;  返回是一个User对象
    //select * from user  返回是一个List<User>集合对象
    //select id,username,password from user where id=1
    public static List<Map<String,Object>> queyrListMap(String sql,Object...objects){
        List<Map<String,Object>> list=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=getCon();
            ps=conn.prepareStatement(sql);
            if(Objects.nonNull(objects)){
                for(int i=0;i<objects.length;i++){
                    ps.setObject(i+1,objects[i]);
                }
            }
            rs=ps.executeQuery();
            //得到rs结果集的源数据
            ResultSetMetaData rsmd=rs.getMetaData();
            //得到源数据的大小
            int count=rsmd.getColumnCount();
            //System.out.println(count);
            while(rs.next()){
                //创建一个map集合用来保存每一行记录的数据
                Map<String,Object> map=new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    String key= rsmd.getColumnName(i);
                    String label=rsmd.getColumnLabel(i);
                    Object value = rs.getObject(label);
                    if(Objects.nonNull(value)) {
                        map.put(key, value);
                    }
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getClose(rs,ps,conn);
        }
        return  list;
    }


    /**
     * 查询总记录数
     * @param sql
     * @param objects
     * @return
     */
    public static int queryConut(String sql,Object...objects){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=getCon();
            ps=conn.prepareStatement(sql);
            if(Objects.nonNull(objects)){
                for(int i=0;i<objects.length;i++){
                    ps.setObject(i+1,objects[i]);
                }
            }
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getClose(rs,ps,conn);
        }
        return  0;
    }

}
