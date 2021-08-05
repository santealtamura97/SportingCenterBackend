package com.sportingCenterBackEnd.dto;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {
	private String id, displayName, email;
	private List<String> roles;
	private String idAbbonamento;

	public UserInfo(String toString, String displayName, String email, List<String> roles, String idAbbonamento) {
		id = toString;
		this.displayName = displayName;
		this.email = email;
		this.roles = roles;
		this.idAbbonamento = idAbbonamento;
	}

	public String toString() {
		return displayName + " " + email;
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getIdAbbonamento() {
		return idAbbonamento;
	}
}
