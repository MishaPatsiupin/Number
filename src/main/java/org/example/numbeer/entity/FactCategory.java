package org.example.numbeer.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fact_category", schema = "public", catalog = "numbeer")
public class FactCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fact_id")
    private Fact fact;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactCategory that = (FactCategory) o;
        return id == that.id && Objects.equals(category, that.category) && Objects.equals(fact, that.fact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, fact);
    }
}