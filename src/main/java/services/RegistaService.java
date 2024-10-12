package services;

import dto.RegistaDTO;
import mapper.RegistaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.RegistaRepository;

import resources.exceptions.RegistaNotFoundException;

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
