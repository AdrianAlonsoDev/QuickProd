package dev.adrianalonso.dekra.quickprod;

import dev.adrianalonso.dekra.quickprod.domain.Product;
import dev.adrianalonso.dekra.quickprod.domain.ProductController;
import dev.adrianalonso.dekra.quickprod.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product(1L, "Laptop", "High performance", 1200.00);
    }

    @Test
    public void listProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = productController.listProducts();
        assertNotNull(products);
        assert products.size() == 1;
        verify(productRepository).findAll();
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        Product found = productController.getProductById(1L);
        assertNotNull(found);
        assertEquals("Product name", product.getName(), found.getName());
        verify(productRepository).findById(1L);
    }
}
