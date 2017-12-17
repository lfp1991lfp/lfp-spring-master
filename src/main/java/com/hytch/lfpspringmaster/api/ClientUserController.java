package com.hytch.lfpspringmaster.api;

import com.hytch.lfpspringmaster.config.mvc.api.WebApi;
import com.hytch.lfpspringmaster.config.mvc.cache.CachePolicy;
import com.hytch.lfpspringmaster.config.mvc.cache.WebCache;
import com.hytch.lfpspringmaster.config.mvc.exception.ServiceApiException;
import com.hytch.lfpspringmaster.model.ClientUser;
import com.hytch.lfpspringmaster.sys.client.clientuser.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 客户端用户管理接口
 */
@RestController
@RequestMapping(value = "/api/user/")
@Slf4j
@Api(value = "客户端用户管理", tags = "clientUser", description = "客户端用户管理",
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//produces表示response的类型,consumes表示请求的类型
public class ClientUserController {

	private final ClientUserService clientUserService;

	@Autowired
	public ClientUserController(ClientUserService clientUserService) {
		this.clientUserService = clientUserService;
	}


	@WebApi
	@WebCache(maxAge = 30, policy = CachePolicy.PUBLIC, enableCache = false)
	@ApiOperation(value = "用户登录", notes = "根据用户名和密码登录用户",
			response = ClientUser.class)
	@GetMapping(value = "login")
	public ClientUser login(String account, String password) {
		ClientUser user = clientUserService.findClientUser(account, password);
		if (user == null) {
			user = new ClientUser();
			user.setCode(-3); //数据为空，或者不存在
			user.setMessage("用户名或者密码错误");
		}

		return user;
	}

	@ApiOperation(value = "新增用户", notes = "新增用户",
			response = ClientUser.class)
	@PostMapping(value = "register")
	public ClientUser register(
			@RequestBody ClientUser clientUser) throws ServiceApiException {

		return clientUserService.insert(clientUser);
	}

	@ApiOperation(value = "更新用户", notes = "根据用户id更新用户",
			response = ClientUser.class)
	@PutMapping(value = "update/{id}")
	public ClientUser update(
			@PathVariable("id") String id,
			@RequestBody ClientUser clientUser) throws ServiceApiException {

		return clientUserService.update(id, clientUser);
	}

	@ApiOperation(value = "删除用户", notes = "根据用户id删除用户")
	@DeleteMapping(value = "delete")
	public int delete(String id) {
		clientUserService.delete(id);

		return Integer.valueOf(id);
	}
}
