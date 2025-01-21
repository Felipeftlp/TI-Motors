/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.br.model.EstadoVeiculo;
import com.br.model.Veiculo;

/**
 *
 * @author felip
 */
public class VeiculoDAO {

    public ArrayList<Veiculo> buscarTodos() {
        String sql = "SELECT * FROM veiculo";

        // Responsável em guardar o resultado
        ResultSet resultado;

        ArrayList<Veiculo> lista = new ArrayList<>();

        Connection conn = FabricaConexao.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            resultado = ps.executeQuery();

            while (resultado.next()) {
                Veiculo objeto = new Veiculo();
                objeto.setId(resultado.getInt("id_veiculo"));
                objeto.setMarca(resultado.getString("marca"));
                objeto.setModelo(resultado.getString("modelo"));
                objeto.setAno(resultado.getString("ano"));
                objeto.setCor(resultado.getString("cor"));
                objeto.setPreco(resultado.getString("preco"));
                
                String estadoString = resultado.getString("estado");
                try {
                    if (estadoString != null) {
                        EstadoVeiculo estado = EstadoVeiculo.valueOf(estadoString.toUpperCase());
                        objeto.setEstado(estado);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Estado inválido encontrado: " + estadoString);
                    objeto.setEstado(EstadoVeiculo.NOVO);
                }
                
                lista.add(objeto);
            }
        } catch (SQLException e) {
        }

        FabricaConexao.fecharConexao(conn);

        return lista;
    }

    @SuppressWarnings("null")
    public Veiculo getById(Integer id) throws ClassNotFoundException {
        if (id == null || id < 0) {
            throw new IllegalArgumentException();
        }
        String sql = "SELECT * FROM veiculo WHERE id_veiculo=?";
        ResultSet resultado = null;
        Veiculo objeto = null;
        try (Connection conn = FabricaConexao.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeQuery();
            resultado.next();

            objeto.setId(resultado.getInt("id_veiculo"));
            objeto.setMarca(resultado.getString("marca"));
            objeto.setModelo(resultado.getString("modelo"));
            objeto.setAno(resultado.getString("ano"));
            objeto.setCor(resultado.getString("cor"));
            objeto.setPreco(resultado.getString("preco"));
            
            String estadoString = resultado.getString("estado");
            try {
                if (estadoString != null) {
                    EstadoVeiculo estado = EstadoVeiculo.valueOf(estadoString.toUpperCase());
                    objeto.setEstado(estado);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Estado inválido encontrado: " + estadoString);
                objeto.setEstado(EstadoVeiculo.NOVO);
            }
            
        } catch (SQLException e) {
        } finally {
            FabricaConexao.fecharConexao(resultado);
        }
        return objeto;
    }

    /**
     * Recebe um aluno para cadastar
     *
     * @param aluno
     * @return
     */
    public boolean create(Veiculo objeto) {

        try {
            String comando = "INSERT INTO veiculo (marca,modelo,ano,preco,cor,estado) VALUES"
                    + " (?, ?, ?, ?, ?, ?)";

            Connection conn = FabricaConexao.getConnection();
            //revisor DE  SQL
            PreparedStatement ps = conn.prepareStatement(comando);
            // substituindo as ?
            ps.setString(1, objeto.getMarca());
            ps.setString(2, objeto.getModelo());
            ps.setString(3, objeto.getAno());
            ps.setString(4, objeto.getPreco());
            ps.setString(5, objeto.getCor());
            ps.setString(6, objeto.getEstado().name());

            //inserindo no banco.
            int linhasAfetadas = ps.executeUpdate();
            FabricaConexao.fecharConexao(conn);

            if (linhasAfetadas > 0) {
                return true;
            }

        } catch (SQLException e) {
        }

        return false;
    }

    public boolean update(Veiculo veiculo) {

        String sql = "UPDATE veiculo SET marca = ?, modelo = ?, ano = ?, preco = ?, cor=?, estado=?"
                + "WHERE veiculo.id_veiculo = ?";

        try {
            Connection conn = FabricaConexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculo.getMarca());
            ps.setString(2, veiculo.getModelo());
            ps.setString(3, veiculo.getAno());
            ps.setString(4, veiculo.getPreco());
            ps.setString(5, veiculo.getCor());
            ps.setString(6, veiculo.getEstado().name());
            ps.setInt(7, veiculo.getId());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean delete(Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM veiculo WHERE veiculo.id_veiculo = ?";
        try {
            Connection conn = FabricaConexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;

    }
}
