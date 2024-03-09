package org.example.numbeer.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fact_category", schema = "public", catalog = "numbeer")
public class FactCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "category_id")
    private Long categoryId;
    @Basic
    @Column(name = "fact_id")
    private Long factId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getFactId() {
        return factId;
    }

    public void setFactId(Long factId) {
        this.factId = factId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactCategoryEntity that = (FactCategoryEntity) o;
        return id == that.id && Objects.equals(categoryId, that.categoryId) && Objects.equals(factId, that.factId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, factId);
    }
}
