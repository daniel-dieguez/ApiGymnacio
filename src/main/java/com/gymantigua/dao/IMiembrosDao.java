package com.gymantigua.dao;

import com.gymantigua.models.models.Miembros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMiembrosDao extends JpaRepository<Miembros,String> {


    @Query("select c from Miembros c where c.nombre like %?1%") // -> consulta que que nos busque la carrera tengnica que se encuentre
    public List<Miembros> findMiembrosByTermino(String termino);
}
