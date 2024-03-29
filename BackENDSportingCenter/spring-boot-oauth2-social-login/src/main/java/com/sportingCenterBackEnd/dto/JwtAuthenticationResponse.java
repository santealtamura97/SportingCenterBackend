package com.sportingCenterBackEnd.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
	private String accessToken;
	private UserInfo user;

	public JwtAuthenticationResponse(String jwt, UserInfo buildUserInfo) {
		accessToken = jwt;
		user = buildUserInfo;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public UserInfo getUser() {
		return user;
	}
}
