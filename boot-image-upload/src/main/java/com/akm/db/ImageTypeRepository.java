package com.akm.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akm.model.ImageType;

public interface ImageTypeRepository extends JpaRepository<ImageType, Long> {
	
	
}
