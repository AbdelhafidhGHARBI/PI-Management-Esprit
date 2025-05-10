package tn.esprit.ressourcesservice.services;

import org.springframework.data.domain.Page;
import tn.esprit.ressourcesservice.entities.Classe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IClasseService {
    Classe createClasse(Classe classe);

    List<Classe> getAllClasses();

    Optional<Classe> getClasseById(String id);

    Classe updateClasse(String id, Classe updatedClasse);

    void deleteClasse(String id);
    
}
