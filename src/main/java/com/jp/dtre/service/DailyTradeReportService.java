package com.jp.dtre.service;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jp.dtre.model.DailyTradeReportModel;
import com.jp.dtre.util.CommonUtils;


public class DailyTradeReportService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	CommonUtils util = new CommonUtils();
	
	public void readJsonData(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			URL in = DailyTradeReportService.class.getClassLoader().getResource(fileName);
			File file = new File(in.getFile());
			JSONArray a = (JSONArray) parser.parse(new FileReader(file));
			
			Double totalBuy =0.0;
			Double totalSell =0.0;
			
			List<DailyTradeReportModel> totBuyList = new ArrayList<DailyTradeReportModel>();
			List<DailyTradeReportModel> totSellList = new ArrayList<DailyTradeReportModel>();
			
			Map<Date,List<DailyTradeReportModel>> mpBuyGroups = new HashMap<Date,List<DailyTradeReportModel>>();
			Map<Date,List<DailyTradeReportModel>> mpSellGroups = new HashMap<Date,List<DailyTradeReportModel>>();
			
			for (Object o : a) {
				JSONObject jsonObject = (JSONObject) o;
				String entity = (String) jsonObject.get("entity");
				String action = (String) jsonObject.get("action");
				Double agreedfx = (Double) jsonObject.get("agreedfx");
				String currency = (String) jsonObject.get("currency");
				Calendar instructiondate =  util.getCalendarFormat((String) jsonObject.get("instructiondate"));
				Calendar settlementdate = adjustSettlementDates(currency,(String) jsonObject.get("settlementdate"));
				Long units = (Long) jsonObject.get("units");
				Double unitprice = (Double) jsonObject.get("unitprice");
				Double totalUSD = unitprice * units * agreedfx;
				
				DailyTradeReportModel mdl = new DailyTradeReportModel(entity, action, agreedfx, currency, 
						instructiondate, settlementdate, units, unitprice,totalUSD);
				
				List<DailyTradeReportModel> lst = new ArrayList<DailyTradeReportModel>();
				if(action.equals("B")){
					totBuyList.add(mdl);
					totalBuy += totalUSD;
					
					//grouping based on settlement Date
					if(mpBuyGroups.containsKey(settlementdate.getTime())){
						lst = (List<DailyTradeReportModel>) mpBuyGroups.get(settlementdate.getTime());
					}
					lst.add(mdl);
					mpBuyGroups.put(settlementdate.getTime(), lst);
				}else{
					totSellList.add(mdl);
					totalSell += totalUSD;
					
					//grouping based on settlement Date
					if(mpSellGroups.containsKey(settlementdate.getTime())){
						lst = (List<DailyTradeReportModel>) mpSellGroups.get(settlementdate.getTime());
					}
					lst.add(mdl);
					mpSellGroups.put(settlementdate.getTime(), lst);
				}
			}
			util.generateHeader("TRADING REPORT BASED ON SETTLEMENT DATE");
			
			showReport(mpBuyGroups,"Outgoing (BUY)");
			showReport(mpSellGroups,"Incoming (SELL)");
			
			util.generateHeader("SUMMARY (OVERALL) TRADING REPORT");
			System.out.println("Total Amount Outgoing (BUY) :	USD "+totalBuy+"\n");
			System.out.println("Total Amount Incoming (SELL) :	USD "+totalSell+"\n");
			
			util.generateSubHeaderReport("Overall Outgoing (BUY) Ranking");
			getRanking(totBuyList);
			
			util.generateSubHeaderReport("Overall Incoming (SELL) Ranking");
			getRanking(totSellList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getRanking(List<DailyTradeReportModel> tradeSet) {
		tradeSet.sort(Comparator.comparing(l -> ((DailyTradeReportModel) l).getTotAmount())
				.thenComparing(l -> ((DailyTradeReportModel) l).getStrEntityName())
				.reversed());
		tradeSet.forEach(System.out::println);
	}
	
	public void showReport(Map<Date,List<DailyTradeReportModel>> mpData,String str){
		Map<Date,List<DailyTradeReportModel>> buyGroups = new HashMap<Date,List<DailyTradeReportModel>>();
		
		for (Entry<Date, List<DailyTradeReportModel>> entry : mpData.entrySet()) {
		    Date key = entry.getKey();
		    List<DailyTradeReportModel> value = entry.getValue();
		    Double totalVal = 0.0;
		    for(DailyTradeReportModel mdl :value){
		    	totalVal +=	mdl.getTotAmount();	    	
		    }
		    util.generateSubHeader("Report of : "+sdf.format(key));
		    System.out.println("Total Amount "+str+" :	USD "+totalVal+"\n");
		    util.generateSubHeaderReport(str+" Ranking");
			getRanking(value);
		}
	}
	
	public Calendar adjustSettlementDates(String currency, String strDate){
		Calendar cal = util.getCalendarFormat(strDate);
		
		if(currency.equals("AED") || currency.equals("SAR")){
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ){
					cal.add(Calendar.DATE, 2);
			}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
				cal.add(Calendar.DATE, 1);
			}
		}else{
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
				cal.add(Calendar.DATE, 2);
			}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
				cal.add(Calendar.DATE, 1);
			}
		}
		return cal;
	}

}
