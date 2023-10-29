package com.gymantigua.dao;

import com.gymantigua.models.models.Entrenadores;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.EntityReference;

public interface IEntrenadoresDao extends JpaRepository<Entrenadores, String> {

}
