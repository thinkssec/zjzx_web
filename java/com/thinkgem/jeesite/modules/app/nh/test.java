package com.thinkgem.jeesite.modules.app.nh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class test {
    public static void main (String[] a){
        List<double[]> arr=new ArrayList<>();
        arr.add(new double[]{199501,1135});
        arr.add(new double[]{199502,1258.353});
        arr.add(new double[]{199503,1335.05});
        arr.add(new double[]{199504,1360.2});
        arr.add(new double[]{199505,1370.1});
        arr.add(new double[]{199506,1385.064});
        arr.add(new double[]{199507,1420.088});
        arr.add(new double[]{199508,1502.31});
        arr.add(new double[]{199509,1552.31});
        arr.add(new double[]{199510,1504.314});
        arr.add(new double[]{199511,1504.08});
        arr.add(new double[]{199512,1452.081});
        arr.add(new double[]{199601,1430.345});
        arr.add(new double[]{199602,1401.123});
        arr.add(new double[]{199603,1385.01});
        arr.add(new double[]{199604,1351.15});
        arr.add(new double[]{199605,1322.1});
        arr.add(new double[]{199606,1283.19});
        arr.add(new double[]{199607,1242.02});
        arr.add(new double[]{199608,1201.46});
        NhService nhService=new NhService();
        //      指数
      nhService.getNh_Zhishu(arr);
      //调和
      nhService.getNh_Tiaohe(arr);
      //双曲
        nhService.getNh_Shuangqu(arr,0.05);
    }
}
