package br.rosa.restapi.controller.swagger;

import br.rosa.restapi.vo.ProductVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "products APIii")
public interface ProductControllerSwagger {

    @Operation(description = "método retorna textos", responses =
            {@ApiResponse(responseCode = "400", description = "User not found")})
    ResponseEntity<String> getTest(
            @Parameter(description = "id of test", required = true) Long id);

    ResponseEntity<ProductVO> postTest(@RequestBody ProductVO test);

    ResponseEntity<String> patchTest(@PathVariable Long orderId);

    ResponseEntity<String> delete(@PathVariable Long orderId);
}
