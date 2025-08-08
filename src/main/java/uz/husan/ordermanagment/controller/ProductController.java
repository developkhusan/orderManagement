package uz.husan.ordermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.husan.ordermanagment.dto.product.ProductDTO;

@RequestMapping("/products")
public interface ProductController {
        @GetMapping
        ResponseEntity<?> getAllProducts(@RequestParam Integer page, @RequestParam Integer size);
        @PostMapping
        ResponseEntity<?> createProduct(ProductDTO productDTO);
        ResponseEntity<?> updateProduct(Long id, ProductDTO productDTO);
        ResponseEntity<?> deleteProduct(Long id);
}
