package com.gymantigua.controllers;


import com.gymantigua.dao.services.IMetasServices;
import com.gymantigua.models.entities.MetasDTO;
import com.gymantigua.models.entities.MiembrosDTO;
import com.gymantigua.models.models.Metas;
import com.gymantigua.models.models.Miembros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/gimnacio/v6/metas")
public class MetasControllers {

    @Autowired
    private IMetasServices iMetasServices;

    private Logger logger = LoggerFactory.getLogger(MetasControllers.class);

    @GetMapping
    public ResponseEntity<?> ListaMetas(){
        Map<String, Object> response = new HashMap<>();
        this.logger.debug("Iniciamos con la consulta de metas");
        try{
            List<Metas> metas = this.iMetasServices.findAll();
            if(metas == null && metas.isEmpty()){
                logger.info("No existe ningun dato de metas");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.info("se ejecuto bien la consulta de metas");
                return new ResponseEntity<List<Metas>>(metas, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<?> ListarMetasByPage(@PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page, 5);//indicaremos el tamano de la pagina (el size 5) es la cantidad el avariable
            Page<Metas> metasPage = iMetasServices.findAll(pageable);
            if(metasPage == null || metasPage.getSize() == 0){
                logger.warn("no existe registro en la tabla de metas");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                logger.info("se realizo la consulta de enlistar por consulta");
                return new ResponseEntity<Page<Metas>>(metasPage, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }


    @GetMapping("{atletaId}")
    public  ResponseEntity<?> showMetas(@PathVariable String atletaId){ //VERIFIQUEMOS ESTE DATO
        Map<String, Object> response = new HashMap<>();
        logger.debug("inica el proceso para la busqueda del Id".concat(atletaId));
        try{
            Metas metas = this.iMetasServices.findById(atletaId);
            if(metas == null){
                logger.warn("no existe ese atleta con el Id");
                response.put("mesaje", "No existe el atleta con el id".concat(atletaId));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("se proceso la busqueda de laa forma correcta");
                return new ResponseEntity<Metas>(metas, HttpStatus.OK);

            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @PostMapping  //recuerda que el post es para agregar
    public ResponseEntity<?> create (@Valid @RequestBody MetasDTO value, BindingResult result){//el bindingresulta para valdiad el campo de Dto //el body lo podremos ver en la parte del post
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors() == true){ //.hasError para ver los errores
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList()); // getFielError me enlistara los campos que se encuentraron errores, El collector nos ayuda a enlistar lso errorres PREGUNTAR A CHATGPT
            response.put("errores", errores);
            logger.info("se encontraron errores al momento de validar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Metas metas = new Metas();
            metas.setId_atleta(UUID.randomUUID().toString());
            metas.setObjetivo(value.getObjetivo());
            this.iMetasServices.save(metas);
            logger.info("se acaba de creaer un nuevo miembro");
            response.put("mensaje", "Un nuevo miembro fue creado con exito ");
            response.put("Meta ", metas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

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
