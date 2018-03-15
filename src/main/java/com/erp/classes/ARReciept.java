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
public class ARReciept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ARReciept_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "ARReciept_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ARReciept_ID")

	@Column(name = "ARReciept_ID")
	private int ARReciept_ID;
	private Date date;
	private double amountReceived;
	private String remarks;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PM_ID")
	private PaymentMethods PM_ID;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="AR_ID")
	private Account_Receivable AR_ID;

	// --------------- Getter & Setters ---------------



	public Account_Receivable getAR_ID() {
		return AR_ID;
	}

	public void setAR_ID(Account_Receivable aR_ID) {
		AR_ID = aR_ID;
	}

	public Date getDate() {
		return date;
	}

	public int getARReciept_ID() {
		return ARReciept_ID;
	}

	public void setARReciept_ID(int aRReciept_ID) {
		ARReciept_ID = aRReciept_ID;
	}

	public PaymentMethods getPM_ID() {
		return PM_ID;
	}

	public void setPM_ID(PaymentMethods pM_ID) {
		PM_ID = pM_ID;
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

	public double getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
	}
}
