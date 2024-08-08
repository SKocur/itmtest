package com.itmagination.itmtest.item.command;

import com.itmagination.itmtest.item.ItemType;

public record CreateItemCommand(
        String name,
        String description,
        ItemType type
) {
}
