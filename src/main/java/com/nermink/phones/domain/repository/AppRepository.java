package com.nermink.phones.domain.repository;

import com.nermink.phones.domain.model.Phone;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public interface AppRepository<T, I> {

  T save(T entity);
  Collection<T> findAll();
  Collection<Phone> findAll(Comparator<Phone> phoneComparator);
  Optional<T> findById(I id);
  default Class<T> getType(Class<T> type){
    return type;
  }
}
