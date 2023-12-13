package com.abn.amro.service;

import com.abn.amro.ConfigManager;
import com.abn.amro.domain.ProductTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class ProductService implements IProductService {

    private final ConfigManager config;

    private List<ProductTransaction> productTransactionList = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(List<ProductTransaction> productTransactionList, ConfigManager config) {
        this.productTransactionList = productTransactionList;
        this.config = config;
    }


    @Override
    public List<ProductTransaction> extractProductsTransaction(List<String> productsInfo, String clientId) {
        ListIterator<String> listIterator = productsInfo.listIterator();

        while (listIterator.hasNext()) {

            ProductTransaction transaction = new ProductTransaction();
            String productTransactionInfo = listIterator.next();


            transaction.setRecordCode(config.getSubstringValue("recordCode", productTransactionInfo));
            transaction.setClientType(config.getSubstringValue("clientType", productTransactionInfo));
            transaction.setClientNumber(config.getSubstringValue("clientNumber", productTransactionInfo));
            transaction.setAccountNumber(config.getSubstringValue("accountNumber", productTransactionInfo));
            transaction.setSubAccountNumber(config.getSubstringValue("subAccountNumber", productTransactionInfo));
            transaction.setOppositePartyCode(config.getSubstringValue("oppositePartyCode", productTransactionInfo));
            transaction.setProductGroupCode(config.getSubstringValue("productGroupCode", productTransactionInfo));
            transaction.setExchangeCode(config.getSubstringValue("exchangeCode", productTransactionInfo));
            transaction.setSymbol(config.getSubstringValue("symbol", productTransactionInfo));
            transaction.setExpirationDate(config.getSubstringValue("expirationDate", productTransactionInfo));
            transaction.setCurrencyCode(config.getSubstringValue("currencyCode", productTransactionInfo));
            transaction.setMovementCode(config.getSubstringValue("movementCode", productTransactionInfo));
            transaction.setBuySellCode(config.getSubstringValue("buySellCode", productTransactionInfo));
            transaction.setQuantityLongSign(config.getSubstringValue("quantityLongSign", productTransactionInfo));
            transaction.setQuantityLong(config.getSubstringValue("quantityLong", productTransactionInfo));
            transaction.setQuantityShortSign(config.getSubstringValue("quantityShortSign", productTransactionInfo));
            transaction.setQuantityShort(config.getSubstringValue("quantityShort", productTransactionInfo));
            transaction.setExchangeBrokerFee(config.getSubstringValue("exchangeBrokerFee", productTransactionInfo));
            transaction.setExchangeBrokerFeeDC(config.getSubstringValue("exchangeBrokerFeeDC", productTransactionInfo));
            transaction.setExchangeBrokerFeeCurrencyCode(config.getSubstringValue("exchangeBrokerFeeCurrencyCode", productTransactionInfo));
            transaction.setClearingFee(config.getSubstringValue("clearingFee", productTransactionInfo));
            transaction.setClearingFeeDC(config.getSubstringValue("clearingFeeDC", productTransactionInfo));
            transaction.setClearingFeeCurrencyCode(config.getSubstringValue("clearingFeeCurrencyCode", productTransactionInfo));
            transaction.setCommissionFee(config.getSubstringValue("commissionFee", productTransactionInfo));
            transaction.setCommissionFeeDC(config.getSubstringValue("commissionFeeDC", productTransactionInfo));
            transaction.setCommissionFeeCurrencyCode(config.getSubstringValue("commissionFeeCurrencyCode", productTransactionInfo));
            transaction.setTransactionDate(config.getSubstringValue("transactionDate", productTransactionInfo));
            transaction.setFutureReference(config.getSubstringValue("futureReference", productTransactionInfo));
            transaction.setTicketNumber(config.getSubstringValue("ticketNumber", productTransactionInfo));
            transaction.setExternalNumber(config.getSubstringValue("externalNumber", productTransactionInfo));
            transaction.setTransactionPrice(config.defineDecimalPlace(config.getSubstringValue("transactionPrice", productTransactionInfo),7));
            transaction.setTraderInitials(config.getSubstringValue("traderInitials", productTransactionInfo));
            transaction.setOpenCloseCode(config.getSubstringValue("openCloseCode", productTransactionInfo));
            transaction.setOppositeTraderID(config.getSubstringValue("oppositeTraderID", productTransactionInfo));

            productTransactionList.add(transaction);
        }

        logger.info("productTransactionList ::::::: " + productTransactionList.size());

        return productTransactionList;
    }
}
