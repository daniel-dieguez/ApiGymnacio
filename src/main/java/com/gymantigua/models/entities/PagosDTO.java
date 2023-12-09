package com.gymantigua.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagosDTO implements Serializable {
    @NotEmpty(message = "recuerad que el campo debe de estar lleno")
    private String id_atleta; /*Verifiquemos por que esto no aparece como el de miembros*/
    @NotEmpty(message = "Nombre no debe de estar vacio el meto de pago ")
    private String metodo_pago;
    @NotEmpty(message = "Nombre no debe de estar vacio el estado del pago ")
    private String estado_de_pago;
    @NotEmpty(message = "Nombre no debe de estar vacio id clase")
    private String id_clase;
    @NotEmpty(message = "Nombre no debe de estar vacio la clase")
    private String clases;

}
