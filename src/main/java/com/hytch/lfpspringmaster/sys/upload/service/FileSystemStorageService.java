package com.hytch.lfpspringmaster.sys.upload.service;

import com.hytch.lfpspringmaster.sys.upload.StorageException;
import com.hytch.lfpspringmaster.sys.upload.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	@Override
	public void store(Path path, MultipartFile file) {
		uploadFile(path, file);
	}

	@Override
	public void stores(Path path, List<MultipartFile> files) {
		if (files == null || files.size() == 0) {
			throw new StorageException("Failed to store empty files ");
		}
		for (MultipartFile file : files) {
			if (file != null) {
				uploadFile(path, file);
			}
		}
	}

	@Override
	public Stream<Path> loadAll(Path filePath) {
		try {
			return Files.walk(filePath, 2)
					.filter(path -> !path.equals(filePath) && path.toString().contains("."));  //relativize
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(Path filePath, String filename) {
		return filePath.resolve(filename);
	}

	@Override
	public Resource loadAsResource(Path filePath, String filename) {
		try {
			Path file = load(filePath, filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll(Path filePath) {
		FileSystemUtils.deleteRecursively(filePath.toFile());
	}

	private Path init(Path filePath, String suffix) {
		try {
//			Files.createDirectory(rootLocation);   //单个目录
			return Files.createDirectories(Paths.get(filePath.toString(), "/" + suffix)); //多级目录
		} catch (FileAlreadyExistsException e) {
//			throw new StorageException("Could not initialize storage", e);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}

		return null;
	}

	private void uploadFile(Path filePath, MultipartFile file) {
		try {
			if (file == null || file.isEmpty()) {
				return;
			}
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String suffix = fileName.substring(index + 1);
			//若是没有后缀，则创建临时文件夹
			if (index == -1) {
				suffix = "tmp";
			}

			if (!filePath.isAbsolute()) {
				Path path = init(filePath, suffix);
				if (path == null) {
					throw new StorageException("获取存储路径不存在");
				}
				Files.copy(file.getInputStream(),
						path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}
}
