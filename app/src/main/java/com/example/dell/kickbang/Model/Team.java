package com.example.dell.kickbang.Model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dell on 2019/3/9 0009.
 */
public class Team implements Serializable{
    private int tid;
    private String tname;
    private String iconpath;
    private Date createTime;
    private String colorcode;
    private String introduce;
    private Set<User> players = new HashSet<>();

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (tid != team.tid) return false;
        if (tname != null ? !tname.equals(team.tname) : team.tname != null) return false;
        if (iconpath != null ? !iconpath.equals(team.iconpath) : team.iconpath != null) return false;
        if (createTime != null ? !createTime.equals(team.createTime) : team.createTime != null) return false;
        if (colorcode != null ? !colorcode.equals(team.colorcode) : team.colorcode != null) return false;
        if (introduce != null ? !introduce.equals(team.introduce) : team.introduce != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tid;
        result = 31 * result + (tname != null ? tname.hashCode() : 0);
        result = 31 * result + (iconpath != null ? iconpath.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (colorcode != null ? colorcode.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        return result;
    }

    public Set<User> getPlayers() {
        return players;
    }

    public void setPlayers(Set<User> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
