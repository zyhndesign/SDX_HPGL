package com.cidic.sdx.model;

import java.io.Serializable;

/**
 * 品类数据模型
 * @author dev
 *
 */
public class CategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826107560113521685L;
	
	private int id;
	private String categoryName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
