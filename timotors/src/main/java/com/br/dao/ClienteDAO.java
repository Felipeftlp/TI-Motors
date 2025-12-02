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

import com.br.model.Cliente;
import com.br.model.EstadoVeiculo;

/**
 *
 * @author felip
 */
public class ClienteDAO {
    
    //@ skipesc
    public ArrayList<Cliente> buscarTodos(){
        String sql = "SELECT * FROM cliente";
        
        ResultSet resultado;

        ArrayList<Cliente> lista = new ArrayList<>();

        Connection conn = FabricaConexao.getConnection();

        try {
                
            PreparedStatement ps = conn.prepareStatement(sql);

            resultado = ps.executeQuery();

            while (resultado.next()) {

                Cliente objeto = new Cliente();
                objeto.setId(resultado.getInt("id_cliente"));
                objeto.setNome(resultado.getString("nome"));
                objeto.setCpf(resultado.getString("cpf"));
                objeto.setEmail(resultado.getString("email"));
                objeto.setTelefone(resultado.getString("telefone"));
                objeto.setEndereco(resultado.getString("endereco"));
                
                String interesseString = resultado.getString("interesse");
                try {
                    if (interesseString != null) {
                        EstadoVeiculo interesse = EstadoVeiculo.valueOf(interesseString.toUpperCase());
                        objeto.setInteresse(interesse);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Estado inválido encontrado: " + interesseString);
                    objeto.setInteresse(EstadoVeiculo.NOVO);
                }
                
                lista.add(objeto);
            }
        } catch (SQLException e) {
        }
        
        FabricaConexao.fecharConexao(conn);
        
        return lista;
    }
    //@ skipesc
    @SuppressWarnings("null")
    public Cliente getById(Integer id) throws ClassNotFoundException {
        if (id == null || id < 0) {
            throw new IllegalArgumentException();
        }
        String sql = "SELECT * FROM ciente WHERE id_cliente=?";
        ResultSet resultado = null;
        Cliente objeto = null;
        try (Connection conn = FabricaConexao.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeQuery();
            resultado.next();
             
            objeto.setId(resultado.getInt("id_cliente"));
            objeto.setNome(resultado.getString("nome"));
            objeto.setCpf(resultado.getString("cpf"));
            objeto.setEmail(resultado.getString("email"));
            objeto.setTelefone(resultado.getString("telefone"));
            objeto.setEndereco(resultado.getString("endereco"));

            String interesseString = resultado.getString("interesse");
            try {
                if (interesseString != null) {
                    EstadoVeiculo interesse = EstadoVeiculo.valueOf(interesseString.toUpperCase());
                    objeto.setInteresse(interesse);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Estado inválido encontrado: " + interesseString);
                objeto.setInteresse(EstadoVeiculo.NOVO); 
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
    //@ skipesc
    public boolean create(Cliente objeto){
       
        try{
            String comando = "INSERT INTO cliente (nome,cpf,telefone,email,endereco,interesse) VALUES"
                    + " (?, ?, ?, ?, ?, ?)";

            Connection conn = FabricaConexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(comando);
            
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getCpf());
            ps.setString(3, objeto.getTelefone());
            ps.setString(4, objeto.getEmail());
            ps.setString(5, objeto.getEndereco());
            ps.setString(6, objeto.getInteresse().name());
            
            //inserindo no banco.
            int linhasAfetadas = ps.executeUpdate();
            FabricaConexao.fecharConexao(conn);

            if (linhasAfetadas > 0) {
                return true;
            }

        
        } catch(SQLException e){
        }
        
        return false;
    }
    
    //@ skipesc
    public boolean update(Cliente cliente){
     
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?, interesse = ?"
                + "WHERE cliente.id_cliente = ?";
        
        try { 
            Connection conn = FabricaConexao.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf() );
            ps.setString(3, cliente.getTelefone() );
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getEndereco());
            ps.setString(6, cliente.getInteresse().name());
            ps.setInt(7, cliente.getId());
            
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    //@ skipesc
    public boolean delete(Integer id){
        if (id == null || id < 0) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM cliente WHERE cliente.id_cliente = ?";
        try{
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
