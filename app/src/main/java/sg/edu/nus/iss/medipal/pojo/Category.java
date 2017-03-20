package sg.edu.nus.iss.medipal.pojo;

/**
 * Created by zhiguo on 13/3/17.
 * Description : Define Category class details
 */

public class Category {

    private int id;
    private String category_name;
    private String category_code;
    private String category_des;
    private Boolean remind;

    public Category(int id,String category_name,String category_code,String category_des,boolean remind) {
        this.id=id;
        this.category_name=category_name;
        this.category_code=category_code;
        this.category_des=category_des;
        this.remind=remind;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategory_des() {
        return category_des;
    }

    public void setCategory_des(String category_des) {
        this.category_des = category_des;
    }

    public String toString () {
        return (getCategory_name());
    }
}
