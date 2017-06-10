package crossword;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lucas.tomasi/artur.tomasi
 */
public class Solver 
{   
    private State state;
    private char[][] board;
    private List<WordMatch> wordMatchs;
    
    public Solver()
    {
        wordMatchs = new ArrayList();
    }
    
    public void setWordMatch( List<WordMatch> wm )
    {
        wordMatchs = wm;
    }
    
    public void solve() throws Exception
    {
        List<String> words = Files.readAllLines(Paths.get("/home/lucas/Desktop/palavras/palavras-1.txt"));
        words.addAll(Files.readAllLines(Paths.get("/home/lucas/Desktop/palavras/palavras-2.txt")));
        List<String> w = Files.readAllLines(Paths.get("/home/lucas/Desktop/palavras/words.txt"));
        words.addAll(w);
        Collections.shuffle(words);       
        
        state = new State(board, wordMatchs, words);
        state.solve();
    }
    
    public void setBoard(char[][] board)
    {
        this.board = board;
    }
}