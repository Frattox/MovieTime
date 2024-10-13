package org.example.movietime.services;

import org.example.movietime.dto.RegistaDTO;
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

    @Transactional(readOnly = true)
    public RegistaDTO getRegista(int idRegista) throws RegistaNotFoundException {
        return RegistaMapper.toRegistaDTO(
                registaRepository.findById(idRegista).orElseThrow(RegistaNotFoundException::new)
        );
    }
}
