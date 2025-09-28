package com.testepratico.products;

import com.testepratico.products.dto.ProductRequest;
import com.testepratico.products.dto.ProductResponse;
import com.testepratico.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest, User user);

    ProductResponse updateProduct(Long id, ProductRequest productRequest, User user);

    void deleteProduct(Long id, User user);

    ProductResponse getProductById(Long id, User user);

    Page<ProductResponse> getAllProducts(User user, Pageable pageable);

    Page<ProductResponse> searchProducts(User user, String search, Pageable pageable);

    List<ProductResponse> getLowStockProducts(User user, Integer threshold);
}
