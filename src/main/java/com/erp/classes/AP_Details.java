
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

@Entity(name = "AccountPayableDetails")
public class AP_Details implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "APD_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "APD_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "APD_ID")
	@Column(name = "APD_ID")
	private int APDetail_ID;
	private double amount_Paid;
	private String Remarks;
	private Date PaidDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Acc_ID")
	private AccountGroup subGroup_ID;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AP_ID")
	private Account_Payable AP_ID;

	public Account_Payable getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(Account_Payable aP_ID) {
		AP_ID = aP_ID;
	}

	public int getAPDetail_ID() {
		return APDetail_ID;
	}

	public void setAPDetail_ID(int aPDetail_ID) {
		APDetail_ID = aPDetail_ID;
	}

	public Date getPaidDate() {
		return PaidDate;
	}

	public void setPaidDate(Date paid_Date) {
		this.PaidDate = paid_Date;
	}

	public double getAmount_Paid() {
		return amount_Paid;
	}

	public void setAmount_Paid(double amount_Paid) {
		this.amount_Paid = amount_Paid;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		this.Remarks = remarks;
	}

	public AccountGroup getSubGroup_ID() {
		return subGroup_ID;
	}

	public void setSubGroup_ID(AccountGroup subGroup_ID) {
		this.subGroup_ID = subGroup_ID;
	}

	public AP_Details(AP_Details APD) {
		this.setAmount_Paid(APD.getAmount_Paid());
		this.setAP_ID(APD.getAP_ID());
		this.setPaidDate(APD.getPaidDate());
		this.setRemarks(APD.getRemarks());
		this.setSubGroup_ID(APD.getSubGroup_ID());

	}

	public AP_Details() {
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return "AMount Paid = " + this.getAmount_Paid() + "\n" + "Paid Date = " + getPaid_Date() + "\n" + "Remarks = "
//				+ getRemarks() + "\n" + "Subgroup = " + getSubGroup_ID() + "\n";
//
//	}
	// --------------- Getter & Setters ---------------

}
