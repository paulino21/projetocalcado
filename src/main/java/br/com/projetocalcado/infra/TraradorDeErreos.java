package br.com.projetocalcado.infra;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TraradorDeErreos {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity trataErro404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity trataErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
       @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(ConstraintViolationException ex) {
        String msn="";
        if(ex.getMessage().contains("itens_nota")){
           msn = "Esse produto está vínculado a uma nota e não pode ser excluido";
       }
       else if(ex.getMessage().contains("produto")) {
           msn = "Essa categoria está vínculada a um produto e não pode ser excluida";
        }
        return ResponseEntity.badRequest().body(msn);
    }
}






