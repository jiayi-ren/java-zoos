package local.jren.zoos.controllers;

import local.jren.zoos.models.Zoo;
import local.jren.zoos.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/zoos")
public class ZooController {

    @Autowired
    private ZooService zooService;

    // GET http://localhost:2019/zoos/zoos
    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> findAllZoos() {
        List<Zoo> zoos = zooService.findAllZoos();
        return new ResponseEntity<>(zoos, HttpStatus.OK);
    }

    // GET http://localhost:2019/zoos/zoo/5
    @GetMapping(value = "/zoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> findZooById(@PathVariable long zooid) {
        Zoo zoo = zooService.findZooById(zooid);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    // DELETE http://localhost:2019/zoos/zoo/5
    @DeleteMapping(value = "/zoo/{zooid}")
    public ResponseEntity<?> deleteZooById(@PathVariable long zooid) {
        zooService.deleteZooById(zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // POST http://localhost:2019/zoos/zoo
    @PostMapping(value = "/zoo", consumes = {"application/json"})
    public ResponseEntity<?> addNewZoo(@Valid @RequestBody Zoo newZoo) {
        newZoo.setZooid(0);
        newZoo = zooService.save(newZoo);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newZooURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{zooid}").buildAndExpand(newZoo.getZooid()).toUri();
        responseHeaders.setLocation(newZooURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
