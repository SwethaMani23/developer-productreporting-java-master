package com.abn.amro.controller;

import com.abn.amro.CSVGeneratorUtil;
import com.abn.amro.ConfigManager;
import com.abn.amro.domain.ProductTransaction;
import com.abn.amro.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private static final String SMART_METER_ID = "1234";
    private ProductController productController;
    private ProductService productService;

    ConfigManager config = mock(ConfigManager.class);

    Optional<List<ProductTransaction>> transactions = mock();

    @BeforeEach
    public void setUp() {
        this.productService = new ProductService(new ArrayList<>(), config );
        this.productController = new ProductController(config, productService,new CSVGeneratorUtil());
    }

    @Test
    public void givenValidInputFileIsPresentWhenCallingStoreProduct() {
        when(config.getConfigValue("input.file")).thenReturn("Input.txt");
        assertThat(productController.storeProduct().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productController.getTransactions().isPresent()).isTrue();
    }

    @Test
    public void givenInvalidInputFileIsPresentWhenCallingStoreProduct() {
        when(config.getConfigValue("input.file")).thenReturn("Input1.txt");
        assertThat(productController.storeProduct().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenNoInputFileIsPresentWhenCallingExtractProductsTransactionShouldReturnErrorResponse() {
        assertThat(productController.getProduct().getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenValidInputFileIsPresentWhenCallingGetProductShouldReturnTransactionsAndOKResponse() {
        when(config.getConfigValue("input.file")).thenReturn("Input.txt");
        productController.storeProduct();
        assertThat(productController.getProduct().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productController.getTransactions().isPresent()).isTrue();
    }

    @Test
    public void givenInValidInputFileIsPresentWhenCallingGetProductShouldNotReturnTransactionsAndOKResponse() {
        when(config.getConfigValue("input.file")).thenReturn("Input1.txt");
        productController.storeProduct();
        assertThat(productController.getProduct().getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(productController.getTransactions().isPresent()).isFalse();
    }

    @Test
    public void givenValidInputFileIsPresentWhenCallingGetProductWithValidClientIDShouldFilterTransactions() {
        when(config.getConfigValue("input.file")).thenReturn("Input.txt");
        productController.storeProduct();
        ProductTransaction transaction = new ProductTransaction();
        transaction.setClientNumber("4321");
        when(transactions.get()).thenReturn(Arrays.asList(transaction));
        assertThat(productController.getProductTransactionByClientId("4321").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenValidInputFileIsPresentWhenCallingGetProductWithInValidClientIDShouldFilterTransactions() {
        when(config.getConfigValue("input.file")).thenReturn("Input.txt");
        productController.storeProduct();
        ProductTransaction transaction = new ProductTransaction();
        transaction.setClientNumber("4321");
        when(transactions.get()).thenReturn(Arrays.asList(transaction));
        assertThat(productController.getProductTransactionByClientId("111").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenGetProductReturnTransactionsThenCSVFileIsCreatedAndOKResponse() {
        when(config.getConfigValue("input.file")).thenReturn("Input.txt");
        productController.storeProduct();
        assertThat(productController.getProductAsCSV().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productController.getTransactions().isPresent()).isTrue();
        assertThat(productController.getProductAsCSV().getBody()).isNotNull();
    }


}
