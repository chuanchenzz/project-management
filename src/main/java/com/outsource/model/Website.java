package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class Website implements Serializable{
    private static final long serialVersionUID = -1L;

    private int id;
    private String name;
    private String favIcon;
    private String logo;
    private Date time;

    public Website(){}

    public Website(String name,String favIcon,String logo){
        this.name = name;
        this.favIcon = favIcon;
        this.logo = logo;
    }

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

    public String getFavIcon() {
        return favIcon;
    }

    public void setFavIcon(String favIcon) {
        this.favIcon = favIcon;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Website{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", favIcon='" + favIcon + '\'' +
                ", logo='" + logo + '\'' +
                ", time=" + time +
                '}';
    }
}
