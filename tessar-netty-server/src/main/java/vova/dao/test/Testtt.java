package vova.dao.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vova.SpringConfig;
import vova.dao.ManageGameInput;

/**
 * @author: Vova
 * @create: date 12:17 2017/12/27
 */

public class Testtt {
    @Autowired
    public static ManageGameInput mgi;

    public static void main(String[] args) {
        System.out.println(mgi);
    }
}
