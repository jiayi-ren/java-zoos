package local.jren.zoos.services;

import local.jren.zoos.models.Zoo;
import local.jren.zoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "zooService")
public class ZooServiceImpl implements ZooService{
    @Autowired
    private ZooRepository zooRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ZooAuditing zooAuditing;

    @Override
    public List<Zoo> findAllZoos() {
        List<Zoo> zoos = new ArrayList<>();
        zooRepository.findAll().iterator().forEachRemaining(zoos::add);
        return zoos;
    }

    @Override
    public Zoo findZooById(long id) {
        return zooRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Zoo "+id+" Not Found"));
    }

    @Override
    public void deleteZooById(long id) {
        if(zooRepository.findById(id).isPresent()) {
            zooRepository.deleteZooByZooid(id);
        } else {
            throw new EntityNotFoundException("Zoo "+id+" Not Found");
        }
    }
}
