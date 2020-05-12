package com.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_user")
public class UserLogin {

    @Id
    String email;
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    Collection<String> roles;
}