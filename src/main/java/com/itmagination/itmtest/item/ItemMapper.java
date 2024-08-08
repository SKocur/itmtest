package com.itmagination.itmtest.item;

import org.springframework.stereotype.Component;

@Component
class ItemMapper {

    ItemDto toDto(Item item) {
        return new ItemDto(
                item.getSavedId(),
                item.getName(),
                item.getDescription(),
                item.getType()
        );
    }
}
