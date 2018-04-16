package com.thinkgem.jeesite.modules.app.nh;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ToolsUtil {

	public static String showShot(String showString, int length) {
		if (showString == null)
			showString = "";
		if (length < 2)
			length = 2;
		String endStr = "<td nowrap class=\"td1\" align=left ";
		if (showString.length() > length - 1)
			endStr += " title=\""
					+ CmUtil.Replace(CmUtil.ReplaceTagChar(showString), "<br>",
							"\n") + "\"";
		endStr += ">";
		endStr += CmUtil
				.Replace(
						"&nbsp;"
								+ CmUtil
										.ReplaceTagChar(showString.length() > (length - 1) ? showString
												.substring(0, length - 2)
												+ "..."
												: showString), "<br>", " ");
		endStr += "</td>";
		return endStr;
	}

	public static String doubleToMoneyKongNo(double money) {
		if (money == 0) {
			return "";
		}
		return CmUtil.Replace(CmUtil.getMoney(String.valueOf(money)), ",", "");
	}

	public static String showShotHaveLink(String showString, int length,
			String linkUrl) {
		if (showString == null)
			showString = "";
		if (length < 2)
			length = 2;
		String endStr = "<td nowrap";
		if (showString.length() > length - 1)
			endStr += " title=\""
					+ CmUtil.Replace(CmUtil.ReplaceTagChar(showString), "<br>",
							"\n") + "\"";
		endStr += "><a href=\"" + linkUrl + "\">";
		endStr += CmUtil
				.Replace(
						"&nbsp;"
								+ CmUtil
										.ReplaceTagChar(showString.length() > (length - 1) ? showString
												.substring(0, length - 2)
												+ "..."
												: showString), "<br>", " ");
		endStr += "</a></td>";
		return endStr;
	}



	public static String WorkType(int workId) {
		String workTypeName = null;
		if (workId == 0)
			workTypeName = "其他";
		if (workId == 1)
			workTypeName = "措施";
		if (workId == 2)
			workTypeName = "维护";

		return workTypeName;
	}



	public static String getSuperDeptID(String deptID) {
		if (deptID.length() < 12)
			return deptID;
		String superDept = null;
		if (deptID.length() > 8) {
			superDept = deptID.substring(0, 8);
		}
		if (superDept == null)
			superDept = "";
		return superDept;
	}

	// 从long到String时间
	public static String pkidToString(long pkid) {
		String nowDate = null;
		if (pkid == 0)
			return "";
		Timestamp timestampDate = new Timestamp(pkid);
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		return nowDate;
	}

	public static String changeDateFormat(String oralString) {
		if (oralString == null)
			return "";
		String endString = CmUtil.Replace(oralString, "-", "/");
		return endString;
	}

	public static String changeDateFormat(long longDate) {
		String oralString = ToolsUtil.pkidToString(longDate);
		if (oralString == null)
			return "";
		String endString = CmUtil.Replace(oralString, "-", "/");
		return endString;
	}

	// 得到当前时间到天
	public static String getNowDate() {
		String nowDate = null;
		Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		return nowDate;
	}

	// 得到本周的第一天
	public static String getWeekFirstDay() {
		String outDate = null;
		GregorianCalendar g = new GregorianCalendar();
		if (g.get(g.DAY_OF_WEEK) == 1) {
			g.set(g.WEEK_OF_YEAR, g.get(g.WEEK_OF_YEAR) - 1);
		}
		g.set(g.DAY_OF_WEEK, 2);
		return (formatDate(g.get(g.YEAR) + "") + "-"
				+ formatDate((g.get(g.MONTH) + 1) + "") + "-" + formatDate(g
				.get(g.DAY_OF_MONTH)
				+ ""));
	}

	public static String longDateToChDate1(long longTime) {
		if (longTime == 0)
			return "年月日";
		if (longTime == 1)
			longTime = System.currentTimeMillis();
		String tempDate = CmUtil.getYYYY_MM_DD(new Timestamp(longTime));
		String endDate = tempDate.substring(0, 4) + "年 "
				+ Integer.parseInt(tempDate.substring(5, 7)) + "月 "
				+ Integer.parseInt(tempDate.substring(8, 10)) + "日";
		return endDate;
	}

	public static String formatDate(String x) {
		if (x == null || x == "")
			return "00";
		if (x.length() == 1)
			return "0" + x;
		return x;
	}

	public static String getNowAPDate(int day) {
		String nowDate = null;
		GregorianCalendar g = new GregorianCalendar();
		g.set(g.DAY_OF_MONTH, g.get(g.DAY_OF_MONTH) + day);
		nowDate = g.get(g.YEAR) + "-" + formatDate((g.get(g.MONTH) + 1) + "")
				+ "-" + formatDate((g.get(g.DAY_OF_MONTH) + ""));

		return nowDate;
	}

	// 转化为中国时间
	public static String longDateToChDate(long longTime) {
		if (longTime == 0)
			return "&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日";
		if (longTime == 1)
			longTime = System.currentTimeMillis();
		String tempDate = CmUtil.getYYYY_MM_DD(new Timestamp(longTime));
		String endDate = tempDate.substring(0, 4) + "年&nbsp;"
				+ Integer.parseInt(tempDate.substring(5, 7)) + "月&nbsp;"
				+ Integer.parseInt(tempDate.substring(8, 10)) + "日&nbsp;";
		return endDate;
	}

	public static String longDateToChDate2(long longTime) {
		if (longTime == 0)
			return "年月日";
		if (longTime == 1)
			longTime = System.currentTimeMillis();
		String tempDate = CmUtil.getYYYY_MM_DD(new Timestamp(longTime));
		String endDate = tempDate.substring(0, 4) + "年"
				+ Integer.parseInt(tempDate.substring(5, 7)) + "月"
				+ Integer.parseInt(tempDate.substring(8, 10)) + "日";
		return endDate;
	}

	// 得到当前时间到分钟
	public static String getNowTime() {
		GregorianCalendar g = new GregorianCalendar();
		return formatDate(g.get(g.YEAR) + "") + "-"
				+ formatDate((g.get(g.MONTH) + 1) + "") + "-"
				+ formatDate(g.get(g.DAY_OF_MONTH) + "") + " "
				+ formatDate(g.get(g.HOUR_OF_DAY) + "") + ":"
				+ formatDate(g.get(g.MINUTE) + "");
	}

	// 从String到TimeStamp的转换
	public static Timestamp StringToTimestamp(String strTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm");
		try {
			return new Timestamp(sdf.parse(strTime).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return new Timestamp(System.currentTimeMillis());
		}
	}
	  public static Timestamp StringToTimestamp1(String strTime) {
		    SimpleDateFormat sdf = new SimpleDateFormat(
		        "yyyy-MM-dd");
		    try {
		      return new Timestamp(sdf.parse(strTime).getTime());
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		      return new Timestamp(System.currentTimeMillis());
		    }
		  }

	// 从String到TimeStamp的转换
	public static String TimestampToString(Timestamp timestampTime) {
		if (timestampTime == null)
			return "";
		String StrTime = (String) timestampTime.toString();
		StrTime = StrTime.substring(0, 16);
		return StrTime;
	}

	// 从String到TimeStamp的转换
	public static String TimestampToString1(Timestamp timestampTime) {
		if (timestampTime == null)
			return "";
		String StrTime = (String) timestampTime.toString();
		StrTime = StrTime.substring(0, 10);
		return StrTime;
	}

	// 根据lang型时间得到 String型时间
	public static String getTimeLongToString(long longTime) {
		if (longTime == 0)
			return "";
		return CmUtil.getYYYY_MM_DD(new Timestamp(longTime));
	}

	// 根据int型日期得到 String型时间
	public static String getDateIntToString(int intTime) {
		if (intTime < 100000)
			return "";
		String StrTime = String.valueOf(intTime);
		return StrTime = StrTime.substring(0, 4) + "-"
				+ StrTime.substring(4, StrTime.length());
	}

	// 根据int型时间得到 String型时间
	public static String getTimeIntToString(int intTime) {
		if (intTime < 1000)
			return ToolsUtil.getNowDate();
		String StrTime = String.valueOf(intTime);
		String outStr = "";
		outStr = StrTime.substring(0, 4);
		if (StrTime.length() > 4)
			outStr += "-" + StrTime.substring(4, 6);
		if (StrTime.length() > 6)
			outStr += "-" + StrTime.substring(6, StrTime.length());
		return outStr;
	}

	public static int getTimeStringToint(String strTime) {
		if (strTime == null)
			strTime = ToolsUtil.getNowDate();
		// if(strTime.length()<10)return 0;
		strTime = CmUtil.Replace(strTime, "-", "");
		strTime = CmUtil.Replace(strTime, "/", "");
		return ToolsUtil.stringToInt(strTime);
	}

	public static java.sql.Date getTimeStringToDate(String strTime) {
		return new java.sql.Date(CmUtil.getTimeStamp(strTime).getTime());
	}

	public static String getTimeDateToString(java.sql.Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	// 根据int型时间得到 String型时间
	public static int getDateStringToInt(String strTime) {
		if (strTime == null)
			strTime = "";
		if (strTime.length() < 6)
			return 0;
		return ToolsUtil.stringToInt(CmUtil.Replace(strTime, "-", ""));
	}

	// 得到给定时间的前一天
	public static Timestamp getTimePriviousDay(Timestamp date) {
		long time = date.getTime();
		time = time - 24 * 60 * 60 * 1000;
		return new Timestamp(time);
	}

	/**
	 * 得到给定日期的前n天
	 * 
	 * @param String
	 *            date YYYY-MM-DD
	 * @return String dateYYYY-MM-DD
	 * @author lxb 2007-1-31
	 */
	public static String getTimePriTenDay(String date, int n) {
		Date temDate = ToolsUtil.getTimeStringToDate(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(temDate);
		gc.add(5, -n);
		gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
		return df.format(gc.getTime());
	}

	// 把string型转为longxing
	public static long getTimeStringToLong(String strTime) {
		if (strTime == null)
			strTime = ToolsUtil.getNowDate();
		if (strTime.length() < 10) {
			String[] temArr = strTime.split("-");
			if (temArr[1].length() < 2)
				temArr[1] = "0" + temArr[1];
			if (temArr[2].length() < 2)
				temArr[2] = "0" + temArr[2];
			strTime = "";
			for (int i = 0; i < temArr.length; i++) {
				strTime += ("-" + temArr[i]);
			}
			strTime = strTime.substring(1);
		}
		return CmUtil.getTimeStamp(strTime).getTime();
	}

	public static String getNowYear() {
		String nowDate = null;
		String newYear = null;
		Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		newYear = nowDate.substring(0, 4);
		return newYear;
	}

	// 得到当前月
	public static String getNowMonth() {
		String nowDate = null;
		String newMonth = null;
		Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		newMonth = nowDate.substring(5, 7);
		return newMonth;
	}
	
	//获取当前月份的前一月
	public static String getPreMonth(){
		String preMonth = null;
		String nowMonth = getNowMonth();
		if("1".equals(nowMonth)){
			preMonth = "12";
		}else{
			preMonth = String.valueOf(Integer.parseInt(nowMonth)-1);
		}
		return preMonth;
	}

	// 得到当前季度
	public static String getNowQuarter() {
		String nowDate = null;
		String nowQuarter = null;
		Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		String nowMonth = nowDate.substring(5, 7);
		if (Integer.parseInt(nowMonth) < 4) {
			nowQuarter = "1";
		}
		;
		if (Integer.parseInt(nowMonth) > 3 && Integer.parseInt(nowMonth) < 7) {
			nowQuarter = "2";
		}
		;
		if (Integer.parseInt(nowMonth) > 6 && Integer.parseInt(nowMonth) < 10) {
			nowQuarter = "3";
		}
		;
		if (Integer.parseInt(nowMonth) > 9 && Integer.parseInt(nowMonth) < 13) {
			nowQuarter = "4";
		}
		;
		return nowQuarter;
	}

	// 得到当前旬度
	public static String getNowTendays() {
		String nowDate = null;
		String nowTendays = null;
		Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		String nowMonth = nowDate.substring(8, nowDate.length());
		if (Integer.parseInt(nowMonth) < 11) {
			nowTendays = "1";
		}
		;
		if (Integer.parseInt(nowMonth) > 10 && Integer.parseInt(nowMonth) < 21) {
			nowTendays = "2";
		}
		;
		if (Integer.parseInt(nowMonth) > 20) {
			nowTendays = "3";
		}
		;
		return nowTendays;
	}

	// 得到当前时间的前几天的旬度
	public static String getNowTendays(String yyyymmdd, int beforDays) {
		String nowTendays = null;
		String nowDate = ToolsUtil.getTimePriTenDay(yyyymmdd, beforDays);
		// Timestamp timestampDate = new Timestamp(System.currentTimeMillis());
		// nowDate = CmUtil.getYYYY_MM_DD(timestampDate);
		String nowMonth = nowDate.substring(8, nowDate.length());
		if (Integer.parseInt(nowMonth) < 11) {
			nowTendays = "1";
		}
		;
		if (Integer.parseInt(nowMonth) > 10 && Integer.parseInt(nowMonth) < 21) {
			nowTendays = "2";
		}
		;
		if (Integer.parseInt(nowMonth) > 20) {
			nowTendays = "3";
		}
		;
		return nowTendays;
	}

	// 得到中文的时间
	public static String getNowDateChin(int timesplice) {
		String dateChin = null;
		String timeSpliceStr = String.valueOf(timesplice);
		if (timeSpliceStr.length() == 4)
			dateChin = timeSpliceStr + "年";
		if (timeSpliceStr.length() == 5) {
			dateChin = timeSpliceStr.substring(0, 4) + "年第"
					+ timeSpliceStr.substring(4, 5) + "季度";
		}
		if (timeSpliceStr.length() == 6) {
			dateChin = timeSpliceStr.substring(0, 4) + "年"
					+ timeSpliceStr.substring(4, 6) + "月";
		}
		if (timeSpliceStr.length() == 7) {
			dateChin = timeSpliceStr.substring(0, 4) + "年"
					+ timeSpliceStr.substring(4, 6) + "月";
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 1) {
				dateChin += "上旬";
			}
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 2) {
				dateChin += "中旬";
			}

			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 3) {
				dateChin += "下旬";
			}
		}
		if (timeSpliceStr.length() == 8) { // 20040101
			dateChin = dateChin = timeSpliceStr.substring(0, 4) + "年"
					+ timeSpliceStr.substring(4, 6) + "月"
					+ timeSpliceStr.substring(6, 8) + "日";
		}
		return dateChin;
	}

	// 得到中文的时间
	public static String getNowDateFormat(int timesplice) {
		String dateChin = null;
		String timeSpliceStr = String.valueOf(timesplice);
		if (timeSpliceStr.length() == 4)
			dateChin = timeSpliceStr;
		if (timeSpliceStr.length() == 5) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 5);
		}
		if (timeSpliceStr.length() == 6) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6);
		}
		if (timeSpliceStr.length() == 7) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6) + "-";
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 1) {
				dateChin += "1";
			}
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 2) {
				dateChin += "2";
			}

			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 3) {
				dateChin += "3";
			}
		}
		if (timeSpliceStr.length() == 8) { // 20040101
			dateChin = dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6) + "-"
					+ timeSpliceStr.substring(6, 8);
		}
		return dateChin;
	}

	// 得到中文的时间
	public static String getNowDateFormat(String timespliceStr) {
		int timesplice = ToolsUtil.stringToInt(timespliceStr);
		String dateChin = null;
		String timeSpliceStr = String.valueOf(timesplice);
		if (timeSpliceStr.length() == 4)
			dateChin = timeSpliceStr;
		if (timeSpliceStr.length() == 5) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 5);
		}
		if (timeSpliceStr.length() == 6) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6);
		}
		if (timeSpliceStr.length() == 7) {
			dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6) + "-";
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 1) {
				dateChin += "1";
			}
			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 2) {
				dateChin += "2";
			}

			if (Integer.parseInt(timeSpliceStr.substring(6, 7)) == 3) {
				dateChin += "3";
			}
		}
		if (timeSpliceStr.length() == 8) { // 20040101
			dateChin = dateChin = timeSpliceStr.substring(0, 4) + "-"
					+ timeSpliceStr.substring(4, 6) + "-"
					+ timeSpliceStr.substring(6, 8);
		}
		return dateChin;
	}

	public static int stringToInt(String intStr) {
		if (intStr == null)
			intStr = "";
		int mm = 0;
		try {
			mm = Integer.parseInt(intStr);
		} catch (Exception ex) {
		}
		return mm;
	}

	public static long stringToLong(String longStr) {
		if (longStr == null)
			longStr = "";
		long mm = 0;
		try {
			mm = Long.parseLong(longStr);
		} catch (Exception ex) {
		}
		return mm;
	}

	public static double stringToDouble(String doubelStr) {
		if (doubelStr == null)
			doubelStr = "";
		doubelStr = CmUtil.Replace(doubelStr, ",", ""); // 此处主要用于在action中如果是钱的话就把，号去掉
		double mm = 0;
		try {
			mm = Double.parseDouble(doubelStr);
		} catch (Exception ex) {
		}
		return mm;
	}
	
	public static double stringToInteger(String integerStr) {
		if (integerStr == null)
			integerStr = "";
		integerStr = CmUtil.Replace(integerStr, ",", ""); // 此处主要用于在action中如果是钱的话就把，号去掉
		int mm = 0;
		try {
			mm = Integer.parseInt(integerStr);
		} catch (Exception ex) {
		}
		return mm;
	}
	
	public static double stringToDoubleMoney(String doubelStr) {
		if (doubelStr == null)
			doubelStr = "";
		doubelStr = CmUtil.Replace(doubelStr, "", ""); // 此处主要用于在action中如果是钱的话就把，号去掉
		double mm = 0;
		try {
			mm = Double.parseDouble(doubelStr);
		} catch (Exception ex) {
		}
		return mm;
	}

	public static String getStringDate(String date) {
		if (date == null) {
			return ToolsUtil.getNowDate();
		} else {
			return date;
		}
	}

	public static String doubleToMoney(double money) {
		return CmUtil.getMoney(String.valueOf(money));
	}

	// 当 是0就显示空得 getmoney
	public static String doubleToMoneyKong(double money) {
		if (money == 0)
			return "";
		return CmUtil.getMoney(String.valueOf(money));
	}

	public static String doubleToMoneyKong(double money, int num) {
		if (money == 0)
			return "";
		return CmUtil.getMoneyCOMM(String.valueOf(money), num);
	}

	// 当 是0就显示空得 getmoney
	public static String doubleToMoneyKong(String money) {
		if (money.equals("0.00") || money.equals(".00") || money.equals("0"))
			return "";
		return CmUtil.getMoney(String.valueOf(money));
	}

	public static String doubleToMoneyNo(double money) {
		return CmUtil.Replace(CmUtil.getMoney(String.valueOf(money)), ",", "");
	}

	public static String replaceToFileName(String fileNameStr) {
		fileNameStr = CmUtil.Replace(fileNameStr, ":", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "/", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "\\", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "<", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, ">", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "?", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, ".", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "#", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "*", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "&", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, ";", "_");
		fileNameStr = CmUtil.Replace(fileNameStr, "\"", "_");
		return fileNameStr;
	}

	// 根据需要的时间得到系统用的时间片
	public static int getNeedTimeSlice(String timeModeStr, String inputString) {
		int timeModeInt = ToolsUtil.stringToInt(timeModeStr.trim());
		int timeSlice = 0;
		inputString = inputString.trim();
		switch (timeModeInt) {
		case 4: // 年
			timeSlice = ToolsUtil.stringToInt(inputString);
			break;
		case 3: // 季
			if (inputString.length() >= 6)
				;
			inputString = inputString.substring(0, 4).trim()
					+ inputString.substring(5, 6);
			timeSlice = ToolsUtil.stringToInt(inputString);
			break;
		case 2: // 月
			if (inputString.length() >= 7)
				;
			inputString = inputString.substring(0, 4).trim()
					+ inputString.substring(5, 7);
			timeSlice = ToolsUtil.stringToInt(inputString);
			break;
		case 1: // 旬
			if (inputString.length() >= 8)
				;
			inputString = inputString.substring(0, 4).trim()
					+ inputString.substring(5, 7) + inputString.substring(8);
			timeSlice = ToolsUtil.stringToInt(inputString);
			break;
		case 0: // 日
			if (inputString.length() >= 10)
				;
			inputString = inputString.substring(0, 4).trim()
					+ inputString.substring(5, 7)
					+ inputString.substring(8, 10);
			timeSlice = ToolsUtil.stringToInt(inputString);
			break;
		}
		return timeSlice;
	}

	// 根据起始时间片---结束时间片-----得到连续的中间时间片组合
	public static int[] getAllTimeSlices(int sTime, int eTime) {
		if (sTime > eTime)
			eTime = sTime;
		String sTimeStr = String.valueOf(sTime);
		String eTimeStr = String.valueOf(eTime);
		ArrayList temArr = new ArrayList();
		if (sTime < 10000) { // 年 2004
			for (int i = sTime; i <= eTime; i++) {
				temArr.add(i + "");
			}
		} else if (sTime < 100000) { // 季 20042
			int num = (eTime / 10 - sTime / 10) * 4 + (eTime % 10 - sTime % 10)
					+ 1; // 得到个数
			int sTem = sTime / 10, eTem = sTime % 10 - 1;
			for (int i = 0; i < num; i++) {
				eTem += 1;
				if (eTem > 4) {
					eTem = 1;
					sTem += 1;
				}
				temArr.add(sTem * 10 + eTem + "");
			}
		} else if (sTime < 1000000) { // 月200401
			GregorianCalendar g = new GregorianCalendar(sTime / 100,
					sTime % 100 - 1, 1);
			for (; g.get(g.YEAR) * 100 + g.get(g.MONTH) < eTime; g.add(g.MONTH,
					1)) {
				temArr.add(g.get(g.YEAR) * 100 + g.get(g.MONTH) + 1 + "");
			}
		} else if (sTime < 10000000) { // 旬2004011
			int num = (eTime / 1000 - sTime / 1000) * 36
					+ (eTime % 1000 / 10 - sTime % 1000 / 10) * 3
					+ (eTime % 10 - sTime % 10) + 1; // 得到个数
			int sTem = sTime / 1000; // 年
			int mTem = sTime % 1000 / 10; // 月
			int eTem = sTime % 10 - 1; // 旬
			for (int i = 0; i < num; i++) {
				eTem += 1;
				if (eTem > 3) {
					eTem = 1;
					mTem += 1;
				}
				if (mTem > 12) {
					mTem = 1;
					sTem += 1;
				}
				temArr.add(sTem * 1000 + mTem * 10 + eTem + "");
			}
		} else if (sTime < 100000000) { // 日20040101
			GregorianCalendar g = new GregorianCalendar(sTime / 10000,
					sTime / 100 % 100 - 1, sTime % 100);
			for (; g.get(g.YEAR) * 10000 + g.get(g.MONTH) * 100 + 100
					+ g.get(g.DAY_OF_MONTH) <= eTime; g.add(g.DATE, 1)) {
				temArr.add(g.get(g.YEAR) * 10000 + g.get(g.MONTH) * 100 + 100
						+ g.get(g.DAY_OF_MONTH) + "");
			}
		}
		int[] intArr = new int[temArr.size()];
		for (int i = 0; i < temArr.size(); i++) {
			intArr[i] = ToolsUtil.stringToInt((String) temArr.get(i));
		}
		return intArr;
	}

	// ///////去除两个double数相减产生的误差
	public static double roundfordouble(double d1, double d2) {
		int i = 0;
		int weishu = 1;
		long int1, int2;
		long long1 = 0;
		i = (d1 + "").length() - (d1 + "").indexOf(".") - 1;
		if (i < (d2 + "").length() - (d2 + "").indexOf(".") - 1)
			i = (d2 + "").length() - (d2 + "").indexOf(".") - 1;
		for (int j = 0; j < i; j++)
			weishu *= 10;
		int1 = (long) (d1 * weishu);
		int2 = (long) (d2 * weishu);
		return (int1 - int2) * 1.0 / weishu;
	}

	public static ArrayList arrayListRevers(ArrayList oralArr) {
		if (oralArr == null)
			return null;
		for (int i = 0; i < oralArr.size(); i++) {
			oralArr.add(i, oralArr.get(oralArr.size() - 1));
			oralArr.remove(oralArr.size() - 1);
		}
		return oralArr;
	}

	// 得到不同的颜色
	public static String[] getDiffColor(int x) {
		String[] mm = new String[x];
		String[] cc = new String[] { "#0b9189", "#b9d13b", "#5f39d2",
				"#a95d10", "#157a43", "#fd673f", "#902a98", "#cb8d17",
				"#144869", "#1cd67f", "#e1f556", "#c465c5", "#0463a4",
				"#ac7c97", "#181288", "#3159ea", "#0b9189", "#080f08",
				"#5f39d2", "#a95d10", "#157a43", "#fd673f", "#902a98",
				"#1b2f47", "#cb8d17", "#144869", "#1cd67f", "#e1f556",
				"#def7f3", "#c465c5", "#0463a4", "#ac7c97", "#181288",
				"#c8eded", "#4316dd", "#3159ea", "#0b9189", "#b9d13b",
				"#080f08", "#5f39d2", "#a95d10", "#157a43", "#fd673f",
				"#902a98", "#1b2f47", "#cb8d17", "#144869", "#1cd67f",
				"#e1f556", "#def7f3", "#c465c5", "#0463a4", "#ac7c97",
				"#181288", "#c8eded", "#4316dd", "#3159ea", "#0b9189",
				"#b9d13b", "#080f08", "#5f39d2", "#a95d10", "#157a43",
				"#fd673f", "#902a98", "#1b2f47", "#cb8d17", "#144869",
				"#1cd67f", "#e1f556", "#def7f3", "#c465c5", "#0463a4",
				"#ac7c97", "#181288", "#c8eded", "#4316dd", "#3159ea",
				"#0b9189", "#b9d13b", "#080f08", "#5f39d2", "#a95d10",
				"#157a43", "#fc9e9d", "#20f4ec", "#de0691", "#8a6a97",
				"#217eb0", "#cb75fc", "#556a2f", "#1854ef", "#38fba2",
				"#f5ad78" };
		Random random = new Random();
		for (int i = 0; i < mm.length; i++) {
			mm[i] = cc[i];
		}

		random.nextInt(16);
		return mm;
	}

	// 四舍五入的方法
	public static double getFormatNum(double oralNum, int length) {
		String addNumStr = "0.";
		for (int i = 0; i < length; i++) {
			addNumStr += "0";
		}
		addNumStr += "5";
		double addNum = ToolsUtil.stringToDouble(addNumStr);
		oralNum = oralNum + addNum;
		addNumStr = String.valueOf(oralNum); //
		//在测试中遇到bug，若数字是类似1.895这样截取后是一位小数的数字时，会抱错
		//现加入判断语句
		String lastStr = addNumStr.substring(addNumStr.indexOf("."),addNumStr.length());
		if(lastStr.length() != 2){
			addNumStr = addNumStr.substring(0,(addNumStr.indexOf(".") + length + 1));
		}

		oralNum = ToolsUtil.stringToDouble(addNumStr);
		return oralNum;
	}

	// 根据提示得到收到的系统提示
	public static String getWebMessage(HttpServletRequest request) {
		String outStr = "";
		String state = (String) request.getAttribute("state");
		if (state != null) {
			outStr = ("<div align=left style=font-size:9pt><img width=25 height=25 src='"
					+ request.getContextPath()
					+ "/imageNew/tishi.gif'>系统提示:" + state + "</div>");
		}
		return outStr;
	}

	public static String getWebAlert(HttpServletRequest request) {
		String outStr = "";
		String state = (String) request.getAttribute("state");
		if (state != null) {
			outStr = ("<script>alert('" + state + "')</script>");
		}
		return outStr;
	}

	//

	// 开始时间。
	public static long getBeginTime(int timeBegin) {
		GregorianCalendar g = null;
		if (timeBegin < 10000) // 年度
			g = new GregorianCalendar(timeBegin, 0, 1);
		else if (timeBegin < 100000) // 季度
			g = new GregorianCalendar(timeBegin / 10, (timeBegin % 10) * 3 - 3,
					1);
		else if (timeBegin < 1000000) // 月度
			g = new GregorianCalendar(timeBegin / 100, (timeBegin % 100) - 1, 1);
		else if (timeBegin < 10000000) // 旬度
			g = new GregorianCalendar(timeBegin / 1000,
					(timeBegin % 1000) / 10 - 1, (timeBegin % 10) * 10 - 9);
		else
			g = new GregorianCalendar(timeBegin / 10000,
					(timeBegin % 10000) / 100 - 1, timeBegin % 100);
		return g.getTime().getTime();
	}

	// 结束时间。
	public static long getEndTime(int timeEnd) {
		GregorianCalendar g = null;
		if (timeEnd < 10000) { // 年度
			g = new GregorianCalendar(timeEnd + 1, 0, 1);
		} else if (timeEnd < 100000) { // 季度
			g = new GregorianCalendar(timeEnd / 10, (timeEnd % 10) * 3, 1);
		} else if (timeEnd < 1000000) { // 月度
			g = new GregorianCalendar(timeEnd / 100, (timeEnd % 100), 1);
		} else if (timeEnd < 10000000) { // 旬度
			if (timeEnd % 10 == 3)
				g = new GregorianCalendar(timeEnd / 1000,
						(timeEnd % 1000) / 10, 1);
			else
				g = new GregorianCalendar(timeEnd / 1000,
						(timeEnd % 1000) / 10 - 1, (timeEnd % 10) * 10 + 1);
		} else { // 日
			g = new GregorianCalendar(timeEnd / 10000,
					(timeEnd % 10000) / 100 - 1, timeEnd % 100 + 1);
		}
		return g.getTime().getTime() - 1;
	}

	public static String getTime(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-").append(
				((cal.get(Calendar.MONTH) < 9) ? ("0" + (cal
						.get(Calendar.MONTH) + 1))
						: ((cal.get(Calendar.MONTH) + 1) + "")));
		sb.append("-").append(
				(cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0" + cal
						.get(Calendar.DAY_OF_MONTH)) : (cal
						.get(Calendar.DAY_OF_MONTH) + ""));
		return sb.toString();
	}

	/**
	 * get cal's time (YYYYMMDD)
	 * 
	 * @param cal
	 *            if(cal==null) is current time
	 * @return String YYYYMMDD
	 */
	public static String getTime2(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(cal.get(Calendar.YEAR))
				.append(
						(cal.get(Calendar.MONTH) < 9) ? ("0" + (cal
								.get(Calendar.MONTH) + 1)) : ((cal
								.get(Calendar.MONTH) + 1) + ""));
		sb.append((cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0" + cal
				.get(Calendar.DAY_OF_MONTH))
				: (cal.get(Calendar.DAY_OF_MONTH) + ""));
		return sb.toString();
	}

	/**
	 * get time (YYYY-MM-DD)
	 * 
	 * @param YYYYMMDD
	 * @return (YYYY-MM-DD)
	 */
	public static String convert(int m) {
		StringBuffer sb = new StringBuffer();
		String str = m + "";
		if (str.length() == 8) {
			sb.append(str.substring(0, 4)).append("-").append(
					str.substring(4, 6)).append("-").append(str.substring(6));
		}
		if (str.length() == 6) {
			sb.append(str.substring(0, 4)).append("-").append(
					str.substring(4, 6));
		}
		return sb.toString();
	}


	/**
	 * 为页面
	 */
	public static void getLimitTextAreaShow(HttpServletRequest request,
			String mapName, String limitInput, int lineNum) {
		// System.out.println("&&&&&&&&&&&&&&&&&&&&:");
		Map pageMap = (Map) request.getAttribute(mapName);
		if (pageMap != null) {
			String limStr = (String) pageMap.get(limitInput);
			// System.out.println("222222222222222222222&:"+limitInput);
			if (limStr != null) {
				// System.out.println("3333333333333333&:"+limStr);
				String[] partStrs;

				if (limStr.indexOf("\n") >= 0) {
					partStrs = limStr.split("\n");

				} else {
					partStrs = new String[] { limStr };
				}
				for (int i = 0; i < partStrs.length; i++) {
					// System.out.println("&&&&&&&&&&&&&&&&&&&&partStrs["+i+"]:"+partStrs[i]);
					if (partStrs[i] != null) {
						String mm = partStrs[i];

						if (mm.length() > lineNum) {
							partStrs[i] = "";
							for (int j = 0; j <= (mm.length() / lineNum); j++) {
								partStrs[i] += mm
										.substring(
												j * lineNum,
												(mm.length() > (j + 1)
														* lineNum) ? ((j + 1) * lineNum)
														: mm.length())
										+ "\n";
							}
						}
					}

				}
				String outStr = "";
				for (int i = 0; i < partStrs.length; i++) {
					outStr += partStrs[i] + "\n";
				}
				// System.out.println("&&&&&&&&&&&&&&&&&&&&:"+outStr);

				pageMap.put(limitInput, outStr);
				request.setAttribute(mapName, pageMap);
			}
		}
	}

	public static ArrayList getCurveSeries(String[] str) {
		// 所对应的工具提示
		ArrayList toolTips = new ArrayList();
		for (int i = 0; i < str.length; i++) {
			// toolTips.add("\" onMouseOver=\"mouseOver('"+str[i]+"')\"
			// onMouseOut=\"mouseOut()");
			toolTips.add(str[i]);
		}
		return toolTips;
	}

	public static long GetDistent(String beDateStr, String endDateStr) {
		Date beDate = ToolsUtil.getTimeStringToDate(beDateStr);
		Date endDate = ToolsUtil.getTimeStringToDate(endDateStr);
		long distince = (beDate.getTime() - endDate.getTime())
				/ (3600 * 24 * 1000);
		return distince;
	}

	/**
	 * 取得月末
	 * 
	 * @param ymd
	 *            String
	 * @return String
	 */
	public static String GetMonthEND(String ymd) {
		int maxDay = CmUtil.getMaxDays(ymd);
		ymd = ymd.substring(0, 8);
		return ymd + maxDay;
	}

	/**
	 * 取得旬度
	 * 
	 * @param xundu
	 *            int
	 * @param delaydays
	 *            int
	 * @return List
	 */
	public static List GetXunduRq(String ymd, int xundu, int delaydays) {
		List outList = new LinkedList();
		if (delaydays > 10)
			return null; // 只允许延迟在10日以内
		int maxday = CmUtil.getMaxDays(ymd);
		if (xundu == 1) {
			for (int i = 0; i < 10; i++) {
				int count = i + 1 + delaydays;
				String countStr = count + "";
				if (count < 10) {
					countStr = "0" + countStr;
				}
				outList.add(countStr);
			}
		} else if (xundu == 2) {
			for (int i = 0; i < 10; i++) {
				int count = i + 11 + delaydays;
				String countStr = count + "";
				if (count < 10) {
					countStr = "0" + countStr;
				}
				outList.add(countStr);
			}
		} else if (xundu == 3) {
			for (int i = 0; i < 11; i++) {
				int count = i + 21 + delaydays;
				if (count > maxday) {
					count = count - maxday;
				}
				String countStr = count + "";
				if (count < 10) {
					countStr = "0" + countStr;
				}
				if (count == delaydays + 1)
					break;
				outList.add(countStr);
			}
		} else {
			return null;
		}
		return outList;
	}

	/**
	 * 取得前一天的日期。
	 * 
	 * @param rq
	 *            当天日期,例如"2003-01-01"
	 * @return 返回rq的前一天。例如rq="2003-01-01"，则返回"2002-12-31"。
	 */
	public static String getPreDate(String rq) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd");
			long timeLong = (format.parse(rq)).getTime();
			return format
					.format(new Date(timeLong - 3600 * 24 * 1000));
		} catch (Exception ex) {

		}
		return "";
	}

	/**
	 * 根据日期取得上一个月份。
	 * 
	 * @param rq
	 *            日期，例如“2003-08”，或者“2003-08-02”
	 * @return java.lang.String 返回rq的上一个月的字符串，例如rq="2003-08-02"，则返回"2003-09"
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

	/**
	 * 根据日期取得下一个月份。
	 * 
	 * @param rq
	 *            日期，例如“2003-08”，或者“2003-08-02”
	 * @return java.lang.String 返回rq的下一个月的字符串，例如rq="2003-08-02"，则返回"2003-09"
	 */
	public static String getNextMonth(String rq) {
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
		int i = d.getMonth() + 2;
		String sm = ""; // 返回的字符串不满足两位时，在前面加一个"0"。
		int n = 0; // 如果是某年的12月份，则表示下一个月为明年的第一个月，此时年份加1，否则加0。

		if (i == 13) {
			n = 1;
			i = 1;
		}

		if (i < 10)
			sm = "0";
		returnStr = (d.getYear() + 1900 + n) + "-" + sm + i;
		return returnStr;
	}

	/**
	 * 取得月份间隔
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static List GetDisMonthList(String startDay, String endDay) {
		List outList = new LinkedList();
		if (startDay == null || startDay.length() < 7 || endDay == null
				|| endDay.length() < 7)
			return null;
		long timeLong = 0;
		if (startDay.length() > 7)
			startDay = startDay.substring(0, 7);
		if (endDay.length() > 7)
			endDay = endDay.substring(0, 7);
		if (startDay.equals(endDay)) { // 如果选择了同月
			outList.add(endDay);
			return outList;
		}
		String nextMonth = startDay;
		for (int i = 0;; i++) {
			outList.add(nextMonth);
			nextMonth = getNextMonth(nextMonth);
			if (nextMonth.equals(endDay)) {
				outList.add(nextMonth);
				break;
			}
		}
		return outList;
	}

	/**
	 * 取得星期几
	 * 
	 * @return String
	 */
	public static String getWeekDays() {
		int day = new Date().getDay();
		if (day == 1) {
			return "星期一";
		}
		if (day == 2) {
			return "星期二";
		}

		if (day == 3) {
			return "星期三";
		}

		if (day == 4) {
			return "星期四";
		}

		if (day == 5) {
			return "星期五";
		}

		if (day == 6) {
			return "星期六";
		}

		if (day == 7) {
			return "星期日";
		}
		return "";
	}

	/**
	 * 取得date型数据的小时间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String GetHours(Timestamp date1, Timestamp date2) {
		if (date1 == null || date2 == null)
			return "";
		long days = ToolsUtil.GetDistent(ToolsUtil.TimestampToString1(date1),
				ToolsUtil.TimestampToString1(date2));
		String date1Str = ToolsUtil.TimestampToString(date1);
		String date2Str = ToolsUtil.TimestampToString(date2);
		date1Str = date1Str.substring(11, 12);
		date2Str = date2Str.substring(11, 12);

		long hour = days
				* 24
				+ (ToolsUtil.stringToInt(date1Str) - ToolsUtil
						.stringToInt(date2Str));

		return hour + "";
	}

	/**
	 * add by jsl 给定两个日期获得日期之间的小时数和分钟
	 * 
	 * @param endTime
	 * @param startTime
	 * @param states是否显示天
	 *            yes是
	 * @return
	 */
	public static String getHoursAndMinute(String endTString, String starString) {
		String time = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = df.parse(endTString);
			Date date = df.parse(starString);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			if (day > 0) {
				time = day + "天";
			}
			if (hour > 0) {
				time += hour + "小时";
			}
			time += min + "分";

		} catch (ParseException e) {
			e.getErrorOffset();
		}
		return time;
	}
	
	
	/**
	 * xrj  2008-09-04 取得所有的月份
	 * @return
	 */
	public static List GetAllMonth(){
		List monthList = new LinkedList();
		for(int i =1; i<13; i++){
			monthList.add(i+"");
		}
		return monthList;
	}
	/**
	 * xrj  2008-09-04 取得所有的年份,倒退四年至未来3年
	 * @return
	 */
	public static List GetAllYear(){
		   List yearList = new LinkedList();
		   for (int i = 0; i < 7; i++) {
		     yearList.add( (ToolsUtil.stringToInt(ToolsUtil.getNowYear()) - 4 + i) +"");
		  }
		return yearList;
	}
	
	/**
	 *   2008-09-04 根据日期，取得全年第几周
	 * @return
	 */
	public static int  GetZhouCiOfYear(String today){
		   int zhouCi = 0;
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			try {
				date = format.parse(today);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.TUESDAY);
			calendar.setTime(date);
			zhouCi = calendar.get(Calendar.WEEK_OF_YEAR);
		return zhouCi;
	}
	
}
