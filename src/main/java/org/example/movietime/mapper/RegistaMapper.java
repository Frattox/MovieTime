package org.example.movietime.mapper;

import org.example.movietime.mapper.dto.RegistaDTO;
import org.example.movietime.entities.Regista;

public class RegistaMapper {
    public static RegistaDTO toRegistaDTO(Regista regista) {
        if (regista == null) {
            return null;
        }
        return new RegistaDTO(
                regista.getIdRegista(),
                regista.getNome(),
                regista.getCognome(),
                regista.getDataN(),
                regista.getNazionalita()
        );
    }

    public static Regista toRegista(RegistaDTO registaDTO) {
        if (registaDTO == null) {
            return null;
        }
        Regista regista = new Regista();
        regista.setIdRegista(registaDTO.getIdRegista());
        regista.setNome(registaDTO.getNome());
        regista.setCognome(registaDTO.getCognome());
        regista.setDataN(registaDTO.getDataN());
        regista.setNazionalita(registaDTO.getNazionalita());
        return regista;
    }
}
