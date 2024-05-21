package com.kkrenzke.tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kkrenzke.tacos.TacoCloudUser;

import lombok.Data;

@Data
public class Registration {
  private String username;
  private String password;
  private String fullname;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String phone;

  public TacoCloudUser toUser(PasswordEncoder passwordEncoder) {
    return new TacoCloudUser(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phone);
  }
}
