package com.lpc.entity;

import java.util.List;

public class Menu {
    private Integer id;
    private String description;
    private String path;
    private List<Menu> childMenus;

    public Menu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", childMenus=" + childMenus +
                '}';
    }
}
