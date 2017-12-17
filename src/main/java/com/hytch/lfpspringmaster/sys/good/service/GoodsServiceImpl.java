package com.hytch.lfpspringmaster.sys.good.service;

import com.hytch.lfpspringmaster.model.Goods;
import com.hytch.lfpspringmaster.model.mapper.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 接口实现类
 */
@Service
@Transactional(value = "txPrimaryManager")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsDao goodsDao;

	@Override
	public Goods addGoods(Goods goods) {
		goodsDao.insert(goods);
		return goods;
	}

	@Override
	public int deleteGoods(int id) {
		return goodsDao.deleteByPrimaryKey(id);
	}

	@Override
	public Goods updateGoods(int id, Goods goods) {
		goods.setId(id);
		goodsDao.updateByPrimaryKeySelective(goods);
		return goods;
	}

	@Override
	public Goods findGoods(int id) {
		return goodsDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Goods> findAllGoods() {
		return goodsDao.selectAll();
	}
}
