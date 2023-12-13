package com.abn.amro.service;

import com.abn.amro.ConfigManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class ProductServiceTest {

    private ProductService productService ;
    ConfigManager config = mock(ConfigManager.class);

    @BeforeEach
    public void setUp() {
        this.productService = new ProductService(new ArrayList<>(), config );
    }

    @Test
    public void givenInValidInputFileThatDoesNotExistShouldReturnEmptyTransactions() {
        assertThat(productService.extractProductsTransaction(new ArrayList<>(),"")).isEqualTo(new ArrayList<>());
    }

    @Test
    public void givenValidInputFileThatExistShouldReturnEmptyTransactions() {
        assertThat(productService.extractProductsTransaction(new ArrayList<>(),"12345")).isEqualTo(new ArrayList<>());
    }


}
