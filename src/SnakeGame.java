import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class SnakeGame extends JFrame implements ActionListener{

	private static final int width = 800;
	private static final int height = 600;
	private static final int col = 20;
	private static final int fil = 20;
	private Tablero tab;
	
	public SnakeGame(){
	
	      JMenuBar bar = new JMenuBar();
		  setJMenuBar(bar);
		  JMenu menu = new JMenu("Archivo");
		  bar.add(menu);
		  JMenuItem nuevo = new JMenuItem("Nuevo");
		  menu.add(nuevo);
		  nuevo.addActionListener(this);
		  nuevo.setActionCommand("nuevo");
		  tab = new Tablero(width, height, col, fil);
	      add(tab);
		  nuevo.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, 
					Event.CTRL_MASK));
		  setSize((int)(width*1.1), (int)(height*1.1));
		  setVisible(true);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
    	String s = e.getActionCommand();

        if (s.equals("nuevo") ) {
            JOptionPane.showMessageDialog(null, "Nuevo juego");
			remove(tab);
			tab = new Tablero(width, height, col, fil);
			add(tab);
        } 
    }
	
public static void main(String... args){
     SwingUtilities.invokeLater( new Runnable(){
	   public void run(){
	     SnakeGame sg = new SnakeGame();
	   }
	 });
   }

}