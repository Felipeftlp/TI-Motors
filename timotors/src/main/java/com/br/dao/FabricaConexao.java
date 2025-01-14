/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author felip
 */
public class FabricaConexao {
    private static final String USER = "root";
    private static final String PASS = "12345";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/femar";
    
       public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
                return null;
            }
        }

        public static void fecharConexao(Connection conn) {
            try {
                if (conn != null)
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
            }	
        }

    public static void fecharConexao(Connection conn, Statement s) {
        fecharConexao(conn);
        try {
                if (s != null)
                        s.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public static void fecharConexao(Connection conn, Statement s, ResultSet rs) {
        fecharConexao(conn, s);
        try {
                if (rs != null)
                        rs.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

	public static void fecharConexao(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
