package com.abn.amro.controller;

import com.abn.amro.CSVGeneratorUtil;
import com.abn.amro.ConfigManager;
import com.abn.amro.domain.ProductTransaction;
import com.abn.amro.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ConfigManager config;
    @Autowired
    private IProductService productService;
    @Autowired
    private CSVGeneratorUtil csvGeneratorUtil;

    public Optional<List<ProductTransaction>> getTransactions() {
        return transactions;
    }

    private Optional<List<ProductTransaction>> transactions = Optional.empty();

    public ProductController(ConfigManager config, IProductService productService, CSVGeneratorUtil csvGen ) {
        this.config = config;
        this.productService = productService;
        this.csvGeneratorUtil=csvGen;
    }

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(value = "/storeProduct")
    public ResponseEntity storeProduct() {
        List<String> productsInfo;
        try {
            productsInfo = readProductFromInputFile();

            transactions = Optional.ofNullable(productService.extractProductsTransaction(productsInfo, ""));
            logger.info("transactions size ::::::: " + transactions.get().size());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(" Unable to process the Input file due to : " + e.getMessage());
        }
        return ResponseEntity.ok().body("Processed the Input file successfully.");
    }

    @GetMapping(value = "/product")
    public ResponseEntity getProduct() {

        ResponseEntity response = transactions.isPresent()
                ? ResponseEntity.ok(transactions.get())
                : ResponseEntity.noContent().build();
        logger.info("transactions size ::::::: " + (transactions.isPresent() ? transactions.get().size() : null));
        return response;

    }

    @GetMapping(value = "/product/csv")
    public ResponseEntity getProductAsCSV() {
        try {
            HttpHeaders headers = getHttpHeadersForDailyCSVReport();

            if (transactions.isPresent()) {
                byte[] csvBytes = csvGeneratorUtil.generateCSVContentForDailyReport(transactions.get());
                logger.info("transactions size ::::::: " + transactions.get().size());
                return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
            }
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(" Unable to process the Input file due to : " + e.getMessage());
        }
    }

    private HttpHeaders getHttpHeadersForDailyCSVReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", config.getConfigValue("output.file"));
        return headers;
    }

    @GetMapping(value = "/product/{clientId}")
    public ResponseEntity getProductTransactionByClientId(@PathVariable String clientId) {

        Optional<List<ProductTransaction>> productTransactionList = Optional.empty();
        if (!StringUtils.isEmpty(clientId) && transactions.isPresent()) {
            productTransactionList = Optional.ofNullable(transactions.get().stream()
                    .filter(x -> clientId.equals(x.getClientNumber())).toList());
            logger.info("productTransactionList size ::::::: " + productTransactionList.get().size());
        }

        return productTransactionList.isEmpty()
                ?  ResponseEntity.noContent().build()
                : ResponseEntity.ok(productTransactionList.get());
    }

    @GetMapping(value = "/product/{clientId}/csv")
    public ResponseEntity getProductTransactionByClientIdAsCSV(@PathVariable String clientId) {

        List<ProductTransaction> productTransactionList;
        if (!StringUtils.isEmpty(clientId) && transactions.isPresent()) {
            try {
                productTransactionList = transactions.get().stream()
                        .filter(x -> clientId.equals(x.getClientNumber())).toList();
                //return ResponseEntity.ok(productTransactionList);

                if (productTransactionList != null) {
                    HttpHeaders headers = getHttpHeadersForDailyCSVReport();

                    byte[] csvBytes = csvGeneratorUtil.generateCSVContentForDailyReport(productTransactionList);

                    logger.info("productTransactionList size ::::::: " + productTransactionList.size());

                    return  new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
                }
                return ResponseEntity.noContent().build();

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(" Unable to process the Input file due to : " + e.getMessage());
            }
        }
        return ResponseEntity.noContent().build();
        //return ResponseEntity.status(HttpStatus.OK).body(" Daily Report CSV Generated.");
    }

    public List<String> readProductFromInputFile() throws IOException {
        List<String> productsInfo = new ArrayList<>();

        //Resource inputDataResource = new ClassPathResource("Input.txt");
        Resource inputDataResource = new ClassPathResource(config.getConfigValue("input.file"));
        File file = inputDataResource.getFile();

        try (Stream<String> linesStream = Files.lines(file.toPath())) {
            linesStream.forEach(line -> {
                productsInfo.add(String.valueOf(line));
            });
        }

        logger.info("productsInfo.size() :::::" + productsInfo.size());
        return productsInfo;
    }
}
