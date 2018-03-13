
package com.erp.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TableGenerator;

@Entity(name = "TrailBalance")
public class TrailBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "TB_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "TB_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TB_ID")

	@Column(name = "TB_ID")
	private int TB_ID;
	private Date date;
	private double total;
	private String diaryNo;
	private String remarks;

	@Column(name = "Type")
	private int type; // To differentiate income , expense and transfer

	@ManyToOne
	@JoinColumn(name = "bankSourceID")
	private AccountGroup bankSourceID;

	@ManyToOne
	@JoinColumn(name = "personID")
	private Person person_ID;

	@OneToMany(mappedBy = "TB_ID")
	private List<TrailBalance> TB_DetailList;

	// --------------- Getter & Setters ---------------

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TrailBalance() {
		TB_DetailList = new ArrayList<>();
	}

	public List<TrailBalance> getTB_DetailList() {
		return TB_DetailList;
	}

	public void setTB_DetailList(List<TrailBalance> tB_DetailList) {
		TB_DetailList = tB_DetailList;
	}

	public int getTB_ID() {
		return TB_ID;
	}

	public void setTB_ID(int tB_ID) {
		TB_ID = tB_ID;
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

	public String getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(String diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AccountGroup getbankSourceID() {
		return bankSourceID;
	}

	public void setbankSourceID(AccountGroup bankSourceID) {
		this.bankSourceID = bankSourceID;
	}

	public Person getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(Person person_ID) {
		this.person_ID = person_ID;
	}

}
