package com.erp.classes;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
public class AccountGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "Acc_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "Acc_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Acc_ID")
	@Column(name = "Acc_ID")
	private int Acc_ID;

	@ManyToOne
	@JoinColumn(name = "isParent")
	private AccountGroup isParent;

	@OneToMany(mappedBy = "isParent")
	private List<AccountGroup> childList;

	private Double amount;
	private String accName;
	private String refNo;
	private String remarks;

	// -------------------------- Getter & setters -------------------------------
	public Double getAmount() {
		if (amount == null) // to cater null pointer expection
			return 0.0;
		return amount;
	}

	public void setAmount(Double amount) {
		if (amount == null)
			amount = 0.0;
		this.amount = amount;
	}

	public AccountGroup getIsParent() {
		return isParent;
	}

	public void setIsParent(AccountGroup isParent) {
		this.isParent = isParent;
	}

	public List<AccountGroup> getChildList() {
		return childList;
	}

	public void setChildList(List<AccountGroup> childList) {
		this.childList = childList;
	}

	public int getAcc_ID() {
		return Acc_ID;
	}

	public void setAcc_ID(int acc_ID) {
		Acc_ID = acc_ID;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AccountGroup() {
		childList = new ArrayList<>();
	}
}
