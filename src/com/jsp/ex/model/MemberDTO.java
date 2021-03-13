package com.jsp.ex.model;

import java.sql.Timestamp;

public class MemberDTO {
	//Member Variable
	private String account;
	private String password;
	private String nickname;
	private String email;
	private int age;
	private Timestamp subscriptionDate;
	
	//Default Constructor
	public MemberDTO() {}
	
	//Constructor
	public MemberDTO(String account, String password, String nickname, String email, int age, Timestamp subscriptionDate) {
		super();
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.age = age;
		this.subscriptionDate = subscriptionDate;
	}

	//Getter & Setter
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Timestamp getSubscriptionDate() {
		return subscriptionDate;
	}
	public void setSubscriptionDate(Timestamp timestamp) {
		this.subscriptionDate = timestamp;
	}
}