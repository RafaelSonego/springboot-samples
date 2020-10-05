package com.rafaelsilva.samples.samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileController {

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
	
}
