package com.erp.classes;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "AccountPayable")
@NamedQueries({
		@NamedQuery(name = "Account_Payable.DateBetween", query = "select t from AccountPayable t "
				+ "WHERE t.date BETWEEN :startDate and :endDate "),
		@NamedQuery(name = "Account_Payable.DueDateBetween", query = "select t from AccountPayable t "
				+ "WHERE t.dueDate BETWEEN :startDate and :endDate "),

		@NamedQuery(name = "Account_Payable.DateAndStatus", query = "select t from AccountPayable t "
				+ "WHERE t.date BETWEEN :startDate and :endDate and t.status=:status") })

public class Account_Payable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "AP_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "AP_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AP_ID")

	@Column(name = "AP_ID")
	private int AP_ID;
	private Date date;
	@Column(name = "due_Date")
	private Date dueDate;
	private double total;

	private String diary_No;

	public String getDiary_No() {
		return diary_No;
	}

	public void setDiary_No(String diary_No) {
		this.diary_No = diary_No;
	}

	private String remarks;

	@Column(name = "status")
	private String status; // To differentiate income , expense and transfer

	// @ManyToOne
	// @JoinColumn(name = "bankSourceID")
	// private AccountGroup bankSourceID;

	@ManyToOne
	@JoinColumn(name = "person_ID")
	private Person person_ID;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<APReciept> getReciptList() {
		return reciptList;
	}

	public void setReciptList(List<APReciept> reciptList) {
		this.reciptList = reciptList;
	}

	@OneToMany(mappedBy = "AP_ID")
	private List<AP_Details> AP_DetailList;

	@OneToMany(mappedBy = "AP_ID")
	private List<APReciept> reciptList;

	// --------------- Getter & Setters ---------------

	public Account_Payable(String status, double total) {
		this.total = total;
		this.status = status;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String Status) {
		this.status = Status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Account_Payable() {
		AP_DetailList = new ArrayList<>();
	}

	public List<AP_Details> getAP_DetailList() {
		return AP_DetailList;
	}

	public void setAP_DetailList(List<AP_Details> aP_DetailList) {
		AP_DetailList = aP_DetailList;
	}

	public int getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(int aP_ID) {
		AP_ID = aP_ID;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// public AccountGroup getbankSourceID() {
	// return bankSourceID;
	// }
	//
	// public void setbankSourceID(AccountGroup bankSourceID) {
	// this.bankSourceID = bankSourceID;
	// }

	public Person getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(Person person_ID) {
		this.person_ID = person_ID;
	}

}
