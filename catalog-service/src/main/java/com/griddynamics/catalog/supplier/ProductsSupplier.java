package com.griddynamics.catalog.supplier;

import com.griddynamics.catalog.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductsSupplier implements Supplier<Set<Product>> {
    @Value("${catalog.service.product.file.path}")
    private String productFilePath;

    @Override
    public Set<Product> get() {
        try {
            return Files.lines(Paths.get(productFilePath))
                    .skip(1L)
                    .map(this::toProduct)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error("Can't read product data from the file=[{}].\n Reason: {}", productFilePath, e);
            return Collections.emptySet();
        }
    }

    private Optional<Product> toProduct(String line) {
        if (StringUtils.isEmpty(line)) {
            return Optional.empty();
        }

        String[] split = StringUtils.split(line, ',');

        int productFieldsLength = FieldUtils.getAllFields(Product.class).length;

        /*if (split.length != productFieldsLength) {
            log.warn("Line length=[{}] doesn't equal to Product class fields number {}",
                    split.length,
                    productFieldsLength);
            log.warn("Skip item=[{}]", line);

            return Optional.empty();
        }*/

        try {
            Product.ProductBuilder builder = Product.builder();

            builder.uid(split[0]);
            builder.sku(split[1]);
            /*builder.nameTitle(split[2]);
            builder.description(split[3]);

            if (isNoneEmpty(split[4])) {
                builder.listPrice(BigDecimal.valueOf(Double.valueOf(split[4])));
            }

            if (isNoneEmpty(split[5])) {
                builder.salePrice(BigDecimal.valueOf(Double.valueOf(split[5])));
            }

            builder.category(split[6]);
            builder.categoryTree(split[7]);
            builder.averageProductRating(split[8]);
            builder.productUrl(split[9]);
            builder.productImageUrls(split[10]);
            builder.brand(split[11]);

            if (StringUtils.isNotEmpty(split[12])) {
                builder.totalNumberReviews(Integer.valueOf(split[12]));
            }

            builder.reviews(split[13]);*/

            return Optional.of(builder.build());
        } catch (Exception e) {
            log.warn("Can't properly parse line=[{}]. Reason: {}", line, e);
        }

        return Optional.empty();
    }
}
