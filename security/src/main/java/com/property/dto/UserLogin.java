package com.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_user")
public class UserLogin {

    private final String PASSWORD_PATTERN = "";

    @Id @Email
    String email;
    @Transient
    @Pattern(regexp = PASSWORD_PATTERN)
    String password;
    String encrypted;

    @ElementCollection(fetch = FetchType.EAGER)
    Collection<String> authentication;
}