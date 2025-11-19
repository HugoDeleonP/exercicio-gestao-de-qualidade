package org.example.repository.acaocorretiva;

import org.example.database.Conexao;
import org.example.model.AcaoCorretiva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcaoCorretivaRepository {

    public AcaoCorretiva save(AcaoCorretiva acaoCorretiva) throws SQLException{
        String sql = """
                INSERT INTO AcaoCorretiva
                (falhaId, dataHoraInicio, dataHoraFim, responsavel, descricaoAcao)
                VALUES
                (?, ?, ?, ?, ?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            stmt.setLong(1, acaoCorretiva.getFalhaId());
            stmt.setObject(2, acaoCorretiva.getDataHoraInicio());
            stmt.setObject(3, acaoCorretiva.getDataHoraFim());
            stmt.setString(4, acaoCorretiva.getResponsavel());
            stmt.setString(5, acaoCorretiva.getDescricaoArea());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                acaoCorretiva.setId(rs.getLong(1));
            }

        }

        return acaoCorretiva;
    }
}
