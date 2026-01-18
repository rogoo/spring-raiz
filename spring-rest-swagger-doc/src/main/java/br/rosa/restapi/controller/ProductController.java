package br.rosa.restapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.rosa.restapi.vo.ProductVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "products API")
public class ProductController {

	@GetMapping(path = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "method to return a TEST", responses = {
			@ApiResponse(responseCode = "400", description = "User not found") })
	public ResponseEntity<String> getTest(
			@PathVariable @Parameter(description = "id of test", required = true) Long id) {
		System.out.println("####### id: " + id);
		return ResponseEntity.ok("get");
	}

	@PostMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductVO> postTest(@RequestBody ProductVO test) {
		System.out.println("####### name: " + test);
		return ResponseEntity.ok(new ProductVO());
	}

	@PatchMapping(path = "/products/{orderId}", consumes = "application/json")
	public ResponseEntity<String> patchTest(@PathVariable Long orderId) {
		System.out.println("####### orderId: " + orderId);
		return ResponseEntity.ok("patch");
	}

	@DeleteMapping(path = "/products/{orderId}", consumes = "application/json")
	public ResponseEntity<String> delete(@PathVariable Long orderId) {
		System.out.println("####### orderId: " + orderId);
		return ResponseEntity.ok("patch");
	}
}
