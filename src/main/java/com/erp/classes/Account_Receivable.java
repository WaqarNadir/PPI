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

@Entity(name = "AccountReceivable")
@NamedQueries({
		@NamedQuery(name = "Account_Receivable.DateBetween", query = "select t from AccountReceivable t "
				+ "WHERE t.date BETWEEN :startDate and :endDate "),
		@NamedQuery(name = "Account_Receivable.ByDateRange", query = "select r from AccountReceivable r "
				+ "WHERE r.date BETWEEN :startDate and :endDate") })

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

	// @ManyToOne
	// @JoinColumn(name = "bankSourceID")
	// private AccountGroup bankSourceID;

	@ManyToOne
	@JoinColumn(name = "personID")
	private Person person_ID;

	@OneToMany(mappedBy = "AR_ID")
	private List<AR_Details> AR_DetailList;

	@OneToMany(mappedBy = "AR_ID")
	private List<ARReciept> recieptList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ARReciept> getRecieptList() {
		return recieptList;
	}

	public void setRecieptList(List<ARReciept> recieptList) {
		this.recieptList = recieptList;
	}

	// --------------- Getter & Setters ---------------
	public Account_Receivable(String status, Double total) {
		this.total = total;
		this.status = status;
	}

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

	public List<AR_Details> getAR_DetailList() {
		return AR_DetailList;
	}

	public void setAR_DetailList(List<AR_Details> aR_DetailList) {
		AR_DetailList = aR_DetailList;
	}

	public int getAR_ID() {
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
