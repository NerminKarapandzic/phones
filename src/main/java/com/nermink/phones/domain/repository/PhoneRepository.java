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
  private static final HashMap<Integer, Phone> phones = new HashMap<>();

  //This is analog to a unique key index
  private static final Set<Phone> phonesNameIndex = new HashSet<>();

  @PostConstruct
  void seedData() {
    var p1 = new Phone("Acer");
    var p2 = new Phone("Alcatel");
    var p3 = new Phone("Bosch");
    this.create(p1);
    this.create(p2);
    this.create(p3);
  }

  //Method is synchronized because server serves requests on multiple threads
  @Override
  public synchronized Phone create(Phone entity) throws ApplicationException{
    entity.setId(phones.size() + 1);

    //We check the unique constraint
    if (phonesNameIndex.contains(entity)) {
      throw new ApplicationException(
          ErrorCode.DATA_CONSTRAINT_VIOLATION,
          HttpStatus.UNPROCESSABLE_ENTITY,
          "field name must be unique");
    }

    phones.put(entity.getId(), entity);
    //We also update the name index
    phonesNameIndex.add(entity);
    return entity;
  }

  @Override
  public synchronized Phone updateById(Integer id, Phone updated) {
    //We check the unique constraint
    if (phonesNameIndex.contains(updated)) {
      throw new ApplicationException(
          ErrorCode.DATA_CONSTRAINT_VIOLATION,
          HttpStatus.UNPROCESSABLE_ENTITY,
          "field name must be unique");
    }

    var phone = phones.replace(id, updated);

    if (phone == null) {
      throw new ApplicationException(ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,
          String.format("resource with id %s not found", id));
    }

    return updated;
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
