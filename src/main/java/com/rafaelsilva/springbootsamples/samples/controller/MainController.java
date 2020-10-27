package com.rafaelsilva.springbootsamples.samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	//private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainController.class);

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String index(Model model) {
		return "index";
	}
}