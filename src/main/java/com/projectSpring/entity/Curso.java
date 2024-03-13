package com.projectSpring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Curso {

    //spring jpa -- nos ayuda a  crear las tablas

    //con y batis y jdbc si es distinto

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128,nullable = false)
    private String titulo;

    @Column(length = 256)
    private String description;

    @Column(nullable = false)
    private int nivel;

    @Column(name = "estado_publicacion")
    private boolean isPublicado;



}
