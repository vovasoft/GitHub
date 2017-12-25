package dao.dbmongo;

import domain.Customer;
import domain.Player;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Vova
 * @create: date 11:18 2017/12/25
 */

@Component
public class PlayerDao extends AbstractBaseMongoTemplete implements IPlayerDao{

    public PlayerDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public void insert(Player player) {
        mongoTemplate.insert(player);
    }

    public void insertAll(List<Player> list) {
        mongoTemplate.insertAll(list);
    }

    public void deleteById(int id) {
        Player player = mongoTemplate.findById(id,Player.class);
        mongoTemplate.remove(player);
    }
}
