package com.dauphine.blogger.dto;

import jakarta.validation.constraints.NotBlank;

public class CreationCategoryRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
