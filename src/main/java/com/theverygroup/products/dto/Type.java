package com.theverygroup.products.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public enum Type {

    BOOKS("Book"),
    CASUAL("Casual"),
    CERAMICS("Ceramics"),
    ELECTRICAL("Electrical"),
    RUNNING("Running"),
    VOUCHERS("Voucher");

    @JsonValue
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

}