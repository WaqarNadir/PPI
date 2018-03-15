package com.erp.classes;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class APReciept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "APReciept_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "APReciept_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "APReciept_ID")

	@Column(name = "APReciept_ID")
	private int APReciept_ID;
	private Date date;
	private double amountPaid;
	private String remarks;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PM_ID")
	private PaymentMethods PM_ID;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "AP_ID")
	private Account_Payable AP_ID;

	// --------------- Getter & Setters ---------------

	public int getAPReciept_ID() {
		return APReciept_ID;
	}

	public void setAPReciept_ID(int aPReciept_ID) {
		APReciept_ID = aPReciept_ID;
	}

	public PaymentMethods getPM_ID() {
		return PM_ID;
	}

	public void setPM_ID(PaymentMethods pM_ID) {
		PM_ID = pM_ID;
	}

	public Account_Payable getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(Account_Payable aP_ID) {
		AP_ID = aP_ID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
}
