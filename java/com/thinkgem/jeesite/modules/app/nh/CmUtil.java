package com.thinkgem.jeesite.modules.app.nh;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/*******************************************************************************
 * 此类作用:本类用来产生一个唯一的Long型主码,用于数据库中的标识唯一
 * 用法:在其他的类中用:::::com.vstruts.util.CmUtil.getPkId()::::::来产生
 ******************************************************************************/
public class CmUtil {
  private static long pkId; // 唯一Long型主码
  static Logger logger = Logger.getLogger(CmUtil.class);
  public static synchronized long getPkId() { 
    long lTmp = System.currentTimeMillis();
    if (pkId < lTmp) {
      pkId = lTmp;
    } 
    else {
      pkId++;
    }
    return pkId;
  }

  public static synchronized long getPkId(String YYYY_MM_DD) {
    if (YYYY_MM_DD == null) {
      return getPkId();
    }
    long lTmp = System.currentTimeMillis();
    if (getYYYY_MM_DD(new Timestamp(lTmp)).equals(YYYY_MM_DD)) {
      return getPkId();
    }
    if (pkId < lTmp) {
      pkId = lTmp;
    }
    else {
      pkId++;
    }
    return pkId % 0x5265c00L + 0x1b77400L + getTimeStamp(YYYY_MM_DD).getTime();
  }

  public static String Replace(String strin, String strRe, String strBy) {
    if (strin == null) {
      return null;
    }
    int iPos;
    String strTemp = "";
    iPos = strin.indexOf(strRe);
    while (iPos != -1) {
      strTemp = strTemp + strin.substring(0, iPos) + strBy;
      strin = strin.substring(iPos + strRe.length());
      iPos = strin.indexOf(strRe);
    }
    strin = strTemp + strin;
    return strin;
  }

  public static String ReplaceTagChar(String strin) {
    if (strin == null) {
      return strin;
    }
    if (strin.equals("")) {
      return strin;
    }
    strin = Replace(strin, "&", "&amp;");
    strin = Replace(strin, "<", "&lt;");
    strin = Replace(strin, ">", "&gt;");
    strin = Replace(strin, "\"", "&quot;");
    strin = Replace(strin, "\r\n", "<br>");
    strin = Replace(strin, "\n", "<br>");
//    strin = Replace(strin, "m3", "m&sup3;");
    return strin;
  }

  public static String ReplaceTagCharNew(String strin) { // 增加了单引号的处理,\的处理
    if (strin == null) {
      return strin;
    }
    if (strin.equals("")) {
      return strin;
    }
    strin = Replace(strin, "&", "&amp;");
    strin = Replace(strin, "<", "&lt;");
    strin = Replace(strin, ">", "&gt;");
    strin = Replace(strin, "\"", "&quot;");
    strin = Replace(strin, "\'", "&lsquo;");
    strin = Replace(strin, "\\", "&frasl;");
    strin = Replace(strin, "\r\n", "<br>");
    strin = Replace(strin, "\n", "<br>");
    strin = Replace(strin, " ", "&nbsp;");
    return strin;
  }
  public static String ReplaceTagChar(String strin, String ENTERChar) {
    if (strin == null) {
      return strin;
    }
    if (strin.equals("")) {
      return strin;
    }
    strin = Replace(strin, "&", "&amp;");
    strin = Replace(strin, "<", "&lt;");
    strin = Replace(strin, ">", "&gt;");
    strin = Replace(strin, "\"", "&quot;");
    if (ENTERChar == null) {
      return strin;
    }
    strin = Replace(strin, "\r\n", ENTERChar);
    strin = Replace(strin, "\n", ENTERChar);
    return strin;
  }

  public static boolean IsOverLength(String strin, int nLength) {
    try {
      byte[] b = strin.getBytes("GB2312");
      if (b.length > nLength) {
        return true;
      }
    }
    catch (Exception e) {}
    return false;
  }

