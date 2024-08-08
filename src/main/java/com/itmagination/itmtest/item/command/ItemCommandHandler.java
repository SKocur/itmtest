package com.itmagination.itmtest.item.command;

import com.itmagination.itmtest.item.Item;
import com.itmagination.itmtest.item.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Cache evicts every entry to avoid stale data, assuming that data adjustments are rare.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandHandler {

    private final ItemRepository itemRepository;

    @CacheEvict(value = "items", allEntries = true)
    public void handle(CreateItemCommand command) {
        itemRepository.save(new Item(
                command.name(),
                command.description(),
                command.type()
        ));
    }

    @CacheEvict(value = "items", allEntries = true)
    public void handle(UpdateItemCommand command) {
        Item item = itemRepository.getReferenceById(command.id());
        item.setName(command.name());
        item.setDescription(command.description());
        item.setType(command.type());
    }

    @CacheEvict(value = "items", allEntries = true)
    public void handle(DeleteItemCommand command) {
        itemRepository.deleteById(command.getId());
    }
}
