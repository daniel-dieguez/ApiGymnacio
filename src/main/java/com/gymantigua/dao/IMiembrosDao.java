package com.gymantigua.dao;

import com.gymantigua.models.models.Miembros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMiembrosDao extends JpaRepository<Miembros,String> {
}
