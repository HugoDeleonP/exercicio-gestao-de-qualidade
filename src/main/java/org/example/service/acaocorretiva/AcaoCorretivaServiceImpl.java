package org.example.service.acaocorretiva;

import org.example.model.AcaoCorretiva;
import org.example.model.Falha;
import org.example.repository.acaocorretiva.AcaoCorretivaRepository;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;

import java.sql.SQLException;
import java.util.Objects;

public class AcaoCorretivaServiceImpl implements AcaoCorretivaService{

    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();
    FalhaRepository falhaRepository = new FalhaRepository();
    AcaoCorretivaRepository repository = new AcaoCorretivaRepository();

    @Override
    public AcaoCorretiva registrarConclusaoDeAcao(AcaoCorretiva acao) throws SQLException {

        Falha falhaEncontrada = falhaRepository.readFalhas(acao.getFalhaId());

        if(falhaEncontrada == null){
            throw new RuntimeException("Falha não encontrada!");
        }

        acao = repository.save(acao);

        falhaRepository.updateStatus(falhaEncontrada.getId(), "RESOLVIDA");

        if(Objects.equals(falhaEncontrada.getCriticidade(), "CRITICA")){
            equipamentoRepository.updateStatus(falhaEncontrada.getEquipamentoId(), "OPERACIONAL");
        }

        return acao;
    }
}
