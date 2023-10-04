package com.gymantigua.dao.services;

import com.gymantigua.dao.IMiembrosDao;
import com.gymantigua.models.models.Miembros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MiembroserviceSimpl implements IMiembroService{

    @Autowired
    private IMiembrosDao iMiembrosDao;


    @Override
    public List<Miembros> findAll() {
        return this.iMiembrosDao.findAll();
    }

    @Override
    public Page<Miembros> findAll(Pageable pageable) {
        return this.iMiembrosDao.findAll(pageable);
    }

    @Override
    public Miembros findById(String id_atleta) {
        return this.iMiembrosDao.findById(id_atleta).orElse( null);
    }

    @Override
    public Miembros save(Miembros miembros) {
        return this.iMiembrosDao.save(miembros);
    }

    @Override
    public void delete(Miembros miembros) {
        this.iMiembrosDao.delete(miembros);
    }
}
