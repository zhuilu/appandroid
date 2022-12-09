package com.xinniu.android.qiqueqiao.utils;

import android.util.SparseArray;

import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/12/19.
 */

public class ResouceHelper {

    public static int seletCityId = 0;


    public static ResouceHelper instace;
    //  start  以下缓存方式与对象均为暂时策略,后期需要更换  --------------  start
    private static List<SelectCategory> leftCategory = new ArrayList<>();
    private static SparseArray<List<SelectCategory>> rightSparseList = new SparseArray<>();
    private static List<SelectCategory> joinList = new ArrayList<>();//合作方式
    private static List<SelectCategory> companyList = new ArrayList<>();//公司行业
    private static List<SelectCategory> orderList = new ArrayList<>();//筛选
    public static int leftSelectPd = -1;//暂存资源类别Pid
    public static int tempLeftSelectPd = -1;//temp暂存，用于用户当前操作  暂存资源类别Pid
    private static List<CityV2Bean> cityV2List = new ArrayList<>();

    private static List<CellTagsBean> cellTags = new ArrayList<>();

    private static List<SourceScreenBean.CategoryListBean> categoryListBeans  = new ArrayList<>();
    private static List<SourceScreenBean.CompanyListBean> companyListBeans = new ArrayList<>();
    private static List<SourceScreenBean.CooperationListBean> cooperationListBeans = new ArrayList<>();

    private static SourceScreenBean sourceScreenBean;





    //    public static List<SourceScreenBean>


    private static SparseArray<List<SelectCategory>> mReleaseRightSparseList = new SparseArray<>();

    public List<SelectCategory> getReleaseRightSparseList(int Pid) {
        if (rightSparseList.size() != 0) {
            return rightSparseList.get(Pid);
        }
        return null;
    }

    private ResouceHelper(){

    }

    public void setReleaseRightSparseList(int Pid, List<SelectCategory> leftCategory) {
        mReleaseRightSparseList.put(Pid, leftCategory);
    }

    private static List<SelectCategory> companySelect = new ArrayList<>();

    public void setCompanySelect(List<SelectCategory> leftCategory){
        companySelect.clear();
        companySelect.addAll(leftCategory);
    }

    public static List<SelectCategory> getCompanySelect() {
        return companySelect;
    }

    public static ResouceHelper getInstance(){
        if (instace == null){
            instace = new ResouceHelper();
        }
        return instace;
    }


    public static SourceScreenBean getSourceScreenBean() {
        return sourceScreenBean;
    }

    public static void setSourceScreenBean(SourceScreenBean sourceScreenBean) {
        if (sourceScreenBean!=null) {
            ResouceHelper.sourceScreenBean = sourceScreenBean;
        }

    }

    public static List<SourceScreenBean.CategoryListBean> getCategoryListBeans() {
        if (categoryListBeans.size()!=0){
            return   categoryListBeans ;
        }
        return null;
    }

    public static void setCategoryListBeans(List<SourceScreenBean.CategoryListBean> categoryListBeans) {
        ResouceHelper.categoryListBeans.clear();
        ResouceHelper.categoryListBeans.addAll(categoryListBeans);
    }

    public static List<SourceScreenBean.CompanyListBean> getCompanyListBeans() {
        if (companyListBeans.size()!=0){
            return   companyListBeans ;
        }
        return null;
    }

    public static void setCompanyListBeans(List<SourceScreenBean.CompanyListBean> companyListBeans) {
        ResouceHelper.companyListBeans.clear();
        ResouceHelper.companyListBeans = companyListBeans;
    }

    public static List<SourceScreenBean.CooperationListBean> getCooperationListBeans() {
        if (cooperationListBeans.size()!=0){
            return   cooperationListBeans ;
        }
        return null;
    }

    public static void setCooperationListBeans(List<SourceScreenBean.CooperationListBean> cooperationListBeans) {
        ResouceHelper.cooperationListBeans.clear();
        ResouceHelper.cooperationListBeans = cooperationListBeans;
    }


    /**
     * 将首页筛选条件放到app内存，退出登陆或者app finish时清空
     *
     * @param leftCategory
     */
    public void setLeftCache(List<SelectCategory> leftCategory) {
        this.leftCategory.clear();
        this.leftCategory.addAll(leftCategory);
    }

    public void setJoinList(List<SelectCategory> joinList) {
        this.joinList.clear();
        this.joinList.addAll(joinList);
    }

    public void setCompanyList(List<SelectCategory> companyList) {
        this.companyList.clear();
        this.companyList.addAll(companyList);
    }

    public void setOrderList(List<SelectCategory> orderList) {
        this.orderList.clear();
        this.orderList.addAll(orderList);
    }

    public  List<CellTagsBean> getCellTags() {
        if (cellTags.size()!=0){
            return  cellTags ;
        }
        return null;
    }

    public  void setCellTags(List<CellTagsBean> cellTags) {
        this.cellTags.clear();
        this.cellTags.addAll(cellTags);
    }

    public void setCityV2List(List<CityV2Bean> cityV2List){
        this.cityV2List.clear();
        this.cityV2List.addAll(cityV2List);
    }




    /**
     * 将首页筛选条件放到app内存，退出登陆或者app finish时清空
     *
     * @param Pid
     * @param leftCategory
     */
    public void setRightCache(int Pid, List<SelectCategory> leftCategory) {
        rightSparseList.put(Pid, leftCategory);
    }

    /**
     * 获取左边缓存
     *
     * @return
     */
    public List<SelectCategory> getLeftCategory() {
        if (leftCategory == null){
            return null;
        }
        if (leftCategory.size() != 0) {
            return leftCategory;
        }
        return null;
    }

    /**
     * 获取右边缓存
     *
     * @param Pid
     * @return
     */
    public List<SelectCategory> getRightListById(int Pid) {
        if (rightSparseList.size() != 0) {
            return rightSparseList.get(Pid);
        }
        return null;
    }

    public List<SelectCategory> getJoinList() {
        if (joinList.size() != 0) {
            return joinList;
        }
        return null;
    }

    public List<SelectCategory> getCompanyList() {
        if (companyList.size() != 0) {
            return companyList;
        }
        return null;
    }

    public List<SelectCategory> getOrderList() {
        if (orderList.size() != 0) {
            return orderList;
        }
        return null;
    }
    public List<CityV2Bean> getCityV2List(){
        if (cityV2List.size()!=0){
            for (int i = 0; i < cityV2List.size(); i++) {
                cityV2List.get(i).setCheck(false);
                for (int j = 0; j < cityV2List.get(i).getZlist().size(); j++) {
                    cityV2List.get(i).getZlist().get(j).setCheck(false);
                }
            }
            return cityV2List;
        }
        return null;
    }







    public void reSettingResouce(){
        if (leftCategory == null){
            return;
        }
        for (SelectCategory left:leftCategory){
            if (getRightListById(left.getId()) == null){
                return;
            }
            for (SelectCategory right:getRightListById(left.getId())){
                for (SelectCategory child:right.getZ_index()){
                    child.setCheck(false);
                }
            }
        }
        leftCategory = null;
    }

    public String getSelectedLeftName(){
        if (leftCategory == null){
            return "全部资源";
        }
        for (SelectCategory left:leftCategory){
            if (left.getId() == leftSelectPd){
                return left.getName();
            }
        }
        return "全部资源";
    }
}
