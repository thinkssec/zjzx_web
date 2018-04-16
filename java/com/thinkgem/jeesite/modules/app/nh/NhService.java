package com.thinkgem.jeesite.modules.app.nh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
 
public class NhService {
	private static Log logger = LogFactory.getLog(NhService.class);
	//指数递减
    public ClSrClnhjg getNh_Zhishu(List<double[]> list){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double y = 0;
    	double xFitst = 0;
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    		   xFitst = xy[0];
    	   }
    	   //令x为时间，y为logQ：产量对数     (xy[0]:时间；   xy[1]产量)
    	   double x = xy[0]-xFitst;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
    		   x = (Math.floor(x/100))*12+(x%100);
    	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
    	   y = Math.log10(xy[1]);
    	   
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	//计算初始递减率:a0
    	double a0 = -1*b*2.3026;
    	//计算初始产量 :q0
    	double q0 = Math.pow(10, a);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
//    	System.out.println("指数2 a:"+a);
//    	System.out.println("指数2 b:"+b);
//    	System.out.println("指数2 q0:"+q0);   
//    	System.out.println("指数2 a0:"+a0);
    	
    	//总离差平方和
    	double tss = 0;
    	//回归平方和
    	double ess = 0;
    	//剩余平方和
    	double rss = 0;
    	//为求各种平方和，需要再次循环
    	y = 0;
    	xFitst = 0;
    	String dataOut = "";
    	String dataIn = "";
    	for(int i=0;list!=null && i<list.size();i++){
     	   double[] xy = list.get(i);
     	   //令x为时间，y为logQ：产量对数     (xy[0]:时间；   xy[1]产量)
     	   if(i==0){
     		   m = list.size();
     		   xFitst = xy[0];
     	   }
     	  //令x为时间，y为logQ：产量对数     (xy[0]:时间；   xy[1]产量)
    	   double x = xy[0]-xFitst;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
    		   x = (Math.floor(x/100))*12+(x%100);
    	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
    	   y = Math.log10(xy[1]);
    	   
     	  //总离差平方和 
     	  tss += (y-y_avg)*(y-y_avg);
     	  //求拟合出的y点
     	  double yy = Math.log10(q0)-(a0/2.3026)*x;
     	  //回归平方和
      	  ess += (yy-y_avg)*(yy-y_avg);
      	  //剩余平方和
      	  rss += (y-yy)*(y-yy);
      	  //计算拟合后的点坐标
      	  double ii = xy[0]-xFitst;
	      	if(xy[0]>190000 && xy[0]<300000 && ii>=100){//如果日期为月度
	 		   ii = (Math.floor(ii/100))*12+(ii%100);
	 	   }
	      	//System.out.println(xy[0]+"===xFitst:"+xFitst+"===ii:"+ii+"==xy[0]-xFitst:"+(xy[0]-xFitst));
      	  double X_over = xy[0];
		 // System.out.println("q0:"+q0+"==a0:"+a0+"==ii:"+ii+"==xy[0]:"+xy[0]+"==xFitst:"+xFitst);
      	  double Y_over =  ToolsUtil.stringToDouble(CmUtil.getMoney(q0 * Math.pow(Math.E, -1*a0*ii)+""));
      	  dataOut += "["+X_over+","+Y_over+"],";
      	  dataIn += "["+X_over+","+xy[1]+"],";

//      	  
     	}
    	//判定系数 r2
    	double pdxs = ess/tss;
//    	System.out.println("指数2 r:"+r);
//    	
    	//System.out.println("指数2 ess:"+ess);
    	//System.out.println("指数2 tss:"+tss);
//    	System.out.println("指数2 rss:"+rss);
    	//System.out.println("指数2 pdxs:"+pdxs);
//    	System.out.println("指数2 dataIn:"+dataIn);
//    	System.out.println("指数2 dataOut:"+dataOut);
    	
    	ClSrClnhjg clSrClnhjg = new ClSrClnhjg();

