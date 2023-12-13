package com.abn.amro;

import com.abn.amro.domain.ProductTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class CSVGeneratorUtil {

    private static final String CSV_HEADER = "Client_Information,Product_Information,Total_Transaction_Amount\n";
    Logger logger = LoggerFactory.getLogger(CSVGeneratorUtil.class);

    public byte[] generateCSVContentForDailyReport(List<ProductTransaction> transactions) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (ProductTransaction transaction : transactions) {
            csvContent.append(generateClientInformation(transaction)).append(",")
                    .append(generateProductInformation(transaction)).append(",")
                    .append(generateTotalTransactionAmountInformation(transaction)).append("\n");

        }

        return csvContent.toString().getBytes();
    }

    private String generateClientInformation(ProductTransaction transaction) {
        StringBuilder csvContent = new StringBuilder();
        try {
            csvContent.append(StringUtils.trimAllWhitespace(transaction.getClientType())).append("_")
                    .append(transaction.getClientNumber()).append("_")
                    .append(transaction.getAccountNumber()).append("_")
                    .append(transaction.getSubAccountNumber());

            return csvContent.toString();
        } catch (Exception e) {
            logger.info("Exception thrown : " + e.getMessage());
            return "";
        }
    }

    private String generateProductInformation(ProductTransaction transaction) {
        try {
            StringBuilder csvContent = new StringBuilder();

            csvContent.append(StringUtils.trimAllWhitespace(transaction.getExchangeCode())).append("_")
                    .append(transaction.getProductGroupCode()).append("_")
                    .append(StringUtils.trimAllWhitespace(transaction.getSymbol())).append("_")
                    .append(transaction.getExpirationDate());

            return csvContent.toString();
        } catch (Exception e) {
            logger.info("Exception thrown : " + e.getMessage());
            return "";
        }
    }

    private String generateTotalTransactionAmountInformation(ProductTransaction transaction) {
        try {
            StringBuilder csvContent = new StringBuilder();

            long netQuantity = calculateNetQuantity(transaction);
            double netTransactionAmount = netQuantity * Double.valueOf(transaction.getTransactionPrice());
            csvContent.append(netQuantity).append(" ::: ")
                    .append(netTransactionAmount).append("\n");

            return csvContent.toString();
        } catch (Exception e) {
            logger.info("Exception thrown : " + e.getMessage());
            return "";
        }
    }

    private long calculateNetQuantity(ProductTransaction transaction) {
        try {
            return Long.parseLong(transaction.getQuantityLong()) - Long.parseLong(transaction.getQuantityShort());
        } catch (Exception e) {
            logger.info("Exception thrown : " + e.getMessage());
            return 0;
        }
    }


}
