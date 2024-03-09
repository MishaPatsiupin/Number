package org.example.numbeer.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "number", schema = "public", catalog = "numbeer")
public class Number {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "number_data")
    private long numberData;

    @OneToMany(mappedBy = "number")
    private List<Fact> facts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberData() {
        return numberData;
    }

    public void setNumberData(long numberData) {
        this.numberData = numberData;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number that = (Number) o;
        return id == that.id && numberData == that.numberData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberData);
    }
}