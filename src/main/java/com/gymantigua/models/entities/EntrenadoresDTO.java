package com.gymantigua.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntrenadoresDTO implements Serializable {

    @NotEmpty(message = "El campo no debe de estar vacio")
    private String id_Entrenado;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String  nombre;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String cumpleanos;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String telefono;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String horarios;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String genero;
    @NotEmpty(message = "El campo no debe de estar vacio")
    private String id_clase;


}
