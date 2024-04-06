package dev.adrianalonso.dekra.quickprod.domain;

import dev.adrianalonso.dekra.quickprod.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("ProductO no encontrado con ID: " + id));
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product actualizarProducto(@PathVariable Long id, @RequestBody Product newProduct) {
        return productRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setName(newProduct.getName());
                    productoExistente.setDescription(newProduct.getDescription());
                    productoExistente.setPrice(newProduct.getPrice());
                    return productRepository.save(productoExistente);
                }).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        productRepository.delete(product);
    }
}
