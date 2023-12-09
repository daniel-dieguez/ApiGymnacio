package com.gymantigua.models.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pagos")
@Entity
public class Pagos implements Serializable {

    @Id
    @Column(name = "id_pago")
    private String id_pago;
   @Column(name = "id_atleta")
   private String id_atleta;
   @Column(name = "metodo_pago")
    private String metodo_pago;
   @Column(name = "estado_de_pago")
    private String estado_de_pago;
    @Column (name = "id_clase")
    private String id_clase;

    private Clases clases;

    @OneToMany(mappedBy = "pagos", fetch = FetchType.LAZY) // lazy para que no me traiga todo el listado del listado miembros
    //el mappedby es de la clase que estamos interconectando
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Miembros>miembros;




}
