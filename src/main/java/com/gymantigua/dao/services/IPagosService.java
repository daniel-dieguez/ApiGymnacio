package com.gymantigua.dao.services;

import com.gymantigua.models.models.Pagos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPagosService {

    public List<Pagos> findAll();
    public Page<Pagos> findAll(Pageable pageable);
    public Pagos findById(String id_pago);
    public Pagos save (Pagos pagos);
    public void delete(Pagos pagos);




}
