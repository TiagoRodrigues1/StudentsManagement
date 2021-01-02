package pt.ufp.inf.esof.projeto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.ufp.inf.esof.projeto.dtos.EmpregadoCreateDTO;
import pt.ufp.inf.esof.projeto.dtos.EmpregadoResponseDTO;
import pt.ufp.inf.esof.projeto.dtos.conversores.ConverterEmpregadoParaDTO;
import pt.ufp.inf.esof.projeto.modelos.Empregado;
import pt.ufp.inf.esof.projeto.services.EmpregadoService;

import java.util.Optional;

@Controller
@RequestMapping("/empregado")

public class EmpregadoController {
    private final EmpregadoService empregadoService;
    private final ConverterEmpregadoParaDTO converterEmpregadoParaDTO = new ConverterEmpregadoParaDTO();

    public EmpregadoController (EmpregadoService empregadoService) {this.empregadoService = empregadoService;}

    @PostMapping
    public ResponseEntity<EmpregadoResponseDTO> criarEmpregado (@RequestBody EmpregadoCreateDTO empregado) {
        Optional <Empregado> optionalEmpregado = empregadoService.criarEmpregado(empregado.converter());
        return optionalEmpregado.map(value -> ResponseEntity.ok(converterEmpregadoParaDTO.converter(value))).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}