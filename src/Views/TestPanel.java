package Views;

import Analyzers.LexicalAnalyzer;
import Analyzers.SemanticAnalyzer;
import Analyzers.SintaticAnalyzer;
import Java.Entitys.*;
import Managers.AuthomatGen;

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
        txtTransitionFunction.setText("( int m fat i ) ( real r1 )\n" +
                "( :>> \"Entre o valor n:\" )\n" +
                "( :<< n )\n" +
                "( ? ( < n 0 ) ( ( :>> \"Valor Invalido\" ) ) ( ( = fat 1 ) ( = i 1 ) ( ... ( <= i n ) ( = fat ( * fat i ) ) ( = i ( + i 1 ) ) ) ) ( string res ) ( = res ( & \"fat : \" fat ) ) ( :>> res ) )");
        this.add(txtTransitionFunction);

        setSize(1366,780);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private ActionListener analyze = e -> {
        List<PhiniteAuthomata> authomataList=new AuthomatGen().AuthomatGen();
        String code = txtTransitionFunction.getText();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(code, authomataList);
        Response response = lexicalAnalyzer.Analyze();
        String lexem = lexicalAnalyzer.getLexem();
        List<Token> tokenList = lexicalAnalyzer.getTokenList();
        List<Identifier> identifierList = lexicalAnalyzer.getIdentifiers();
        Token token = new Token("$");
        token.setTokenClass("$");
        if(tokenList.size() > 1){
            token.setLine(tokenList.get(tokenList.size()-1).getLine() + 1);
        } else {
            token.setLine(1);
        }
        tokenList.add(token);
        SintaticAnalyzer sintaticAnalyzer = new SintaticAnalyzer(tokenList);
        List<Error> errors = sintaticAnalyzer.getErrorLit();
        Node root = sintaticAnalyzer.getRoot();
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(root, identifierList);
        String saida = semanticAnalyzer.toString();
        if(response.isCorrect() && errors.size() == 0){
            String[] options = {"Ok"};
            writeCFile(saida);
            JOptionPane.showOptionDialog(null,
                    "Codigo aceito pelo compilador L2S",
                    "Aprovado!!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
        }
        else
        {
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
    };

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
            List<PhiniteAuthomata> authomataList = new AuthomatGen().AuthomatGen();
            try {
                reader = new BufferedReader(new FileReader(caminho));
                String line = reader.readLine();
                String code = "";
                while (line != null){
                    code += line + "\n";
                    line = reader.readLine();
                }
                txtTransitionFunction.setText(code);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };
}
