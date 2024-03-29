package pt.ufp.inf.esof.projeto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ufp.inf.esof.projeto.modelos.Empregado;
import pt.ufp.inf.esof.projeto.repositories.EmpregadoRepository;

import java.util.Optional;

@Service
public class EmpregadoServiceImpl implements EmpregadoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmpregadoRepository empregadoRepository;


    @Autowired
    public EmpregadoServiceImpl(EmpregadoRepository empregadoRepository) {
        this.empregadoRepository = empregadoRepository;
    }

    @Override
    public Optional<Empregado> criarEmpregado(Empregado empregado) {
        this.logger.info("A criar novo Empregado " + empregado.getNome());
        Optional<Empregado> optionalEmpregado = empregadoRepository.findByEmail(empregado.getEmail());
        if (optionalEmpregado.isEmpty()) {
            this.logger.info("Empregado criado com Sucesso");
            empregadoRepository.save(empregado);
            return Optional.of(empregadoRepository.save(empregado));
        }
        this.logger.info("Empregado já existia");
        return Optional.empty();
    }

    @Override
    public Optional<Empregado> findById(Long id) {
        this.logger.info("A procurar Empregado com id:" + id);
        return empregadoRepository.findById(id);
    }

}