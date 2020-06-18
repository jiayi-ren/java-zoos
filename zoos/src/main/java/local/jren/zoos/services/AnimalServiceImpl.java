package local.jren.zoos.services;

import local.jren.zoos.models.Animal;
import local.jren.zoos.repositories.AnimalRepository;
import local.jren.zoos.views.AnimalCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "animalService")
public class AnimalServiceImpl implements AnimalService{

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<Animal> findAll() {

        return null;
    }

    @Override
    public List<AnimalCounts> getAnimalCounts() {
        return animalRepository.getCountAnimal();
    }

    @Override
    public Animal findAnimalById(long id) {
        return animalRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Animal "+id+" Not Found"));
    }
}
