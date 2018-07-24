package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class WebsitConfig implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String faviconLogo;
    private String logo;

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

    public String getFaviconLogo() {
        return faviconLogo;
    }

    public void setFaviconLogo(String faviconLogo) {
        this.faviconLogo = faviconLogo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "WebsitConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", faviconLogo='" + faviconLogo + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
