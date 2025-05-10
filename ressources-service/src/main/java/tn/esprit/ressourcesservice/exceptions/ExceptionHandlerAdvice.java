package tn.esprit.ressourcesservice.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    // Gère toutes les entités non trouvées : Classe, Salle, Ressource
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFound(ConfigDataResourceNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    // Gère les erreurs dues à des arguments illégaux (ex: validations métiers)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    // Gère toute autre erreur inattendue
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGlobalException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Une erreur interne est survenue. Détails : " + ex.getMessage());
        return errorMap;
    }
}
