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

    @NotEmpty(message = "Recuerda que el campo debe de estar lleno")
    private String id_miembro;

}
