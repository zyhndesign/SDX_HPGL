package com.cidic.sdx.model;

import java.io.Serializable;

/**
 * 颜色数据模型
 * @author dev
 *
 */
public class ColorModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String colorName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	
	
}
