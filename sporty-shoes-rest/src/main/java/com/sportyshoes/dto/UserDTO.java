package com.sportyshoes.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean isAdmin;

    public UserDTO(Long id, String username, String email, boolean isAdmin) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setAdmin(isAdmin);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}