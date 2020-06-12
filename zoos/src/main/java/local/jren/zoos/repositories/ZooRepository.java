package local.jren.zoos.repositories;

import local.jren.zoos.models.Zoo;
import local.jren.zoos.views.Count;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ZooRepository extends CrudRepository<Zoo, Long> {
    void deleteZooByZooid(long zooid);

    @Query(value = "SELECT COUNT(*) as count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    Count checkZooAnimalsCombo(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ZooAnimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    void deleteZooAnimals(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO zooanimals(zooid, animalid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:zooid, :animalid, :zname, CURRENT_TIMESTAMP, :zname, CURRENT_TIMESTAMP);", nativeQuery = true)
    void insertZooAnimals(@Param("zname")String zname, @Param("zooid") long zooid, @Param("animalid") long animalid);
}
