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
    private String id_pago; /*Verifiquemos por que esto no aparece como el de miembros*/
}
