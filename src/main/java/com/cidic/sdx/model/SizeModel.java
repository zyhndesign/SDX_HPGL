package com.cidic.sdx.model;

import java.io.Serializable;

/**
 * ≥ﬂ¬Î…Ë÷√
 * @author dev
 *
 */
public class SizeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String sizeName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	
}
