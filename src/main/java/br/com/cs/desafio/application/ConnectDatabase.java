package br.com.cs.desafio.application;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectDatabase {

	public static void main(String[] args) {
		Connection con = null;
		
		try{
			//Registra o driver do banco
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			
			//Cria a conexao com o banco
			con = DriverManager.getConnection("jdbc:hsqldb:mem:.", "alinesantos", "");
			
			if(con!=null) {
				JOptionPane.showMessageDialog(null, "Funcionou");
			} else {
				JOptionPane.showMessageDialog(null, "Nao deu");
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

	}

}
