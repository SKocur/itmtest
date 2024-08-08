package com.itmagination.itmtest.item;

import com.itmagination.itmtest.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "items")
@SequenceGenerator(
        name = "Item_seq_gen",
        sequenceName = "items_seq",
        allocationSize = 1
)
@NoArgsConstructor
public class Item extends BaseEntity<Long> {

    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "Item_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    public Item(String name, String description, ItemType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    @Override
    protected Long getId() {
        return id;
    }
}
