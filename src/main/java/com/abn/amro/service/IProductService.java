package com.abn.amro.service;

import com.abn.amro.domain.ProductTransaction;

import java.util.List;

public interface IProductService {
    List<ProductTransaction> extractProductsTransaction(List<String> productsInfo, String clientId);
}
