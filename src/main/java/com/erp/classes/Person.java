package com.erp.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
