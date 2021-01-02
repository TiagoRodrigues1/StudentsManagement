package pt.ufp.inf.esof.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ufp.inf.esof.projeto.dtos.ProjetoRespondeDTO;
import pt.ufp.inf.esof.projeto.dtos.TarefaPrevistaCreateDTO;
import pt.ufp.inf.esof.projeto.dtos.conversores.ConverterProjetoParaDTO;
import pt.ufp.inf.esof.projeto.dtos.ProjetoCreateDTO;
import pt.ufp.inf.esof.projeto.modelos.Projeto;
import pt.ufp.inf.esof.projeto.services.ProjetoService;

import java.util.Optional;

@Controller
@RequestMapping("/projeto")
public class ProjetoController {
    private final ProjetoService projetoService;
    private final ConverterProjetoParaDTO converterProjetoParaDTO = new ConverterProjetoParaDTO();
    @Autowired
    public ProjetoController (ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping("/{id}/valor")
    public ResponseEntity<Float> getProjetoByIdVal(@PathVariable Long id) {
        float valor = projetoService.getProjetoByIdVal(id);
        if(valor != 0F) {
            return ResponseEntity.ok(valor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjetoRespondeDTO> criarProjeto (@RequestBody ProjetoCreateDTO projeto) {
        Optional<Projeto> optionalProjeto = projetoService.criarProjeto(projeto.converter());
        return optionalProjeto.map(value -> ResponseEntity.ok(converterProjetoParaDTO.converter(value))).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping("/{id}/tempo")
    public ResponseEntity<Float> getProjetoByIdTempo(@PathVariable Long id) {
        float tempo = projetoService.getProjetoByIdTempo(id);
        if(tempo != 0F) {
            return ResponseEntity.ok(tempo);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/tarefa/{id}")
    public ResponseEntity<ProjetoRespondeDTO> adicionaTarefa (@PathVariable Long id, @RequestBody TarefaPrevistaCreateDTO tarefa) {
    Optional<Projeto> optionalTarefaPrevista = projetoService.adicionaTarefa(id,tarefa.converter());
        return optionalTarefaPrevista.map(projeto -> ResponseEntity.ok(converterProjetoParaDTO.converter(projeto))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

}