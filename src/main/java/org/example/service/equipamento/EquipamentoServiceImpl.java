package org.example.service.equipamento;

import org.example.model.Equipamento;
import org.example.repository.equipamento.EquipamentoRepository;

import java.sql.SQLException;

public class EquipamentoServiceImpl implements EquipamentoService{

    EquipamentoRepository repository = new EquipamentoRepository();

    @Override
    public Equipamento criarEquipamento(Equipamento equipamento) throws SQLException {
        equipamento.setStatusOperacional("OPERACIONAL");

        Equipamento equipamentoGerado = repository.save(equipamento);

        if(equipamentoGerado.getId() == null){
            throw new RuntimeException("Erro de id");
        }

        return equipamentoGerado;
    }

    @Override
    public Equipamento buscarEquipamentoPorId(Long id) throws SQLException {

        Equipamento equipamentoBuscado = repository.readById(id);

        if(equipamentoBuscado == null){
            throw new RuntimeException("Equipamento não encontrado!");
        }

        return equipamentoBuscado;
    }
}
