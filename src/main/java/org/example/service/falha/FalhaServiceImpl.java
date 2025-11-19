package org.example.service.falha;

import org.example.model.Falha;
import org.example.repository.equipamento.EquipamentoRepository;
import org.example.repository.falha.FalhaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FalhaServiceImpl implements FalhaService{
    EquipamentoRepository equipamentoRepository = new EquipamentoRepository();
    FalhaRepository repository = new FalhaRepository();

    @Override
    public Falha registrarNovaFalha(Falha falha) throws SQLException {

        if(!equipamentoRepository.equipamentoExiste(falha.getEquipamentoId())){
            throw new IllegalArgumentException("Equipamento não encontrado!");
        }

        falha.setStatus("ABERTA");

        falha = repository.save(falha);

        if(Objects.equals(falha.getCriticidade(), "CRITICA")){
            equipamentoRepository.updateStatus(falha.getEquipamentoId(), "EM_MANUTENCAO");
        }

        if(falha.getId() == null){
            throw new RuntimeException("ERRO");
        }

        return falha;
    }

    @Override
    public List<Falha> buscarFalhasCriticasAbertas() throws SQLException {
        return repository.readFalhaCriticaAberta();
    }
}
