package pt.ufp.inf.esof.projeto.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ufp.inf.esof.projeto.modelos.Projeto;
import pt.ufp.inf.esof.projeto.modelos.TarefaEfetiva;
import pt.ufp.inf.esof.projeto.modelos.TarefaPrevista;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProjetoRepositoryTest {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private TarefaPrevistaRepository tarefaPrevistaRepository;
    @Autowired
    private TarefaEfetivaRepository tarefaEfetivaRepository;

    @Test
    public void testaCriacaoProjeto() {
        Projeto p = new Projeto();
        p.setNome("PayPal");

        TarefaPrevista tarefaPrevista = new TarefaPrevista();
        tarefaPrevista.setNome("TarefaPrevista");

        TarefaEfetiva tarefaEfetiva = new TarefaEfetiva();
        tarefaEfetiva.setNome("TarefaEfetive");

        tarefaEfetiva.setProgresso(50);
        tarefaPrevista.setTarefaEfetiva(tarefaEfetiva);
        p.adicionaTarefa(tarefaPrevista);

        tarefaEfetivaRepository.save(tarefaEfetiva);
        tarefaPrevistaRepository.save(tarefaPrevista);

        projetoRepository.save(p);

        assertEquals(1,projetoRepository.count());
        assertEquals(1,tarefaPrevistaRepository.count());
        assertTrue(tarefaPrevistaRepository.findById(tarefaPrevista.getId()).isPresent());
        assertTrue(projetoRepository.findByNome(p.getNome()).isPresent());
        assertEquals(1,tarefaEfetivaRepository.count());
        assertTrue(tarefaEfetivaRepository.findByNome(tarefaEfetiva.getNome()).isPresent());
    }
}