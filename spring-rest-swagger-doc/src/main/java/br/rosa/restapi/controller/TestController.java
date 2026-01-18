package br.rosa.restapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.rosa.restapi.vo.TestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Test method to manage test")
public class TestController {

	@GetMapping(path = "/tests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "method to return a TEST SHIT", responses = {
			@ApiResponse(responseCode = "400", description = "User not found") })
	public ResponseEntity<String> getTest(
			@PathVariable @Parameter(description = "id of test", required = true) Long id) {
		System.out.println("####### id: " + id);
		return ResponseEntity.ok("get");
	}

	@GetMapping("/tests_v2/{id}/{name}")
	@Operation(description = "method to return a TEST V2", parameters = {
			@Parameter(name = "id", description = "id of test2", required = true),
			@Parameter(name = "name", description = "name of test", required = false) })
	public ResponseEntity<String> getTestV2(@PathVariable Long id,
			@RequestParam(value = "name", defaultValue = "die") String name) {
		System.out.println("####### id: " + id);
		return ResponseEntity.ok("get");
	}

	@PostMapping(path = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestVO> postTest(@RequestBody TestVO test) {
		System.out.println("####### name: " + test);
		return ResponseEntity.ok(new TestVO());
	}

	@PatchMapping(path = "/tests/{orderId}", consumes = "application/json")
	public ResponseEntity<String> patchTest(@PathVariable Long orderId) {
		System.out.println("####### orderId: " + orderId);
		return ResponseEntity.ok("patch");
	}
}
