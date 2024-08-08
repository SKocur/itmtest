package com.itmagination.itmtest.controller;

import com.itmagination.itmtest.common.SearchResult;
import com.itmagination.itmtest.controller.requests.CreateItemRequest;
import com.itmagination.itmtest.controller.requests.UpdateItemRequest;
import com.itmagination.itmtest.item.ItemDto;
import com.itmagination.itmtest.item.ItemQueryService;
import com.itmagination.itmtest.item.ItemType;
import com.itmagination.itmtest.item.command.CreateItemCommand;
import com.itmagination.itmtest.item.command.DeleteItemCommand;
import com.itmagination.itmtest.item.command.ItemCommandHandler;
import com.itmagination.itmtest.item.command.UpdateItemCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemQueryService itemQueryService;

    private final ItemCommandHandler itemCommandHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.principal.username == 'admin'")
    public void create(@RequestBody @Valid CreateItemRequest request) {
        itemCommandHandler.handle(new CreateItemCommand(
                request.name(),
                request.description(),
                request.type()
        ));
    }

    @PostMapping("/create-many")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.principal.username == 'admin'")
    public void createMany(@RequestBody @Valid Set<CreateItemRequest> request) {
        request.forEach(itemRequest -> {
            itemCommandHandler.handle(new CreateItemCommand(
                    itemRequest.name(),
                    itemRequest.description(),
                    itemRequest.type()
            ));
        });
    }

    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public void update(@PathVariable long id, @RequestBody @Valid UpdateItemRequest request) {
        itemCommandHandler.handle(new UpdateItemCommand(
                id,
                request.name(),
                request.description(),
                request.type()
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public void delete(@PathVariable long id) {
        itemCommandHandler.handle(new DeleteItemCommand(id));
    }

    @GetMapping("/search")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public SearchResult<ItemDto> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ItemType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemQueryService.findAll(id, name, type, pageable);
    }

    @GetMapping("/list")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public SearchResult<ItemDto> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemQueryService.findAll(null, null, null, pageable);
    }
}
