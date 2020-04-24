package com.weblient;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rs")
@Log
public class RestController {

	@RequestMapping("/all")
	public Flux<String> getAll() {
		return Flux.just("puson ", "ashmita ", "riju ", "gourab ");
	}
}
