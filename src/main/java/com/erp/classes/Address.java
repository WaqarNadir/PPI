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
public class Address {

	@ManyToOne
	@JoinColumn(name = "PersonID")
	private Person Person;
	
	@Column(name = "Type")
	private String Type;
	
	@Column(name = "City")
	private String City;
	
	@Column(name = "Street")
	private String Street;
	
	@Column(name = "Country")
	private String Country;
	
	@Id
	@TableGenerator(
	        name="AddressID", 
	        		 table="ID_GEN", 
	     	        pkColumnName="GEN_KEY", 
	     	        valueColumnName="GEN_VALUE", 
	        pkColumnValue="AddressID", 
	        allocationSize=1)
	    @GeneratedValue(strategy=GenerationType.TABLE, generator="AddressID")
	@Column(name = "AddressID")
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

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


}
