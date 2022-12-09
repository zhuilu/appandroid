package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/11/14.
 */

public class IndexClassify {
    private String name;
    private List<Child> childList;

    public IndexClassify(String name,  List<Child>  childList) {
        this.name = name;
        this.childList = childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  List<Child> getChild() {
        return childList;
    }

    public void setChild( List<Child> childList) {
        this.childList = childList;
    }

    public static class Child {
        private String name;
        private List<Item> itemList;

        public Child(String name, List<Item> itemList) {
            this.name = name;
            this.itemList = itemList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Item> getItem() {
            return itemList;
        }

        public void setItem(List<Item> itemList) {
            this.itemList = itemList;
        }

        public static class Item {
            private String name;
            private List<ItemMenu> itemMenuList;

            public List<ItemMenu> getItemMenuList() {
                return itemMenuList;
            }

            public void setItemMenuList(List<ItemMenu> itemMenuList) {
                this.itemMenuList = itemMenuList;
            }

            public Item(String name, List<ItemMenu> itemMenuList) {
                this.name = name;
                this.itemMenuList = itemMenuList;
            }

            public static class ItemMenu{
                private String name;
                public ItemMenu(String name) {
                    this.name = name;
                }
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
