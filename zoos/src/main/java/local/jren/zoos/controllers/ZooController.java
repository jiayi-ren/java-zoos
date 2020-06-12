package local.jren.zoos.controllers;

import local.jren.zoos.models.Zoo;
import local.jren.zoos.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