  public static boolean IsEmail(String strin) {
    int nPos;
    String strTemp;
    String strLeft, strRight;
    boolean bFlag = true;

    if (!IsHalfWidth(strin)) {
      return false;
    }
    if (strin.indexOf("@") == -1) {
      return false;
    }
    if (strin.indexOf("@") != strin.lastIndexOf("@")) {
      return false;
    }

    strTemp = strin.replace('@', '1');
    strTemp = strTemp.replace('.', '1');
    strTemp = strTemp.replace('-', '1');
    strTemp = strTemp.replace('_', '1');
    if (!IsHalfEnglishAndNum(strTemp)) {
      return false;
    }

    nPos = strin.indexOf("@");
    strLeft = strin.substring(0, nPos);
    strRight = strin.substring(nPos + 1);

    if (strLeft.startsWith(".") == true) {
      return false;
    }
    if (strLeft.length() < 3) {
      return false;
    }

    if (strRight.indexOf(".") == -1) {
      return false;
    }
    if ( (strRight.indexOf(".") == 0) ||
        (strRight.lastIndexOf(".") == strRight.length() - 1)) {
      return false;
    }
    nPos = strin.lastIndexOf(".");
    strTemp = strin.substring(nPos + 1);
    if (IsHalfNum(strTemp)) {
      return false;
    }

    strTemp = strRight;
    nPos = strTemp.indexOf(".");
    while (bFlag) {
      strTemp = strTemp.substring(nPos + 1);
      nPos = strTemp.indexOf(".");
      if (nPos == 0) {
        return false;
      }
      if (nPos == -1) {
        bFlag = false;
      }
    }
    return true;
  }

  public static boolean IsHalfWidth(String strin) {
    try {
      byte[] b = strin.getBytes("GB2312");
      if (b.length == strin.length()) {
        return true;
      }
    }
    catch (Exception e) {}
    return false;
  }

  public static boolean IsHalfEnglishAndNum(String strin) {
    char c;
    for (int i = 0; i < strin.length(); i++) {
      c = strin.charAt(i);
      if (c <= 0x007A && c >= 0x0061) {
        continue;
      }
      if (c <= 0x005A && c >= 0x0041) {
        continue;
      }
      if (c <= 0x0039 && c >= 0x0030) {
        continue;
      }
      return false;
    }
    return true;
  }

  public static boolean IsHalfNum(String strin) {
    if (strin == null) {
      return false;
    }
    if (strin.length() == 0) {
      return false;
    }
    char c;
    for (int i = 0; i < strin.length(); i++) {
      c = strin.charAt(i);
      if (c < 0x0030 || c > 0x0039) {
        return false;
      }
    }
    return true;
  }

  public static boolean IsYear(String strin, boolean isExectNum) {
    if (strin == null) {
      return false;
    }
    if (!IsHalfNum(strin)) {
      return false;
    }
    int year = Integer.parseInt(strin);
    if (isExectNum) {
      if (year < 1000 || year > 3000) {
        return false;
      }
    }
    else {
      if (year < 1 || year > 1100) {
        return false;
      }
    }
    return true;
  }
  
