package com.gymantigua.dao.services;


import com.gymantigua.dao.IMetasDao;
import com.gymantigua.models.models.Metas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetasServicesImple implements IMetasServices {

    @Autowired
    private IMetasDao iMetasDao;


    @Override
    public List<Metas> findAll() {
        return this.iMetasDao.findAll();
    }

    @Override
    public Page<Metas> findAll(Pageable pageable) {
        return this.iMetasDao.findAll(pageable);
    }

    @Override
    public Metas findById(String id_atleta) {
        return this.iMetasDao.findById(id_atleta).orElse(null);
    }

    @Override
    public Metas save(Metas metas) {
        return this.iMetasDao.save(metas);
    }

    @Override
    public void delete(Metas metas) {
        this.iMetasDao .delete(metas);
    }

    @Override
    public List<Metas> findMetasByTermino(String termino) {
        return this.iMetasDao.findMetasByTermino(termino);
    }
}
