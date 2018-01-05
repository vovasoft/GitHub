package vova;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vova.dao.manager.ManageGameInput;
import vova.dao.dbmongo.UseMyMongo;
import vova.dao.dbsql.UseMySql;

/**
 * @author: Vova
 * @create: date 11:55 2017/12/25
 */
@Configuration            // 有问题再测试
@ComponentScan(basePackageClasses = {ManageGameInput.class, UseMySql.class, UseMyMongo.class})
public class SpringConfig {
}
