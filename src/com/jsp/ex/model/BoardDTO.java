package com.jsp.ex.model;

public class BoardDTO {
	//Member Variable
	private int id;
	private String name;
	private String title;
	private String content;
	private String date;
	private int hit;
	private int group;
	private int step;
	private int indent;
	private String category;
	private int replyCount;
	
	//Default Constructor
	public BoardDTO() {}
	
	//Constructor
	public BoardDTO(int id, String name, String title, String content, String date, int hit, int group, int step, int indent, String category, int replyCount) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.content = content;
		this.date = date;
		this.hit = hit;
		this.group = group;
		this.step = step;
		this.indent = indent;
		this.category = category;
		this.replyCount = replyCount;
	}

	//Getter & Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
}