package local.jren.zoos.services;

import local.jren.zoos.models.Animal;
import local.jren.zoos.models.Telephone;
import local.jren.zoos.models.Zoo;
import local.jren.zoos.models.ZooAnimals;
import local.jren.zoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
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

    @Transactional
    @Override
    public Zoo save(Zoo zoo) {
        Zoo newZoo = new Zoo();

        if(zoo.getZooid() != 0) {
            Zoo oldZoo = zooRepository.findById(zoo.getZooid()).orElseThrow(()-> new EntityNotFoundException("Zoo "+zoo.getZooid()+" Not Found"));

            for (ZooAnimals zooAnimal: oldZoo.getAnimals()) {
                deleteZooAnimal(zooAnimal.getZoo().getZooid(), zooAnimal.getAnimal().getAnimalid());
            }
            newZoo.setZooid(zoo.getZooid());
        }

        newZoo.setZooname(zoo.getZooname());

        newZoo.getTelephones().clear();
        for (Telephone telephone: zoo.getTelephones()) {
            Telephone newTelephone = new Telephone(telephone.getPhonetype(),telephone.getPhonenumber(),newZoo);
            newZoo.getTelephones().add(newTelephone);
        }

        newZoo.getAnimals().clear();
        if (zoo.getZooid() == 0 ) {
            for (ZooAnimals zooAnimal: zoo.getAnimals()) {
                Animal newAnimal = animalService.findAnimalById(zooAnimal.getAnimal().getAnimalid());

                newZoo.addAnimal(newAnimal);
            }
        } else {
            for (ZooAnimals zooAnimal: zoo.getAnimals()) {
                addZooAnimal(newZoo.getZooid(),zooAnimal.getAnimal().getAnimalid());
            }
        }

        return zooRepository.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(Zoo zoo, long id) {

        Zoo currentZoo = findZooById(id);

        if (zoo.getZooname() != null ) {
            currentZoo.setZooname(zoo.getZooname());
        }

        if (zoo.getTelephones().size() > 0) {
            currentZoo.getTelephones().clear();
            for (Telephone telephone : zoo.getTelephones()) {
                currentZoo.getTelephones().add(new Telephone(telephone.getPhonetype(),telephone.getPhonenumber(),currentZoo));
            }
        }

        if (zoo.getAnimals().size() > 0) {
            for (ZooAnimals zooAnimal : currentZoo.getAnimals()) {
                deleteZooAnimal(zooAnimal.getZoo().getZooid(), zooAnimal.getAnimal().getAnimalid());
            }

            for (ZooAnimals zooAnimal : zoo.getAnimals()) {
                addZooAnimal(currentZoo.getZooid(), zooAnimal.getAnimal().getAnimalid());
            }
        }

        return zooRepository.save(currentZoo);
    }

    @Transactional
    @Override
    public void deleteZooAnimal(long zooid, long animalid) {
        zooRepository.findById(zooid).orElseThrow(()-> new EntityNotFoundException("Zoo "+zooid+" Not Found"));
        animalService.findAnimalById(animalid);

        if (zooRepository.checkZooAnimalsCombo(zooid,animalid).getCount() > 0) {
            zooRepository.deleteZooAnimals(zooid, animalid);
        } else {
           throw new EntityNotFoundException("Zoo and Animal Combination Doesn't Exist");
        }
    }

    @Transactional
    @Override
    public void addZooAnimal(long zooid, long animalid) {
        zooRepository.findById(zooid).orElseThrow(()-> new EntityNotFoundException("Zoo "+zooid+" Not Found"));
        animalService.findAnimalById(animalid);

        if (zooRepository.checkZooAnimalsCombo(zooid,animalid).getCount() <= 0) {
            zooRepository.insertZooAnimals(zooAuditing.getCurrentAuditor().get(), zooid, animalid);
        } else {
            throw new EntityExistsException("Zoo and Animal Combination Already Exists");
        }
    }
}
