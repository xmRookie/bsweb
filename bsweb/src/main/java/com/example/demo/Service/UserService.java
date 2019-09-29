package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getById(int id){
        return userMapper.getById(id);
    }

    public User getByName(String name){
        return userMapper.getByName(name);
    }

    public boolean insert(String name,String passwd){
        return userMapper.insert(name,passwd);
    }


    public boolean updateUser(String name,String passwd) {
        return userMapper.updateUser(name,passwd);
    }




}
