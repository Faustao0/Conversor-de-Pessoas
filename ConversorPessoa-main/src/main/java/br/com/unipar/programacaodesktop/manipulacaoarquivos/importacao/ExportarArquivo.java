package br.com.unipar.programacaodesktop.manipulacaoarquivos.importacao;

import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.Pessoa;
import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.PessoaDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportarArquivo extends JFrame {
   
    public ExportarArquivo() {
        setTitle("Exportar Arquivo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton exportButton = new JButton("Exportar");
        exportButton.setAlignmentX(CENTER_ALIGNMENT);
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarArquivoActionPerformed(evt);
            }
        });

        panel.add(exportButton);
        add(panel);
    }

    private void exportarArquivoActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setDialogTitle("Salvar Arquivo");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            List<Pessoa> pessoaList = buscarPessoasDoBanco();
            if (pessoaList != null) {
                boolean success = escreverPessoasNoArquivo(pessoaList, filePath);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Arquivo exportado com sucesso! Verifique o caminho: " + filePath);
                } else {
                    JOptionPane.showMessageDialog(this, "Algo deu errado, tente novamente.");
                }
            }
        }
    }

    private List<Pessoa> buscarPessoasDoBanco() {
        try {
            PessoaDAO pessoaDAO = new PessoaDAO();
            return pessoaDAO.listarPessoas();
        } catch (Exception e) {
            System.err.println("Erro ao buscar pessoas no banco de dados: " + e.getMessage());
            return null;
        }
    }

    private boolean escreverPessoasNoArquivo(List<Pessoa> pessoaList, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {
            
            bw.write("Nome;Idade;CPF;RG;Data Nascimento;Sexo;Signo;Mãe;Pai;Email;Senha;CEP;Endereço;Número;Bairro;Cidade;Estado;Telefone Fixo;Celular;Altura;Peso;Tipo Sanguíneo;Cor\n");
            
            for (Pessoa pessoa : pessoaList) {
                bw.write(String.format("%s;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%.2f;%s;%s\n",
                        pessoa.getNome(),
                        pessoa.getIdade(),
                        pessoa.getCpf(),
                        pessoa.getRg(),
                        pessoa.getData_nasc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        pessoa.getSexo(),
                        pessoa.getSigno(),
                        pessoa.getMae(),
                        pessoa.getPai(),
                        pessoa.getEmail(),
                        pessoa.getSenha(),
                        pessoa.getCep(),
                        pessoa.getEndereco(),
                        pessoa.getNumero(),
                        pessoa.getBairro(),
                        pessoa.getCidade(),
                        pessoa.getEstado(),
                        pessoa.getTelefone_fixo(),
                        pessoa.getCelular(),
                        pessoa.getAltura(),
                        pessoa.getPeso(),
                        pessoa.getTipo_sanguineo(),
                        pessoa.getCor()));
            }

            return true;
        } catch (IOException ex) {
            System.err.println("Erro ao escrever no arquivo: " + ex.getMessage());
            return false;
        }
    }
}
