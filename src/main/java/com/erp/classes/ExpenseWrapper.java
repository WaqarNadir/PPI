package com.erp.classes;

import java.util.List;

public class ExpenseWrapper {
	private TrailBalance TrailBalance;
	private List<TB_Details> TB_DetailList;
	private PaymentDetails PaymentDetail;

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
