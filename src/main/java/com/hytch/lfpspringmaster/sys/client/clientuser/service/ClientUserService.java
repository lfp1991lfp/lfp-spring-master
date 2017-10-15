package com.hytch.lfpspringmaster.sys.client.clientuser.service;

import com.hytch.lfpspringmaster.model.ClientUser;

public interface ClientUserService {
	/**
	 * 登陆的用户名和密码找到用户.
	 *
	 * @param clientUser 用户信息
	 * @return 当前登录的用户信息
	 */
	ClientUser findClientUser(ClientUser clientUser);

	/**
	 * 通过id找到客户端用户.
	 *
	 * @param id 客户端id
	 * @return 用户信息
	 */
	ClientUser findClientUserById(String id);

	/**
	 * 通过当前用户id,修改用户信息
	 *
	 * @param id         客户端id
	 * @param clientUser 客户端用户信息
	 * @return 修改成功后的用户信息
	 */
	ClientUser update(String id, ClientUser clientUser);

	/**
	 * 通过id删除客户端信息
	 *
	 * @param id 客户端id
	 * @return 删除后的id
	 */
	int delete(String id);

	/**
	 * 客户端注册
	 *
	 * @param clientUser 客户端信息
	 * @return 插入成功后的数据
	 */
	ClientUser insert(ClientUser clientUser);

	/**
	 * 清除内存中的缓存.
	 *
	 * @param account 账号
	 * @return 成功
	 */
	String clearCache(String account);
}
