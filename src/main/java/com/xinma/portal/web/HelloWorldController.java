package com.xinma.portal.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping(value = "hello")
	public String hello() {
		return "Hi, nihao , world!";
	}
}
