package com.itmagination.itmtest.controller.requests;

import com.itmagination.itmtest.item.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateItemRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull ItemType type
) {
}
