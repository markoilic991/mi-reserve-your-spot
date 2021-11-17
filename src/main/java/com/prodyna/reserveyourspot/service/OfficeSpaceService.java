package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfficeSpaceService {

    private OfficeSpaceRepository officeSpaceRepository;

    @Autowired
    public OfficeSpaceService(OfficeSpaceRepository officeSpaceRepository) {
        this.officeSpaceRepository = officeSpaceRepository;
    }

    public OfficeSpace save(OfficeSpace officeSpace){
        return officeSpaceRepository.save(officeSpace);
    }

    public List<OfficeSpace> saveAll(List<OfficeSpace>officeSpaces){
        return officeSpaceRepository.saveAll(officeSpaces);
    }

    public List<OfficeSpace> findAll(){
        return officeSpaceRepository.findAll();
    }
}
