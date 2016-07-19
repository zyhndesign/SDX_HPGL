package com.cidic.sdx.dao;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	private String name;  
    private Date created;  
    private int age;  
    public User(){}  
    
    public User(String name,int age){  
        this.name = name;  
        this.age = age;  
        this.created = new Date();  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}  
    
    
}
