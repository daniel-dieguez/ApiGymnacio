package com.gymantigua.models.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "clases")
@NotEmpty
public class Clases implements Serializable {

    @Id
    @Column(name = "id_clase")
    private String id_clase;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "id_Entrenador")
    private String id_Entrenador;
    @Column(name = "campo)")
    private String campo;
    @Column(name = "precio")
    private String precio;
    @Column(name = "dia_semana")
    private String dia_semana;




}
