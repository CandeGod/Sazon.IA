package com.proyecto.SazonIA.service;


import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.Mapa;
import com.proyecto.SazonIA.repository.MapaRepository;

import java.util.List;

@Service
public class MapaService {

    private final MapaRepository mapaRepository;

    public MapaService(MapaRepository mapaRepository) {
        this.mapaRepository = mapaRepository;
    }

    public List<Mapa> obtenerTodos() {
        return mapaRepository.findAll();
    }

    public Mapa guardarMapa(Mapa mapa) {
        return mapaRepository.save(mapa);
    }

    public void eliminarMapa(Integer id) {
        mapaRepository.deleteById(id);
    }
}