    	clSrClnhjg.setDatain(dataIn.length()>0?"["+dataIn.substring(0,dataIn.length()-1)+"]":"");
    	clSrClnhjg.setDataout(dataOut.length()>0?"["+dataOut.substring(0,dataOut.length()-1)+"]":"");
    	clSrClnhjg.setCscl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(q0+"", 6)));
    	clSrClnhjg.setCsdjl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(a0+"", 6)));
    	//clSrClnhjg.setDjzs(0);
        clSrClnhjg.setDjzs(xFitst);
    	clSrClnhjg.setXgxs(Math.abs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(r+"", 6))));
    	clSrClnhjg.setPdxs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(pdxs+"", 6)));
    	clSrClnhjg.setTss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(tss+"", 6)));
    	clSrClnhjg.setEss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(ess+"", 6)));
    	clSrClnhjg.setRss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(rss+"", 6)));
    	clSrClnhjg.setDjlx("指数");
		clSrClnhjg.setX0(xFitst);
    	return clSrClnhjg;
    }
    
    //调和递减拟合
    public ClSrClnhjg getNh_Tiaohe(List<double[]> list){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   //令Y=lgQ，A=lgQo，B=-a0/2.3026Qo，X=Np，则得方程 Y=A+BX
    	   y = Math.log10(xy[1]);
    	   if(i==0){
    		   m = list.size();
    	   }else{
    		   //当年的累计产量=上年的产量+上年的累计产量； （因为累计产量第0年为0，所以感觉别扭）
    		   double[] xyLasyYear = list.get(i-1);
//    		   x += xyLasyYear[1];
    	   }
    	   x += xy[1];
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	//计算初始产量 :q0
    	double q0 = Math.pow(10, a);
    	//计算初始递减率:a0
    	double a0 = -1*b*2.3026*q0;
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
    	//System.out.println("调和 a:"+a);
    	//System.out.println("调和 b:"+b);
    	//System.out.println("调和 q0:"+q0);
    	//System.out.println("调和 a0:"+a0);
    	//总离差平方和
    	double tss = 0;
    	//回归平方和
    	double ess = 0;
    	//剩余平方和
    	double rss = 0;
    	//为求各种平方和，需要再次循环
    	y = 0;
    	double xFitst = 0;
    	String dataOut = "";
    	String dataIn = "";
    	 x = 0;
    	 y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
     	   double[] xy = list.get(i);
     	   //令Y=lgQ，A=lgQo，B=-a0/2.3026Qo，X=Np，则得方程 Y=A+BX
     	   y = Math.log10(xy[1]);
     	   if(i==0){
     		   m = list.size();
     		   xFitst = xy[0];
     	   }else{
     		   //当年的累计产量=上年的产量+上年的累计产量； （因为累计产量第0年为0，所以感觉别扭）
     		   double[] xyLasyYear = list.get(i-1);
//     		   x += xyLasyYear[1];  
     	   }
     	  x += xy[1];
     	  //总离差平方和 
     	  tss += (y-y_avg)*(y-y_avg);
     	  //求拟合出的y点
     	  //令Y=lgQ，A=lgQo，B=-a0/2.3026Qo，X=Np，则得方程 Y=A+BX
     	  double yy = Math.log10(q0) - (a0/(2.3026*q0))*x;
     	  //回归平方和
      	  ess += (yy-y_avg)*(yy-y_avg);
      	  //剩余平方和
      	  rss += (y-yy)*(y-yy);
      	  //计算拟合后的点坐标
      	  double ii = xy[0]-xFitst;
        	if(xy[0]>190000 && xy[0]<300000 && ii>=100){//如果日期为月度
	 		   ii = (Math.floor(ii/100))*12+(ii%100);
	 	   }
        	//System.out.println(xy[0]+"===xFitst:"+xFitst+"===ii:"+ii+"==xy[0]-xFitst:"+(xy[0]-xFitst));
      	  double X_over = xy[0];
      	//System.out.println("-----------------q0:"+q0+"==a0:"+a0+"==ii:"+ii+"==xy[0]:"+xy[0]+"==xFitst:"+xFitst);
      	  double Y_over =  ToolsUtil.stringToDouble(CmUtil.getMoney(q0 * Math.pow((1+a0*ii), -1)+""));
      	dataOut += "["+X_over+","+Y_over+"],";
    	  dataIn += "["+X_over+","+xy[1]+"],";
      	  
     	}
    	
    	//判定系数 r2
    	double pdxs = ess/tss;
