package com.hytch.lfpspringmaster.sys.good.service;

import com.hytch.lfpspringmaster.model.Goods;

import java.util.List;

/**
 * 商店服务层管理类
 */
public interface GoodsService {

	/**
	 * 增加商品.
	 *
	 * @param goods 商品数据模型
	 * @return 新增的商品
	 */
	Goods addGoods(Goods goods);

	/**
	 * 删除商品.
	 *
	 * @param id 删除id
	 * @return 删除成功的id
	 */
	int deleteGoods(int id);

	/**
	 * 修改商品.
	 *
	 * @param id    所要修改的商品ID
	 * @param goods 商品数据模型
	 * @return 修改的模型
	 */
	Goods updateGoods(int id, Goods goods);

	/**
	 * 根据商品ID，查找商品.
	 *
	 * @param id 商品ID
	 * @return 查找成功的商品
	 */
	Goods findGoods(int id);

	/**
	 * 查找所有商品.
	 *
	 * @return 返回所有商品
	 */
	List<Goods> findAllGoods();
}
