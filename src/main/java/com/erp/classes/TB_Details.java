
package com.erp.classes;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

@Entity(name = "TrailBalanceDetails")
public class TB_Details implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "TBD_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "TBD_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TBD_ID")
	@Column(name = "TBD_ID")
	private int TBDetail_ID;

	private double subTotal;
	private String remarks;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Acc_ID")
	private AccountGroup subGroup_ID;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TB_ID")
	private TrailBalance TB_ID;

	public int getTBDetail_ID() {
		return TBDetail_ID;
	}

	public void setTBDetail_ID(int tBDetail_ID) {
		TBDetail_ID = tBDetail_ID;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AccountGroup getSubGroup_ID() {
		return subGroup_ID;
	}

	public void setSubGroup_ID(AccountGroup subGroup_ID) {
		this.subGroup_ID = subGroup_ID;
	}

	public TrailBalance getTB_ID() {
		return TB_ID;
	}

	public void setTB_ID(TrailBalance tB_ID) {
		TB_ID = tB_ID;
	}

	// --------------- Getter & Setters ---------------

}
