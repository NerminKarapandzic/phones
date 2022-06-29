package com.nermink.phones.domain.repository;

import com.nermink.phones.domain.model.Phone;
import com.nermink.phones.exception.ApplicationException;
import com.nermink.phones.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneRepository implements AppRepository<Phone, Integer> {

  //This is analog to a table with a primary key
  public static HashMap<Integer, Phone> phones = new HashMap<>();

  //This is analog to a unique key index
  public static Set<Phone> phonesNameIndex = new HashSet<>();

  @PostConstruct
  void seedData() {
    var p1 = new Phone("Acer");
    var p2 = new Phone("Alcatel");
    var p3 = new Phone("Bosch");
    this.save(p1);
    this.save(p2);
    this.save(p3);
  }

  //Method is synchronized because server serves requests on multiple threads
  @Override
  synchronized public Phone save(Phone entity) {
    //If entityId is null then it's an insert, so we generate an id
    if (entity.getId() == null) {
      entity.setId(phones.size() + 1);
    }

    //We check the unique constraint
    if (phonesNameIndex.contains(entity)) {
      throw new ApplicationException(
          ErrorCode.DATA_CONSTRAINT_VIOLATION,
          HttpStatus.UNPROCESSABLE_ENTITY,
          "field name must be unique");
    }

    //This will now replace the entity if it's an update and insert if not
    phones.put(entity.getId(), entity);
    //We also update the name index
    phonesNameIndex.add(entity);
    return entity;
  }

  @Override
  public Collection<Phone> findAll() {
    return phones.values();
  }

  @Override
  public Collection<Phone> findAll(Comparator<Phone> phoneComparator) {
    List<Phone> arrList = new ArrayList<>(phones.values());
    arrList.sort(Comparator.comparing(Phone::getName));
    return arrList;
  }

  @Override
  public Optional<Phone> findById(Integer id) {
    return Optional.ofNullable(phones.get(id));
  }
}
