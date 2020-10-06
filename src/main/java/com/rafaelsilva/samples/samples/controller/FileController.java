package com.rafaelsilva.samples.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rafaelsilva.samples.samples.data.entity.FileGeneratedEntity;
import com.rafaelsilva.samples.samples.data.repository.FileGeneratedRepository;

@Controller
public class FileController {

	@Autowired
	FileGeneratedRepository fileRepo;
	
	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
	
	@RequestMapping("/addFile")
	public String addFile(FileGeneratedEntity entity) {
		fileRepo.save(entity);
		return "home.jsp";
	}
	
	@RequestMapping("/getAllFiles")
	public Iterable<FileGeneratedEntity> getAllFiles() {
		Iterable<FileGeneratedEntity> findAll = fileRepo.findAll();
		return findAll;
	}
	
}
