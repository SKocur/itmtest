package com.itmagination.itmtest.item;

public record ItemDto(
        long id,
        String name,
        String description,
        ItemType type
) {
}
