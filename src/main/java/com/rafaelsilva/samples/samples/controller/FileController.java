package com.rafaelsilva.samples.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rafaelsilva.samples.samples.data.repository.FileGeneratedRepository;

@Controller
public class FileController {

	@Autowired
	FileGeneratedRepository fileRepo;
	
	@RequestMapping("/")
	public String home() {
		return "sampleFiles/GenerateFiles";
	}
	
}
