package com.itmagination.itmtest.item.command;

import com.itmagination.itmtest.item.ItemType;

public record UpdateItemCommand(
        long id,
        String name,
        String description,
        ItemType type
) {
}
