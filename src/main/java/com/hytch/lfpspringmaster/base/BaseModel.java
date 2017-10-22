package com.hytch.lfpspringmaster.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class BaseModel implements Serializable {

	@Transient
	@JsonIgnore
	private int index = 1;

	@Transient
	@JsonIgnore
	private int row = 10;

	@Transient
	@JsonIgnore
	private String message;

	@Transient
	@JsonIgnore
	private int code;
}
