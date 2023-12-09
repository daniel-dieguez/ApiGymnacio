package com.gymantigua.dao.services;

import com.gymantigua.models.models.Metas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMetasServices {

    public List<Metas> findAll();
    public Page<Metas> findAll(Pageable pageable);
    public Metas findById(String id_atleta);
    public Metas save (Metas metas);
    public void delete(Metas metas);
    public List<Metas> findMetasByTermino (String termino);
}
