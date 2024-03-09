package org.example.numbeer.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fact", schema = "public", catalog = "numbeer")
public class FactEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "number_id")
    private long numberId;
    @Basic
    @Column(name = "description")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberId() {
        return numberId;
    }

    public void setNumberId(long numberId) {
        this.numberId = numberId;
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
        FactEntity that = (FactEntity) o;
        return id == that.id && numberId == that.numberId && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberId, description);
    }
}
