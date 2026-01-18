package br.rosa.rest_rod_2.rest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.rosa.rest_rod_2.rest.feign.vo.Hello;

@FeignClient(name = "${feign.hello.name}", url = "${feign.hello.url}")
public interface HelloClient {

	@GetMapping("/v1/hello")
	Hello hello(@RequestParam(value = "name", defaultValue = "yooo") String name);
}
