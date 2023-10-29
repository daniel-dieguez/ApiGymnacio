package com.gymantigua.controllers;

import com.gymantigua.dao.services.IPagosService;
import com.gymantigua.models.models.Pagos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/gimnacio/v2/pagos")
public class PagosController {

    @Autowired
    private IPagosService iPagosService;

    private Logger logger = LoggerFactory.getLogger(PagosController.class);

    @GetMapping
    public ResponseEntity<?>  ListarPagos(){
       Map<String, Object> response = new HashMap<>();
       this.logger.debug("Iniciamos con el proceso de consultas de miembros");
       try{
           List<Pagos> pagos = this.iPagosService.findAll();
           if(pagos == null & pagos.isEmpty()){
               logger.warn("No existe registro alguno");
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }else{
               logger.info("se ejecuto la consulta");
               return new ResponseEntity<List<Pagos>>(pagos, HttpStatus.OK);
           }
       }catch (CannotCreateTransactionException e){
           response = this.getTransactionExepcion(response, e);
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
       }catch (DataAccessException e){
           response = this.getDataAccessException(response, e);
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
       }

    }




    private Map<String, Object> getTransactionExepcion(Map<String,Object> response, CannotCreateTransactionException e){
        logger.error("Error al momento de conectarse a la base de datos");
        response.put("mensajee", "error al moneotno de contectarse a la");
        response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
        return response;
    }

    private Map<String, Object> getDataAccessException(Map<String, Object> response, DataAccessException e){
        logger.error("El error al momento de ejecutlar la consulta ea  la base d adatos");
        response.put("mensaje", "Error al momenot de ejecutar ola consulta a la base de datos");
        response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
        return response;

    }


}
