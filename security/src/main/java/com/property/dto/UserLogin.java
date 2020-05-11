package com.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_user")
public class UserLogin {

    @Id
    String username;
    @GeneratedValue
    int userId;

    String password;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roles;
}