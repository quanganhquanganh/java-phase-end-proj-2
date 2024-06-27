package com.sportyshoes.dto;

public class AdminLoginDTO {
	private String username;
  private String password;
    
	public String getPassword() {
		return password;
	}
	public AdminLoginDTO setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String getUsername() {
		return username;
	}
	
	public AdminLoginDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	@Override
	public String toString() {
		return "AdminLoginDTO [username=" + username + ", password=" + password + "]";
	}
}
