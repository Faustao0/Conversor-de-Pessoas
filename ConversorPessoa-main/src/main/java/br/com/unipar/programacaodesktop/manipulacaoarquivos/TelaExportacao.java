/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.unipar.programacaodesktop.manipulacaoarquivos;

import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.Pessoa;
import br.com.unipar.programacaodesktop.manipulacaoarquivos.model.PessoaDAO;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author 00233397
 */
public class TelaExportacao extends javax.swing.JFrame {

    /**
     * Creates new form TelaExportacao
     */
    public TelaExportacao() {
        initComponents();
        setTitle("Exportar Arquivo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelArquivo = new javax.swing.JLabel();
        textArquivo = new javax.swing.JTextField();
        btnBuscarArquivo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaArquivo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelArquivo.setText("Arquivo");

        textArquivo.setEnabled(false);
        textArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textArquivoActionPerformed(evt);
            }
        });

        btnBuscarArquivo.setText("Exportar");
        btnBuscarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArquivoActionPerformed(evt);
            }
        });

        textAreaArquivo.setEditable(false);
        textAreaArquivo.setColumns(20);
        textAreaArquivo.setRows(5);
        jScrollPane1.setViewportView(textAreaArquivo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarArquivo)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelArquivo)
                    .addComponent(textArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarArquivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarArquivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setDialogTitle("Salvar Arquivo");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

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
    }//GEN-LAST:event_btnBuscarArquivoActionPerformed
        
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
    
    private void textArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textArquivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textArquivoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaExportacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaExportacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaExportacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaExportacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaExportacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarArquivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelArquivo;
    private javax.swing.JTextArea textAreaArquivo;
    private javax.swing.JTextField textArquivo;
    // End of variables declaration//GEN-END:variables
}
