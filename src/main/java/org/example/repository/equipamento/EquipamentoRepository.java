package org.example.repository.equipamento;

import org.example.database.Conexao;
import org.example.model.Equipamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoRepository {

    public Equipamento save(Equipamento equipamento) throws SQLException {
        String sql= """
                INSERT INTO Equipamento
                (nome, numeroDeSerie, areaSetor, statusOperacional)
                VALUES
                (?,?,?,?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, equipamento.getNome());
            stmt.setString(2, equipamento.getNumeroDeSerie());
            stmt.setString(3, equipamento.getAreaSetor());
            stmt.setString(4, equipamento.getStatusOperacional());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                equipamento.setId(rs.getLong(1));
            }
        }

        return equipamento;
    }

    public Equipamento readById(Long id) throws SQLException{
        String sql = """
                SELECT id,
                nome,
                numeroDeSerie,
                areaSetor,
                statusOperacional
                FROM Equipamento
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String nome = rs.getString("nome");
                String numeroDeSerie = rs.getString("numeroDeSerie");
                String areaSetor = rs.getString("areaSetor");
                String statusOperacional = rs.getString("statusOperacional");

                return new Equipamento(rs.getLong(1), nome, numeroDeSerie, areaSetor, statusOperacional);
            }
        }

        return null;
    }

    public boolean equipamentoExiste(Long id) throws SQLException{
        String sql = """
                SELECT COUNT(0) from Equipamento
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt =conn.prepareStatement(sql)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getLong(1) > 0;
            }

        }

        return false;
    }

    public void updateStatus(Long id, String status) throws SQLException {

        String sql = """
                UPDATE Equipamento
                SET statusOperacional = ?
                WHERE id = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, status);
            stmt.setLong(2, id);

            stmt.executeUpdate();

        }

    }
}
