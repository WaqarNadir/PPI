package com.erp.classes;

import java.util.List;

public class TrailBalanceWrapper {
	private TrailBalance TrailBalance;
	private List<TB_Details> TB_DetailList;
	private PaymentDetails PaymentDetail;
	private Account_Payable AccountPayable;
	private List<AP_Details> AP_DetailList;
//	private Account_Receivable accountReceivable;
//	private List<AR_Details> AR_DetailsList;
//	
//
//	public List<AR_Details> getAR_DetailsList() {
//		return AR_DetailsList;
//	}
//
//	public void setAR_DetailsList(List<AR_Details> aR_DetailsList) {
//		AR_DetailsList = aR_DetailsList;
//	}
//
//
//
//	public Account_Receivable getAccountReceivable() {
//		return accountReceivable;
//	}
//
//	public void setAccountReceivable(Account_Receivable accountReceivable) {
//		this.accountReceivable = accountReceivable;
//	}

	public List<AP_Details> getAP_DetailList() {
		return AP_DetailList;
	}

	public void setAP_DetailList(List<AP_Details> aP_DetailList) {
		AP_DetailList = aP_DetailList;
	}

	public Account_Payable getAccountPayable() {
		return AccountPayable;
	}

	public void setAccountPayable(Account_Payable accountPayable) {
		this.AccountPayable = accountPayable;
	}

	public PaymentDetails getPaymentDetail() {
		return PaymentDetail;
	}

	public void setPaymentDetail(PaymentDetails paymentDetail) {
		PaymentDetail = paymentDetail;
	}

	public TrailBalance getTrailBalance() {
		return TrailBalance;
	}

	public void setTrailBalance(TrailBalance trailBalance) {
		TrailBalance = trailBalance;
	}

	public List<TB_Details> getTB_DetailList() {
		return TB_DetailList;
	}

	public void setTB_DetailList(List<TB_Details> tB_DetailList) {
		TB_DetailList = tB_DetailList;
	}

}
