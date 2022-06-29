package com.nermink.phones.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone{
  private Integer id;
  private String name;

  public Phone(String name) {
    this.name = name;
  }

  public Phone(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) return false;

    if(!(obj instanceof Phone)){
      return false;
    }

    return name.compareTo(((Phone) obj).name) == 0;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
