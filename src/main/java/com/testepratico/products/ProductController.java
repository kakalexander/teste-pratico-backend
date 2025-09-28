package com.testepratico.products;

import com.testepratico.products.dto.ProductRequest;
import com.testepratico.products.dto.ProductResponse;
import com.testepratico.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto para o usuário autenticado")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest,
            @AuthenticationPrincipal User user) {
        
        log.info("Criando produto: {} para usuário: {}", productRequest.getName(), user.getUsername());
        
        ProductResponse response = productService.createProduct(productRequest, user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos do usuário autenticado com paginação")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Listando produtos para usuário: {}", user.getUsername());
        
        Page<ProductResponse> products = productService.getAllProducts(user, pageable);
        
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos", description = "Busca produtos por nome ou descrição")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @RequestParam String search,
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Buscando produtos com termo: '{}' para usuário: {}", search, user.getUsername());
        
        Page<ProductResponse> products = productService.searchProducts(user, search, pageable);
        
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Produtos com estoque baixo", description = "Lista produtos com estoque baixo")
    public ResponseEntity<List<ProductResponse>> getLowStockProducts(
            @RequestParam(defaultValue = "5") Integer threshold,
            @AuthenticationPrincipal User user) {
        
        log.info("Buscando produtos com estoque baixo (<= {}) para usuário: {}", threshold, user.getUsername());
        
        List<ProductResponse> products = productService.getLowStockProducts(user, threshold);
        
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Busca um produto específico por ID")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        
        log.info("Buscando produto ID: {} para usuário: {}", id, user.getUsername());
        
        ProductResponse product = productService.getProductById(id, user);
        
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest,
            @AuthenticationPrincipal User user) {
        
        log.info("Atualizando produto ID: {} para usuário: {}", id, user.getUsername());
        
        ProductResponse response = productService.updateProduct(id, productRequest, user);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        
        log.info("Deletando produto ID: {} para usuário: {}", id, user.getUsername());
        
        productService.deleteProduct(id, user);
        
        return ResponseEntity.noContent().build();
    }
}
