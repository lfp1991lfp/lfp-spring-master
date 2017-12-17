package com.hytch.lfpspringmaster.api;

import com.hytch.lfpspringmaster.config.mvc.api.WebApi;
import com.hytch.lfpspringmaster.config.mvc.cache.CachePolicy;
import com.hytch.lfpspringmaster.config.mvc.cache.WebCache;
import com.hytch.lfpspringmaster.model.Goods;
import com.hytch.lfpspringmaster.sys.good.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器.
 */
@RestController
@RequestMapping(value = "/api/good/")
@Slf4j
@WebApi
@Api(value = "商品管理接口", description = "用于管理商品的信息（增删改查）",
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GoodsController {

	@Autowired
	GoodsService goodsService;

	@ApiOperation(value = "新增商品", response = Goods.class)
	@PostMapping(value = "add")
	public Goods addGoods(Goods goods) {
		return goodsService.addGoods(goods);
	}

	@ApiOperation(value = "根据商品ID，删除商品")
	@DeleteMapping(value = "delete/{id}")
	public int deleteGoods(@PathVariable("id") int id) {
		return goodsService.deleteGoods(id);
	}

	@ApiOperation(value = "根据商品ID，修改商品")
	@PatchMapping(value = "update/{id}")
	public Goods updateGoods(
			@PathVariable("id") int id,
			Goods goods) {
		return goodsService.updateGoods(id, goods);
	}

	@ApiOperation(value = "根据某个商品ID，查找商品")
	@WebCache(maxAge = 10, policy = CachePolicy.PRIVATE)
	@GetMapping(value = "find/{id}")
	public Goods findGoods(@PathVariable("id") int id) {
		return goodsService.findGoods(id);
	}

	@ApiOperation(value = "查找所有商品")
	@WebCache(maxAge = 10, policy = CachePolicy.PRIVATE)
	@GetMapping(value = "find/all")
	public List<Goods> findAllGoods() {
		return goodsService.findAllGoods();
	}
}
