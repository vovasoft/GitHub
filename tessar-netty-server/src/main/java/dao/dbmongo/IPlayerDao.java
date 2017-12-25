package dao.dbmongo;

import domain.Player;

import java.util.List;

/**
 * @author: Vova
 * @create: date 11:29 2017/12/25
 */
public interface IPlayerDao {

    void insert(Player player);

    void insertAll(List<Player> list);

    void deleteById(int id);
}
