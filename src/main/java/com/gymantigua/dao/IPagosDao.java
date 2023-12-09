package com.gymantigua.dao;

import com.gymantigua.models.models.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPagosDao extends JpaRepository<Pagos, Object> {

    @Query("select c from Pagos c where c.id_pago like %?1%")
    public List<Pagos>findPagosByTermino(String termino);
}
