package org.libertas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class PessoaDAO {
	
	public void salvar(Pessoa p) {
		if (p.getIdpessoa() > 0) {
			alterar(p);
		} else {
			inserir(p);
		}
	}
	
	public boolean inserir(Pessoa p) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	        String sql = "INSERT INTO pessoa (nome, telefone, email, cidade, endereco, cep) VALUES (?,?,?,?,?,?)";
	        PreparedStatement prep = con.getConnection().prepareStatement(sql);
	        prep.setString(1, p.getNome());
	        prep.setString(2, p.getTelefone());
	        prep.setString(3, p.getEmail());
	        prep.setString(4, p.getCidade());
	        prep.setString(5, p.getEndereco());
	        prep.setString(6, p.getCep());
	        prep.execute();
	        retorno = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconecta();
	    }
	    
	    return retorno;
	}

	
	public LinkedList<Pessoa> listar() {
		LinkedList<Pessoa> lista = new LinkedList<Pessoa>();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM pessoa";
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				Pessoa p = new Pessoa();
				p.setIdpessoa(res.getInt("idpessoa"));
				p.setNome(res.getString("nome"));
				p.setTelefone(res.getString("telefone"));
				p.setEmail(res.getString("email"));
				p.setCidade(res.getString("cidade"));
				p.setEndereco(res.getString("endereco"));
				p.setCep(res.getString("cep"));
				lista.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		return lista;
	}
	
	public boolean alterar(Pessoa p) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	    	String sql = "SELECT idpessoa FROM pessoa WHERE idpessoa = " + p.getIdpessoa();
	    	Statement sta = con.getConnection().createStatement();
	    	ResultSet res = sta.executeQuery(sql);
	    	
	    	if (res.next()) {
	    		sql = "UPDATE pessoa SET nome = ?, "
	    				+ "telefone = ?, "
	    				+ "email = ?, "
	    				+ "cidade = ?, "
	    				+ "endereco = ?, "
	    				+ "cep = ? "
	    				+ "WHERE idpessoa = ?";
	    		PreparedStatement prep = con.getConnection().prepareStatement(sql);
	    		prep.setString(1, p.getNome());
	    		prep.setString(2, p.getTelefone());
	    		prep.setString(3, p.getEmail());
	    		prep.setString(4, p.getCidade());
	    		prep.setString(5, p.getEndereco());
	    		prep.setString(6, p.getCep());
	    		prep.setInt(7, p.getIdpessoa());
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

	
	public boolean excluir(Pessoa p) {
	    Conexao con = new Conexao();
	    boolean retorno = false;
	    try {
	    	String sql = "SELECT idpessoa FROM pessoa WHERE idpessoa = " + p.getIdpessoa();
	    	Statement sta = con.getConnection().createStatement();
	    	ResultSet res = sta.executeQuery(sql);
	    	
	    	if (res.next()) {
	    		sql = "DELETE FROM pessoa WHERE idpessoa = ?";
	    		PreparedStatement prep = con.getConnection().prepareStatement(sql);
	    		prep.setInt(1, p.getIdpessoa());
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

	
	public Pessoa consultar(int id) {
		Pessoa p = new Pessoa();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM pessoa WHERE idpessoa = " + id;
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				p.setIdpessoa(res.getInt("idpessoa"));
				p.setNome(res.getString("nome"));
				p.setTelefone(res.getString("telefone"));
				p.setEmail(res.getString("email"));
				p.setCidade(res.getString("cidade"));
				p.setEndereco(res.getString("endereco"));
				p.setCep(res.getString("cep"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		return p;
	}

}
