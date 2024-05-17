package br.com.unipar.programacaodesktop.manipulacaoarquivos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManipulacaoArquivos extends JFrame {

    public ManipulacaoArquivos() {
        setTitle("Manipulação de Arquivos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem importarItem = new JMenuItem("Importar Arquivo");
        JMenuItem exportarItem = new JMenuItem("Exportar Arquivo");

        importarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaImportacao telaImportacao = new TelaImportacao();
                telaImportacao.setVisible(true);
            }
        });

        exportarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaExportacao telaExportacao = new TelaExportacao();
                telaExportacao.setVisible(true);
            }
        });

        menu.add(importarItem);
        menu.add(exportarItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ManipulacaoArquivos().setVisible(true);
            }
        });
    }
}
