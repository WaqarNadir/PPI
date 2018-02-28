package com.erp.classes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class AccountGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "Acc_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "Acc_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Acc_ID")
	@Column(name = "Acc_ID")
	private int Acc_ID;
	private String accName;
	@Column(name = "isParent")
	private Integer parentRef;

	public Integer getParentRef() {
		return parentRef;
	}

	public void setParentRef(Integer parentRef) {
		this.parentRef = parentRef;
	}

	private String refNo;
	private String remarks;

	// -------------------------- Getter & setters -------------------------------

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

}
