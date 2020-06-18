package local.jren.zoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "zooanimals", uniqueConstraints = {@UniqueConstraint(columnNames = {"zooid", "animalid"})})
public class ZooAnimals extends Auditable implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "zooid")
    @JsonIgnoreProperties(value = "animals", allowSetters = true)
    private Zoo zoo;

    @Id
    @ManyToOne
    @JoinColumn(name = "animalid")
    @JsonIgnoreProperties(value = "zoos", allowSetters = true)
    private Animal animal;

//    private String incomingzoo;

    public ZooAnimals() {
    }

    public ZooAnimals(Zoo zoo, Animal animal) {
        this.zoo = zoo;
        this.animal = animal;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

//    public String getIncomingzoo() {
//        return incomingzoo;
//    }
//
//    public void setIncomingzoo(String incomingzoo) {
//        this.incomingzoo = incomingzoo;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooAnimals that = (ZooAnimals) o;
        return getZoo().equals(that.getZoo()) &&
                getAnimal().equals(that.getAnimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZoo(), getAnimal());
    }
}
