package com.hytch.lfpspringmaster.api;

import com.hytch.lfpspringmaster.sys.upload.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件控制器.
 */
@RestController
@RequestMapping(value = "/api/upload/")
@Slf4j
@Api(value = "上传文件管理", tags = "upload", description = "上传文件管理",
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

public class UploadController {

	private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@ApiOperation(value = "导入用户", notes = "导入用户", response = String.class, tags = {})
	@ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = String.class),
			@ApiResponse(code = 400, message = "Invalid mobile supplied", response = String.class),
			@ApiResponse(code = 404, message = "server not available", response = String.class)})
	@PostMapping(value = "/importCustomer", produces = {"application/json"})

	public String uploadSuccess(
			HttpServletRequest request,
			@RequestParam("importFile") MultipartFile importFile) {
		if (importFile.isEmpty()) {
			return "false";
		}
		String fileName = importFile.getOriginalFilename();
		int size = (int) importFile.getSize();
		log.debug(fileName + "-->" + size);

		String filePath = request.getSession().getServletContext().getRealPath("upload/");
		log.debug("保存文件路径＝" + filePath);
		try {
			storageService.store(importFile);
		} catch (Exception e) {
			return "upload file fail" + e.getMessage();
		}
		return "upload success";
	}

	@ApiOperation(value = "删除文件", notes = "删除文件")
	@DeleteMapping(value = "delete")
	public int delete(String id) {
		storageService.deleteAll();

		return Integer.valueOf(id);
	}

}
