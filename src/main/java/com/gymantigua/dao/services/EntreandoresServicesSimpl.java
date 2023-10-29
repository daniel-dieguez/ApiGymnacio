package com.gymantigua.dao.services;

import com.gymantigua.dao.IEntrenadoresDao;
import com.gymantigua.dao.services.IEntrenadoresService;
import com.gymantigua.models.models.Entrenadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntreandoresServicesSimpl implements IEntrenadoresService {

    @Autowired
    private IEntrenadoresDao iEntrenadoresDao;


    @Override
    public List<Entrenadores> findAll() {
        return this.iEntrenadoresDao.findAll();
    }

    @Override
    public Page<Entrenadores> findAll(Pageable pageable) {
        return this.iEntrenadoresDao.findAll(pageable);
    }

    @Override
    public Entrenadores findById(String id_Entrenador) {
        return this.iEntrenadoresDao.findById(id_Entrenador).orElse(null);
    }

    @Override
    public Entrenadores save(Entrenadores entrenadores) {

        return this.iEntrenadoresDao.save(entrenadores);
    }

    @Override
    public void delete(Entrenadores entrenadores) {

        this.iEntrenadoresDao.delete(entrenadores);

    }
}
