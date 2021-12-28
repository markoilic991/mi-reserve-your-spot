package com.prodyna.reserveyourspot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class SampleDataService {

  private OfficeRoomService officeRoomService;

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public SampleDataService(OfficeRoomService officeRoomService, OfficeSpaceService officeSpaceService) {
    this.officeRoomService = officeRoomService;
    this.officeSpaceService = officeSpaceService;
  }

}




