package tn.esprit.saroua.feedsyncservice.services;

import tn.esprit.saroua.feedsyncservice.dto.AnnonceRequestDTO;
import tn.esprit.saroua.feedsyncservice.dto.AnnonceResponseDTO;

import java.util.List;

public interface AnnonceService {

    AnnonceResponseDTO createAnnonce(AnnonceRequestDTO dto);

    AnnonceResponseDTO updateAnnonce(String id, AnnonceRequestDTO dto);

    void deleteAnnonce(String id);

    AnnonceResponseDTO getAnnonceById(String id);

    List<AnnonceResponseDTO> getAllAnnonces();
}
