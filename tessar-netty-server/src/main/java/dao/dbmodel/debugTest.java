package dao.dbmodel;

import dao.mysql.UseMySql;
import nettydemo.User;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class debugTest {
    @Test
    public void fun() throws IOException {
        User user = new User("vova", 123);
        UseMySql.insert(user);
    }
}
