package br.rosa.rest_rod_1.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.rosa.rest_rod_1.vo.Hello;

@RestController
@RequestMapping("/v1")
public class HelloController {

	private final AtomicInteger counter = new AtomicInteger();

	@GetMapping("/hello")
	public Hello hello(@RequestParam(value = "name", defaultValue = "yooo") String name) {
		return new Hello(counter.incrementAndGet(), name);
	}

}
