package local.jren.zoos.services;

import local.jren.zoos.repositories.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "telephoneService")
public class TelephoneServiceImpl implements TelephoneService{

    @Autowired
    private TelephoneRepository telephoneRepository;
}
