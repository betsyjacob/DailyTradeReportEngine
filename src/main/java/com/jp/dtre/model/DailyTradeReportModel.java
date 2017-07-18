package com.jp.dtre.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyTradeReportModel {

	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

	String strEntityName;
	String strAction;
	Double dAgreedFx;
	String strCurrency;
	Long lUnits;
	Double dUnitPrice;

	Double totAmount;
	Calendar cInstructionDate;
	Calendar cSettlementDate;

	public DailyTradeReportModel() {

	}

	public DailyTradeReportModel(String strEntityName, String strAction,
			Double dAgreedFx, String strCurrency, Calendar cInstructionDate,
			Calendar cSettlementDate, Long lUnits, Double dUnitPrice,
			Double totAmount) {
		super();
		this.strEntityName = strEntityName;
		this.strAction = strAction;
		this.dAgreedFx = dAgreedFx;
		this.strCurrency = strCurrency;
		this.cInstructionDate = cInstructionDate;
		this.cSettlementDate = cSettlementDate;
		this.lUnits = lUnits;
		this.dUnitPrice = dUnitPrice;
		this.totAmount = totAmount;
	}

	public String getStrEntityName() {
		return strEntityName;
	}

	public void setStrEntityName(String strEntityName) {
		this.strEntityName = strEntityName;
	}

	public String getStrAction() {
		return strAction;
	}

	public void setStrAction(String strAction) {
		this.strAction = strAction;
	}

	public Double getdAgreedFx() {
		return dAgreedFx;
	}

	public void setdAgreedFx(Double dAgreedFx) {
		this.dAgreedFx = dAgreedFx;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public Long getlUnits() {
		return lUnits;
	}

	public void setlUnits(Long lUnits) {
		this.lUnits = lUnits;
	}

	public Double getdUnitPrice() {
		return dUnitPrice;
	}

	public void setdUnitPrice(Double dUnitPrice) {
		this.dUnitPrice = dUnitPrice;
	}

	public Double getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(Double totAmount) {
		this.totAmount = totAmount;
	}

	public Calendar getcInstructionDate() {
		return cInstructionDate;
	}

	public void setcInstructionDate(Calendar cInstructionDate) {
		this.cInstructionDate = cInstructionDate;
	}

	public Calendar getcSettlementDate() {
		return cSettlementDate;
	}

	public void setcSettlementDate(Calendar cSettlementDate) {
		this.cSettlementDate = cSettlementDate;
	}

	public String toString() {
		return strEntityName + "	|" + strCurrency + "	|"
				+ sdf.format(cSettlementDate.getTime()) + "	|" + totAmount;
	}

}
