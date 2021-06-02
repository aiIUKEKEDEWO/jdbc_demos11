package test;

import dao.user_typeDaopImpl;
import dao.usersDaoImpl;
import entity.user_type;
import entity.users;

import java.util.List;
import java.util.Scanner;

/**
 * @author shkstart
 * @create 2021-06-01 19:09
 */
public class Test {
    public static void main(String[] args) {
        //1.用户登录

        //1.用户登录//1.用户登录//1.用户登录//1.用户登录//1.用户登录//1.用户登录
        System.out.println(1111);
        //1.用户登录
        /*Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        System.out.println("请输入密码：");
        String password = sc.next();
        users u = new users();
        u.setName(username);
        u.setPassword(password);
        usersDaoImpl u1 = new usersDaoImpl();
        List<users> byObjectAllInfo1 = u1.findByObjectAllInfo1(u);
        if (byObjectAllInfo1.size() != 0) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败！");
        }*/
        //2.查看所有用户信息功能
        /*usersDaoImpl u2 = new usersDaoImpl();
        List<users> byObjectAllInfo = u2.findByObjectAllInfo();
        byObjectAllInfo.forEach(System.out::println);*/
        //3.用户信息分页功能
        usersDaoImpl u2 = new usersDaoImpl();
        List<users> byObjectPage = u2.findByObjectPage(1, 2);
        byObjectPage.forEach(System.out::println);
        //4.修改密码功能
        /*users u1 = new users();
        usersDaoImpl u2 = new usersDaoImpl();
        u1.setId(1);
        u1.setPassword("999");
        boolean flang = u2.update(u1);
        if(flang){
            System.out.println("修改成功");
        }else{
            System.out.println("NO");
        }*/
        //5.根据id查询相关用户信息功能
        /*user_type u1 = new user_type();
        u1.setTypeId(2);
        user_typeDaopImpl u2 = new user_typeDaopImpl();
        user_type byObjectId = u2.findByObjectId(2);
        System.out.println(byObjectId.toString());*/
    }
}