//    	System.out.println("调和 r:"+r);
//    	System.out.println("调和 tss:"+tss);
//    	System.out.println("调和 ess:"+ess);
//    	System.out.println("调和 rss:"+rss);
//    	System.out.println("调和 pdxs:"+pdxs);
//    	System.out.println("调和 dataIn:"+dataIn);
//    	System.out.println("调和 dataOut:"+dataOut);
    	
    	ClSrClnhjg clSrClnhjg = new ClSrClnhjg();
    	clSrClnhjg.setDatain(dataIn.length()>0?"["+dataIn.substring(0,dataIn.length()-1)+"]":"");
    	clSrClnhjg.setDataout(dataOut.length()>0?"["+dataOut.substring(0,dataOut.length()-1)+"]":"");
    	clSrClnhjg.setCscl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(q0+"", 6)));
    	clSrClnhjg.setCsdjl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(a0+"", 6)));
    	//clSrClnhjg.setDjzs(0);
        clSrClnhjg.setDjzs(xFitst);
    	clSrClnhjg.setXgxs(Math.abs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(r+"", 6))));
    	clSrClnhjg.setPdxs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(pdxs+"", 6)));
    	clSrClnhjg.setTss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(tss+"", 6)));
    	clSrClnhjg.setEss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(ess+"", 6)));
    	clSrClnhjg.setRss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(rss+"", 6)));
    	clSrClnhjg.setDjlx("调和");
		clSrClnhjg.setX0(xFitst);
    	return clSrClnhjg;
    }
    
    //双曲递减拟合
    public ClSrClnhjg getNh_Shuangqu_over(List<double[]> list,double n){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	double xFitst = 0;//初始年度
    	double yFitst = 0;//初始产量
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    		   xFitst = xy[0];
    		   yFitst = xy[1];
    	   }
    	   x = xy[0]-xFitst;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100);
	 	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
    	   y = Math.pow(yFitst/xy[1], n);
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	//计算初始递减率:a0
    	double a0 = b/n;
    	//计算初始产量 :q0
    	//取顺时点1
    	double[] xyTmp = list.get(1);
    	double q0 = xyTmp[1] * Math.pow(1+b*1, 1/n);
		System.out.println("--------------===="+n);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
//    	System.out.println("双曲 a:"+a);
//    	System.out.println("双曲 b:"+b);
//    	System.out.println("双曲 q0:"+q0);   
//    	System.out.println("双曲 a0:"+a0);  
    	//总离差平方和
    	double tss = 0;
    	//回归平方和
    	double ess = 0;
    	//剩余平方和
    	double rss = 0;
    	//为求各种平方和，需要再次循环
    	y = 0;
    	xFitst = 0;
    	String dataOut = "";
    	String dataIn = "";
    	 x = 0;
    	 y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
     	   if(i==0){
     		   m = list.size();
     		   xFitst = xy[0];
     		   yFitst = xy[1];
     	   }
     	   x = xy[0]-xFitst;
     	  if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100);
	 	   }
     	 //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
     	   y = Math.pow(yFitst/xy[1], n);
     	  //总离差平方和 
     	  tss += (y-y_avg)*(y-y_avg);
     	  //求拟合出的y点
     	  double yy = 1+b*x;
     	  //回归平方和
      	  ess += (yy-y_avg)*(yy-y_avg);
      	  //剩余平方和
      	  rss += (y-yy)*(y-yy);
      	  //计算拟合后的点坐标
      	  double ii = xy[0]-xFitst;
      	  if(xy[0]>190000 && xy[0]<300000 && ii>=100){//如果日期为月度
	 		   ii = (Math.floor(ii/100))*12+(ii%100);
	 	   }
      	//System.out.println(xy[0]+"===xFitst:"+xFitst+"===ii:"+ii+"==xy[0]-xFitst:"+(xy[0]-xFitst));
      	  double X_over = xy[0];
     	//System.out.println("q0:"+q0+"==a0:"+a0+"==ii:"+ii+"==xy[0]:"+xy[0]+"==xFitst:"+xFitst);
      	  double Y_over =  ToolsUtil.stringToDouble(CmUtil.getMoney(q0 * Math.pow((1+a0*ii), -1)+""));
      	  dataOut += "["+X_over+","+Y_over+"],";
    	  dataIn += "["+X_over+","+xy[1]+"],";
//  System.out.println(dataOut);    	  
     	}
    	//判定系数 r2
    	double pdxs = ess/tss;
