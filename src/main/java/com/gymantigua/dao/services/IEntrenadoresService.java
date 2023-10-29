package com.gymantigua.dao.services;

import com.gymantigua.models.models.Entrenadores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEntrenadoresService {

    public List<Entrenadores> findAll();
    public Page<Entrenadores> findAll(Pageable pageable);
    public Entrenadores findById (String id_Entrenador);
    public Entrenadores save (Entrenadores entrenadores);
    public  void delete(Entrenadores entrenadores);


}
