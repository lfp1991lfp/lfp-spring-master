package com.hytch.lfpspringmaster.sys.client.clientuser.service;

import com.hytch.lfpspringmaster.model.ClientUser;
import com.hytch.lfpspringmaster.model.mapper.ClientUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(value = "txPrimaryManager")
public class ClientUserServiceImpl implements ClientUserService {

	private final ClientUserDao clientUserDao;

	@Autowired
	public ClientUserServiceImpl(ClientUserDao clientUserDao) {
		this.clientUserDao = clientUserDao;
	}

	@Override
	public ClientUser findClientUser(String account, String password) {
		ClientUser clientUser = new ClientUser();
		clientUser.setAccount(account);
		clientUser.setPassword(password);
		clientUser.setIsDelete(false);
		clientUser.setIsFrozen(false);

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
		clientUser.setCreateDate(new Date());
		clientUser.setUpdateTime(new Date());
		clientUser.setIsFrozen(false);
		clientUser.setIsDelete(false);

		clientUserDao.insert(clientUser);
		return clientUser;
	}

	@CacheEvict(cacheNames = "client_user", key = "#p0")
	@Override
	public String clearCache(String account) {
		return "1";
	}
}
