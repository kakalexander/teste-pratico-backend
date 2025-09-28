package com.testepratico.products.service.impl;

import com.testepratico.products.ProductService;
import com.testepratico.products.dto.ProductRequest;
import com.testepratico.products.dto.ProductResponse;
import com.testepratico.products.model.Product;
import com.testepratico.products.repository.ProductRepository;
import com.testepratico.users.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest, User user) {
        log.info("Criando produto: {} para usuário: {}", productRequest.getName(), user.getUsername());
        
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .user(user)
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Produto criado com sucesso. ID: {}", savedProduct.getId());
        
        return ProductResponse.from(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest, User user) {
        log.info("Atualizando produto ID: {} para usuário: {}", id, user.getUsername());
        
        Product product = findProductByIdAndUser(id, user);
        
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        Product updatedProduct = productRepository.save(product);
        log.info("Produto atualizado com sucesso. ID: {}", updatedProduct.getId());
        
        return ProductResponse.from(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) {
        log.info("Deletando produto ID: {} para usuário: {}", id, user.getUsername());
        
        Product product = findProductByIdAndUser(id, user);
        productRepository.delete(product);
        
        log.info("Produto deletado com sucesso. ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id, User user) {
        log.info("Buscando produto ID: {} para usuário: {}", id, user.getUsername());
        
        Product product = findProductByIdAndUser(id, user);
        return ProductResponse.from(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(User user, Pageable pageable) {
        log.info("Listando produtos para usuário: {} - Página: {}, Tamanho: {}", 
                user.getUsername(), pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Product> products = productRepository.findByUser(user, pageable);
        return products.map(ProductResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(User user, String search, Pageable pageable) {
        log.info("Buscando produtos com termo: '{}' para usuário: {}", search, user.getUsername());
        
        Page<Product> products = productRepository.findByUserAndSearch(user, search, pageable);
        return products.map(ProductResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStockProducts(User user, Integer threshold) {
        log.info("Buscando produtos com estoque baixo (<= {}) para usuário: {}", threshold, user.getUsername());
        
        List<Product> products = productRepository.findLowStockProducts(user, threshold);
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    private Product findProductByIdAndUser(Long id, User user) {
        return productRepository.findById(id)
                .filter(product -> product.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> {
                    log.error("Produto não encontrado. ID: {}, Usuário: {}", id, user.getUsername());
                    return new RuntimeException("Produto não encontrado");
                });
    }
}
