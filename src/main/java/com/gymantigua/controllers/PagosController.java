package com.gymantigua.controllers;

import com.gymantigua.dao.services.IPagosService;
import com.gymantigua.models.entities.PagosDTO;
import com.gymantigua.models.models.Miembros;
import com.gymantigua.models.models.Pagos;
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

    @GetMapping("/page/{page}")
    public ResponseEntity<?> ListaPagosByPage(@PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page, 5);
            Page<Pagos> pagosPage = iPagosService.findAll(pageable);
            if(pagosPage == null || pagosPage.getSize() == 0 ){
                logger.info("No existe registro en la tabla de metas");
                return new ResponseEntity<>(pagosPage, HttpStatus.NO_CONTENT);
                }else{
                logger.info("se realizo la consuklta de enlistar por consulta");
                return new ResponseEntity<Page<Pagos>>(pagosPage,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @GetMapping("{PagoId}")
    public ResponseEntity<?> ShowPagos (@PathVariable String PagoId){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Inicia la busqueda de Id".concat(PagoId));
        try{
            Pagos pagos = this.iPagosService.findById(PagoId);
            if(pagos == null ){
                logger.warn("no existe el id del pago");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("Se proceso la busqueda del Id de pago ");
                return new ResponseEntity<Pagos>(pagos, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
        response = this.getTransactionExepcion(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }catch(DataAccessException e){
        response = this.getDataAccessException(response, e);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }
    }

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody PagosDTO value, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors() == true){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores", errores);
            logger.info("se encoentraron los errores al momento de validar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
             Pagos pagos = new Pagos();
             pagos.setId_pago(UUID.randomUUID().toString());
             pagos.setEstado_de_pago(value.getEstado_de_pago());
             pagos.setId_atleta(value.getId_atleta());
             pagos.setId_clase(value.getId_clase());
             this.iPagosService.save(pagos);
             logger.info("se acaba de creeer un nuevo miembro ");
             response.put("mensaje", "un nuevo pago se ha habilitado ");
             response.put("pago", pagos);
             return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @PutMapping("{pagoId}")
    public ResponseEntity <?> update (@Valid @RequestBody PagosDTO value, BindingResult result, @PathVariable String pagoId){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("errores", errores);
            logger.info("se encuentraron errores en la peticion de ");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Pagos pagos = this.iPagosService.findById(pagoId);
            if(pagos == null){
                response.put("mensaje", "EL nuevo miembro con le id".concat(pagoId).concat("No existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                pagos.setEstado_de_pago(value.getEstado_de_pago());
                this.iPagosService.save(pagos);
                response.put("mensaje","el pago fue actualizado");
                response.put("pago ", pagos);
                logger.info("el paog fue actualizada con exito ");
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

    @DeleteMapping("/{pagoId}")
    public ResponseEntity<?> delete(@PathVariable String pagoId){
        Map<String, Object> response = new HashMap<>();
        try {
            Pagos pagos = this.iPagosService.findById(pagoId);
            if(pagos == null){
                response.put("mensaje", "El pago con el Id".concat(pagoId).concat("no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                this.iPagosService.delete(pagos);
                response.put("mensaje","el miembro con el id".concat(pagoId).concat("fue eliminado "));
                response.put("miembro ",pagoId);
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
    public ResponseEntity<?> ListarNombrePorTerminino(@RequestParam("termino") String termino) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Pagos> pagos = this.iPagosService.findPagosByTermino(termino);
            if (pagos == null && pagos.size() == 0) {
                logger.warn("mensaje", "no existe nunguna concidencia");
                response.put("mensaje", "no existe nunguna concidencia");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } else {
                logger.info("se ejecuto la consulta de manera exitosa");
                return new ResponseEntity<List<Pagos>>(pagos, HttpStatus.OK);

            }
        } catch (CannotCreateTransactionException e) {
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
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
