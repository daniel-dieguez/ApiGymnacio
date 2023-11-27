package com.gymantigua.models.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pagos")
@Entity
public class Pagos implements Serializable {

    @Id
    @Column(name = "id_pago")
    private String id_pago;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_atleta", referencedColumnName = "id_atleta" )
    private Miembros miembros;
    @Column(name = "metodo_pago")
    private String metodo_pago;
    @Column(name = "estado_de_pago")
    private String estado_de_pago;

    /*@Column(name = "id_clase")
    private String id_clase;*/

}
