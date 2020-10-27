package com.rafaelsilva.springbootsamples.samples.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.rafaelsilva.springbootsamples.samples.dto.CSVFileDTO;
import com.rafaelsilva.springbootsamples.samples.service.CSVFileService;

@Controller
public class CSVFileController {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CSVFileController.class);

	@Autowired
	private CSVFileService csvFileService;

	@RequestMapping(method = RequestMethod.GET, path = "/importCSVFile")
	public String importCSVFile(Model model) {
		model.addAttribute("message", "Select a file to upload!");
		model.addAttribute("status", true);
		return "sampleFiles/importCSVFile";
	}

	@RequestMapping(method = RequestMethod.POST, path = "/upload-csv-file")
	public ModelAndView uploadCSVFileUsingCsvToBean(@RequestParam("fileSeparator") char fileSeparator, @RequestParam("file") MultipartFile file) {
		try {
			List<CSVFileDTO> listCSVFiles = csvFileService.CSVToBean(file, fileSeparator);
			ModelAndView mv = new ModelAndView("sampleFiles/uploadstatusCSVfile");
			
			if (listCSVFiles.size() == 0) {
				throw new Exception("The file selected does not contain values or the separator selected is not correct.");
			}else {
				// save list on the model
				mv.addObject("csvName", file.getOriginalFilename());
				mv.addObject("fileRows", listCSVFiles);
				mv.addObject("status", true);				
			}
			return mv;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			ModelAndView mv = new ModelAndView("sampleFiles/importCSVFile");
			mv.addObject("message", "An error occurred while processing the CSV file.");
			mv.addObject("status", false);
			return mv;
		}
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