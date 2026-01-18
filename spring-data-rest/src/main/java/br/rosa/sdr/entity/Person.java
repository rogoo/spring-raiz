package br.rosa.sdr.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "born_in")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date bornIn;

	public Person() {
	}

	public Person(String firstName, String lastName, Date bornIn) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.bornIn = bornIn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBornIn() {
		return bornIn;
	}

	public void setBornIn(Date bornIn) {
		this.bornIn = bornIn;
	}
}
