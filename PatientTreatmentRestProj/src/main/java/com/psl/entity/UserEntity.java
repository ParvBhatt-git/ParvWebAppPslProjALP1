package com.psl.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.NonNull;
/*
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;*/

@Data
/*
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor
 * 
 * @RequiredArgsConstructor
 */
@Entity
@Table(name = "userLogin")
public class UserEntity {
	
	
	public UserEntity() {	}
	
	public UserEntity(String string, String string2) {
		this.userName = string;
		this.password = string2;
	}

	@Id
	private String userName;
	

	@Column
	@NonNull
	private String password;	

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}//class
