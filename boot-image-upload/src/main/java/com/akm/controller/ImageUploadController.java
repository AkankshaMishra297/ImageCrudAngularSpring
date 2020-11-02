package com.akm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akm.db.ImageRepository;
import com.akm.db.ImageSubTypeRepository;
import com.akm.db.ImageTypeRepository;
import com.akm.model.ImageModel;
import com.akm.model.ImageSubType;
import com.akm.model.ImageType;

@RestController
@CrossOrigin
@RequestMapping(path = "image")
public class ImageUploadController {

	@Autowired
	ImageRepository imageRepository;
	
	@Autowired
	ImageTypeRepository imageTypeRepository;
	
	@Autowired
	ImageSubTypeRepository imageSubTypeRepository;
	
	
	@PostMapping("/create/{type}/{subType}/{prize}/{desc}")
	public BodyBuilder create(@RequestParam("imageFile") MultipartFile file, @PathVariable("type") Long type, @PathVariable("subType") Long subType, @PathVariable("prize") Long prize, @PathVariable("desc") String desc) throws IOException {

		Optional<ImageType> type1 = imageTypeRepository.findById(type);
		
		Optional<ImageSubType> subType1 = imageSubTypeRepository.findById(subType);

		
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressZLib(file.getBytes()), type1.get().getName(), subType1.get().getName(), prize, desc);
		imageRepository.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}


	
	@GetMapping("/getAll")
	public List<ImageModel> getAllImage() throws IOException {

		final List<ImageModel> retrievedImages = imageRepository.findAll();
		
		List<ImageModel> images = retrievedImages.stream().map(retrievedImage -> {
			ImageModel img = new ImageModel(retrievedImage.getName(), retrievedImage.getType(),
					decompressZLib(retrievedImage.getPicByte()),retrievedImage.getImgType(),retrievedImage.getImgSubType(),retrievedImage.getPrize(),retrievedImage.getDesc());
			return img;
		}).collect(Collectors.toList());

		return images;
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressZLib(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressZLib(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	@GetMapping(path = { "/getImgType" })
	public List<ImageType> getImgType() throws IOException {

		List<ImageType> retrievedImage = imageTypeRepository.findAll();
		return retrievedImage;
		
	}
	
	@GetMapping(path = { "/getImgSubType/{id}" })
	public List<ImageSubType> getImgType(@PathVariable("id") Long id) throws IOException {

		List<ImageSubType> retrievedImage = imageSubTypeRepository.findAllByImageTypeId(id);
		return retrievedImage;
		
	}
}