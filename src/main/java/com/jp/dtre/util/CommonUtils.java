package com.jp.dtre.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	public Calendar getCalendarFormat(String strDate){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(strDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	public void generateHeader(String strHeader){
		System.out.println("\n\n----------------------------------------------------------------------");
		System.out.println(strHeader);
		System.out.println("----------------------------------------------------------------------");
	}
	
	public void generateSubHeader(String strSubHeader){
		System.out.println("\n------------------------------------");
		System.out.println(strSubHeader);
		System.out.println("------------------------------------\n");
	}
	
	public void generateSubHeaderReport(String strSubHeader){
		System.out.println("--------------------------------------------");
		System.out.println(strSubHeader);
		System.out.println("--------------------------------------------");
		System.out.println("Entity	|Curr	|Settle Date	|Total(USD)");
		System.out.println("--------------------------------------------");		
	}
	
}
