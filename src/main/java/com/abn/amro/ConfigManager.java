package com.abn.amro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConfigManager {

    Logger logger = LoggerFactory.getLogger(CSVGeneratorUtil.class);

    @Autowired
    private Environment env;

    public ConfigManager (Environment env) {
        this.env = env;

    }

    public String getConfigValue(String configKey) {
        return env.getProperty(configKey) == null ? "" :  env.getProperty(configKey);
    }

    public String[] getConfigValueSubstringRange(String configKey) {
        try {
            String substring = env.getProperty(configKey);
            String[] range = substring.split(",");
            return range;
        } catch (Exception e) {
            logger.info("Exception thrown : " + e.getMessage());
            return null;
        }
    }

    public String getSubstringValue(String field, String transactionString) {
        String[] range = getConfigValueSubstringRange(field);
        if(range != null) {
            String value = transactionString.substring(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
            return value;
        }
        return "";
    }

    public String defineDecimalPlace(String transactionPrice, int decimalPosition) {
        StringBuilder str = new StringBuilder(transactionPrice);
        return str.insert(str.length()-decimalPosition,'.').toString();
    }


}
