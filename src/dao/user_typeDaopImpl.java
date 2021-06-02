package dao;

import basedao.IBaseDao;
import entity.user_type;
import util.DBUtils;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-06-01 19:18
 */
public class user_typeDaopImpl implements IBaseDao <user_type>{

    @Override
    public boolean add(user_type user_type) {
        return false;
    }

    @Override
    public boolean del(int id) {
        return false;
    }

    @Override
    public boolean update(user_type user_type) {
        return false;
    }

    @Override
    public user_type findByObjectId(int id) {
        String sql = "select * from user_type where TypeId=?";
        return DBUtils.querySingle(user_type.class,sql,id);
    }

    @Override
    public List<user_type> findByObjectAllInfo() {
        return null;
    }

    @Override
    public List<user_type> findByObjectWhereInfo(user_type user_type) {
        return null;
    }

    @Override
    public int findByObjectCount() {
        return 0;
    }

    @Override
    public List<user_type> findByObjectPage(int pageNo, int pageSize) {
        return null;
    }
}
