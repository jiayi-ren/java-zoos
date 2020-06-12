package local.jren.zoos.repositories;

import local.jren.zoos.models.Zoo;
import org.springframework.data.repository.CrudRepository;

public interface ZooRepository extends CrudRepository<Zoo, Long> {
    void deleteZooByZooid(long zooid);
}
