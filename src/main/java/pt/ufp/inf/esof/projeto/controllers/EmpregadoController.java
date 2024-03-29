package pt.ufp.inf.esof.projeto.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ufp.inf.esof.projeto.dtos.EmpregadoCreateDTO;
import pt.ufp.inf.esof.projeto.dtos.EmpregadoResponseDTO;
import pt.ufp.inf.esof.projeto.dtos.conversores.ConverterEmpregadoParaDTO;
import pt.ufp.inf.esof.projeto.modelos.Empregado;
import pt.ufp.inf.esof.projeto.services.EmpregadoService;

import java.util.Optional;

@Controller
@RequestMapping("/empregado")
public class EmpregadoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmpregadoService empregadoService;
    private final ConverterEmpregadoParaDTO converterEmpregadoParaDTO = new ConverterEmpregadoParaDTO();
    @Autowired
    public EmpregadoController (EmpregadoService empregadoService) {this.empregadoService = empregadoService;}

    @PostMapping
    public ResponseEntity<EmpregadoResponseDTO> criarEmpregado (@RequestBody EmpregadoCreateDTO empregado) {
        this.logger.info("Post - criarEmpregado");
        Optional <Empregado> optionalEmpregado = empregadoService.criarEmpregado(empregado.converter());
        return optionalEmpregado.map(value -> ResponseEntity.ok(converterEmpregadoParaDTO.converter(value))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpregadoResponseDTO> getEmpregadoById(@PathVariable Long id) {
        this.logger.info("Get - getEmpregadoById");
        Optional<Empregado> optionalEmpregado = empregadoService.findById(id);
        return optionalEmpregado.map(empregado -> {
            EmpregadoResponseDTO empregadoResponseDTO=converterEmpregadoParaDTO.converter(empregado);
            return ResponseEntity.ok(empregadoResponseDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
