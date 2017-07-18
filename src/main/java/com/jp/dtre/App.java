package com.jp.dtre;

import com.jp.dtre.service.DailyTradeReportService;

public class App {
	public static void main(String[] args) {
		DailyTradeReportService srv = new DailyTradeReportService();
		srv.readJsonData("data.json");
	}
}
