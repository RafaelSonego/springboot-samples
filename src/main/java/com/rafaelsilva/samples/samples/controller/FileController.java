package com.rafaelsilva.samples.samples.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rafaelsilva.samples.samples.data.repository.FileGeneratedRepository;

@Controller
public class FileController {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FileController.class);
	@Autowired
	FileGeneratedRepository fileRepo;
	
	@RequestMapping("/")
	public String home() {
		LOGGER.info("We are here!!!!");
		return "sampleFiles/GenerateFiles";
	}
	
}
