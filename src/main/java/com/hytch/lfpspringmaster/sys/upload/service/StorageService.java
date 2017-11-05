package com.hytch.lfpspringmaster.sys.upload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

	void store(Path path, MultipartFile file);

	void stores(Path path, List<MultipartFile> files);

	Stream<Path> loadAll(Path path);

	Path load(Path filePath, String filename);

	Resource loadAsResource(Path filePath, String filename);

	void deleteAll(Path filePath);
}
