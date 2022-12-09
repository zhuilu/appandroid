package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class AreaBean {

    /**
     * id : 3
     * name : 安徽
     * city : ["安庆","蚌埠","巢湖","池州","滁州","阜阳","淮北","淮南","黄山","六安","马鞍山","宿州","铜陵","芜湖","宣城","亳州","合肥"]
     */

    private int id;
    private String name;
    private List<String> city;

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

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }
}
