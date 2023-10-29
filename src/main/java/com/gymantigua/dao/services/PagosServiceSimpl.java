package com.gymantigua.dao.services;

import com.gymantigua.dao.IPagosDao;
import com.gymantigua.models.models.Pagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagosServiceSimpl implements  IPagosService{

    @Autowired
    private IPagosDao iPagosDao;


    @Override
    public List<Pagos> findAll() {
        return this.iPagosDao.findAll();
    }

    @Override
    public Page<Pagos> findAll(Pageable pageable) {
        return this.iPagosDao.findAll(pageable);
    }

    @Override
    public Pagos findById(String id_pago) {
        return this.iPagosDao.findById(id_pago).orElse(null);
    }

    @Override
    public Pagos save(Pagos pagos) {
        return this.iPagosDao.save(pagos);
    }

    @Override
    public void delete(Pagos pagos) {
        this.iPagosDao.delete(pagos);

    }
}
