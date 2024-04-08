package dev.adrianalonso.dekra.quickprod.product;

import dev.adrianalonso.dekra.quickprod.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Producto no encontrado con ID: " + id));
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product actualizarProducto(@PathVariable Long id, @RequestBody Product newProduct) {
        return productRepository.findById(id)
                .map(productExists -> {
                    productExists.setName(newProduct.getName());
                    productExists.setDescription(newProduct.getDescription());
                    productExists.setPrice(newProduct.getPrice());
                    return productRepository.save(productExists);
                }).orElseThrow(() -> new ApiException("Producto no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Producto no encontrado con ID: " + id));
        productRepository.delete(product);
    }
}
