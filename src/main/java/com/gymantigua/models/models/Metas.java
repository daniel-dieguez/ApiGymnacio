package com.gymantigua.models.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "metas")
@Entity
public class Metas implements Serializable {

    @Id
    @Column(name = "id_atleta")
    private String id_atleta;
    @Column (name = "objetivo")
    private String objetivo;


}
