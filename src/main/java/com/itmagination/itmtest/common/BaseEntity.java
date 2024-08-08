package com.itmagination.itmtest.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OptimisticLock;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@BatchSize(size = 10)
public abstract class BaseEntity<ID extends Serializable> {

    @Version
    @Column(name = "entity_version", nullable = false)
    private long entityVersion;

    @OptimisticLock(excluded = true)
    @Column(name = "entity_created", nullable = false)
    private Instant created = Instant.now();

    @OptimisticLock(excluded = true)
    @Column(name = "entity_updated", nullable = false)
    private Instant updated = Instant.now();

    protected abstract ID getId();

    public void setId(ID id) {
        throw new IllegalStateException("Entity cannot have its ID changed");
    }

    /**
     * Accessing `savedId` implies that entity has been retrieved from a
     * repository or crafted with a pre-set identifier.
     * If this condition is not met, accessing it will generate an `IllegalStateException`.
     */
    public ID getSavedId() {
        ID id = getId();
        if (id == null) {
            throw new IllegalStateException("Entity has not been saved");
        }
        return id;
    }

    @PreUpdate
    void onPreUpdate() {
        updated = Instant.now();
    }
}
