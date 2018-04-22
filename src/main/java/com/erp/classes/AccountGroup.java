package com.erp.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.ColumnDefault;

@Entity

@NamedNativeQuery(name = "AccountGroup.BalanceSheet", query = "select a.Acc_ID as Acc_ID, a.accName as accName ,a.isParent as isParent,a.refNo AS refNo,SUM(jd.subTotal) as amount,'2' AS type\r\n"
		+ "from AccountGroup a\r\n" + "JOIN JournalDetails jd on jd.Acc_ID=a.Acc_ID\r\n"
		+ "join Journal j on j.J_ID = JD.J_ID\r\n" + "where j.[date] BETWEEN :startDate and :endDate\r\n"
		+ "group by a.Acc_ID,a.accName,a.isParent,a.refNo", resultSetMapping = "BalanceSheet")

@SqlResultSetMapping(name = "BalanceSheet", classes = { @ConstructorResult(targetClass = AccountGroup.class, columns = {
		@ColumnResult(name = "accName", type = String.class), @ColumnResult(name = "isParent", type = Double.class),
		@ColumnResult(name = "refNo", type = String.class), @ColumnResult(name = "amount", type = Double.class),

		@ColumnResult(name = "Acc_ID", type = Double.class) }) })

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

	public AccountGroup(String accName, Double isParent, String refNo, Double amount, Double acc_ID) {
		int val = isParent.intValue();
		Acc_ID = acc_ID.intValue();
		this.isParent = new AccountGroup();
		this.isParent.setAcc_ID(val);
		this.amount = amount;
		this.accName = accName;
		this.refNo = refNo;
	}

	@OneToMany(mappedBy = "isParent", fetch = FetchType.LAZY)
	private List<AccountGroup> childList;

	@OneToMany(mappedBy = "subGroup_ID", fetch = FetchType.LAZY)
	private List<TB_Details> TBDList;
	@ColumnDefault(value = "0.0")
	private Double amount;
	private String accName;
	private String refNo;
	private String remarks;

	// -------------------------- Getter & setters -------------------------------
	public List<TB_Details> getTBDList() {
		return TBDList;
	}

	public void setTBDList(List<TB_Details> tBDList) {
		TBDList = tBDList;
	}

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
		TBDList = new ArrayList<>();
		this.amount = 0.0;
	}

	public AccountGroup(AccountGroup AG) {
		Acc_ID = AG.getAcc_ID();
		this.isParent = AG.isParent;
		this.childList = AG.getChildList();
		TBDList = AG.getTBDList();
		if (amount == null)
			amount = 0.0;
		else
			this.amount = AG.amount;

		this.accName = AG.accName;
		this.refNo = AG.refNo;
		this.remarks = AG.remarks;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		AccountGroup param = (AccountGroup) obj;
		if (this.getAcc_ID() == param.getAcc_ID())
			return true;
		return false;
	}

}
