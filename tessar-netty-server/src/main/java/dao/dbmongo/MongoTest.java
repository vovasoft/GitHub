package dao.dbmongo;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 16:25 2017/12/25
 */
public class MongoTest {

    @Id
    public String id;
    public String name;
    public String lastName;
    public Date date;

    public MongoTest() {
    }

    public MongoTest(String name, String lastName,Date date) {

        this.date=date;
        this.name = name;
        this.lastName = lastName;
    }
    public void showString(){
        System.out.println("id:"+id+"name:"+name+"lastName:"+lastName);
    }
}
