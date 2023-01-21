package com.engeto.Project2;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

@CrossOrigin
@RestController
public class Project2controller {

    ProductService productService;

    public Project2controller() throws SQLException {
        productService = new ProductService();
    }

    @GetMapping("/product")
    public Collection < Product > getAllProducts() throws SQLException {

        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getItemById(@PathVariable("id") Long id) throws SQLException {
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public Product postProduct(@RequestBody Product product) throws SQLException {
        Long generatedId = productService.saveNewProduct(product);
        product.setId(generatedId);

        return product;
    }

    @PutMapping("/product/{id}")
    public void putProduct(@RequestParam(value = "price", required = true) BigDecimal price, @PathVariable("id") Long id) throws SQLException {
        productService.updatePriceById(id, price);
    }

    @DeleteMapping("/product")
    public void deleteOutOfSaleProducts() throws SQLException {

        productService.deleteOutOfSaleProducts();
    }

}