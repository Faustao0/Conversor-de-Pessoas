package br.com.unipar.programacaodesktop.manipulacaoarquivos.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    
    private String url = "jdbc:postgresql://localhost:5432/ConversorDePessoas";
    private String user = "postgres";
    private String password = "postgres";

    public void salvarPessoa(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, idade, cpf, rg, data_nasc, sexo, signo, mae, pai, email, senha, cep, endereco, numero, bairro, cidade, estado, telefone_fixo, celular, altura, peso, tipo_sanguineo, cor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setString(3, pessoa.getCpf());
            stmt.setString(4, pessoa.getRg());
            stmt.setDate(5, new java.sql.Date(pessoa.getData_nasc().getTime()));
            stmt.setString(6, pessoa.getSexo());
            stmt.setString(7, pessoa.getSigno());
            stmt.setString(8, pessoa.getMae());
            stmt.setString(9, pessoa.getPai());
            stmt.setString(10, pessoa.getEmail());
            stmt.setString(11, pessoa.getSenha());
            stmt.setString(12, pessoa.getCep());
            stmt.setString(13, pessoa.getEndereco());
            stmt.setString(14, pessoa.getNumero());
            stmt.setString(15, pessoa.getBairro());
            stmt.setString(16, pessoa.getCidade());
            stmt.setString(17, pessoa.getEstado());
            stmt.setString(18, pessoa.getTelefone_fixo());
            stmt.setString(19, pessoa.getCelular());
            stmt.setDouble(20, pessoa.getAltura());
            stmt.setDouble(21, pessoa.getPeso());
            stmt.setString(22, pessoa.getTipo_sanguineo());
            stmt.setString(23, pessoa.getCor());
            stmt.executeUpdate();
        }
    }

    public List<Pessoa> listarPessoas() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setIdade(rs.getInt("idade"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setRg(rs.getString("rg"));
                pessoa.setData_nasc(rs.getDate("data_nasc"));
                pessoa.setSexo(rs.getString("sexo"));
                pessoa.setSigno(rs.getString("signo"));
                pessoa.setMae(rs.getString("mae"));
                pessoa.setPai(rs.getString("pai"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setCep(rs.getString("cep"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setNumero(rs.getString("numero"));
                pessoa.setBairro(rs.getString("bairro"));
                pessoa.setCidade(rs.getString("cidade"));
                pessoa.setEstado(rs.getString("estado"));
                pessoa.setTelefone_fixo(rs.getString("telefone_fixo"));
                pessoa.setCelular(rs.getString("celular"));
                pessoa.setAltura(rs.getDouble("altura"));
                pessoa.setPeso(rs.getDouble("peso"));
                pessoa.setTipo_sanguineo(rs.getString("tipo_sanguineo"));
                pessoa.setCor(rs.getString("cor"));
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }
}
