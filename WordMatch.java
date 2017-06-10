package crossword;

import java.awt.Point;

public class WordMatch 
{
    private Point start;
    private Point direction;
    private int   length;
    
    public static Point HORIZONTAL = new Point(0, 1);
    public static Point VERTICAL   = new Point(1, 0);

    public WordMatch(Point start, Point direction) 
    {
        this.start = start;
        this.direction = direction;
    }
    
    public WordMatch ( Point start, Point direction, int length ) 
    {
        this.start = start;
        this.direction = direction;
        this.length = length;
    }
    
    public Point getStart() 
    {
        return start;
    }
    
    public Point getDirection() 
    {
        return direction;
    }
    
    public int getLength() 
    {
        return length;
    }

    public void setStart(Point start) 
    {
        this.start = start;
    }

    public void setDirection(Point direction) 
    {
        this.direction = direction;
    }

    public void setLength(int length) 
    {
        this.length = length;
    }

    @Override
    public String toString() 
    {
        return "Pos: " + start.x + ", " +  start.y + " - " + length + " : " + ((direction.x == 1)? "VERTICAL":"HORIZONTAL");
    }
    
    
}