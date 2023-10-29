package com.gymantigua.models.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name = "miembros")
@Entity
public class Miembros implements Serializable {

    @Id
    @Column(name = "id_atleta")
    private  String id_atleta;
    @Column(name = "cumplenos")
    private String cumpleanos;
    @Column(name="nombre")
    private String nombre;
    @Column(name =" telefono")
    private String telefono;
    @Column(name ="objetivo")
    private String Objetivo;
    @Column(name="genero")
    private String genero;
    @Column(name="mensualidad")
    private String mensualidad;
    @Column(name="id_clase")
    private String id_clase;


}
