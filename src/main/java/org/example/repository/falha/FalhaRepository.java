package org.example.repository.falha;

import org.example.database.Conexao;
import org.example.model.Falha;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FalhaRepository {

    public Falha save(Falha falha) throws SQLException {

        String sql = """
                INSERT INTO Falha
                (equipamentoId, dataHoraOcorrencia, descricao,
                criticidade, status, tempoParadaHoras)
                VALUES
                (?, ?, ?, ?, ?, ?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, falha.getEquipamentoId());
            stmt.setTimestamp(2, Timestamp.valueOf(falha.getDataHoraOcorrencia()));
            stmt.setString(3, falha.getStatus());
            stmt.setString(4, falha.getCriticidade());
            stmt.setString(5, falha.getStatus());
            stmt.setBigDecimal(6, falha.getTempoParadaHoras());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                falha.setId(rs.getLong(1));
            }

        }

        return falha;
    }

    public List<Falha> readFalhaCriticaAberta() throws SQLException{
        String sql = """
                SELECT id, equipamentoId, dataHoraOcorrencia, descricao,
                criticidade, status, tempoParadaHoras
                FROM Falha
                WHERE criticidade = "CRITICA"
                AND status = "ABERTA"
                """;

        List<Falha> falhas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                Long id = rs.getLong("id");
                Long equipamentoId = rs.getLong("equipamentoId");
                LocalDateTime dataHoraOcorrencia = rs.getObject("dataHoraOcorrencia", LocalDateTime.class);
                String descricao = rs.getString("descricao");
                String criticidade = rs.getString("criticidade");
                String status = rs.getString("status");
                BigDecimal tempoParadaHoras = rs.getBigDecimal("tempoParadaHoras");

                Falha falha = new Falha(id, equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras);
                falhas.add(falha);
            }

        }

        return falhas;
    }

    public Falha readFalhas(long id) throws SQLException{
        String sql = """
                SELECT id, equipamentoId, dataHoraOcorrencia, descricao,
                criticidade, status, tempoParadaHoras
                FROM Falha
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt =conn.prepareStatement(sql)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){

                id = rs.getLong("id");
                Long equipamentoId = rs.getLong("equipamentoId");
                LocalDateTime dataHoraOcorrencia = rs.getObject("dataHoraOcorrencia", LocalDateTime.class);
                String descricao = rs.getString("descricao");
                String criticidade = rs.getString("criticidade");
                String status = rs.getString("status");
                BigDecimal tempoParadaHoras = rs.getBigDecimal("tempoParadaHoras");

                return new Falha(id, equipamentoId, dataHoraOcorrencia, descricao, criticidade, status, tempoParadaHoras);
            }
        }

        return null;
    }

    public void updateStatus(Long id, String status) throws SQLException {

        String sql = """
                UPDATE Falha
                SET status = ?
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, status);
            stmt.setLong(2, id);

            stmt.executeUpdate();

        }

    }

    public String readStatusById(Long id) throws SQLException{
        String sql = """
                SELECT status
                FROM Falha
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("status");
            }
        }

        return null;
    }
}
