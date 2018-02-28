package com.erp.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Email {

	@ManyToOne
	@JoinColumn(name = "PersonID")
	private Person Person;
	
	@Column(name = "Type")
	private String Type;
	
	@Column(name = "Email")
	private String Email;
	@Id
	@TableGenerator(
	        name="EmailID", 
	        		 table="ID_GEN", 
	     	        pkColumnName="GEN_KEY", 
	     	        valueColumnName="GEN_VALUE", 
	        pkColumnValue="EmailID", 
	        allocationSize=1)
	    @GeneratedValue(strategy=GenerationType.TABLE, generator="EmailID")
	@Column(name = "EmailID")
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
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

}