//    	System.out.println("双曲 r:"+r);
//    	System.out.println("双曲 tss:"+tss);
//    	System.out.println("双曲 ess:"+ess);
//    	System.out.println("双曲 rss:"+rss);
//    	System.out.println("双曲 pdxs:"+pdxs);
//    	System.out.println("双曲 dataIn:"+dataIn);
//    	System.out.println("双曲 dataOut:"+dataOut);
    	
    	ClSrClnhjg clSrClnhjg = new ClSrClnhjg();
    	clSrClnhjg.setDatain(dataIn.length()>0?"["+dataIn.substring(0,dataIn.length()-1)+"]":"");
    	clSrClnhjg.setDataout(dataOut.length()>0?"["+dataOut.substring(0,dataOut.length()-1)+"]":"");
    	clSrClnhjg.setCscl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(q0+"", 6)));
    	clSrClnhjg.setCsdjl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(a0+"", 6)));
    	//clSrClnhjg.setDjzs(0);
        clSrClnhjg.setDjzs(xFitst);
    	clSrClnhjg.setXgxs(Math.abs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(r+"", 6))));
    	clSrClnhjg.setPdxs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(pdxs+"", 6)));
    	clSrClnhjg.setTss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(tss+"", 6)));
    	clSrClnhjg.setEss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(ess+"", 6)));
    	clSrClnhjg.setRss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(rss+"", 6)));
    	clSrClnhjg.setDjlx("双曲");
		clSrClnhjg.setX0(xFitst);
    	return clSrClnhjg;
    }
    
    //双曲递减拟合  试算
    public double getNh_Shuangqu_SS(List<double[]> list,double n){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	double xFitst = 0;//初始年度
    	double yFitst = 0;//初始产量
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    		   xFitst = xy[0];
    		   yFitst = xy[1];
    	   }
    	   x = xy[0]-xFitst;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100);
	 	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
    	   y = Math.pow(yFitst/xy[1], n);
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	//计算初始递减率:a0
    	double a0 = b/n;
    	//计算初始产量 :q0
    	//取顺时点1
    	double[] xyTmp = list.get(1);
    	double q0 = xyTmp[1] * Math.pow(1+b*1, 1/n);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
    	
//    	System.out.println("r:"+r);
    	
    	return r;
    }
    /**
     * 双曲递减获取相关系数r,x坐标是累计产量而不是日期
     * @param list
     * @param n
     * @return
     */
    public double getNh_Shuangqu_SS2(List<double[]> list,double n){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	double xFitst = 0;//初始累计产量
    	double yFitst = 0;//初始产量
    	for(int i=0;list!=null && i<list.size();i++){
    		double[] xy = list.get(i);
    		if(i==0){
    			m = list.size();
    			xFitst = xy[0];
    			yFitst = xy[1];
    		}
//    		x = xy[0]-xFitst;
//    		if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
//    			x = (Math.floor(x/100))*12+(x%100);
//    		}
    		//System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
//    		y = Math.pow(yFitst/xy[1], n);
    		x = xy[0];
    		y = Math.pow(xy[1], (1-n));  // Q的1-n次方
    		a_fz1 += x*y;
    		a_fz2_x += x;
    		a_fz2_y += y;
    		a_fm1 += x*x;
    		a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	//计算初始递减率:a0
    	double a0 = b/n;
    	//计算初始产量 :q0
    	//取顺时点1
    	double[] xyTmp = list.get(1);
    	double q0 = xyTmp[1] * Math.pow(1+b*1, 1/n);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
    	
//    	System.out.println("r:"+r);
    	
    	return r;
    }
    
    public ClSrClnhjg getNh_Shuangqu(List<double[]> list,double n){
    	ArrayList<double[]> arr = new ArrayList<double[]>();
    	//循环0-1之间个步长
    	//判定各步长n测算的相关系数最大
    	double[] douMax = new double[]{0,0};
    	for(double i=n;i<1 && i>0;i+=n){
    	  double[] douS = new double[]{i,getNh_Shuangqu_SS(list,i)};
//    	  System.out.println("=="+i+","+getNh_Shuangqu_SS(list,i));
    	  if(douS[1]>douMax[1]){
    		  douMax = douS;
    	  }
    	}
//    	System.out.println("n:"+douMax[0]+",r:"+douMax[1]);
    	NhService nhService = new NhService();
		System.out.println("================"+douMax[0]);
    	ClSrClnhjg clSrClnhjg = nhService.getNh_Shuangqu_over(list,0.05);
    	return clSrClnhjg;
    }
    
    
  ////广义翁氏预测模型递减拟合
    public ClSrClnhjg getNh_Nh_Wongshi_over(List<double[]> list,double n){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	double xFitst = 0;//初始年度
    	double yFitst = 0;//初始产量
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    		   xFitst = xy[0];
    		   yFitst = xy[1];
    	   }
    	   x = xy[0]-xFitst+1;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100)+1;
	 	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
    	   //令Y=lg(Q/tb)，A=lga，B=1/(2.303*c)，X=t，则得方程 Y=A+BX
    	   y = Math.log10(yFitst/(Math.pow(x,n)));
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	double aa = Math.pow(10, a);
    	double cc = 1/(2.303*b);
    	double bb = n;
    	System.out.println("翁氏 a:"+a);
    	System.out.println("翁氏 b:"+b);
