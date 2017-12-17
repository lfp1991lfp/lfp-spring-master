package com.hytch.lfpspringmaster.model;

import com.hytch.lfpspringmaster.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends BaseModel {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "COVER")
	private String cover;

	@Column(name = "IMG")
	private String img;

	@Column(name = "TYPE_ID")
	private Integer typeId;

	/**
	 * 标价
	 */
	@Column(name = "PRICE")
	private Double price;

	/**
	 * 市场价
	 */
	@Column(name = "MARKET_PRICE")
	private Double marketPrice;

	/**
	 * 介绍
	 */
	@Column(name = "INTRODUCE")
	private String introduce;

	/**
	 * 摘要
	 */
	@Column(name = "BRIEF")
	private String brief;

	/**
	 * 是否上架
	 */
	@Column(name = "IS_SOLD")
	private String isSold;

	/**
	 * 销量
	 */
	@Column(name = "SALES")
	private Integer sales;

	/**
	 * 邮费
	 */
	@Column(name = "POSTAGE")
	private Double postage;

	@Column(name = "PV")
	private Integer pv;
}