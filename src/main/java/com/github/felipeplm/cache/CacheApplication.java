package com.github.felipeplm.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableCaching
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }

    @Bean
    ApplicationRunner runner(ProductService productService) {
        return args -> {
            System.out.println("\n\n\n\n");

            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 2: " + productService.getById(2L));
            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 1: " + productService.getById(1L));
        };
    }
}

record Product(Long id, String name, String description) {
}

@Service
class ProductService {

    private final Map<Long, Product> products = new HashMap<>();

    public ProductService() {
        products.put(1L, new Product(1L, "Notebook", "Notebook gamer"));
        products.put(2L, new Product(2L, "Mouse", "Mouse sem fio"));
        products.put(3L, new Product(3L, "Teclado", "Teclado mecânico"));
        products.put(4L, new Product(4L, "Monitor", "Monitor 144 Hz"));
        products.put(5L, new Product(5L, "SSD", "SSD NVMe 1 TB"));
    }

    @Cacheable(value = "products", key = "#id")
    public Product getById(Long id) {
        System.out.println("Searching product ID " + id + "...");

        simulateLatency();

        return products.get(id);
    }

    private void simulateLatency() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(exception);
        }
    }
}