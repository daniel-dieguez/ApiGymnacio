package com.gymantigua.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetasDTO implements Serializable {

    @NotEmpty(message = "Objetivo no deseados ni encontrado")
    private String objetivo;


}
