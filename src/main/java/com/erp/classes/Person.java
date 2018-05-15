package com.erp.classes;

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
public class Person {
	
	@Id
	@TableGenerator(
	        name="PersonID", 
	        		 table="ID_GEN", 
	     	        pkColumnName="GEN_KEY", 
	     	        valueColumnName="GEN_VALUE", 
	        pkColumnValue="PersonID", 
	        allocationSize=1)
	    @GeneratedValue(strategy=GenerationType.TABLE, generator="PersonID")
	@Column(name="PersonID")
	private int PersonID;
	
	@Column(name = "FirstName")
	private String Fname;
	@Column(name = "LastName")
	private String Lname;
	@Column(name = "CompanyName")
	private String Company;
	@Column(name = "Remarks")
	private String Remarks;
	@Column(name = "Type")
	private String Type;
	
	@OneToMany(mappedBy = "Person")
	private List<Email> EmailList;
	
	@OneToMany(mappedBy = "Person")
	private List<Phone> phoneList;
	
	@OneToMany(mappedBy = "Person")
	private List<Address> addressList;
	
	// ---------------------- Constructor --------------------
	
	public Person() {
		EmailList = new ArrayList<>();
		phoneList = new ArrayList<>();
		addressList = new ArrayList<>();
	}
	
	// -------------------------- Getter & setters -------------------------------
	
	public List<Email> getEmailList() {
		return EmailList;
	}

	public void setEmailList(List<Email> emailList) {
		EmailList = emailList;
	}
	
	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	
	public int getPersonID() {
		return PersonID;
	}

	public void setPersonID(int personID) {
		PersonID = personID;
	}
	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
