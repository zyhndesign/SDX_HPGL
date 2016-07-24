package com.cidic.sdx.model;

import java.io.Serializable;

public class HPModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -980151689545332204L;

	private String hp_num; //
	private String brand; //
	private String category;
	private String size;
	private String color;
	private float price;
	private String createTime;
	
	public String getHp_num() {
		return hp_num;
	}
	public void setHp_num(String hp_num) {
		this.hp_num = hp_num;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
