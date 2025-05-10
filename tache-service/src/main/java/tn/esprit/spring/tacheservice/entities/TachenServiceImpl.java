package tn.esprit.spring.tacheservice.entities;



import java.util.List;
import java.util.Optional;

public class TachenServiceImpl implements TachenService {
    
    private TachenRepository tachen;

    @Override
    public tachen createTache(tachen tache) {
        return tachenRepository.save(tache);
    }

    @Override
    public List<tachen> getAllTaches() {
        return null;
    }

    @Override
    public Optional<tachen> getTacheById(String id) {
        return null;
    }

    @Override
    public Optional<tachen> updateTache(String id, tachen updatedTache) {
        return null;
    }

    @Override
    public boolean deleteTache(String id) {
        return false;
    }
}
