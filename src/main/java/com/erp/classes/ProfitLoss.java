package com.erp.classes;

public class ProfitLoss {

	public ProfitLoss() {
		// TODO Auto-generated constructor stub
	}

	public ProfitLoss(String name, double subTotal, double type) {
		this.name = name;
		this.subTotal = subTotal;
		this.type = (int) type;

	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private double subTotal;
	private int type;

}
