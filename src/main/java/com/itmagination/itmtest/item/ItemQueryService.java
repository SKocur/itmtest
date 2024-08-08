package com.itmagination.itmtest.item;

import com.itmagination.itmtest.common.SearchResult;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemQueryService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Cacheable(value = "items", key = "{#id, #name, #type, #pageable.pageNumber, #pageable.pageSize}")
    public SearchResult<ItemDto> findAll(@Nullable Long id, @Nullable String name, @Nullable ItemType type, Pageable pageable) {
        return pageToSearchResult(
                itemRepository.findAllByProperties(id, name, type, pageable).map(itemMapper::toDto)
        );
    }

    private <T> SearchResult<T> pageToSearchResult(Page<T> page) {
        return new SearchResult<>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
        );
    }
}
