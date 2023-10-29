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



}
