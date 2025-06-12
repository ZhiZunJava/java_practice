package com.itheima.domain;

public class User {
    //用户编号
    private Integer id;
    //用户名称
    private String name;
    //用户密码
    private String password;
    //用户手机号
    private String tel;
    //用户性别
    private String gender;
    //用户角色
    private String role;

    public User() {
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(Integer id, String name, String password,  String tel,String gender, String role) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
