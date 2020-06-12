package local.jren.zoos.services;

import local.jren.zoos.models.Animal;
import local.jren.zoos.views.AnimalCounts;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();
    List<AnimalCounts> getAnimalCounts();
}