//    	System.out.println("翁氏 aa:"+aa);
//    	System.out.println("翁氏 bb:"+bb);
//    	System.out.println("翁氏 cc:"+cc);
    	//计算初始递减率:a0
//    	double a0 = b/n;
    	//计算初始产量 :q0
    	//取顺时点1
    	double[] xyTmp = list.get(1);
//    	double q0 = xyTmp[1] * Math.pow(1+b*1, 1/n);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
    	//总离差平方和
    	double tss = 0;
    	//回归平方和
    	double ess = 0;
    	//剩余平方和
    	double rss = 0;
    	//为求各种平方和，需要再次循环
    	y = 0;
    	xFitst = 0;
    	String dataOut = "";
    	String dataIn = "";
    	x = 0;
    	y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
     	   if(i==0){
     		   m = list.size();
     		   xFitst = xy[0];
     		   yFitst = xy[1];
     	   }
     	   x = xy[0]-xFitst+1;
     	  if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100)+1;
	 	   }
     	//令Y=lg(Q/tb)，A=lga，B=1/(2.303*c)，X=t，则得方程 Y=A+BX
   	      y = Math.log10(yFitst/(Math.pow(x,n)));
     	  //总离差平方和 
     	  tss += (y-y_avg)*(y-y_avg);
     	  //求拟合出的y点
     	  double yy = 1+b*x;
     	  //回归平方和
      	  ess += (yy-y_avg)*(yy-y_avg);
      	  //剩余平方和
      	  rss += (y-yy)*(y-yy);
      	  //计算拟合后的点坐标
      	  double ii = xy[0]-xFitst+1;
      	  if(xy[0]>190000 && xy[0]<300000 && ii>=100){//如果日期为月度
	 		   ii = (Math.floor(ii/100))*12+(ii%100)+1;
	 	   }
      	//System.out.println(xy[0]+"===xFitst:"+xFitst+"===ii:"+ii+"==xy[0]-xFitst:"+(xy[0]-xFitst));
      	  double X_over = xy[0];
	System.out.println("aa:"+aa+"==bb:"+bb+"==cc:"+cc+"==ii:"+ii+"==xy[0]:"+xy[0]+"==xFitst:"+xFitst);
      	  double Y_over =  ToolsUtil.stringToDouble(CmUtil.getMoney((aa*Math.pow(ii, bb)*Math.pow(Math.E, (-1*ii/cc)))+""));
      	  dataOut += "["+X_over+","+Y_over+"],";
    	  dataIn += "["+X_over+","+xy[1]+"],";
//    	  System.out.println(dataOut);    	  
     	}
    	//判定系数 r2
    	double pdxs = ess/tss;
//    	System.out.println("翁氏 r:"+r);
//    	System.out.println("翁氏 tss:"+tss);
//    	System.out.println("翁氏 ess:"+ess);
//    	System.out.println("翁氏 rss:"+rss);
//    	System.out.println("翁氏 pdxs:"+pdxs);
//    	System.out.println("翁氏 dataIn:"+dataIn);
//    	System.out.println("翁氏 dataOut:"+dataOut);
    	
    	ClSrClnhjg clSrClnhjg = new ClSrClnhjg();
    	clSrClnhjg.setDatain(dataIn.length()>0?"["+dataIn.substring(0,dataIn.length()-1)+"]":"");
    	clSrClnhjg.setDataout(dataOut.length()>0?"["+dataOut.substring(0,dataOut.length()-1)+"]":"");
