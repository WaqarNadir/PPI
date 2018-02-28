package com.erp.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name="Phone")
public class Phone {

	@ManyToOne
	@JoinColumn(name = "PersonId")
	private Person Person;
	
	@Column(name = "Type")
	private String Type;
	@Column(name = "PhoneNO")
	private String phone;
	
	@Id
	@TableGenerator(
	        name="PhoneID", 
	        		 table="ID_GEN", 
	     	        pkColumnName="GEN_KEY", 
	     	        valueColumnName="GEN_VALUE", 
	        pkColumnValue="PhoneID", 
	        allocationSize=1)
	    @GeneratedValue(strategy=GenerationType.TABLE, generator="PhoneID")
	@Column(name="PhoneID")
	private int ID;

	
	public Person getPerson() {
		return Person;
	}

	public void setPerson(Person person) {
		Person = person;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
