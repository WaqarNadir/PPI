
package com.erp.classes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class PaymentMethods implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "PM_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "PM_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PM_ID")
	@Column(name = "PM_ID")
	private int PM_ID;
	private String name;
	private String remarks;

	// --------------- Getter & Setters ---------------

	public int getPM_ID() {
		return PM_ID;
	}

	public void setPM_ID(int pM_ID) {
		PM_ID = pM_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
