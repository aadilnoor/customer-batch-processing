package com.aadil.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aadil.springbatch.entity.UploadedFile;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
	boolean existsByFileHash(String fileHash);
}
