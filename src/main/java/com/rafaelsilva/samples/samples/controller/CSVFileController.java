package com.rafaelsilva.samples.samples.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rafaelsilva.samples.samples.dto.CSVFileDTO;
import com.rafaelsilva.samples.samples.service.CSVFileService;

@Controller
public class CSVFileController {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CSVFileController.class);

	@Autowired
	private CSVFileService csvFileService;

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String index(Model model) {
		model.addAttribute("message", "Select a file to upload!");
		model.addAttribute("status", true);
		return "sampleFiles/selectCSVfile";
	}

	@RequestMapping(method = RequestMethod.POST, path = "/upload-csv-file")
	public String uploadCSVFileUsingCsvToBean(Model model, @RequestParam("fileSeparator") char fileSeparator, @RequestParam("file") MultipartFile file) {
		try {
			List<CSVFileDTO> listCSVFiles = csvFileService.CSVToBean(file, fileSeparator);

			if (listCSVFiles.size() == 0) {
				model.addAttribute("message", "The file selected does not contain values or the separator selected is not correct.");
				model.addAttribute("status", false);
				return "sampleFiles/selectCSVfile";
			}

			// save list on the model
			model.addAttribute("csvName", file.getOriginalFilename());
			model.addAttribute("fileRows", listCSVFiles);
			model.addAttribute("status", true);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			model.addAttribute("message", "An error occurred while processing the CSV file.");
			model.addAttribute("status", false);
			return "sampleFiles/selectCSVfile";
		}

		return "sampleFiles/uploadstatusCSVfile";
	}
	

	@RequestMapping(method = RequestMethod.GET, path = "/exportZipFile", produces="application/zip")
	public void exportZipFile(HttpServletResponse response, @RequestParam("csvFileName") String csvFileName, @RequestParam("fileName") String customFileName,
			@RequestParam("fileContent") String customFileContent) {
		try {
		    //setting headers  
		    response.setStatus(HttpServletResponse.SC_OK);
		    response.addHeader("Content-Disposition", "attachment; filename=\"customFiles.zip\"");
		    
			csvFileService.exportCustomFiles(response.getOutputStream(), csvFileName, customFileName, customFileContent);
			
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LOGGER.error(ex.getMessage());
		}

	}
	
}