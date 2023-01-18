package com.gt.proyect.RegistroGT.controller;

import com.gt.proyect.RegistroGT.model.Usuario;
import com.gt.proyect.RegistroGT.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/getAll")
    public ResponseEntity<List<Usuario>> getAll(){
        try {
            return ResponseEntity.ok(userService.findByAll());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/findId/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id") Integer id){
        try {
            return ResponseEntity.ok(userService.findById(id));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario usuario){
        try {
            return new ResponseEntity<>(userService.save(usuario), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Correcto al eliminar al usuario");
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Usuario usuario, @PathVariable("id") Integer id){
       Usuario usuarioUp = userService.findById(id);

       if(usuarioUp == null){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se a podido encontrar a este usuario");
       }else{
           try {
               usuarioUp.setNombre(usuario.getNombre());
               usuarioUp.setClave(usuario.getClave());
               usuarioUp.setEmail(usuario.getEmail());
               usuarioUp.setEstado(usuario.getEstado());
               return new ResponseEntity<>(userService.save(usuarioUp), HttpStatus.CREATED);
           }catch (Exception e){
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
           }
       }
    }
}
