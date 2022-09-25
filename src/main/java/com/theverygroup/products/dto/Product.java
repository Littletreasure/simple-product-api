package com.theverygroup.products.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @NotEmpty(message = "id is required")
    private String id;
    @NotEmpty(message = "name is required")
    private String name;
    @NotEmpty(message = "description is required")
    private String description;
    @Valid
    private Price price;
    @Valid
    private Type type;
    @NotEmpty(message = "department is required")
    private String department;
    @NotEmpty(message = "weight is required")
    private String weight;

    public String getId() {
        return id;
    }

    public String getType() {
        return type.getName();
    }

}