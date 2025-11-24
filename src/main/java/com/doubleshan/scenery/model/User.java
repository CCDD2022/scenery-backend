package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "open_id", unique = true, length = 64)
    private String openId;
    @Column(length = 64)
    private String nickname;
    @Column(length = 256)
    private String avatar;
    @Column(length = 32)
    private String phone;
    private int points;
    @Column(length = 16)
    private String role = "USER"; // USER / ADMIN / MERCHANT
    @Column(length = 64)
    private String department; // 部门(后台管理使用)
    private boolean disabled; // 是否禁用

    public User() {
    }

    public User(String openId, String nickname, String avatar) {
        this.openId = openId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.points = 0;
    }

    public String getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoints() {
        return points;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    public void spendPoints(int amount) {
        this.points -= amount;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
