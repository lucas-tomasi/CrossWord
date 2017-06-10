package crossword;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;


public class State 
{    
    private final char[][]        board;
    private final List<WordMatch> wordMatchs;
    private final List<String>    words;
    private final int[][]         letterUsage;
    
    public static final char BLANK = ' ';
    public static final char FILLED = '#';
   
    public State ( char[][] board, List<WordMatch> matchs, List<String> words ) 
    {
        this.board = board;
        this.wordMatchs = matchs;
        this.words = words;                  
        this.letterUsage = new int[board.length][board[0].length];
    }
    
    public void solve() 
    {    
        if ( solve(0) ) 
        {
            System.out.println("Solution found!");
        }
        else
        {
            System.out.println("No solution found!");
        }
    }
    
    private boolean solve ( int slot ) 
    {
        if ( slot == wordMatchs.size() ) 
        {
            print();
            return true;
        }
        
        Iterator<String> it =  words.iterator();
        
        while (it.hasNext()) 
        {
            String word = it.next();
             
            if ( match(word, wordMatchs.get(slot)) ) 
            {
                putWord(word, wordMatchs.get(slot));
                
                if ( solve(slot + 1) ) 
                {
                    return true;
                }
                
                removeWord(word, wordMatchs.get(slot));
            }
        }
        
        return false;
    }
    
    private boolean match( String word, WordMatch wordMatch ) 
    {
        if ( word.length() != wordMatch.getLength() ) 
        {
            return false;
        }
        
        // pega a posição inicial da palavra
        Point position = new Point( wordMatch.getStart() );
        
        for ( int i = 0; i < wordMatch.getLength(); i++ )
        {
            // Campo já está  preenchido com letra diferento ou é bloqueado
            if ( board[position.x][position.y] != BLANK && board[position.x][position.y] != String.valueOf(word.charAt(i)).toUpperCase().charAt(0) )
            {
                return false;    
            }
            
            // avança para proxima posição para preencher com a proxima letra
            position.x += wordMatch.getDirection().x;
            position.y += wordMatch.getDirection().y;
        }
        
        return true;
    }
    
    private void putWord ( String word, WordMatch wordMatch )
    {
        // pega a posição inicial da palavra
        Point position = new Point(wordMatch.getStart());
             
        for ( int i = 0; i < wordMatch.getLength(); i++ ) 
        {
            board[position.x][position.y] = String.valueOf(word.charAt(i)).toUpperCase().charAt(0);   
            letterUsage[position.x][position.y]++;
            position.x += wordMatch.getDirection().x;
            position.y += wordMatch.getDirection().y;
        }
    }
    
    private void removeWord ( String word, WordMatch wordMatch )
    {
        Point position = new Point(wordMatch.getStart());
        
        for ( int i = 0; i < wordMatch.getLength(); i++ )
        { 
            letterUsage[position.x][position.y]--;    
            
            if ( letterUsage[position.x][position.y] == 0 )
            {                                      
                board[position.x][position.y] = BLANK;
            }
            
            position.x += wordMatch.getDirection().x;
            position.y += wordMatch.getDirection().y;
        }
    }
    
    public void print() 
    {        
        for (char[] board1 : board) 
        {
            System.out.print("|");
            
            for (int col = 0; col < board1.length; col++) 
            {
                System.out.print(board1[col] + "|");
            }
            
            System.out.println();
        }        
        System.out.println();
    }
}