//    	clSrClnhjg.setCscl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(q0+"", 6)));
//    	clSrClnhjg.setCsdjl(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(a0+"", 6)));
    	clSrClnhjg.setDjzs(0);
    	clSrClnhjg.setXgxs(Math.abs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(r+"", 6))));
    	clSrClnhjg.setPdxs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(pdxs+"", 6)));
    	clSrClnhjg.setTss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(tss+"", 6)));
    	clSrClnhjg.setEss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(ess+"", 6)));
    	clSrClnhjg.setRss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(rss+"", 6)));
    	clSrClnhjg.setDjlx("翁氏");
    	clSrClnhjg.setA(aa);
    	clSrClnhjg.setB(bb);
    	clSrClnhjg.setC(cc);
    	return clSrClnhjg;
    }
    
    ////广义翁氏预测模型递减拟合  试算
    public double getNh_Nh_Wongshi_SS(List<double[]> list,double n){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	double xFitst = 0;//初始年度
    	double yFitst = 0;//初始产量
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    		   xFitst = xy[0];
    		   yFitst = xy[1];
    	   }
    	   x = xy[0]-xFitst+1;
    	   if(xy[0]>190000 && xy[0]<300000 && x>=100){//如果日期为月度
	 		   x = (Math.floor(x/100))*12+(x%100)+1;
	 	   }
    	   //System.out.println(xy[0]+"===xFitst:"+xFitst+"===x:"+x+"==xy[0]-xFitst:"+(xy[0]-xFitst));
//    	   System.out.println(n+"==x:"+x+"==="+Math.pow(x,n));
    	 //令Y=lg(Q/tb)，A=lga，B=1/(2.303*c)，X=t，则得方程 Y=A+BX
    	   y = Math.log10(yFitst/(Math.pow(x,n)));
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	//计算初始递减率:a0
//    	double a0 = b/n;
    	//计算初始产量 :q0
    	//取顺时点1
    	double[] xyTmp = list.get(1);
