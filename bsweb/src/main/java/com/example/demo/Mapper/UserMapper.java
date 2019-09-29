package com.example.demo.Mapper;

import com.example.demo.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User getById(int id);
    User getByName(String name);
    public boolean insert(String name,String passwd);
    //public List<User> getUsers();
    public boolean updateUser(String name,String passwd);
    //public boolean deleteUser(int id);
    //public boolean deleteAllUsers();
}