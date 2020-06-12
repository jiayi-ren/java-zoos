package local.jren.zoos.services;

import local.jren.zoos.models.Zoo;

import java.util.List;

public interface ZooService {
    List<Zoo> findAllZoos();
    Zoo findZooById(long id);
    void deleteZooById(long id);

    Zoo save(Zoo zoo);
    void deleteZooAnimal(long zooid, long animalid);
    void addZooAnimal(long zooid, long animalid);
}
