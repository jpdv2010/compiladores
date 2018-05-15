package Views;

import Analyzers.LexicalAnalyzer;
import Analyzers.SintaticAnalyzer;
import Java.Entitys.Identifier;
import Java.Entitys.PhiniteAuthomata;
import Java.Entitys.State;
import Java.Entitys.Token;
import Managers.AuthomatGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by joaop on 05/09/2017.
 */
public class TestPanel extends JFrame{
    JTextField txtTransitionFunction = new JTextField();
    JButton btnGenerateAuthomata = new JButton();
    List<State> stateList=new ArrayList<State>();
    ActionListener pressGenerateAuthomata=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<PhiniteAuthomata> authomataList=new AuthomatGen().AuthomatGen();
            String code = txtTransitionFunction.getText();
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(code, authomataList);
            boolean response = lexicalAnalyzer.Analyze();
            String lexem = lexicalAnalyzer.getLexem();
            List<Token> tokenList = lexicalAnalyzer.getTokenList();
            Token token = new Token();
            token.setTokenClass("$");
            token.setImage("$");
            tokenList.add(token);
            List<Identifier> identifiers = lexicalAnalyzer.getIdentifiers();
            SintaticAnalyzer sintaticAnalyzer = new SintaticAnalyzer(tokenList);
            List<Error> errors = sintaticAnalyzer.getErrorLit();


            if(response && errors.size() == 0){
                String[] options = {"Ok"};
                JOptionPane.showOptionDialog(null,
                        lexem,
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
                if(!response) message += "erro lexico!";
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
        }
    };


    public TestPanel(java.util.List<State> stateList){
        this.stateList=stateList;
        /*super("O que está escrito aqui nao importa, não vai aparecer!!");*/
        Container c=getContentPane();
        c.setLayout(new BorderLayout());

        btnGenerateAuthomata=new JButton("Gerar Automato");
        btnGenerateAuthomata.addActionListener(pressGenerateAuthomata);
        c.add(btnGenerateAuthomata,BorderLayout.SOUTH);


        txtTransitionFunction=new JTextField(5);
        txtTransitionFunction.setText("aaabbb");
        this.add(txtTransitionFunction);

        setSize(170,85);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
