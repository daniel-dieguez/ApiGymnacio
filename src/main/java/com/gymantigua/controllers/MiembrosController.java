package com.gymantigua.controllers;


import com.gymantigua.dao.services.IMiembroService;
import com.gymantigua.models.entities.MiembrosDTO;
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
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/gimnacio/v2/miembros")
public class MiembrosController {

    @Autowired
    private IMiembroService iMiembroService;

    private Logger logger = LoggerFactory.getLogger(MiembrosController.class);


    @GetMapping
    public ResponseEntity<?> ListaMiembros() {
        Map<String, Object> response = new HashMap<>();
        this.logger.debug("inicinado el proceso de consulta de miebro");
        try {
            List<Miembros> miembros = this.iMiembroService.findAll();
            if(miembros == null && miembros.isEmpty()){
                logger.warn("no existe registro para la entidad");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.info("Se ejecuta la consulta");
                return new ResponseEntity<List<Miembros>>(miembros, HttpStatus.OK);
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
    public ResponseEntity<?> ListarMiembrosByPage(@PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page, 5);//indicaremos el tamano de la pagina (el size 5) es la cantidad el avariable
            Page<Miembros> miembrosPage = iMiembroService.findAll(pageable);
            if(miembrosPage == null || miembrosPage.getSize() == 0){
                logger.warn("no existe registro en la tabla de metas");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                logger.info("se realizo la consulta de enlistar por consulta");
                return new ResponseEntity<Page<Miembros>>(miembrosPage, HttpStatus.OK);
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
    public  ResponseEntity<?> showMiembros(@PathVariable String atletaId){ //VERIFIQUEMOS ESTE DATO
        Map<String, Object> response = new HashMap<>();
        logger.debug("inica el proceso para la busqueda del Id".concat(atletaId));
        try{
            Miembros miembros = this.iMiembroService.findById(atletaId);
            if(miembros == null){
                logger.warn("no existe ese atleta con el Id");
                response.put("mesaje", "No existe el atleta con el id".concat(atletaId));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("se proceso la busqueda de laa forma correcta");
                return new ResponseEntity<Miembros>(miembros, HttpStatus.OK);

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
    public ResponseEntity<?> create (@Valid @RequestBody MiembrosDTO value, BindingResult result){//el bindingresulta para valdiad el campo de Dto //el body lo podremos ver en la parte del post
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors() == true){ //.hasError para ver los errores
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList()); // getFielError me enlistara los campos que se encuentraron errores, El collector nos ayuda a enlistar lso errorres PREGUNTAR A CHATGPT
            response.put("errores", errores);
            logger.info("se encontraron errores al momento de validar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Miembros miembros = new Miembros();
            miembros.setId_atleta(UUID.randomUUID().toString());
            miembros.setNombre(value.getNombre());
          miembros.setCumpleanos(value.getCumpleanos());   // Analizcemos el get, este viene del entities miembrosDTO
           miembros.setTelefono(value.getTelefono());
           miembros.setObjetivo(value.getObjetivo());
            this.iMiembroService.save(miembros);
            logger.info("se acaba de creaer un nuevo miembro");
            response.put("mensaje", "Un nuevo miembro fue creado con exito ");
            response.put("miembro ", miembros);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    ///---------------------------------

    @PutMapping("{atletaId}")
    public ResponseEntity<?>update(@Valid @RequestBody MiembrosDTO value, BindingResult result, @PathVariable String atletaId){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("eorrres", errores);
            logger.info("Se encotraron errores en la peticion ");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Miembros miembros = this.iMiembroService.findById(atletaId);
            if(miembros == null){
                response.put("mensaje", "el nuevo miebro con el id".concat(atletaId).concat("no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                miembros.setNombre(value.getNombre());
                this.iMiembroService.save(miembros);
                response.put("mensaje","el miembro fue actualizado");
                response.put("miembro ", miembros);
                logger.info("el miebro fue actualizada con exito ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
        response = this.getTransactionExepcion(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }catch (DataAccessException e){
        response = this.getDataAccessException(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }
    }



    @DeleteMapping("/{atletaId}")
    public ResponseEntity<?> delete(@PathVariable String atletaId){
        Map<String, Object> response = new HashMap<>();
        try {
            Miembros miembros = this.iMiembroService.findById(atletaId);
            if(miembros == null){
                response.put("mensaje", "El miembro con el Id".concat(atletaId).concat("no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                this.iMiembroService.delete(miembros);
                response.put("mensaje","el miembro con el id".concat(atletaId).concat("fue eliminado "));
                response.put("miembro ",miembros);
                logger.info("El miembro fue eliminada con exito");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
        response = this.getTransactionExepcion(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }catch (DataAccessException e){
        response = this.getDataAccessException(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }
    }



    // query params para la creacion de parametros de busqueda un "search

    @GetMapping("/search")
    public ResponseEntity<?> ListarNombrePorTerminino(@RequestParam("termino") String termino){
        Map<String, Object> response = new HashMap<>();
        try{
        List<Miembros> miembros = this.iMiembroService.findMiembrosByTermino(termino);
        if(miembros == null && miembros.size() == 0){
            logger.warn("mensaje", "no existe nunguna concidencia");
            response.put("mensaje", "no existe nunguna concidencia");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            logger.info("se ejecuto la consulta de manera exitosa");
            return new ResponseEntity<List<Miembros>>(miembros, HttpStatus.OK);

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
