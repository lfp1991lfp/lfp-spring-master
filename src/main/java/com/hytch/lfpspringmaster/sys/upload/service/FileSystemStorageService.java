package com.hytch.lfpspringmaster.sys.upload.service;

import com.hytch.lfpspringmaster.sys.upload.StorageException;
import com.hytch.lfpspringmaster.sys.upload.StorageFileNotFoundException;
import com.hytch.lfpspringmaster.sys.upload.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		uploadFile(file);
	}

	@Override
	public void stores(List<MultipartFile> files) {
		if (files == null || files.size() == 0) {
			throw new StorageException("Failed to store empty files ");
		}
		files.forEach(this::uploadFile);
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
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
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	private Path init(String suffix) {
		try {
//			Files.createDirectory(rootLocation);   //单个目录
			return Files.createDirectories(Paths.get(rootLocation.toString(), "/" + suffix)); //多级目录
		} catch (FileAlreadyExistsException e) {
//			throw new StorageException("Could not initialize storage", e);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}

		return null;
	}

	private void uploadFile(MultipartFile file) {
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

			if (!rootLocation.isAbsolute()) {
				Path path = init(suffix);
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
