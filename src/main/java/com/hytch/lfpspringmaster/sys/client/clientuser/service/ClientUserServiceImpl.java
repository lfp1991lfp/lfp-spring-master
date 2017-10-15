package com.hytch.lfpspringmaster.sys.client.clientuser.service;

import com.hytch.lfpspringmaster.model.ClientUser;
import com.hytch.lfpspringmaster.model.mapper.ClientUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "txPrimaryManager")
public class ClientUserServiceImpl implements ClientUserService {

	private final ClientUserDao clientUserDao;

	@Autowired
	public ClientUserServiceImpl(ClientUserDao clientUserDao) {
		this.clientUserDao = clientUserDao;
	}

	@Override
	@Cacheable(cacheNames = "client_user", key = "#clientUser.account")   //缓存集合名为user,key取第一个参数作为默认key
	public ClientUser findClientUser(ClientUser clientUser) {
		return clientUserDao.selectOne(clientUser);
	}

	@Override
	@Cacheable(cacheNames = "client_user", key = "#p0")   //缓存集合名为user,key取第一个参数作为默认key
	public ClientUser findClientUserById(String id) {
		return clientUserDao.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(cacheNames = "client_user", key = "#p0 + \"\"")       //更新成功，清除缓存数据
	public ClientUser update(String id, ClientUser clientUser) {
		clientUser.setId(Integer.valueOf(id));
		clientUserDao.updateByPrimaryKeySelective(clientUser);
		return clientUser;
	}

	@Override
	@CacheEvict(cacheNames = "client_user", key = "#p0")
	public int delete(String id) {
		return clientUserDao.deleteByPrimaryKey(id);
	}

	@Override
	@Cacheable(cacheNames = "client_user", key = "#clientUser.id + \"\"")
	public ClientUser insert(ClientUser clientUser) {
		clientUserDao.insert(clientUser);
		return clientUser;
	}

	@CacheEvict(cacheNames = "client_user", key = "#p0")
	@Override
	public String clearCache(String account) {
		return "1";
	}
}
