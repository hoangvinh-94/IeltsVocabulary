package com.example.healer.ieltsvocabulary.model;

public class Unit {
	String name;
	String avatar;
	int id;
	int numberOfWord;
	public Unit(){}
	
	
	public Unit(int id,String name, int number, String avatar) {
		super();
		
		this.id = id;
		this.name = name;
		this.numberOfWord = number;
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberOfWord() {
		return numberOfWord;
	}
	public void setNumberOfWord(int numberOfWord) {
		this.numberOfWord = numberOfWord;
	}
	

}
