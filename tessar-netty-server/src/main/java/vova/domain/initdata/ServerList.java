package vova.domain.initdata;

/**
 * @author: Vova
 * @create: date 17:25 2018/1/2
 */
public class ServerList {
    int id;
    String name;

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

    public ServerList() {

    }

    public ServerList(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
