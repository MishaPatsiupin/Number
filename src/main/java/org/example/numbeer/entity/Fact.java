package org.example.numbeer.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fact", schema = "public", catalog = "numbeer")
public class Fact {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "number_id", referencedColumnName = "id")
    private Number number;

    @Basic
    @Column(name = "description")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact that = (Fact) o;
        return id == that.id && Objects.equals(number, that.number) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, description);
    }
}
