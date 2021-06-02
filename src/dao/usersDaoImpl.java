package dao;

import basedao.IBaseDao;
import entity.users;
import util.DBUtils;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-06-01 19:19
 */
public class usersDaoImpl implements IBaseDao <users>{

    @Override
    public boolean add(users users) {
        return false;
    }

    @Override
    public boolean del(int id) {

        return false;
    }

    @Override
    public boolean update(users u) {
        String sql = "update users set password=? where id =?";
        Object [] obj = {u.getPassword(),u.getId()};
        return DBUtils.execUpdate(sql,obj)>0;
    }

    @Override
    public users findByObjectId(int id) {
        return null;
    }

    @Override
    public List<users> findByObjectAllInfo() {
        String sql = "select * from users";
        return DBUtils.queryList(users.class,sql);
    }
    public List<users> findByObjectAllInfo1(users u) {
        String sql = "select * from users where name=? and password=?";
        Object [] obj = {u.getName(),u.getPassword()};
        return DBUtils.queryList(users.class,sql,obj);
    }

    @Override
    public List<users> findByObjectWhereInfo(users users) {
        return null;
    }

    @Override
    public int findByObjectCount() {
        return 0;
    }

    @Override
    public List<users> findByObjectPage(int pageNo, int pageSize) {
        String sql = "select * from users limit ?,?";
        Object [] obj = {(pageNo-1)*pageSize,pageSize};
        return DBUtils.queryList(users.class,sql,obj);
    }
}
