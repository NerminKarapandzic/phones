package com.nermink.phones.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nermink.phones.controller.request.CreatePhoneRequest;
import com.nermink.phones.controller.request.UpdatePhoneRequest;
import com.nermink.phones.domain.model.Phone;
import com.nermink.phones.domain.repository.PhoneRepository;
import com.nermink.phones.exception.ApplicationException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PhoneServiceTest {

  PhoneService phoneService;

  @Mock
  PhoneRepository phoneRepository;

  @BeforeEach
  void setup(){
    phoneService = new PhoneService(phoneRepository);
  }

  @Test
  @DisplayName("Should throw exception when wrong param value passed")
  void getAll_throwsException() {
    assertThrows(ApplicationException.class, () -> phoneService.getAll("wrong"));
  }

  @Test
  @DisplayName("Should invoke the repository findAll method with sort option")
  void getAll_callsRepositoryWithSort() {
    doReturn(this.mockPhones()).when(phoneRepository).findAll(any());

    var response = phoneService.getAll("name");

    verify(phoneRepository, times(1)).findAll(any());
    assertThat(response).hasSize(1);
  }

  @Test
  @DisplayName("Should invoke the repository findAll method")
  void getAll_callsRepository() {

    doReturn(this.mockPhones()).when(phoneRepository).findAll();

    var response = phoneService.getAll(null);

    verify(phoneRepository, times(1)).findAll();
    assertThat(response).hasSize(1);
  }

  @Test
  @DisplayName("Should invoke repository create method")
  void create_callsRepositoryCreate() {

    var request = mockCreatePhoneRequest();
    var mockedResponse = new Phone(request.getName());

    doReturn(mockedResponse).when(phoneRepository).create(any(Phone.class));

    var result = phoneService.create(request);

    verify(phoneRepository, times(1)).create(any(Phone.class));
    assertThat(mockedResponse.getName()).isEqualTo(request.getName());
  }

  @Test
  @DisplayName("Should invoke repository update method")
  void update_callsRepositoryUpdate() {
    var request = mockUpdatePhoneRequest();
    var mockedResponse = new Phone(request.getName());

    doReturn(mockedResponse).when(phoneRepository).updateById(any(Integer.class), any(Phone.class));

    var result = phoneService.update(1, request);

    verify(phoneRepository, times(1)).updateById(any(Integer.class), any(Phone.class));
    assertThat(mockedResponse.getName()).isEqualTo(request.getName());
  }

  private List<Phone> mockPhones(){
    return List.of(new Phone(1,"Alcatel"));
  }

  private CreatePhoneRequest mockCreatePhoneRequest(){
    var r = new CreatePhoneRequest();
    r.setName("Motorola");
    return r;
  }

  private UpdatePhoneRequest mockUpdatePhoneRequest(){
    var r = new UpdatePhoneRequest();
    r.setName("Motorola");
    return r;
  }
}