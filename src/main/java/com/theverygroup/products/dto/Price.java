package com.theverygroup.products.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    @NotNull(message = "value is required")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal value;
    @NotEmpty(message = "currency is required")
    private String currency;

}