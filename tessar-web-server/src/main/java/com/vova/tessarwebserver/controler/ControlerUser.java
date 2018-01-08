package com.vova.tessarwebserver.controler;

import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import com.vova.tessarwebserver.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author: Vova
 * @create: date 15:30 2018/1/8
 */
@RestController
@RequestMapping("/user")
public class ControlerUser {
    @Autowired
    private AllInOneMapper allInOneMapper;

    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody User user) {
        List<User> uList = allInOneMapper.findUserList(user.getName());
        if (uList.size()!=0) {
            return -2;
        } else {
            return allInOneMapper.insertUser(user.getName(),user.getPasswd());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user) {
        List<User> uList = allInOneMapper.checkUser(user.getName(),user.getPasswd());
        if (uList.size()!=0) {
            return 1;
        }
        return 0;
    }



}