  public static String getTomorrow(String rq, long num) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd");
			long timeLong = (format.parse(rq)).getTime();
			return format.format(new java.util.Date(timeLong + 3600 * 24 * 1000
					* num));
		} catch (Exception ex) {

		}
		return "";

	}
  //String rq= 201501;
  public static String getNextMonth(String rq, long num) {
	  int rqYear = ToolsUtil.stringToInt(rq.substring(0,4));
	  int rqMonth = ToolsUtil.stringToInt(rq.substring(4));
	  String newRq = rq;
	  if(num==0){
		  return newRq;
	  }
	  
	  if(rqMonth+num<=12){
		  if(rqMonth+num<10){
			  newRq =   rqYear + "0" +(rqMonth+num);
		  }else{
			  newRq =   rqYear +"" + (rqMonth+num) ;
		  }
	  }else{
		  int newYear = ToolsUtil.stringToInt(CmUtil.getMoneyCOMM((Math.floor((rqMonth+num)/12))+"",0));
		  int newMonth = ToolsUtil.stringToInt(CmUtil.getMoneyCOMM(((rqMonth+num)%12)+"",0));  
		  if(newMonth==0){
			  newYear = newYear-1;
			  newMonth = 12;
		  }
		  if(newMonth<10){
			  newRq =  (rqYear+newYear) + "0" +newMonth;
		  }else{
			  newRq =  (rqYear+newYear)+"" +newMonth ;
		  }
	  }
	  
		return newRq;

	}
  public static String getYesterday(String rq, long num) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd");
			long timeLong = (format.parse(rq)).getTime();
			return format.format(new java.util.Date(timeLong - 3600 * 24 * 1000
					* num));
		} catch (Exception ex) {

		}
		return "";

	}
  
  /**
	 * 根据起始日期,得到指定间隔内的所有日期数组
	 * 
	 * @param rq
	 * @return
	 */
  public static String[] getNextMonths(String rq, long num,String desc) {
    String months[]=null;
    try {
      months=new String[(int)num];

      int yy = Integer.valueOf(rq.substring(0, 4)).intValue();
      int mm = Integer.valueOf(rq.substring(5, 7)).intValue();
      int dd = Integer.valueOf(rq.substring(8, 10)).intValue();

      // 排列的顺序，由大到小
      if (desc != null) {
        for (int i = (int)num-1; i >= 0; i--) {
          GregorianCalendar g = new GregorianCalendar(yy, mm - 1, dd);
          g.add(GregorianCalendar.MONTH, -i);
// System.out.println("i=="+i);
          java.util.Date d = g.getTime();
          SimpleDateFormat df = new SimpleDateFormat(
              "yyyy-MM");
          months[i] = df.format(d);
        }
      }
      else {
        // 由小到大
        for (int i = 0; i < (int)num; i++) {
          GregorianCalendar g = new GregorianCalendar(yy, mm - 1, dd);
          g.add(GregorianCalendar.MONTH, i);
          java.util.Date d = g.getTime();
          SimpleDateFormat df = new SimpleDateFormat(
              "yyyy-MM");
          months[i] = df.format(d);
        }
      }


    }
    catch (Exception ex) {

    }
    return months;
  }
  
  public static boolean IsMonth(String strin) {
    if (strin == null) {
      return false;
    }
    if (!IsHalfNum(strin)) {
      return false;
    }
    int month = Integer.parseInt(strin);
    if (month < 1 || month > 12) {
      return false;
    }
    return true;
  }

  public static boolean IsDayOfMonth(String strin) {
    if (strin == null) {
      return false;
    }
    if (!IsHalfNum(strin)) {
      return false;
    }
    int date = Integer.parseInt(strin);
    if (date < 1 || date > 31) {
      return false;
    }
    return true;
  }

  public static String getCurrentFileName() {
    GregorianCalendar now = new GregorianCalendar();
    String fileName = "";
    fileName += now.get(now.YEAR);
    fileName += now.get(now.MONTH) + 1;
    if (fileName.length() < 6) {
      fileName = fileName.substring(0, 4) + "0" +
          fileName.substring(fileName.length() - 1);
    }
    fileName += now.get(now.DAY_OF_MONTH);
    if (fileName.length() < 8) {
      fileName = fileName.substring(0, 6) + "0" +
          fileName.substring(fileName.length() - 1);
    }
    fileName += now.get(now.HOUR_OF_DAY);
    if (fileName.length() < 10) {
      fileName = fileName.substring(0, 8) + "0" +
          fileName.substring(fileName.length() - 1);
    }
    fileName += now.get(now.MINUTE);
    if (fileName.length() < 12) {
      fileName = fileName.substring(0, 10) + "0" +
          fileName.substring(fileName.length() - 1);
    }
    fileName += now.get(now.SECOND);
    if (fileName.length() < 14) {
      fileName = fileName.substring(0, 12) + "0" +
          fileName.substring(fileName.length() - 1);
    }
    fileName += now.get(now.MILLISECOND);
    if (fileName.length() < 17) {
      fileName = fileName.substring(0, 14) + "0" + fileName.substring(14);
    }
    if (fileName.length() < 17) {
      fileName = fileName.substring(0, 14) + "0" + fileName.substring(14);
    }
    return fileName;
  }

  
  public static void creatFile(String filePath, String fileContent) {
    File toFile = new File(filePath);
    if (toFile.exists()) {
      toFile.delete();
    }
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(toFile);
      fileOutputStream.write(fileContent.getBytes());
      fileOutputStream.close();
    }
    catch (FileNotFoundException e) {
//      Debug.debug.log("CmUtil", toFile.getPath() + " Not Founded--", e);
    }
    catch (IOException e) {
//      Debug.debug.log("CmUtil", toFile.getPath(), e);
    }
  }

  public static void deleteFile(String filePath) {
    File toFile = new File(filePath);
    if (toFile.exists()) {
      toFile.delete();
    }
  }

  public static String getYYYY_MM_DD(Timestamp timestamp) {
    GregorianCalendar g = new GregorianCalendar();
    g.setTime(timestamp);
    GregorianCalendar _tmp = g;
    int year = g.get(1);
    GregorianCalendar _tmp1 = g;
    int month = g.get(2) + 1;
    GregorianCalendar _tmp2 = g;
    int day = g.get(5);
    return year + "-" + (month >= 10 ? month + "" : "0" + month) + "-" +
        (day >= 10 ? day + "" : "0" + day);
  }

  public static Timestamp getTimeStamp(String YYYY_MM_DD) {

    int year;
    int month;
    int day;
    year = 1900;
    month = 0;
    day = 1;
    try {
      year = Integer.parseInt(YYYY_MM_DD.substring(0, 4));
      month = Integer.parseInt(YYYY_MM_DD.substring(5, 7)) - 1;
      day = Integer.parseInt(YYYY_MM_DD.substring(8, 10));
    }
    catch (Exception e) {}
    GregorianCalendar g = new GregorianCalendar(year, month, day);
    return new Timestamp(g.getTime().getTime());
  }

  public static Timestamp getTimeStampNew(String YYYY_MM_DD_hh_mm) {

    int year;
    int month;
    int day;
    year = 1900;
    month = 0;
    day = 1;
    int hour = 0;
    int minite = 0;

    try {
      year = Integer.parseInt(YYYY_MM_DD_hh_mm.substring(0, 4));
      month = Integer.parseInt(YYYY_MM_DD_hh_mm.substring(5, 7)) - 1;
      day = Integer.parseInt(YYYY_MM_DD_hh_mm.substring(8, 10));
      hour = Integer.parseInt(YYYY_MM_DD_hh_mm.substring(11, 13));
      minite = Integer.parseInt(YYYY_MM_DD_hh_mm.substring(14, 16));
    }
    catch (Exception e) {}
    GregorianCalendar g = new GregorianCalendar(year, month, day, hour, minite);
    return new Timestamp(g.getTime().getTime());
  }

  public static String getMoney(String source) {
    double d;
    if (source == null) {
      return "0.00";
    }
    source = source.trim();
    d = 0.0D;
    try {
      d = Double.parseDouble(source);
    }
    catch (Exception e) {
      return source;
    }
    DecimalFormat df = new DecimalFormat(",###.00");
    String cc = df.format(d);
    if(cc.indexOf(".") == 0){
    	cc = "0" + cc;
    }
    return cc;
  }
  public static String getMoney2(String source) {
	    double d;
	    if (source == null ||ToolsUtil.stringToDouble(source)==0) {
	      return "";
	    }
	    source = source.trim();
	    d = 0.0D;
	    try {
	      d = Double.parseDouble(source);
	    }
	    catch (Exception e) {
	      return source;
	    }
	    DecimalFormat df = new DecimalFormat(",###.00");
	    String cc = df.format(d);
	    if(cc.indexOf(".") == 0){
	    	cc = "0" + cc;
	    }
	    return cc;
	  }

  public static String getModelProperty(String columnName) {
    if (columnName == null || columnName.length() == 0) {
      return null;
    }
    columnName = columnName.toLowerCase();
    for (columnName = Replace(columnName, "__", "-");
         columnName.indexOf("_") == 0; columnName = columnName.substring(1)) {
      ;
    }
    for (; columnName.indexOf("_") == columnName.length() - 1;
         columnName = columnName.substring(0, columnName.length() - 1)) {
      ;
    }
    String lowChar;
    for (; columnName.indexOf("_") > 0;
         columnName = Replace(columnName, "_" + lowChar, lowChar.toUpperCase())) {
      int i = columnName.indexOf("_");
      lowChar = columnName.charAt(i + 1) + "";
    }

    return columnName;
  }

  /**
	 * 
	 * @param source
	 *            String
	 * @param decimalDigits
	 *            int
	 * @return String
	 */
  public static String getFormDouble(String sourceStr) {
    if (sourceStr != null && (sourceStr.equals("0") || sourceStr.equals("0.0"))) {
      return "";
    }
    String tem = sourceStr; // getMoneyCOMM(sourceStr,8);
    while (tem.substring(tem.indexOf(".")).endsWith("0")) {
      tem = tem.substring(0, tem.length() - 1);
    }
    if (tem.endsWith(".")) tem = tem.substring(0, tem.length() - 1);
    return tem;

  }

  public static String getFormDouble(double sourceDou) {
    return getFormDouble(sourceDou + "");
  }

  public static String getMoneyCOMM(String source, int decimalDigits) {
    String rtValue;
    double d;
    if (decimalDigits < 0)
      decimalDigits = 2;
    rtValue = "0";
    if (decimalDigits > 0)
      rtValue = rtValue + ".";
    for (int i = 0; i < decimalDigits; i++)
      rtValue = rtValue + "0";

    if (source == null)
      return rtValue;
    source = source.trim();
    d = 0.0D;
    try {
      d = Double.parseDouble(source);
    }
    catch (Exception ex) {
    }
    rtValue = "#" + rtValue.substring(1);
    DecimalFormat df = new DecimalFormat(rtValue);
    return df.format(d);
  }

  public static String getMoneyCOMM(String source) {
    return getMoneyCOMM(source, 2);
  }

  /**
	 * 根据长度得到一个随机字符串
	 * 
	 * @param length
	 *            int
	 * @return String
	 */
  public static String getRandomStr(int length) {
    String outStr = "";
    Random ran = new Random();
    ran.setSeed(getPkId());
    while (length > 0) {
      String t = Long.toString(ran.nextLong());
      if (length > t.length()) {
        t = t.substring(0, length);
      }
      outStr += t.charAt(length);
      length--;
    }
    return outStr;
  }

  public static String replaceMark(String str, String destStr, Object[] srcStr) {
    // 返回值
    StringBuffer retVal = new StringBuffer();
    // 记录查找到相似字符的位置
    int findStation = str.indexOf(destStr);
    int resumStation = 0;
    int i = 0;
    while (findStation > -1) {
      retVal.append(str.substring(resumStation, findStation));
      if (srcStr != null && srcStr.length > i) {
        retVal.append(srcStr[i]);
      }
      else {
        retVal.append("[没有匹配参数]");
      }
      resumStation = findStation + destStr.length();
      findStation = str.indexOf(destStr, resumStation);
      i++;
    }
    if (srcStr != null && srcStr.length - 1 > i) {
      retVal.append("参数长度大于 SQL 中的问号数 ！");
    }

    return retVal.toString();

  }

  public static double doubleValueOf(Object obj) {
    double result = 0.00;
    if (obj != null) {
      String temp = obj.toString().replaceAll(",","");
      try {
        result = Double.valueOf(temp).doubleValue();
      }
      catch (Exception ex) {
        result = 0.00;
      }
    }
    return result;
  }

  public static long longValueOf(Object obj) {
    long result = 0;
    if (obj != null) {
      try {
        String temp = obj.toString();
        result = Long.valueOf(temp).longValue();
      }
      catch (Exception ex) {
        result = 0;
      }
    }
    return result;
  }

  public static int intValueOf(Object obj) {
    int result = 0;
    if (obj != null) {
      try {
        String temp = obj.toString();
        result = Integer.valueOf(temp).intValue();
      }
      catch (Exception ex) {
        result = 0;
      }
    }
    return result;
  }

  /**
	 * 如果str为空，则返回False。
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
  public static boolean getIsNull(String str) {
    if (str == null || str.equals("") || str.equals("null") ||
        str.length() <= 0) {
      return false;
    }
    return true;
  }

  public static String getBlank(int len) {
    String blank = "";
    for (int i = 0; i < len; i++) {
      blank += "&nbsp;&nbsp;";
    }
    return blank;
  }

  
  /**
	 * 得到今天的日期
	 * 
	 * @return String
	 */
  public static String getToday() {
    GregorianCalendar g = new GregorianCalendar();
    Timestamp today = new Timestamp(g.getTime().getTime());
    return today.toString().substring(0, 10);
  }
  
  /**
	 * 根据日期取得上一个月份。
	 * 
	 * @param rq
	 *            日期，例如“2003-08”，或者“2003-08-02”
	 * @return java.lang.String 返回rq的上一个月的字符串，例如rq="2003-08-02"，则返回"2003-07"
	 */
	public static String getPreMonth(String rq) {
		long timeLong = 0;
		if (rq.length() > 7)
			rq = rq.substring(0, 7);
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM");
			timeLong = (format.parse(rq)).getTime();
		} catch (Exception e) {
		}

		Timestamp d = new Timestamp(timeLong);
		String returnStr = "";
		int i = d.getMonth();
		String sm = ""; // 返回的字符串不满足两位时，在前面加一个"0"。
		int n = 0; // 如果是某年的12月份，则表示下一个月为明年的第一个月，此时年份加1，否则加0。

		if (i == 0) {
			n = -1;
			i = 12;
		}
		if (i < 10)
			sm = "0";
		returnStr = (d.getYear() + 1900 + n) + "-" + sm + i;
		return returnStr;
	}


  public static int getMaxDays(String ymd) {
    Hashtable days = new Hashtable();
    days.put("01", "31");
    days.put("03", "31");
    days.put("04", "30");
    days.put("05", "31");
    days.put("06", "30");
    days.put("07", "31");
    days.put("08", "31");
    days.put("09", "30");
    days.put("10", "31");
    days.put("11", "30");
    days.put("12", "31");
    int mm = 0, yy = 0, dd = 0;
    if (ymd.length() < 7) {
      return 0;
    }
    try {
      yy = Integer.parseInt(ymd.substring(0, 4));
    }
    catch (Exception ex) {}
    try {
      mm = Integer.parseInt(ymd.substring(5, 7));
    }
    catch (Exception ex) {}
    if (mm == 2 && yy > 0) {
      if ( (yy % 100 != 0 && yy % 4 == 0) || yy % 400 == 0) {
        days.put("02", "29");
      }
      else {
        days.put("02", "28");
      }
    }
    dd = Integer.parseInt( (String) days.get(ymd.substring(5, 7)));
    return dd;
  }

	/**
	 * 根据日期格式得到long型数据
	 */
	public static long gainRqForlong(String rq) {
		long tt = 0;
		String strFormat = "yyyy-MM-dd HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(
				strFormat);
		try {
			tt = (format.parse(rq)).getTime();
		} catch (Exception ex) {
		}
		return tt;
	}
  
  public static String getOptionValueById(ArrayList al, String id) {
    if (al == null || al.size() == 0)
      return "";
    ArrayList idArray = (ArrayList) al.get(0);
    ArrayList nameArray = (ArrayList) al.get(1);
    for (int i = 0; i < idArray.size() && i < nameArray.size(); i++) {
// System.out.println(idArray.get(i) +" "+id +"
// "+idArray.get(i).toString().equals(id));
      id = (id == null) ? "" : id.trim();
      String addObj = idArray.get(i).toString().trim();
      if (id.equals(addObj)) {
        return nameArray.get(i).toString();
      }
    }
    return "";
  }
  // rq1:20040604 rq2:20050908 rq1<rq2
  public static int getDaysFromTwoRq(String rq1, String rq2) {
    if (Integer.parseInt(rq1)>Integer.parseInt(rq2) || rq1 == null || "".equals(rq1)||rq2 == null || "".equals(rq2)){
      return 0;
    }
// logger.error("??????????>>>>>2");
    int days = 0;
    if(rq1.substring(0,6).equals(rq2.substring(0,6))){
      days = Integer.parseInt(rq2.substring(6,8)) - Integer.parseInt(rq1.substring(6,8));
// logger.error("1========"+Integer.parseInt(rq2.substring(6,8)));
// logger.error("2========"+Integer.parseInt(rq1.substring(6,8)));
// logger.error("========"+days);
    }else if(rq1.substring(0,4).equals(rq2.substring(0,4))){
      for(int i=Integer.parseInt(rq1.substring(4,6));i<Integer.parseInt(rq2.substring(4,6));i++){
        String day = "";
        if(i<10){
          day = "0"+i;
        }else{
          day = i+"";
        }
        days += CmUtil.getMaxDays(rq1.substring(0,4)+"-"+day);
      }
      days = days - Integer.parseInt(rq1.substring(6,8))+Integer.parseInt(rq2.substring(6,8));
    }else{
      // 20040604
      // 20050908
      for(int i=Integer.parseInt(rq1.substring(4,6));i<=12;i++){
        String day = "";
        if(i<10){
          day = "0"+i;
        }else{
          day = i+"";
        }
        days += CmUtil.getMaxDays(rq1.substring(0,4)+"-"+day);
      }
      days = days - Integer.parseInt(rq1.substring(6,8));
      for(int i=1;i<Integer.parseInt(rq2.substring(4,6));i++){
        String day = "";
        if(i<10){
          day = "0"+i;
        }else{
          day = i+"";
        }
        days += CmUtil.getMaxDays(rq1.substring(0,4)+"-"+day);
      }
      days = days + Integer.parseInt(rq2.substring(6,8));
      days += 365*(Integer.parseInt(rq2.substring(0,4))-Integer.parseInt(rq1.substring(0,4))-1);
    }

    return days;
  }
  /**
	 * 根据天数获得新的日期 //JDk中有bug，一旦加到超过24天，所取得的日期就会出错
	 * //例：九月十日，若直接加3600*24*1000*31.则取得的日期时八月的，郁闷！！！ //故加入一个过度日期，经测试24天可以。 Date
	 * tmpDate = new Date(startLong + 3600 * 24* 1000 *24);
	 * 
	 * @param rqStart
	 *            String
	 * @param days
	 *            int
	 * @return String
	 */
   public static String getRqByDays(String rqStart, int days) {
       if(rqStart == null || "".equals(rqStart)) {
         return null;
       }
       String rq_str = "";
     try {
       SimpleDateFormat format = new SimpleDateFormat(
           "yyyy-MM-dd");

       long timeLong = (format.parse(rqStart)).getTime();
       int temp = 0;
       // 如果小于等于 24 直接转化
       if(days <= 24) {
         rq_str = format.format(new java.util.Date(timeLong +
                                               3600 * 24 * 1000 * days));
       // 如果大于 24 需要循环（递归）判断
       } else {
        temp = days - 24;
        rq_str = getRqByDays(rqStart,24);
        rq_str = getRqByDays(rq_str,temp);
       }
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
     return rq_str;
   }

   /**
	 * 取得作业监督系统的ID 计划部分与此不同,但是暂无更多了解,暂时共用 hh-mm-ss hhss 20位
	 * 
	 * @return String
	 */
   public static String getZyjdPkId()
       {
         String outStr = new Timestamp(System.currentTimeMillis()).toString() + "001";
         outStr = outStr.replaceAll("-", "");
         outStr = outStr.replaceAll(":", "");
         outStr = outStr.replaceAll(" ", "");
         outStr = outStr.substring(0, 14) + outStr.substring(15, outStr.length());
         return outStr;
       }
   /**
	 * 取得作业监督系统的ID 计划部分与此不同,但是暂无更多了解,暂时共用 hh-mm-ss hhss 17位
	 * 
	 * @return String
	 */
   public static String getCurrentTime()
       {
         String outStr = new Timestamp(System.currentTimeMillis()).toString();
         outStr = outStr.replaceAll("-", "");
         outStr = outStr.replaceAll(":", "");
         outStr = outStr.replaceAll(" ", "");
         outStr = outStr.substring(0, 14) + outStr.substring(15, outStr.length());
         if(outStr.length() == 16){
           outStr = outStr + "0";
         } else if(outStr.length() == 15){
           outStr = outStr + "00";
         }
         return outStr;
       }

   public static int countDays(String begin,String end){  
	   int days = 0;    
	   if(end==null || "".equals(end)){
		   return days; 
	   }
	   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	   Calendar c_b = Calendar.getInstance();  
	   Calendar c_e = Calendar.getInstance();    
	   int flg =0;
	   try{   
		   c_b.setTime(df.parse(begin));   
		   c_e.setTime(df.parse(end)); 
		   if(!c_b.before(c_e)){
			   c_b.setTime(df.parse(end));   
			   c_e.setTime(df.parse(begin)); 
			   flg=1;
		   }
		   while(c_b.before(c_e)){    
			   if(flg==1){
				   days--;  
			   }else{
				   days++;  
			   }
			   
			   c_b.add(Calendar.DAY_OF_YEAR, 1);   
			   }     
		   }catch(ParseException pe){   
			   pe.printStackTrace();  
			}    
		   
		   
		   return days; 
		}
   
   /**
   * 判断当前日期是星期几<br>
   * <br>
   * @param pTime 要判断的时间<br>
   * @return dayForWeek 判断结果<br>
   */
   public static String getWeekday(String date){//必须yyyy-MM-dd 
	    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); 
	    SimpleDateFormat sdw = new SimpleDateFormat("E"); 
	    java.util.Date d = null; 
	    try { 
	    d = sd.parse(date); 
	    } catch (ParseException e) { 
	    e.printStackTrace(); 
	    } 
	    return sdw.format(d); 
	    } 
   /**把数据源HashMap转换成json
	 * @param map 
	 */
	public static String hashMapToJson(HashMap map) {
		String string = "{";
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry e = (Entry) it.next();
			string += "\"" + e.getKey() + "\":";
			string += "\"" + e.getValue() + "\",";
		}
		string = string.substring(0, string.lastIndexOf(","));
		string += "}";
		return string;
	}

     }

