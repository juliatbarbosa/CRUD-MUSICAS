package org.libertas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class MusicaDAO {
	private static LinkedList<Musica> lista;
	private Conexao con;
	private Statement sta;
	private PreparedStatement prep;
	
//	public boolean criarTabela() {
//	    String sql = "CREATE TABLE IF NOT EXISTS musica( " +
//	                "id INT PRIMARY KEY AUTO_INCREMENT, " +
//	                "compositor VARCHAR(255), " +
//	                "cantor VARCHAR(255), " +
//	                "nomemusica VARCHAR(255), " +
//	                "anolancamento CHAR(4), " +
//	                "genero VARCHAR(100));";
//	    
//	    try {
//	        con = new Conexao();
//	        sta = con.getConnection().createStatement();
//	        sta.executeUpdate(sql);
//	        return true;
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return false;
//	    } finally {
//	        con.desconecta();
//	    }
//	}
	
	public LinkedList<Musica> listar(String nomemusica) {
	    LinkedList<Musica> lista = new LinkedList<Musica>();
	    Conexao con = new Conexao();
	    
	    try {
	        String sql = "SELECT * FROM musica "
	        		+ "WHERE nomemusica LIKE ? "
	        		+ "ORDER BY nomemusica";
	        
	        prep = con.getConnection().prepareStatement(sql);
	        prep.setString(1, "%" + nomemusica + "%");
	        
	        ResultSet res = prep.executeQuery();

	        while (res.next()) {
	            Musica m = new Musica();
	            m.setId(res.getInt("id"));
	            m.setNome(res.getString("nomemusica"));
	            m.setCompositor(res.getString("compositor"));
	            m.setGenero(res.getString("genero"));
	            m.setCantor(res.getString("cantor"));
	            m.setAnoLancamento(res.getString("anolancamento"));
	            lista.add(m);
	        }
	        res.close();
	    }  catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconecta();
	    }
	    
	    return lista;
	}

	
	
	public boolean inserir(Musica m) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	    	String sql = "INSERT INTO musica (compositor, cantor, nomemusica, anolancamento, genero) VALUES "
					+ "(?, ?, ?, ?, ?);";
	        PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setString(1, m.getCompositor());
			prep.setString(2, m.getCantor());
			prep.setString(3, m.getNome());
			prep.setString(4, m.getAnoLancamento());
			prep.setString(5, m.getGenero());
	        prep.execute();
	        retorno = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconecta();
	    }
	    
	    return retorno;
	}

	
	
	public boolean alterar(Musica m) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	    	String sql = "SELECT id FROM musica WHERE id = " + m.getId();
	    	Statement sta = con.getConnection().createStatement();
	    	ResultSet res = sta.executeQuery(sql);
	    	
	    	if (res.next()) {
	    		sql = "UPDATE musica SET compositor = ?, cantor = ?, nomemusica = ?, anolancamento = ?, genero = ? WHERE id = ?";
				
	    		prep = con.getConnection().prepareStatement(sql);
				prep.setString(1, m.getCompositor());
				prep.setString(2, m.getCantor());
				prep.setString(3, m.getNome());
				prep.setString(4, m.getAnoLancamento());
				prep.setString(5, m.getGenero());
				prep.setInt(6, m.getId());
	    		prep.execute();
	    		retorno = true;
	    	}
	    	res.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconecta();
	    }
	    
	    return retorno;
	}

	
	public boolean excluir(Musica m) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	    	String sql = "SELECT id FROM musica WHERE id = " + m.getId();
	    	Statement sta = con.getConnection().createStatement();
	    	ResultSet res = sta.executeQuery(sql);
	    	
	    	if (res.next()) {
	    		sql = "DELETE FROM musica WHERE id = ?";
	    		PreparedStatement prep = con.getConnection().prepareStatement(sql);
	    		prep.setInt(1, m.getId());
	    		prep.execute();
	    		retorno = true;
	    	}
	    	
	        res.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconecta();
	    }
	    
	    return retorno;
	}

	
	public Musica consultar(int id) {
		Conexao con = new Conexao();
		Musica m = new Musica();
		try {
			String sql = "SELECT * FROM musica WHERE id = " + id;
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				m.setId(res.getInt("id"));
				m.setCompositor(res.getString("compositor"));
				m.setCantor(res.getString("cantor"));
				m.setNome(res.getString("nomemusica"));
				m.setAnoLancamento(res.getString("anolancamento"));
				m.setGenero(res.getString("genero"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		return m;
	}
	
	public void salvar(Musica m) {
		if (m.getId() > 0) {
			alterar(m);
		} else {
			inserir(m);
		}
	}
}


