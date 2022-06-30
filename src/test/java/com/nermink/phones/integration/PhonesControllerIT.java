package com.nermink.phones.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nermink.phones.controller.request.CreatePhoneRequest;
import com.nermink.phones.controller.request.UpdatePhoneRequest;
import com.nermink.phones.controller.response.ErrorResponse;
import com.nermink.phones.controller.response.PhoneResponse;
import com.nermink.phones.domain.repository.PhoneRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class PhonesControllerIT {

  Gson gson = new Gson();
  @Autowired
  TestRestTemplate testRestTemplate;

  PhoneRepository phoneRepository;

  @BeforeEach
  void setup(){
    phoneRepository = new PhoneRepository();
  }

  @Test
  @DisplayName("Should return status 200")
  void get_successResponse(){
    var response = testRestTemplate.exchange("/api/v1/phones", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    List<PhoneResponse> body = gson.fromJson(response.getBody(), new TypeToken<List<PhoneResponse>>(){}.getType());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(body).hasSize(3);
  }

  @Test
  @DisplayName("Should return status 422")
  void get_successUnprocessableEntity(){
    var response = testRestTemplate.exchange("/api/v1/phones?sortBy=wrong", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @Test
  @DisplayName("Should return status 200")
  void create_successResponse(){

    CreatePhoneRequest request = new CreatePhoneRequest();
    request.setName("Xiaomi");
    HttpEntity<CreatePhoneRequest> req = new HttpEntity<>(request);

    var response = testRestTemplate.exchange("/api/v1/phones", HttpMethod.POST, req, String.class);
    PhoneResponse body = gson.fromJson(response.getBody(), PhoneResponse.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(body.getName()).isEqualTo("Xiaomi");
  }

  @Test
  @DisplayName("Should return status 422, unique constraint validation fail")
  void create_uniqueError(){

    CreatePhoneRequest request = new CreatePhoneRequest();
    request.setName("Alcatel");
    HttpEntity<CreatePhoneRequest> req = new HttpEntity<>(request);

    var response = testRestTemplate.exchange("/api/v1/phones", HttpMethod.POST, req, String.class);
    ErrorResponse body = gson.fromJson(response.getBody(), ErrorResponse.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(body.getCode()).isEqualTo(40001);
  }

  @Test
  @DisplayName("Should return status 200")
  void update_successResponse(){

    UpdatePhoneRequest request = new UpdatePhoneRequest();
    request.setName("Xiaomi");
    HttpEntity<UpdatePhoneRequest> req = new HttpEntity<>(request);

    var response = testRestTemplate.exchange("/api/v1/phones/1", HttpMethod.PUT, req, String.class);
    PhoneResponse body = gson.fromJson(response.getBody(), PhoneResponse.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(body.getName()).isEqualTo("Xiaomi");
  }

  @Test
  @DisplayName("Should return status 404, resource not found")
  void update_uniqueError(){

    CreatePhoneRequest request = new CreatePhoneRequest();
    request.setName("Xiaomi");
    HttpEntity<CreatePhoneRequest> req = new HttpEntity<>(request);

    var response = testRestTemplate.exchange("/api/v1/phones/212", HttpMethod.PUT, req, String.class);
    ErrorResponse body = gson.fromJson(response.getBody(), ErrorResponse.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(body.getCode()).isEqualTo(40401);
  }

}
