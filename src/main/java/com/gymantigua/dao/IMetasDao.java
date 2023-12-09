package com.gymantigua.dao;

import com.gymantigua.models.models.Metas;
import com.gymantigua.models.models.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMetasDao extends JpaRepository<Metas, String> {

    @Query("select c from Metas c where c.objetivo like %?1%")
    public List<Metas> findMetasByTermino(String termino);
}
