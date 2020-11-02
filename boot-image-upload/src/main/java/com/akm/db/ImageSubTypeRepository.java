package com.akm.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akm.model.ImageSubType;

public interface ImageSubTypeRepository extends JpaRepository<ImageSubType, Long> {
	
	List<ImageSubType> findAllByImageTypeId(Long id);
}
