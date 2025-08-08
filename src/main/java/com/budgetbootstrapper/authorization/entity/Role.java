package com.budgetbootstrapper.authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role", schema = "authorization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

}
