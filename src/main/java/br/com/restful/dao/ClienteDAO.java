package br.com.restful.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.restful.factory.ConnectionFactory;
import br.com.restful.model.Cliente;

/**
 * Classe responsovel por conter os metodos do CRUD
 */
public class ClienteDAO extends ConnectionFactory {

	private static ClienteDAO instance;

	/**
	 * Metodo responsovel por criar uma instancia da classe ClienteDAO
	 * (Singleton)
	 */
	public static ClienteDAO getInstance() {
		if (instance == null)
			instance = new ClienteDAO();
		return instance;
	}

	/**
	 * 
	 * Metodo responsavel por listar todos os clientes do banco
	 */
	public ArrayList<Cliente> listarTodos() {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Cliente> clientes = null;

		conexao = criarConexao();
		clientes = new ArrayList<Cliente>();
		try {
			pstmt = conexao
					.prepareStatement("select * from cliente order by nome");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();

				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEndereco(rs.getString("endereco"));

				clientes.add(cliente);
			}

		} catch (Exception e) {
			System.out.println("Erro ao listar todos os clientes: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return clientes;
	}

	/**
	 * Busca um cliente no banco dado um id
	 * 
	 * 
	 * @param id
	 * @return cliente
	 * @author Manoel Silva Motoso <manoelmotoso@hotmail.com>
	 * @since 11/05/2016 11:48:45
	 * @version 1.0
	 */
	public Cliente getById(long id) {

		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Cliente cliente = null;
		conexao = criarConexao();

		try {
			pstmt = conexao.prepareStatement("select * from cliente where id="
					+ id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEndereco(rs.getString("endereco"));
			}
		} catch (Exception e) {
			System.out
					.println("Erro ao buscar cliente com ID=" + id + "\n" + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}

		return cliente;

	}

	/**
	 * Metodo responsavel por gravar cliente no banco de dados.
	 * 
	 * @param cliente
	 * @return verdade se cliente gravado e falso se nao gravado
	 * @author Manoel Silva Motoso <manoelmotoso@hotmail.com>
	 * @since 11/05/2016 11:49:38
	 * @version 1.0
	 */
	public boolean insert(Cliente cliente) {
		String nome = cliente.getNome();
		String cpf = cliente.getCpf();
		String endereco = cliente.getEndereco();
		boolean isGravado = false;
		PreparedStatement pstmt = null;
		Connection conexao = criarConexao();
		try {
			pstmt = conexao
					.prepareStatement("insert into cliente(nome,cpf,endereco)"
							+ "values(?,?,?)");
			pstmt.setString(1, nome);
			pstmt.setString(2, cpf);
			pstmt.setString(3, endereco);
			boolean execute = pstmt.execute();
			isGravado = true;
			System.out.println("Respota do insert: " + execute);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isGravado = false;
			e.printStackTrace();

		}
		return isGravado;
	}

	public static boolean update(Cliente cliente) {
		long id = cliente.getId();
		String nome = cliente.getNome();
		String cpf = cliente.getCpf();
		String endereco = cliente.getEndereco();
		boolean isAtualizado = false;
		PreparedStatement pstmt = null;
		Connection conexao = criarConexao();
		try {
			pstmt = conexao
					.prepareStatement("UPDATE cliente SET nome =?,cpf = ?,endereco = ? WHERE id = ?");
			pstmt.setString(1, nome);
			pstmt.setString(2, cpf);
			pstmt.setString(3, endereco);
			pstmt.setLong(4, id);
			int execute = pstmt.executeUpdate();
			isAtualizado = true;
			System.out.println("Respota do update: " + execute);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isAtualizado = false;
			e.printStackTrace();

		}
		return isAtualizado;
		
	}
}
