package com.gymantigua.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MiembrosDTO implements Serializable {


    @NotEmpty(message = "Nombre no debe de estar vacio")
    private String nombre;
   @NotEmpty(message = "Recuerda que el campo cumpleaños debe de estar lleno")
    private String cumpleanos;
    @NotEmpty(message = "Recuerda el telefono no vacio")
    private String telefono;
    @NotEmpty(message = "Recuerda que objetivo debe de ser")
   private String objetivo;
    @NotEmpty(message = "Recuerda que genero hay dos papi")
    private String genero;
    @NotEmpty(message = "Recuerda que hay que pagar mensualidad ")
    private String mensualidad;





}
