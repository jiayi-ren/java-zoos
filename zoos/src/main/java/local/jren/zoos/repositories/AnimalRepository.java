package local.jren.zoos.repositories;

import local.jren.zoos.models.Animal;
import local.jren.zoos.views.AnimalCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {
    @Query(value = "SELECT a.animaltype, a.animalid,count(za.animalid) as countzoos FROM animals a LEFT JOIN zooanimals za ON a.animalid = za.animalid GROUP BY a.animaltype",
            nativeQuery = true)
    List<AnimalCounts> getCountAnimal();
}
