package nettydemo;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.data.annotation.Id;

/**
 * @author vova
 * @version Create in 上午1:27 2017/12/21
 */

@TableName("user")
public class User {
    @Id
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
