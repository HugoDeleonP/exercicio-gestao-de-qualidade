package org.example.repository.relatorio;

import org.example.database.Conexao;
import org.example.dto.EquipamentoContagemFalhasDTO;
import org.example.dto.FalhaDetalhadaDTO;
import org.example.dto.RelatorioParadaDTO;
import org.example.model.Equipamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRepository {

    public List<RelatorioParadaDTO> readByRelatorioParada() throws SQLException {
        String sql = """
                SELECT f.id as falha_id, e.nome as equipamento_nome, f.tempoParadaHoras as falha_tempoParada 
                FROM Falha f
                LEFT JOIN Equipamento e ON
                f.equipamentoId = e.id;
                """;

        List<RelatorioParadaDTO> relatorios = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Long falha_id = rs.getLong("falha_id");
                String equipamento_nome = rs.getString("equipamento_nome");
                int falha_tempoParada = rs.getInt("falha_tempoParada");

                RelatorioParadaDTO relatorio = new RelatorioParadaDTO(falha_id, equipamento_nome, falha_tempoParada);
                relatorios.add(relatorio);
            }
        }

        return relatorios;
    }

    public List<Equipamento> readByEquipamentoSemFalhas(LocalDate dataInicio, LocalDate dataFim) throws SQLException{

        String sql = """
                SELECT id, nome, numeroDeSerie
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

        }

        return List.of();
    }

    public List<FalhaDetalhadaDTO> readByDetalhesCompletosFalha(long falhaId) throws SQLException{

        String sql = """
                
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

        }

        return List.of();
    }

    public List<EquipamentoContagemFalhasDTO> readByRelatorioManutencaoPreventiva(int contagemMinimaFalhas) throws SQLException{

        String sql = """
                
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

        }

        return List.of();
    }
}
