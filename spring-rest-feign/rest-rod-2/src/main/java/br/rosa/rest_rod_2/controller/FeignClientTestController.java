package br.rosa.rest_rod_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.rosa.rest_rod_2.rest.feign.HelloClient;
import br.rosa.rest_rod_2.rest.feign.vo.Hello;

@RestController
public class FeignClientTestController {

	@Autowired
	private HelloClient helloClient;

	@GetMapping("/feign/hello")
	public Hello getMethodName(@RequestParam(name = "param", required = false) String param) {
		System.out.println("die");
		return this.helloClient.hello(param);
	}

}
