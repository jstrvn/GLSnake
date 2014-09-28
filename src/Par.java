import java.util.*;

public class Par implements java.io.Serializable{
  private static final long serialVersionUID = 4006326223537L;
   
  private int[] coord;
  
  public String toString(){
    return "x:" + coord[0] + ", y:" + coord[1];
  }
  
  public Par(int x, int y){
      coord = new int[]{x, y};
  }
  
  public int getX(){
    return coord[0];
  }
  
  public int getY(){
	return coord[1];
  }

}