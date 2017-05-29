package org.models;

public enum Role {

	Patient("PATIENT"),
	Doctor("DOCTOR"),
	Pharmiest("PHARMIEST");
	final String role;
	Role(String role) {
		this.role = role;
	}
}
