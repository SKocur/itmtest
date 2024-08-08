package com.itmagination.itmtest.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
                select i from Item i
                where (:id is null or i.id = :id)
                and (:name is null or i.name = :name)
                and (:type is null or i.type = :type)
            """)
    Page<Item> findAllByProperties(Long id, String name, ItemType type, Pageable pageable);
}
