package org.example.movietime.services;

import org.example.movietime.exceptionHandler.dto.RegistaDTO;
import org.example.movietime.mapper.RegistaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.repositories.RegistaRepository;

import org.example.movietime.exceptions.RegistaNotFoundException;

@Service
public class RegistaService{

    private final RegistaRepository registaRepository;

    public RegistaService(RegistaRepository registaRepository){
        this.registaRepository = registaRepository;
    }

    //Utile per ottenere le informazioni del regista per una schermata dedicata solo a lui
    @Transactional(readOnly = true)
    public RegistaDTO getRegista(int idRegista) throws RegistaNotFoundException {
        return RegistaMapper.toRegistaDTO(
                registaRepository.findById(idRegista).orElseThrow(RegistaNotFoundException::new)
        );
    }
}
