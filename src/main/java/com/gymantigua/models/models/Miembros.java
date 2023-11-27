package com.gymantigua.models.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    //@Temporal(TemporalType.TIMESTAMP) //Date para guardar dia, timestamp para guardar dato, .Date -> fecha .TiME -> Guardara la hora pero no fecha, .TIMESTAMP ambas
    @Column(name = "cumpleanos")
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
