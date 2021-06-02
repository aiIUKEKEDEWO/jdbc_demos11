package basedao;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-06-01 19:17
 */
public interface IBaseDao <T>{
    boolean add(T t);

    boolean del(int id);

    boolean update(T t);

    T findByObjectId(int id);

    List<T> findByObjectAllInfo();

    List<T> findByObjectWhereInfo(T t);

    int findByObjectCount();

    List<T> findByObjectPage(int pageNo, int pageSize);
}
