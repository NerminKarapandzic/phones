package com.nermink.phones.domain.repository;

import com.nermink.phones.domain.model.Phone;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public interface AppRepository<T, I> {

  T create(T entity);
  T updateById(I id, T updated);
  Collection<T> findAll();
  Collection<Phone> findAll(Comparator<T> entityComparator);
  Optional<T> findById(I id);
}
