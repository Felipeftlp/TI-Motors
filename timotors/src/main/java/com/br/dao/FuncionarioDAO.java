/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.br.model.Cargo;
import com.br.model.Funcionario;

/**
 *
 * @author felip
 */
public class FuncionarioDAO {

    /*@
      @ public normal_behavior
      @   assignable \nothing;
      @*/
    //@ skipesc
    public FuncionarioDAO() {
        super();
    }

    //@ skipesc
    public ArrayList<Funcionario> buscarTodos(){
        String sql = "SELECT * FROM funcionario";
        
        ResultSet resultado;

        ArrayList<Funcionario> lista = new ArrayList<>();

        Connection conn = FabricaConexao.getConnection();

        try {
                
            PreparedStatement ps = conn.prepareStatement(sql);

            resultado = ps.executeQuery();

            while (resultado.next()) {

                Funcionario objeto = new Funcionario();
                objeto.setId(resultado.getInt("id_funcionario"));
                objeto.setNome(resultado.getString("nome"));
                objeto.setCpf(resultado.getString("cpf"));
                objeto.setEmail(resultado.getString("email"));
                objeto.setTelefone(resultado.getString("telefone"));
                objeto.setSalario(resultado.getDouble("salario"));
                double comissao = resultado.getDouble("comissao");
                if (resultado.wasNull()) {
                    comissao = 0.0;
                }
                objeto.setComissao(comissao);
                objeto.setAnosNaEmpresa(resultado.getInt("anosNaEmpresa"));

                String dataAdmissaoString = resultado.getString("dataAdmissao");
                String cargoString = resultado.getString("cargo");
                try {
                    if (cargoString != null && dataAdmissaoString != null) {
                        LocalDate dataAdmissao = LocalDate.parse(dataAdmissaoString);
                        objeto.setDataAdmissao(dataAdmissao);

                        Cargo cargo = Cargo.valueOf(cargoString.toUpperCase());
                        objeto.setCargo(cargo);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Estado inválido encontrado: " + cargoString);
                    objeto.setCargo(Cargo.VENDEDOR);
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
    public Funcionario getById(Integer id) throws ClassNotFoundException {
        if (id == null || id < 0) {
            throw new IllegalArgumentException();
        }
        String sql = "SELECT * FROM funcionario WHERE id_funcionario=?";
        ResultSet resultado = null;
        Funcionario objeto = null;
        try (Connection conn = FabricaConexao.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeQuery();
            resultado.next();
             
            objeto.setId(resultado.getInt("id_funcionario"));
            objeto.setNome(resultado.getString("nome"));
            objeto.setCpf(resultado.getString("cpf"));
            objeto.setEmail(resultado.getString("email"));
            objeto.setTelefone(resultado.getString("telefone"));
            objeto.setSalario(resultado.getDouble("salario"));
            double comissao = resultado.getDouble("comissao");
            if (resultado.wasNull()) {
                comissao = 0.0;
            }
            objeto.setComissao(comissao);
            objeto.setAnosNaEmpresa(resultado.getInt("anosNaEmpresa"));

            String dataAdmissaoString = resultado.getString("dataAdmissao");
            String cargoString = resultado.getString("cargo");
            try {
                if (cargoString != null && dataAdmissaoString != null) {
                    LocalDate dataAdmissao = LocalDate.parse(dataAdmissaoString);
                    objeto.setDataAdmissao(dataAdmissao);

                    Cargo cargo = Cargo.valueOf(cargoString.toUpperCase());
                    objeto.setCargo(cargo);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Estado inválido encontrado: " + cargoString);
                objeto.setCargo(Cargo.VENDEDOR);
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
    public boolean create(Funcionario objeto){
       
        try{
            String comando = "INSERT INTO funcionario (nome,cpf,telefone,email,salario,comissao,anosNaEmpresa,dataAdmissao,cargo) VALUES"
                    + " (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection conn = FabricaConexao.getConnection();
            //revisor DE  SQL
            PreparedStatement ps = conn.prepareStatement(comando);
            // substituindo as ?
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getCpf());
            ps.setString(3, objeto.getTelefone());
            ps.setString(4, objeto.getEmail());
            ps.setDouble(5, objeto.getSalario());
            ps.setDouble(6, objeto.getComissao());
            ps.setInt(7, objeto.getAnosNaEmpresa());
            ps.setDate(8, Date.valueOf(objeto.getDataAdmissao()));
            ps.setString(9, objeto.getCargo().name());

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
    public boolean update(Funcionario funcionario){
     
        String sql = "UPDATE funcionario SET nome = ?, cpf = ?, telefone = ?, email = ?, salario = ?, comissao = ?, anosNaEmpresa = ?, dataAdmissao = ?, cargo = ?"
                + "WHERE funcionario.id_funcionario = ?";
        
        try { 
            Connection conn = FabricaConexao.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf() );
            ps.setString(3, funcionario.getTelefone() );
            ps.setString(4, funcionario.getEmail());
            ps.setDouble(5, funcionario.getSalario());
            ps.setDouble(6, funcionario.getComissao());
            ps.setInt(7, funcionario.getAnosNaEmpresa());
            ps.setDate(8, Date.valueOf(funcionario.getDataAdmissao()));
            ps.setString(9, funcionario.getCargo().name());
            ps.setInt(10, funcionario.getId());
            
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
        String sql = "DELETE FROM funcionario WHERE funcionario.id_funcionario = ?";
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

    /*@ 
      @ public behavior
      @   ensures \result == true || \result == false;
      @   assignable \nothing;
      @*/
    //@ skipesc
    public boolean atualizarComissao(Integer idFuncionario, double valorComissao) {
        if (idFuncionario == null || idFuncionario < 0) {
            return false;
        }
        String sql = "UPDATE funcionario SET comissao = comissao + ? WHERE funcionario.id_funcionario = ?";
        Connection conn = null;
        try {
            conn = FabricaConexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, valorComissao);
            ps.setInt(2, idFuncionario);
            
            int linhasAfetadas = ps.executeUpdate();
            FabricaConexao.fecharConexao(conn);
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            if (conn != null) {
                FabricaConexao.fecharConexao(conn);
            }
            e.printStackTrace();
        }
        return false;
    }
}
