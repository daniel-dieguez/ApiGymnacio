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
@Table(name = "entrenador")
@Entity
public class Entrenadores implements Serializable {
    @Id
    @Column(name = "id_Entrenador")
    private String id_Entrenador;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cumpleanos")
    private String cumpleanos;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "horario")
    private String horario;
    @Column(name = "genero")
    private String genero ;
    @Column(name = "salario")
    private String salario;
    @Column(name = "id_clase")
    private String id_clase;



}
