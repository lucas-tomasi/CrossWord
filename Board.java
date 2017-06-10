package crossword;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Board extends javax.swing.JFrame 
{
    private final int x;
    private final int y;
    private final Button[][] fields;
    private final List<WordMatch> wordMatchs = new ArrayList();        
    private final Button btnSolver = new Button();
    private final Button btnReset  = new Button();
    private final Button btnClose  = new Button();
    
    public Board( int x, int y ) throws Exception
    {
        this.x = x;
        this.y = y;
        fields = new Button[x][y];
        
        super.setExtendedState(MAXIMIZED_BOTH);
        
        super.setTitle("CrossWord - Tomasis");
        
        initComponents();
        defineBoard();
    }
    
    private void defineBoard() throws Exception
    {
        int posX, posY = -50;
        
        if( x > 0 && y > 0 )
        {
            for (int i = 0; i < x; i++) 
            {
                posY += 60;
                posX = -50;
                for (int j = 0; j < y; j++) 
                {
                    posX += 60;
                    fields[i][j] = makeButton(posX,posY);
                }
            }
            
            makeActions(posY);
            
            repaint();
        }
        else
        {
            throw new Exception("Tamanho inválido");
        }
    }
    
    private void onReset()
    {
        for (int horizontal = 0; horizontal < this.x; horizontal++) 
        {
            for (int vertical = 0; vertical < this.y; vertical++) 
            {
                fields[horizontal][vertical].setBackground(Color.WHITE);
                fields[horizontal][vertical].setLabel(""); 
                fields[horizontal][vertical].setEnabled(true);
            }
        }
        
        btnSolver.setEnabled(true);
        
        repaint();
    }
    
    private void block()
    {
        for (int horizontal = 0; horizontal < this.x; horizontal++) 
        {
            for (int vertical = 0; vertical < this.y; vertical++) 
            {
                fields[horizontal][vertical].setEnabled(false);
            }
        }
        
        btnSolver.setEnabled(false);
        btnReset.setEnabled(false);
        btnClose.setEnabled(false);
        
        repaint();
    }
    
    private void onClose()
    {
        System.exit(0);
    }
    
    private void onSolver() throws Exception
    {
        Long start = System.currentTimeMillis();
        
        block();
        
        wordMatchs.clear();
        
        defineWordMatchs(x,y,WordMatch.HORIZONTAL);
        defineWordMatchs(y,x,WordMatch.VERTICAL);
            
        char[][] board = getBoard();
        
        Solver s = new Solver();
        s.setWordMatch(wordMatchs);
        s.setBoard(board);
        s.solve();
        
        printSolution(board);
        
        Long end = System.currentTimeMillis();
        String time = ( ( end - start ) /60  < 60 ) ?  ( ( end - start ) / 60 ) + " segundos!": ( ( end - start ) / 60 / 60 ) + " minutos!";
        
        btnReset.setEnabled(true);
        btnClose.setEnabled(true);
        repaint();
        JOptionPane.showMessageDialog(null, "Achamos uma solução em " + time);
    }

    private void printSolution(char[][] board)
    {
        for (int horizontal = 0; horizontal < this.x; horizontal++) 
        {
            for (int vertical = 0; vertical < this.y; vertical++) 
            {
                String letter = String.valueOf(board[horizontal][vertical]);
                fields[horizontal][vertical].setLabel((!letter.equalsIgnoreCase(" ")?letter:"A"));
            }
        }
    }
    
    private char[][] getBoard()
    {
        char[][] board = new char[this.x][this.y];
        
        for (int horizontal = 0; horizontal < this.x; horizontal++) 
        {
            for (int vertical = 0; vertical < this.y; vertical++) 
            {
                board[horizontal][vertical] = (fields[horizontal][vertical].getLabel().equalsIgnoreCase("#"))? State.FILLED : State.BLANK;
            }
        }
        
        return board;
    }
    
    private void defineWordMatchs(int a, int b,Point align)
    {
        for (int horizontal = 0; horizontal < a; horizontal++) 
        {
            int len = 0;
            boolean reading = false, filled;
            
            for (int vertical = 0; vertical < b; vertical++)
            {
                if(align == WordMatch.HORIZONTAL)
                {
                    filled = fields[horizontal][vertical].getLabel().equalsIgnoreCase("#");
                }
                else
                {
                    filled = fields[vertical][horizontal].getLabel().equalsIgnoreCase("#");
                }
                
                if( !filled ) len++;
                 
                if( !filled && !reading )
                {
                    WordMatch wm;
                    if(align == WordMatch.HORIZONTAL)
                    {
                        wm = new WordMatch(new Point(horizontal, vertical), align);
                    }
                    else
                    {
                        wm = new WordMatch(new Point(vertical,horizontal), align);
                    }
                    wordMatchs.add(wm);
                    reading = true;
                }
                else if( filled )
                {
                    // Não comparar letra por letra
                    if(len>1)
                    {
                        wordMatchs.get(wordMatchs.size()-1).setLength(len);
                    }
                    else if( reading )
                    {
                        wordMatchs.remove(wordMatchs.size()-1);
                    }
                    
                    reading = false;
                    len = 0;
                }
                
                // é o ultimo
                if( vertical == (b-1) )
                {
                    
                    if(len>1)
                    {
                        wordMatchs.get(wordMatchs.size()-1).setLength(len);
                    }
                    else if(!filled)
                    {
                        wordMatchs.remove(wordMatchs.size()-1);
                    }
                }
            }
        }
    }
    
    private void makeActions(int posY)
    {
        btnSolver.setLabel("Resolver");
        btnReset.setLabel("Limpar");
        btnClose.setLabel("Fechar");
        btnSolver.setLocation(10, posY+60);
        btnReset.setLocation(95, posY+60);
        btnClose.setLocation(185, posY+60);
        btnSolver.setSize(75,30);
        btnReset.setSize(75,30);
        btnClose.setSize(75,30);
        
        btnSolver.addActionListener((ActionEvent e) -> {
            try 
            {
                onSolver();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Houve um erro!\n"+ex.getMessage());
            }
        });
        
        btnReset.addActionListener((ActionEvent e) -> {
            onReset();
        });
                
        btnClose.addActionListener((ActionEvent e) -> {
            onClose();
        });
        
        add(btnSolver);
        add(btnReset);
        add(btnClose);
    }
    
    private Button makeButton(int x, int y)
    {
        Button button = new Button();
        button.setSize(50,50);
        button.setLocation(x,y);
        button.setLabel("");
        button.setForeground(Color.WHITE);
        button.setFont(new Font("", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.addActionListener((ActionEvent e) -> {
            onClick(button);
        });
        
        add(button);
        
        return button;
    }
    
    private void onClick(Button button)
    {
        String label = (button.getLabel().equalsIgnoreCase("#"))? "" : "#";
        Color  color = (label.equalsIgnoreCase("#"))? Color.BLACK : Color.WHITE;
        button.setBackground(color);
        button.setLabel(label);
    }
    
    private void initComponents() 
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }
}
