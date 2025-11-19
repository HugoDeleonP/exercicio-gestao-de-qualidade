package org.example.service.acaocorretiva;

import org.example.model.AcaoCorretiva;
import org.example.repository.acaocorretiva.AcaoCorretivaRepository;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;

import java.sql.SQLException;

public class AcaoCorretivaServiceImpl implements AcaoCorretivaService{

    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();
    FalhaRepository falhaRepository = new FalhaRepository();
    AcaoCorretivaRepository repository = new AcaoCorretivaRepository();

    @Override
    public AcaoCorretiva registrarConclusaoDeAcao(AcaoCorretiva acao) throws SQLException {

        if(!falhaRepository.falhaExiste(acao.getFalhaId())){
            throw new RuntimeException();
        }

        acao = repository.save(acao);

        falhaRepository.updateStatus(acao.getFalhaId(), "RESOLVIDA");

        return null;
    }
}
