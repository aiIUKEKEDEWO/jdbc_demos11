package entity;

/**
 * @author shkstart
 * @create 2021-06-01 19:09
 */
public class user_type {
    private int typeId;
    private String names;

    @Override
    public String toString() {
        return "user_type{" +
                "typeId=" + typeId +
                ", names='" + names + '\'' +
                '}';
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