//    	double q0 = xyTmp[1] * Math.pow(1+b*1, 1/n);
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
//    	System.out.println("n:"+n+"==x:"+x+"==y:"+y+"==r:"+r+"==:"+Math.pow(x,n));
    	System.out.println(n+"==r:"+r+"==a:"+a+"==b:"+b);
    	return r;
    }
    
    public ClSrClnhjg getNh_Wongshi(List<double[]> list){
    	ArrayList<double[]> arr = new ArrayList<double[]>();
    	//循环0-1之间个步长
    	//判定各步长n测算的相关系数最大
    	double[] douMax = new double[]{0,0};
    	for(double i=1;i<30 ;i++){
    	  double[] douS = new double[]{i,getNh_Nh_Wongshi_SS(list,i)};
    	  if(Math.abs(douS[1])>Math.abs(douMax[1])){
    		  douMax = douS;
    	  }
    	}
    	NhService nhService = new NhService();
    	ClSrClnhjg clSrClnhjg = nhService.getNh_Nh_Wongshi_over(list,douMax[0]);
    	return clSrClnhjg;
    }
    
    //哈波特递减拟合
    public ClSrClnhjg getNh_HaBeTe(List<double[]> list){
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double x = 0;
    	double y = 0;
    	//计算第二步  求解bb,nr
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   //令Y=Q/Np，x=Np，则得方程 Y=A-BX
    	   if(i==0){
    		  m = list.size();
    	   }
    	   x += xy[1];
    	   y = xy[1]/x;
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	System.out.println("哈波特 a1--:"+a);
//    	System.out.println("哈波特 b--:"+b);
    	double bb = a;
    	double nr = 0;
    	if(b!=0){
    		nr = -1*a/b;
    	}
    	//计算第二步  求解aa
   	 double aa_fz1 = 0; //分子1
   	 double aa_fz2 = 0; //分子2
   	 double aa_fz2_x = 0; //分子2 x
   	 double aa_fz2_y = 0; //分子2 y
   	 double aa_fm1 = 0; //分母1
   	 double aa_fm2 = 0; //分母2
   	 double aa_fm1_y = 0; //  y的平方求和
   	
   	 double am = 0;//拟合源数据个数
   	 double ax = 0;
   	 double ay = 0;
   	 double np = 0;
   	for(int i=0;list!=null && i<list.size();i++){
   	   double[] xy = list.get(i);
   	   //令Y=lg(Nr-Np/Np)，x=t，则得方程 Y=A-BX
   	   if(i==0){
   		  am = list.size();
   	   }
   	   ax = i;
   	   np += xy[1];
   	   if(np!=0){
   		 ay = Math.log((nr-np)/np);
   	   }
//   	System.out.println("哈波特 nr:"+nr);
//   	System.out.println("哈波特 np:"+np);
//   	System.out.println("哈波特 ay:"+ay);
   	   aa_fz1 += ax*ay;
   	   aa_fz2_x += ax;
   	   aa_fz2_y += ay;
   	   aa_fm1 += ax*ax;
   	   aa_fm1_y += ay*ay;
   	}
   	aa_fz2 = aa_fz2_x * aa_fz2_y;
   	aa_fm2 = aa_fz2_x *aa_fz2_x;
   	//系数b
   	 b = (am*aa_fz1 - aa_fz2)/(am*aa_fm1-aa_fm2);
   	//系数a
   	 a = (aa_fm1*aa_fz2_y - aa_fz1*aa_fz2_x)/(am*aa_fm1-aa_fm2);
   	 
   	System.out.println("哈波特nr:"+nr);
   	System.out.println("哈波特 np:"+np);
   	System.out.println("哈波特 a:"+a);
   	System.out.println("哈波特 b:"+b);
   	double aa = Math.pow(10, a);
   	
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	System.out.println("哈波特 aa:"+aa);
    	System.out.println("哈波特 bb:"+bb);   
    	System.out.println("哈波特 nr:"+nr);  
    	//总离差平方和
    	double tss = 0;
    	//回归平方和
    	double ess = 0;
    	//剩余平方和
    	double rss = 0;
    	//为求各种平方和，需要再次循环
    	y = 0;
    	double xFitst = 0;
    	String dataOut = "";
    	String dataIn = "";
    	 x = 0;
    	 y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
     	   double[] xy = list.get(i);
     	  //令Y=Q/Np，x=Np，则得方程 Y=A-BX
    	   if(i==0){
    		   m = list.size();
    	   }
    	   x += xy[1];
    	   y = xy[1]/x;
     	  //总离差平方和 
     	  tss += (y-y_avg)*(y-y_avg);
     	  //求拟合出的y点
     	  //令Y=Q/Np，x=Np，则得方程 Y=A-BX
     	  double yy = a-b*x;
     	  //回归平方和
      	  ess += (yy-y_avg)*(yy-y_avg);
//      	System.out.println("哈勃特a："+a);  
//      	System.out.println("哈勃特b："+b);  
//      	System.out.println("哈勃特x："+x);
//    System.out.println("哈勃特yy："+yy);  
//    System.out.println("哈勃特y_avg："+y_avg); 
      	  //剩余平方和
      	  rss += (y-yy)*(y-yy);
      	  //计算拟合后的点坐标
      	  double ii = xy[0]-xFitst;
        	if(xy[0]>190000 && xy[0]<300000 && ii>=100){//如果日期为月度
	 		   ii = (Math.floor(ii/100))*12+(ii%100);
	 	   }
        	//System.out.println(xy[0]+"===xFitst:"+xFitst+"===ii:"+ii+"==xy[0]-xFitst:"+(xy[0]-xFitst));
      	  double X_over = xy[0];
//      	System.out.println("q0:"+q0+"==a0:"+a0+"==ii:"+ii+"==xy[0]:"+xy[0]+"==xFitst:"+xFitst);
      	  double Y_over =  ToolsUtil.stringToDouble(CmUtil.getMoney((aa*bb*nr*Math.pow(Math.E, -1*bb*ii))/(Math.pow((1+aa*Math.pow(Math.E, -1*bb*ii)), 2))+""));
      	  dataOut += "["+X_over+","+Y_over+"],";
    	  dataIn += "["+X_over+","+xy[1]+"],";
      	  
     	}
    	
    	//判定系数 r2
    	double pdxs = ess/tss;
