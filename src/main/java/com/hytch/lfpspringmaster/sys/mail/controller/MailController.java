package com.hytch.lfpspringmaster.sys.mail.controller;

import com.hytch.lfpspringmaster.base.Result;
import com.hytch.lfpspringmaster.config.mvc.api.WebApi;
import com.hytch.lfpspringmaster.sys.mail.model.CustomMessage;
import com.hytch.lfpspringmaster.sys.mail.service.MailService;
import com.hytch.lfpspringmaster.sys.upload.StorageProperties;
import com.hytch.lfpspringmaster.sys.upload.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邮件控制层
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/mail/")
@Api(value = "邮件管理接口", description = "管理客户端的邮件发送",
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MailController {

	private final MailService service;

	private final StorageService storageService;

	private final Path attachPath;

	@Autowired
	public MailController(
			MailService service,
			StorageService storageService,
			StorageProperties properties) {
		this.service = service;
		this.storageService = storageService;
		this.attachPath = Paths.get(properties.getAddAttachment());

	}

	@WebApi
	@PostMapping(value = "send")
	public Result sendMessage(@RequestBody CustomMessage customMessage) {
		service.sendMailMessage(customMessage);

		return new Result("发送成功");
	}

	@WebApi
	@PostMapping(value = "sendHtml")
	public Result sendMessageHtml(@RequestBody CustomMessage customMessage) {
		service.sendHtml(customMessage);

		return new Result("发送成功");
	}

	@WebApi
	@ApiOperation(value = "发送邮件包含单附件", notes = "每个附件大小不能超过50MB",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping(value = "sendAttachment")
	public Result sendAttachment(
			CustomMessage customMessage,
			@RequestParam MultipartFile file) {
		List<MultipartFile> files = new ArrayList<>();
		files.add(file);
		storageService.stores(attachPath, files);
		List<Path> paths =
				storageService.loadAll(attachPath).collect(Collectors.toList());
		service.sendThymeleaf(customMessage, "mailTemplates1", paths);
		return new Result("发送成功");
	}

	@WebApi
	@ApiOperation(value = "发送邮件包含多附件", notes = "每个附件大小不能超过50MB",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping(value = "sendAttachments")
	public Result sendAttachments(
			CustomMessage customMessage,
			@RequestParam(value = "importFile1", required = false) MultipartFile file,
			@RequestParam(value = "importFile2", required = false) MultipartFile file1,
			@RequestParam(value = "importFile3", required = false) MultipartFile file2,
			@RequestParam(value = "importFile4", required = false) MultipartFile file3) {
		// TODO: 17/11/5 邮件异步发送 
		List<MultipartFile> files = new ArrayList<>();
		files.add(file);
		files.add(file1);
		files.add(file2);
		files.add(file3);
		storageService.stores(attachPath, files);
		List<Path> paths =
				storageService.loadAll(attachPath).collect(Collectors.toList());
		service.sendThymeleaf(customMessage, "mailTemplates", paths);
		return new Result("发送成功");
	}

}
