package local.jren.zoos.services;

import local.jren.zoos.models.Zoo;

import java.util.List;

public interface ZooService {
    List<Zoo> findAllZoos();
    Zoo findZooById(long id);
    void deleteZooById(long id);
}
