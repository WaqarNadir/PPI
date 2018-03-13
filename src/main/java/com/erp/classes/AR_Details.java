//
//package com.erp.classes;
//
//import java.io.Serializable;
//import java.sql.Date;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.TableGenerator;
//
//@Entity(name = "Account_Receivable_Details")
//public class AR_Details implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@TableGenerator(name = "ARD_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "ARD_ID", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ARD_ID")
//	@Column(name = "ARD_ID")
//	private int ARDetail_ID;
//	private double amountRecie;
//	private String Remarks;
//	private Date received_Date;
//
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "Acc_ID")
//	private AccountGroup subGroup_ID;
//	
//
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "AR_ID")
//	private Account_Receivable AR_ID;
//
//	public int getARDetail_ID() {
//		return ARDetail_ID;
//	}
//
//	public void setARDetail_ID(int aRDetail_ID) {
//		ARDetail_ID = aRDetail_ID;
//	}
//
//	public String getRemarks() {
//		return Remarks;
//	}
//
//	public void setRemarks(String remarks) {
//		Remarks = remarks;
//	}
//
//	public Date getReceived_Date() {
//		return received_Date;
//	}
//
//	public void setReceived_Date(Date received_Date) {
//		this.received_Date = received_Date;
//	}
//
//	public AccountGroup getSubGroup_ID() {
//		return subGroup_ID;
//	}
//
//	public void setSubGroup_ID(AccountGroup subGroup_ID) {
//		this.subGroup_ID = subGroup_ID;
//	}
//
//	public Account_Receivable getAR_ID() {
//		return AR_ID;
//	}
//
//	public void setAR_ID(Account_Receivable aR_ID) {
//		AR_ID = aR_ID;
//	}
//
//
//
//	public double getAmount_Received() {
//		return amountRecie;
//	}
//
//	public void setAmount_Received(double amount_Received) {
//		this.amountRecie = amount_Received;
//	}
//
//
//
//	// --------------- Getter & Setters ---------------
//
//}
//
