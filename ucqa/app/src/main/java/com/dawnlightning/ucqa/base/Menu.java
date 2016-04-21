package com.dawnlightning.ucqa.base;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Menu{
    private String menuname;
    private int status;

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MenuList{" +
                "menuname='" + menuname + '\'' +
                ", status=" + status +
                '}';
    }

    public Menu(String menuname, int status) {
        this.menuname = menuname;
        this.status = status;
    }
}
