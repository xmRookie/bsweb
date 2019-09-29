package com.example.demo.Entity;

public class User {
    private String name;
    private String passwd;
    private int id;

    public User(){}
    public User(int id,String name,String passwd){
        this.id = id;
        this.name = name;
        this.passwd = passwd;
    }
    public String getName() {
        return name;
    }
    public String getPasswd(){
        return passwd;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPasswd(String passwd){
        this.passwd = passwd;
    }
    public void setId(int id) {
        this.id = id;
    }
}