//    	System.out.println("哈波特r:"+r);
//    	System.out.println("哈波特 tss:"+tss);
//    	System.out.println("哈波特 ess:"+ess);
//    	System.out.println("哈波特 rss:"+rss);
//    	System.out.println("哈波特 pdxs:"+pdxs);
//    	System.out.println("哈波特 dataIn:"+dataIn);
//    	System.out.println("哈波特 dataOut:"+dataOut);
    	
    	ClSrClnhjg clSrClnhjg = new ClSrClnhjg();
    	clSrClnhjg.setDatain(dataIn.length()>0?"["+dataIn.substring(0,dataIn.length()-1)+"]":"");
    	clSrClnhjg.setDataout(dataOut.length()>0?"["+dataOut.substring(0,dataOut.length()-1)+"]":"");
    	clSrClnhjg.setDjzs(0);
    	clSrClnhjg.setXgxs(Math.abs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(r+"", 6))));
    	clSrClnhjg.setPdxs(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(pdxs+"", 6)));
    	clSrClnhjg.setTss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(tss+"", 6)));
    	clSrClnhjg.setEss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(ess+"", 6)));
    	clSrClnhjg.setRss(ToolsUtil.stringToDouble(CmUtil.getMoneyCOMM(rss+"", 6)));
    	clSrClnhjg.setDjlx("哈波特");
    	
    	return clSrClnhjg; 
    }
    
  //递减拟合 求a、b
    public double[] getNh_AB(List<double[]> list){
    	double[] douS = new double[]{0,0,0};
    	String res = "";
    	double a_fz1 = 0; //分子1
    	double a_fz2 = 0; //分子2
    	double a_fz2_x = 0; //分子2 x
    	double a_fz2_y = 0; //分子2 y
    	double a_fm1 = 0; //分母1
    	double a_fm2 = 0; //分母2
    	double a_fm1_y = 0; //  y的平方求和
    	
    	double m = 0;//拟合源数据个数
    	double y = 0;
    	for(int i=0;list!=null && i<list.size();i++){
    	   double[] xy = list.get(i);
    	   if(i==0){
    		   m = list.size();
    	   }
    	   double x = xy[0];
    	   y = xy[1];
    	   
    	   a_fz1 += x*y;
    	   a_fz2_x += x;
    	   a_fz2_y += y;
    	   a_fm1 += x*x;
    	   a_fm1_y += y*y;
    	}
    	a_fz2 = a_fz2_x * a_fz2_y;
    	a_fm2 = a_fz2_x *a_fz2_x;
    	//系数b
    	double b = (m*a_fz1 - a_fz2)/(m*a_fm1-a_fm2);
    	//系数a
    	double a = (a_fm1*a_fz2_y - a_fz1*a_fz2_x)/(m*a_fm1-a_fm2);
    	
    	//计算相关系数 r
    	double x_avg = 0;//x平均值
    	double y_avg = 0;//y平均值
    	if(m>0){
    		x_avg = a_fz2_x/m;
    		y_avg = a_fz2_y/m;
    	}
    	double r = (a_fz1-m*x_avg*y_avg)/
    			Math.sqrt((a_fm1-m*x_avg*x_avg)*(a_fm1_y-m*y_avg*y_avg));
    	
//    	System.out.println("指数2 a:"+a);
//    	System.out.println("指数2 b:"+b);
//    	System.out.println("指数2 r:"+r);   
    	
    	douS[0] = a;
    	douS[1] = b;
    	douS[2] = r;
    	return douS;
    }
    
    public static void main(String[] args) {
      List<double[]> arr = new ArrayList();
//      arr.add(new double[]{1995,75});
//      arr.add(new double[]{1996,61});
//      arr.add(new double[]{1997,50.5});
//      arr.add(new double[]{1998,41.35});
//      arr.add(new double[]{1999,33.1});
//      arr.add(new double[]{2000,27.3});
//      arr.add(new double[]{2001,22.8});
//      arr.add(new double[]{2005,10.15});
//      arr.add(new double[]{2006,8.31});
//      arr.add(new double[]{2007,6.8});
//      arr.add(new double[]{1995,75});
//      arr.add(new double[]{1996,61});
//      arr.add(new double[]{1997,50.5});
//      arr.add(new double[]{1998,41.35});
//      arr.add(new double[]{1999,33.1});
//      arr.add(new double[]{2000,27.3});
//      arr.add(new double[]{2001,22.8});
//      arr.add(new double[]{2005,10.15});
//      arr.add(new double[]{2006,8.31});
//      arr.add(new double[]{2007,6.8});
      
//      arr.add(new double[]{199501,75});
//      arr.add(new double[]{199502,61});
//      arr.add(new double[]{199503,50.5});
//      arr.add(new double[]{199504,41.35});
//      arr.add(new double[]{199505,33.1});
//      arr.add(new double[]{199506,27.3});
//      arr.add(new double[]{199507,22.8});
//      arr.add(new double[]{199508,10.15});
//      arr.add(new double[]{199509,8.31});
//      arr.add(new double[]{199510,6.8});
      
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
      
      
      
      NhService nhService = new NhService();
//      指数
//      nhService.getNh_Zhishu(arr);
//      调和
//      nhService.getNh_Tiaohe(arr);
//      双曲
      nhService.getNh_Shuangqu(arr,0.05);
//      广义翁氏预测模型
//      nhService.getNh_Wongshi(arr);
      //哈波特模型
//      nhService.getNh_HaBeTe(arr);
      
    }
    
    
}
