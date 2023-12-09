package com.gymantigua.controllers;

import com.gymantigua.dao.services.IEntrenadoresService;
import com.gymantigua.models.entities.EntrenadoresDTO;
import com.gymantigua.models.entities.MiembrosDTO;
import com.gymantigua.models.models.Entrenadores;
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
@RequestMapping(value = "/gimnacio/v3/entrenadores")
public class EntrenadorController {

    @Autowired
    private IEntrenadoresService iEntrenadoresService;

    private Logger logger = LoggerFactory.getLogger(EntrenadorController.class);



    @GetMapping
    public ResponseEntity<?> ListaEntrenadores(){
        Map<String, Object> response = new HashMap<>();
        this.logger.debug("iniciando consulta");
        try{
            List<Entrenadores> entrenadores =  this.iEntrenadoresService.findAll();
            if(entrenadores == null && entrenadores.isEmpty()){
                logger.warn("No existe registro de entidad");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                logger.info("se ejecuta la consulta");
                return new ResponseEntity<List<Entrenadores>>(entrenadores, HttpStatus.OK);
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
    public ResponseEntity<?> ListarEntrenadoresByPage(@PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        try{
            Pageable pageable = PageRequest.of(page, 5);//indicaremos el tamano de la pagina (el size 5) es la cantidad el avariable
            Page<Entrenadores> entrenadoresPage = iEntrenadoresService.findAll(pageable);
            if(entrenadoresPage == null || entrenadoresPage.getSize() == 0){
                logger.warn("no existe registro en la tabla de metas");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                logger.info("se realizo la consulta de enlistar por consulta");
                return new ResponseEntity<Page<Entrenadores>>(entrenadoresPage, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @GetMapping("{idEntrenador}")
    public  ResponseEntity<?> showEntrenador(@PathVariable String idEntrenador){ //VERIFIQUEMOS ESTE DATO
        Map<String, Object> response = new HashMap<>();
        logger.debug("inica el proceso para la busqueda del Id".concat(idEntrenador));
        try{
            Entrenadores entrenadores = this.iEntrenadoresService.findById(idEntrenador);
            if(entrenadores == null){
                logger.warn("no existe ese atleta con el Id");
                response.put("mesaje", "No existe el entrenadr con el id".concat(idEntrenador));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("se proceso la busqueda de laa forma correcta");
                return new ResponseEntity<Entrenadores>(entrenadores, HttpStatus.OK);

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
            Entrenadores entrenadores = new Entrenadores();
            entrenadores.setId_Entrenador(UUID.randomUUID().toString());
            entrenadores.setNombre(value.getNombre());
            entrenadores.setCumpleanos(value.getCumpleanos());
            entrenadores.setTelefono(value.getTelefono());

            this.iEntrenadoresService.save(entrenadores);
            logger.info("se acaba de creaer un nuevo miembro");
            response.put("mensaje", "Un nuevo miembro fue creado con exito ");
            response.put("entrenador", entrenadores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch (CannotCreateTransactionException e){
            response = this.getTransactionExepcion(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            response = this.getDataAccessException(response, e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @PutMapping("{idEntrenador}")
    public ResponseEntity<?>update(@Valid @RequestBody EntrenadoresDTO value, BindingResult result, @PathVariable String idEntrenador) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            response.put("eorrres", errores);
            logger.info("Se encotraron errores en la peticion ");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Entrenadores entrenadores = this.iEntrenadoresService.findById(idEntrenador);
            if (entrenadores == null) {
                response.put("mensaje", "el nuevo entrenador con el id".concat(idEntrenador).concat("no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                entrenadores.setNombre(value.getNombre());
                this.iEntrenadoresService.save(entrenadores);
                response.put("mensaje", "el miembro fue actualizado");
                response.put("entreadores ", entrenadores);
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
    @DeleteMapping("/{idEntrenador}")
    public ResponseEntity<?> delete(@PathVariable String idEntrenador){
        Map<String, Object> response = new HashMap<>();
        try {
            Entrenadores entrenadores = this.iEntrenadoresService.findById(idEntrenador);
            if(entrenadores == null){
                response.put("mensaje", "El entrenadores con el Id".concat(idEntrenador).concat("no existe"));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                this.iEntrenadoresService.delete(entrenadores);
                response.put("mensaje","el miembro con el id".concat(idEntrenador).concat("fue eliminado "));
                response.put("entrenadores ",entrenadores);
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
