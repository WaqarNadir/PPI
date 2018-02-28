package com.erp.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Accoun_Receivable")
public class Account_Receivable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "AR_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "AR_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AR_ID")

	@Column(name = "AR_ID")
	private int AR_ID;
	private Date date;
	private Date due_Date;
	private double total;
	
	private String diary_No;
	private String remarks;
	
	@Column(name = "status")
	private String status; // To differentiate income , expense and transfer

//	@ManyToOne
//	@JoinColumn(name = "bankSourceID")
//	private AccountGroup bankSourceID;

	@ManyToOne
	@JoinColumn(name = "personID")
	private Person person_ID;

	@OneToMany(mappedBy = "AR_ID")
	private List<Account_Receivable> AR_DetailList;

	// --------------- Getter & Setters ---------------

	public String getstatus() {
		return status;
	}

	public void setstatus(String Status) {
		this.status = Status;
	}

	public Date getDue_Date() {
		return due_Date;
	}

	public void setDue_Date(Date due_Date) {
		this.due_Date = due_Date;
	}

	public Account_Receivable() {
		AR_DetailList = new ArrayList<>();
	}

	public List<Account_Receivable> getAR_DetailList() {
		return AR_DetailList;
	}

	public void setTB_DetailList(List<Account_Receivable> aP_DetailList) {
		AR_DetailList = aP_DetailList;
	}

	public int getAP_ID() {
		return AR_ID;
	}

	public void setAR_ID(int aP_ID) {
		AR_ID = aP_ID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDiary_No() {
		return diary_No;
	}

	public void setDiary_No(String diary_No) {
		this.diary_No = diary_No;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

//	public AccountGroup getbankSourceID() {
//		return bankSourceID;
//	}
//
//	public void setbankSourceID(AccountGroup bankSourceID) {
//		this.bankSourceID = bankSourceID;
//	}

	public Person getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(Person person_ID) {
		this.person_ID = person_ID;
	}

}

