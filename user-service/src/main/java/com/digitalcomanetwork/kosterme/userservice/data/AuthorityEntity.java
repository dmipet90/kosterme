package com.digitalcomanetwork.kosterme.userservice.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class AuthorityEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, length = 20)
    @NonNull
    private String name;
    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
}
