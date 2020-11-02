package com.akm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image_sub_type")
public class ImageSubType {

	public ImageSubType() {
		super();
	}

	public ImageSubType(String name) {
		this.name = name;
		
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "image_id")
	private Long imageTypeId;
	

	@Column(name = "name")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public Long getImageTypeId() {
		return imageTypeId;
	}

	public void setImageTypeId(Long imageTypeId) {
		this.imageTypeId = imageTypeId;
	}

	@Override
	public String toString() {
		return "ImageSubType [id=" + id + ", name=" + name + "]";
	}
	
	

	
}