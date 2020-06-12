package local.jren.zoos.controllers;

import local.jren.zoos.services.AnimalService;
import local.jren.zoos.views.AnimalCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    // GET http://localhost:2019/animals/count
    @GetMapping(value = "/count", produces = {"application/json"})
    public ResponseEntity<?> listAllAnimalsWithZooCount() {
        List<AnimalCounts> counts = animalService.getAnimalCounts();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }
}
