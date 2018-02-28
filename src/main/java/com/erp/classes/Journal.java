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
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity
public class Journal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "journal_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "journal_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "journal_ID")
	@Column(name = "journal_ID")
	private int journal_ID;

	private Date date;
	private double total;
	private String diaryNo;

	@OneToMany(mappedBy = "journal_ID")
	List<JournalDetails> JDList;

	// -------------------------- Getter & setters -------------------------------

	public Journal() {
		JDList = new ArrayList<>();
	}

	public List<JournalDetails> getJDList() {
		return JDList;
	}

	public void setJDList(List<JournalDetails> jDList) {
		JDList = jDList;
	}

	public int getJournal_ID() {
		return journal_ID;
	}

	public void setJournal_ID(int journal_ID) {
		this.journal_ID = journal_ID;
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

}
