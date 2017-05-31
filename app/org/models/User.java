package org.models;

import java.util.Date;

public class User {

	
	private String address;
	private String name;
	private Integer age;
	private Date created;
	public User() {
		created = new Date();
	}
	public Date getCreated() {
		return created;
	}

	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
