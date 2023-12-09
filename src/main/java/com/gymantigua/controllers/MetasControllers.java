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


    @PutMapping("{atletaId}")
    public ResponseEntity<?>update(@Valid @RequestBody MetasDTO value, BindingResult result, @PathVariable String atletaId) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("eorrres", errores);
            logger.info("Se encotraron errores en la peticion ");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Metas metas = this.iMetasServices.findById(atletaId);
            if (metas == null) {
                response.put("mensaje", "el nuevo miebro con el id".concat(atletaId).concat("no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                metas.setObjetivo(value.getObjetivo());
                this.iMetasServices.save(metas);
                response.put("mensaje", "la meta fue actualizado");
                response.put("Metas ", metas);
                logger.info("el miebro fue actualizada con exito ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }


        } catch (CannotCreateTransactionException e) {
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }


    @DeleteMapping("/{atletaId}")
    public ResponseEntity<?> delete(@PathVariable String atletaId){
        Map<String, Object> response = new HashMap<>();
        try {
            Metas metas = this.iMetasServices.findById(atletaId);
            if(metas == null){
                response.put("mensaje", "El miembro con el Id".concat(atletaId).concat("no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                this.iMetasServices.delete(metas);
                response.put("mensaje","el miembro con el id".concat(atletaId).concat("fue eliminado "));
                response.put("metas ",metas);
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






    @GetMapping("/search")
    public ResponseEntity<?> ListarNombrePorTerminino(@RequestParam("termino") String termino){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Metas> metas = this.iMetasServices.findMetasByTermino(termino);
            if(metas == null && metas.size() == 0){
                logger.warn("mensaje", "no existe nunguna concidencia");
                response.put("mensaje", "no existe nunguna concidencia");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }else{
                logger.info("se ejecuto la consulta de manera exitosa");
                return new ResponseEntity<List<Metas>>(metas, HttpStatus.OK);

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
