package br.com.unipar.programacaodesktop.manipulacaoarquivos.importacao;

import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.PessoaDAO;
import java.util.List;
import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.Pessoa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportarArquivo {
    
    public static void importar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            List<Pessoa> pessoaList = lerPessoasDoArquivo(selectedFile.getAbsolutePath());
            if (pessoaList != null) {
                salvarPessoasNoBanco(pessoaList);
                JOptionPane.showMessageDialog(null, "Arquivo importado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Algo deu errado, tente novamente.");
            }
        }
    }

    private static List<Pessoa> lerPessoasDoArquivo(String filePath) {
        List<Pessoa> pessoaList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"))) {

            String linha;
            Integer id = 0;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Pessoa pessoa = new Pessoa();
                pessoa.setId(id++);
                pessoa.setNome(campos[0]);
                pessoa.setIdade(Integer.parseInt(campos[1]));
                pessoa.setCpf(campos[2]);
                pessoa.setRg(campos[3]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(campos[4], formatter);
                pessoa.setData_nasc(Date.from(Instant.MIN).from(date.atStartOfDay(ZoneId.from(date).systemDefault()).toInstant()));
                pessoa.setSexo(campos[5]);
                pessoa.setSigno(campos[6]);
                pessoa.setMae(campos[7]);
                pessoa.setPai(campos[8]);
                pessoa.setEmail(campos[9]);
                pessoa.setSenha(campos[10]);
                pessoa.setCep(campos[11]);
                pessoa.setEndereco(campos[12]);
                pessoa.setNumero(campos[13]);
                pessoa.setBairro(campos[14]);
                pessoa.setCidade(campos[15]);
                pessoa.setEstado(campos[16]);
                pessoa.setTelefone_fixo(campos[17]);
                pessoa.setCelular(campos[18]);
                pessoa.setAltura(Double.valueOf(campos[19]));
                pessoa.setPeso(Double.valueOf(campos[20]));
                pessoa.setTipo_sanguineo(campos[21]);
                pessoa.setCor(campos[22]);

                pessoaList.add(pessoa);
            }
        } catch (IOException ex) {
            System.err.println("Algo deu errado..." + ex.getMessage());
            return null;
        }
        return pessoaList;
    }

    private static void salvarPessoasNoBanco(List<Pessoa> pessoaList) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        for (Pessoa pessoa : pessoaList) {
            try {
                pessoaDAO.salvarPessoa(pessoa);
                System.out.println("Pessoa salva no banco de dados: " + pessoa);
            } catch (Exception e) {
                System.err.println("Erro ao salvar pessoa no banco de dados: " + e.getMessage());
            }
        }
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
