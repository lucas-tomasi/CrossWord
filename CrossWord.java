package crossword;

import javax.swing.JOptionPane;

/**
 *
 * @author lucas
 */
public class CrossWord 
{
    public static void main(String[] args) throws Exception
    {
        Object ox = JOptionPane.showInputDialog("Linhas?");
        Object oy = JOptionPane.showInputDialog("Colunas?");
        
        try 
        {
            Integer x = Integer.parseInt(String.valueOf(ox));
            Integer y = Integer.parseInt(String.valueOf(oy));
            
            Board board = new Board(x,y);
            board.setVisible(true);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Tem erro!\n"+e.getMessage());
        }
    }
}
