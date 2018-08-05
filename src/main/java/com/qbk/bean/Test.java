package com.qbk.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 13624 on 2018/6/20.
 */
public class Test implements Serializable {

    private int id ;
    private  String name ;
    private  int sex ;
    private Date date ;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }
}
