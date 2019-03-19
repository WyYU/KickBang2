package com.example.dell.kickbang.Model;

import java.io.Serializable;

/**
 * Created by dell on 2019/3/9 0009.
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private Integer tid;
    private Integer num;
    private String imagepatch;
    private Integer level;
    private int goal;
    private Integer assisting;
    private String position;
    private Double balance;
    private Team team;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public String getImagepatch() {
        return imagepatch;
    }

    public void setImagepatch(String imagepatch) {
        this.imagepatch = imagepatch;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public Integer getAssisting() {
        return assisting;
    }

    public void setAssisting(Integer assisting) {
        this.assisting = assisting;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position==null){
            this.position = null;
        }
        this.position = position;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (goal != user.goal) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (tid != null ? !tid.equals(user.tid) : user.tid != null) return false;
        if (num != null ? !num.equals(user.num) : user.num != null) return false;
        if (imagepatch != null ? !imagepatch.equals(user.imagepatch) : user.imagepatch != null) return false;
        if (level != null ? !level.equals(user.level) : user.level != null) return false;
        if (assisting != null ? !assisting.equals(user.assisting) : user.assisting != null) return false;
        if (position != null ? !position.equals(user.position) : user.position != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (tid != null ? tid.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (imagepatch != null ? imagepatch.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + goal;
        result = 31 * result + (assisting != null ? assisting.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        team = team;
    }

    @Override
    public String toString() {
        return this.getId()+" "+this.getUsername()+" "+getPosition()+" "+getLevel()+" "+getNum()+" "+getGoal()+" "+getAssisting()+" "+getImagepatch();
    }
}
