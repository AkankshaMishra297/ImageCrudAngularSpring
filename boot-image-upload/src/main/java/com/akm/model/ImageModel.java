package com.akm.model;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "image_model")
public class ImageModel {

	public ImageModel() {
		super();
	}

	public ImageModel(String name, String type, byte[] picByte,String imgType, String imgSubType, Long prize, String desc ) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
		this.imgType = imgType;
		this.imgSubType = imgSubType;
		this.prize = prize;
		this.desc = desc;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "picByte", length = 3000)
	private byte[] picByte;
	
	@Column(name = "img_type")
	private String imgType;
	
	@Column(name = "img_sub_type")
	private String imgSubType;
	
	@Column(name = "prize")
	private Long prize;
	
	@Column(name = "description")
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	
	

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getImgSubType() {
		return imgSubType;
	}

	public void setImgSubType(String imgSubType) {
		this.imgSubType = imgSubType;
	}

	public Long getPrize() {
		return prize;
	}

	public void setPrize(Long prize) {
		this.prize = prize;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "ImageModel [id=" + id + ", name=" + name + ", type=" + type + ", picByte=" + Arrays.toString(picByte)
				+ "]";
	}
	
}