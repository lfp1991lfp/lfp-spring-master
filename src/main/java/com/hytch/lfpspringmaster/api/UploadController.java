package com.hytch.lfpspringmaster.api;

import com.hytch.lfpspringmaster.base.Result;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@ApiOperation(value = "单文件上传", notes = "单文件上传")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = String.class),
			@ApiResponse(code = 400, message = "Invalid mobile supplied", response = String.class),
			@ApiResponse(code = 404, message = "server not available", response = String.class)})
	@PostMapping(value = "/importSingle", produces = {"application/json"})

	public String uploadSuccess(
			@RequestParam("importFile") MultipartFile importFile) {
		if (importFile.isEmpty()) {
			return "false";
		}
		storageService.store(importFile);
		return "upload success";
	}

	@ApiOperation(value = "多文件上传", notes = "多文件上传")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = String.class),
			@ApiResponse(code = 400, message = "Invalid mobile supplied", response = String.class),
			@ApiResponse(code = 404, message = "server not available", response = String.class)})
	@PostMapping(value = "/importMultiple", produces = {"application/json"})
	public String multiUploadSuccess(
			@RequestParam("importFile") MultipartFile[] importFile) {

		storageService.stores(Arrays.asList(importFile));
		return "upload success";
	}

	@ApiOperation(value = "多文件上传测试", notes = "多文件上传测试")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = String.class),
			@ApiResponse(code = 400, message = "Invalid mobile supplied", response = String.class),
			@ApiResponse(code = 404, message = "server not available", response = String.class)})
	@PostMapping(value = "/importMultipleTest", produces = {"application/json"})
	public Result multiUploadSuccess(
			@RequestParam(value = "importFile1", required = false) MultipartFile importFile1,
			@RequestParam(value = "importFile2", required = false) MultipartFile importFile2,
			@RequestParam(value = "importFile3", required = false) MultipartFile importFile3,
			@RequestParam(value = "importFile4", required = false) MultipartFile importFile4,
			@RequestParam(value = "importFile5", required = false) MultipartFile importFile5) {

		List<MultipartFile> files = new ArrayList<>();
		files.add(importFile1);
		files.add(importFile2);
		files.add(importFile3);
		files.add(importFile4);
		files.add(importFile5);

		storageService.stores(files);
		return new Result("upload success");
	}

	@ApiOperation(value = "删除文件", notes = "删除文件")
	@DeleteMapping(value = "delete")
	public int delete(String id) {
		storageService.deleteAll();

		return Integer.valueOf(id);
	}

}
