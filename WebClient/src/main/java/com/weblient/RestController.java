package com.weblient;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMapping;


import reactor.core.publisher.Flux;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rs")
public class RestController {

	@RequestMapping("/all")
	public Flux<String> getAll(){
		return Flux.just("puson ","ashmita ","riju ","gourab ");
	}
}
