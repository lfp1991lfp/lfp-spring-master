package com.hytch.lfpspringmaster.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;

public class BaseModel implements Serializable {
	
	@Transient
	@JsonIgnore
	private int index = 1;
	
	@Transient
	@JsonIgnore
	private int row = 10;
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
}
