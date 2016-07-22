package com.cidic.sdx.model;

import java.io.Serializable;

/**
 * @author dev
 *
 */
public class BrandModel implements Serializable {
	
	private static final long serialVersionUID = -2651214827910333046L;
	
	private int id;
	private String name;
	private int pId;
	
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

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}
	
	
	
}
