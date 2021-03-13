package com.jsp.ex.model;

public class ReplyDTO {
	//Member Variable
	private int rId;
	private int bId;
	private String name;
	private String content;
	private String Date;
	
	//Default Constructor
	public ReplyDTO() {}
	
	//Constructor
	public ReplyDTO(int rId, int bId, String name, String content, String date) {
		this.rId = rId;
		this.bId = bId;
		this.name = name;
		this.content = content;
		Date = date;
	}
	
	//Getter & Setter
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
}