package com.sportyshoes.dto;

public class PasswordChangeDTO {
	private String currentPassword;
  private String newPassword;
    
	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public PasswordChangeDTO setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
		return this;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public PasswordChangeDTO setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}   
}
