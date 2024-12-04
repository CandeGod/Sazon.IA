package com.proyecto.SazonIA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.SazonIA.model.Mapa;
import com.proyecto.SazonIA.service.MapaService;

import java.util.List;

@RestController
@RequestMapping("/mapas")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
    RequestMethod.PUT })
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<Mapa> obtenerTodos() {
        return mapaService.obtenerTodos();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<Mapa> guardarMapa(@RequestBody Mapa mapa) {
        Mapa nuevoMapa = mapaService.guardarMapa(mapa);
        return ResponseEntity.ok(nuevoMapa);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMapa(@PathVariable Integer id) {
        mapaService.eliminarMapa(id);
        return ResponseEntity.noContent().build();
    }
}