package com.abn.amro;

import com.abn.amro.domain.ProductTransaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestHelper {

    public static void main(String[] args) {
        List<Integer> numbers
                = Arrays.asList(11, 22, 33, 44,
                55, 66, 77, 88,
                99, 100);


        System.out.println(
                numbers.stream()
                        .filter(number -> number % 2 == 0)
                        .mapToInt(e -> e * 2).count()
        );
        StringBuilder str
                = new StringBuilder("GeeksForGeeks");

        System.out.println("String contains = " + str);

        System.out.println("SubSequence = "
                + str.substring(5, 8));

        System.out.println("SubSequence = "
                + str.substring(0, 1));

        System.out.println("String contains = " + Double.valueOf("00009250.0050000"));
        StringBuilder str1 = new StringBuilder("000092500050000");
      /*Adding character at the beginning
      using StringBuilder insert() function */
        System.out.println(str1.insert(str1.length()-7,'.'));
        System.out.println("String contains = " + str);
        System.out.println("String contains = " + System.getProperty("user.dir"));
        System.out.println("String contains = " + new File("").getPath());

        Optional<List<ProductTransaction>> productTransactionList = Optional.empty();

        System.out.println(productTransactionList.isPresent());


    }
}
