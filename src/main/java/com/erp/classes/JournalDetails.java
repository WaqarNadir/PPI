package com.erp.classes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class JournalDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "JDetail_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "JDetail_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "JDetail_ID")
	@Column(name = "JD_ID")
	private int JDetail_ID;

	private double subTotal;

	private int iscredit;

	@ManyToOne
	@JoinColumn(name = "J_ID")
	private Journal journal_ID;

	@ManyToOne
	@JoinColumn(name = "Acc_ID")
	private AccountGroup subGroup_ID;

	// -------------------------- Getter & setters -------------------------------

	public int getJDetail_ID() {
		return JDetail_ID;
	}

	public void setJDetail_ID(int jDetail_ID) {
		JDetail_ID = jDetail_ID;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public int getIscredit() {
		return iscredit;
	}

	public void setIscredit(int iscredit) {
		this.iscredit = iscredit;
	}

	public Journal getJournal_ID() {
		return journal_ID;
	}

	public void setJournal_ID(Journal journal_ID) {
		this.journal_ID = journal_ID;
	}

	public AccountGroup getSubGroup_ID() {
		return subGroup_ID;
	}

	public void setSubGroup_ID(AccountGroup subGroup_ID) {
		this.subGroup_ID = subGroup_ID;
	}

}
