package tn.esprit.saroua.feedsyncservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.saroua.feedsyncservice.dto.AnnonceRequestDTO;
import tn.esprit.saroua.feedsyncservice.dto.AnnonceResponseDTO;
import tn.esprit.saroua.feedsyncservice.entities.Annonce;
import tn.esprit.saroua.feedsyncservice.mapper.AnnonceMapper;
import tn.esprit.saroua.feedsyncservice.repositories.AnnonceRepository;
import tn.esprit.saroua.feedsyncservice.services.AnnonceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final AnnonceMapper annonceMapper;

    @Override
    public AnnonceResponseDTO createAnnonce(AnnonceRequestDTO dto) {
        Annonce annonce = annonceMapper.toEntity(dto);
        return annonceMapper.toDTO(annonceRepository.save(annonce));
    }

    @Override
    public AnnonceResponseDTO updateAnnonce(String id, AnnonceRequestDTO dto) {
        Annonce existing = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce not found with id: " + id));

        annonceMapper.updateEntityFromDTO(dto, existing);
        return annonceMapper.toDTO(annonceRepository.save(existing));
    }

    @Override
    public void deleteAnnonce(String id) {
        if (!annonceRepository.existsById(id)) {
            throw new RuntimeException("Annonce not found with id: " + id);
        }
        annonceRepository.deleteById(id);
    }

    @Override
    public AnnonceResponseDTO getAnnonceById(String id) {
        return annonceRepository.findById(id)
                .map(annonceMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Annonce not found with id: " + id));
    }

    @Override
    public List<AnnonceResponseDTO> getAllAnnonces() {
        return annonceRepository.findAll().stream()
                .map(annonceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
