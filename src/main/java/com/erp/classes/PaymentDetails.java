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
public class PaymentDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "PD_ID", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "PD_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PD_ID")
	@Column(name = "PD_ID")
	private int PMDetails_ID;

	@ManyToOne
	@JoinColumn(name = "PM_ID")
	private PaymentMethods PM_ID;

	@ManyToOne
	@JoinColumn(name = "TB_ID")
	private TrailBalance TB_ID;

	private String paymentDetail;
	
	private String bankName;

	// --------------- Getter & Setters ---------------
	public int getPMDetails_ID() {
		return PMDetails_ID;
	}

	public void setPMDetails_ID(int pMDetails_ID) {
		PMDetails_ID = pMDetails_ID;
	}

	public PaymentMethods getPM_ID() {
		return PM_ID;
	}

	public void setPM_ID(PaymentMethods pM_ID) {
		PM_ID = pM_ID;
	}

	public TrailBalance getTB_ID() {
		return TB_ID;
	}

	public void setTB_ID(TrailBalance tB_ID) {
		TB_ID = tB_ID;
	}

	public String getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(String paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
