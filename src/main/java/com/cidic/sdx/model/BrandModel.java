package com.cidic.sdx.model;

import java.io.Serializable;

/**
 * 品牌数据模型
 * @author dev
 *
 */
public class BrandModel implements Serializable {
	
	private static final long serialVersionUID = -2651214827910333046L;
	
	private int id;
	private String broadName;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBroadName() {
		return broadName;
	}
	
	public void setBroadName(String broadName) {
		this.broadName = broadName;
	}
	
	
}
