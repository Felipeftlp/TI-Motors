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

/**
 *
 * @author felip
 */
public class ClienteDAO {
    public ArrayList<Cliente> buscarTodos(){
        String sql = "SELECT * FROM cliente";
        
        // Responsável em guardar o resultado
        ResultSet resultado;

        ArrayList<Cliente> lista = new ArrayList<>();

        Connection conn = FabricaConexao.getConnection();

        try {
                
            PreparedStatement ps = conn.prepareStatement(sql);

            resultado = ps.executeQuery();

            while (resultado.next()) {
                //Antes a gente estava imprimindo.
                // Agora estamos guardando no arrayList
                Cliente objeto = new Cliente();
                objeto.setId_cliente(resultado.getInt("id_cliente"));
                objeto.setNome(resultado.getString("nome"));
                objeto.setCpf(resultado.getString("cpf"));
                objeto.setEmail(resultado.getString("email"));
                objeto.setTelefone(resultado.getString("telefone"));
                objeto.setInteresse(resultado.getString("interesse"));
                
                lista.add(objeto);
            }
        } catch (SQLException e) {
        }
        
        FabricaConexao.fecharConexao(conn);
        
        return lista;
    }

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
             
            objeto.setId_cliente(resultado.getInt("id_cliente"));
            objeto.setNome(resultado.getString("nome"));
            objeto.setCpf(resultado.getString("cpf"));
            objeto.setEmail(resultado.getString("email"));
            objeto.setTelefone(resultado.getString("telefone"));
            objeto.setInteresse(resultado.getString("interesse"));
                
                
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
    public boolean create(Cliente objeto){
       
        try{
            String comando = "INSERT INTO cliente (nome,cpf,telefone,email,interesse) VALUES"
                    + " (?, ?, ?, ?, ?)";

            Connection conn = FabricaConexao.getConnection();
            //revisor DE  SQL
            PreparedStatement ps = conn.prepareStatement(comando);
            // substituindo as ?
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getCpf());
            ps.setString(3, objeto.getTelefone());
            ps.setString(4, objeto.getEmail());
            ps.setString(5, objeto.getInteresse());
            
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

    public boolean update(Cliente cliente){
     
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ?, interesse=?"
                + "WHERE cliente.id_cliente = ?";
        
        try { 
            Connection conn = FabricaConexao.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf() );
            ps.setString(3, cliente.getTelefone() );
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getInteresse());
            ps.setInt(6, cliente.getId_cliente());
            
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    
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
