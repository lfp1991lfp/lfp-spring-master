package com.hytch.lfpspringmaster.api;

import com.hytch.lfpspringmaster.config.mvc.cache.CachePolicy;
import com.hytch.lfpspringmaster.config.mvc.cache.WebCache;
import com.hytch.lfpspringmaster.model.ClientUser;
import com.hytch.lfpspringmaster.sys.client.clientuser.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

	@ApiOperation(value = "清除缓存")
	@GetMapping(value = "clear")
	public String clearCache(String account) {
		return clientUserService.clearCache(account);
	}

	@WebCache(maxAge = 30, policy = CachePolicy.PUBLIC)
	@ApiOperation(value = "用户登录", notes = "根据用户名和密码登录用户",
			response = ClientUser.class)
	@GetMapping(value = "login")
	public ClientUser login(String account, String password) {

		ClientUser clientUser = new ClientUser();
		clientUser.setAccount(account);
		clientUser.setPassword(password);

		return clientUserService.findClientUser(clientUser);
	}

	@ApiOperation(value = "新增用户", notes = "新增用户",
			response = ClientUser.class)
	@PostMapping(value = "register")
	public ClientUser register(
			@RequestBody ClientUser clientUser) {
		clientUser.setCreateDate(new Date());
		clientUser.setUpdateTime(new Date());
		clientUser.setIsFrozen(false);
		clientUser.setIdDelete(false);

		return clientUserService.insert(clientUser);
	}

	@ApiOperation(value = "更新用户", notes = "根据用户id更新用户",
			response = ClientUser.class)
	@PutMapping(value = "update/{id}")
	public ClientUser update(@PathVariable("id") String id,
													 @RequestBody ClientUser clientUser) {
		clientUser.setUpdateTime(new Date());

		return clientUserService.update(id, clientUser);
	}

	@ApiOperation(value = "删除用户", notes = "根据用户id删除用户")
	@DeleteMapping(value = "delete")
	public int delete(String id) {
		clientUserService.delete(id);

		return Integer.valueOf(id);
	}
}
