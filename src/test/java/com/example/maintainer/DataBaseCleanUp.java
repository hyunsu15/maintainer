package com.example.maintainer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class DataBaseCleanUp {

  @Autowired
  List<? extends CrudRepository> crudRepositories;

  public void cleanUp() {
    crudRepositories.stream().forEach(CrudRepository::deleteAll);
  }
}