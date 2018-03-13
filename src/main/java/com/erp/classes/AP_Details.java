
package com.erp.classes;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name = "Account_Payable_Details")
public class AP_Details implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "APD_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "APD_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "APD_ID")
	@Column(name = "APD_ID")
	private int APDetail_ID;
	private double amountPayable;
	private String Remarks;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Acc_ID")
	private AccountGroup subGroup_ID;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AP_ID")
	private Account_Payable AP_ID;

	public Account_Payable getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(Account_Payable aP_ID) {
		AP_ID = aP_ID;
	}

	public int getAPDetail_ID() {
		return APDetail_ID;
	}

	public void setAPDetail_ID(int aPDetail_ID) {
		APDetail_ID = aPDetail_ID;
	}

	public double getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(double amountPayable) {
		this.amountPayable = amountPayable;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		this.Remarks = remarks;
	}

	public AccountGroup getSubGroup_ID() {
		return subGroup_ID;
	}

	public void setSubGroup_ID(AccountGroup subGroup_ID) {
		this.subGroup_ID = subGroup_ID;
	}

	// --------------- Getter & Setters ---------------

}
