package Views;

import Analyzers.LexicalAnalyzer;
import Analyzers.SemanticAnalyzer;
import Analyzers.SintaticAnalyzer;
import Java.Entitys.*;
import Managers.AuthomatGen;
import Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by joaop on 05/09/2017.
 */
public class TestPanel extends JFrame{
    private JEditorPane txtTransitionFunction = new JEditorPane();
    private JButton btnGenerateAuthomata = new JButton();
    private JButton btnOpenArchive = new JButton();
    private List<State> stateList=new ArrayList<State>();

    private List<Token> tokenList;
    private List<Identifier> identifierList;

    private List<Error> errors;

    public TestPanel(java.util.List<State> stateList){
        this.stateList=stateList;
        /*super("O que está escrito aqui nao importa, não vai aparecer!!");*/
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.darkGray);

        btnGenerateAuthomata=new JButton("Validar Script L2S");
        btnGenerateAuthomata.addActionListener(analyze);
        c.add(btnGenerateAuthomata,BorderLayout.NORTH);

        btnOpenArchive = new JButton("Carregar script");
        //btnOpenArchive.setBounds(10,10,100,10);
        btnOpenArchive.addActionListener(pressOpenArchive);
        c.add(btnOpenArchive, BorderLayout.SOUTH);

        txtTransitionFunction=new JEditorPane();
        txtTransitionFunction.setText(Constants.PLACEHOLD_CODE);
        this.add(txtTransitionFunction);

        setSize(1366,780);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private ActionListener analyze = e -> {
        Response response = lexicalAnalyze();
        Node root = sintaticAnalyze();
        String output = semanticAnalyze(root);
        if(response.isCorrect() && this.errors.size() == 0){
            writeCFile(output);
            showOkDialog();
        }
        else
        {
            showErrorDialog(response, this.errors);
        }
    };

    private String semanticAnalyze(Node root) {
        SemanticAnalyzer semanticAnalyzer = null;
        Error error;
        String output = "";
        try {
            semanticAnalyzer = new SemanticAnalyzer(root, identifierList);
            return semanticAnalyzer.toString();
        } catch (Exception e1) {
            error = new Error(e1.getMessage());
            this.errors.add(error);
            return "";
        }
    }

    private Node sintaticAnalyze() {
        SintaticAnalyzer sintaticAnalyzer = new SintaticAnalyzer(this.tokenList);
        this.errors = sintaticAnalyzer.getErrorLit();
        return sintaticAnalyzer.getRoot();
    }

    private Response lexicalAnalyze() {
        List<PhiniteAuthomata> authomataList=new AuthomatGen().AuthomatGen();
        String code = txtTransitionFunction.getText();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(code, authomataList);
        Response response = lexicalAnalyzer.Analyze();
        this.tokenList = lexicalAnalyzer.getTokenList();
        this.identifierList = lexicalAnalyzer.getIdentifiers();
        Token token = new Token("$");
        token.setTokenClass("$");
        if(this.tokenList.size() > 1){
            token.setLine(this.tokenList.get(this.tokenList.size()-1).getLine() + 1);
        } else {
            token.setLine(1);
        }
        this.tokenList.add(token);
        return response;
    }

    private void showErrorDialog(Response response, List<Error> errors) {
        String message = "";
        if(!response.isCorrect()) message += response.getError();
        if(errors.size() > 0) message += "1 ou mais erro(s) sintático(s): " + errors.get(0).getMessage();
        String[] options = {"Ok"};
        JOptionPane.showOptionDialog(null,
                "Erro: " + message,
                "Nao Aprovado!!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private void showOkDialog() {
        String[] options = {"Ok"};
        JOptionPane.showOptionDialog(null,
                "Codigo aceito pelo compilador L2S",
                "Aprovado!!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private void writeCFile(String saida) {
        File file = new File("l2s.txt");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter("C:/Users/Neppo/Documents/Comp/" + file.getName(),"UTF-8");
            writer.print(saida);
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private ActionListener pressOpenArchive = e -> {
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int i= file.showSaveDialog(null);
        if (i==1){
            //faz nada
        } else {
            File arquivo = file.getSelectedFile();
            String caminho=arquivo.getPath();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(caminho));
                String line = reader.readLine();
                StringBuilder code = new StringBuilder();
                while (line != null){
                    code.append(line).append("\n");
                    line = reader.readLine();
                }
                txtTransitionFunction.setText(code.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };
}
