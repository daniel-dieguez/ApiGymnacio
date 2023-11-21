package com.gymantigua.dao.services;

import com.gymantigua.models.models.Miembros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface   IMiembroService {

    public List<Miembros> findAll();
    public Page<Miembros> findAll(Pageable pageable);
    public Miembros findById(String id_atleta);
    public Miembros save (Miembros miembros);
    public void delete(Miembros miembros);


}
