import java.awt.event.*;
import javax.media.opengl.*;
import javax.swing.*;
import com.jogamp.opengl.util.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.event.*;
public class GLSnake extends JFrame implements ActionListener{

	private static JFileChooser choose;
    private static final int width = 800;
	private static final int height = 600;
	private static final int col = 20;
	private static final int fil = 20;
	private static  TableroGL t1;
	private static 	GLCapabilities cap;
	private static JLabel mar ;
	JMenuBar bar;
	JMenu menu ;
	JMenuItem nuevo, abrir, guardar, salir, pausar;
	public static void main(String... args){
	 try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
		GLSnake snake = new GLSnake();
	}
	
	public GLSnake(){
	  super("GLSnake");
	  SwingUtilities.invokeLater(new Runnable(){
	    public void run(){
		choose = new JFileChooser();
		bar = new JMenuBar();
		setJMenuBar(bar);
	    menu = new JMenu("Archivo"); 
		bar.add(menu);
		pausar = new JMenuItem("Pausar");
		nuevo = new JMenuItem("Nuevo");
		abrir = new JMenuItem("Abrir");
		guardar = new JMenuItem("Guardar");
		salir = new JMenuItem("Salir");
		menu.add(nuevo);
		menu.add(abrir);
		menu.add(guardar);
		menu.add(pausar);
		menu.add(salir);
		nuevo.setActionCommand("nuevo");
	    guardar.setActionCommand("guardar");
	    salir.setActionCommand("salir");/*
		menu.addMenuListener(new MenuListener() {

        public void menuSelected(MenuEvent e) {
			t1.setEstado("pausa");
        }

        public void menuDeselected(MenuEvent e) {
            t1.setEstado("Juego");
        }

        public void menuCanceled(MenuEvent e) {
			t1.setEstado("Juego");
        }
    });*/
	    pausar.setActionCommand("pausar");
		abrir.setActionCommand("abrir");
		nuevo.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, 
					Event.CTRL_MASK));
					
		abrir.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, 
					Event.CTRL_MASK));
					
		guardar.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_G, 
					Event.CTRL_MASK));
	        
					
		salir.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, 
					Event.CTRL_MASK));
	        
				
		pausar.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_P, 
					Event.CTRL_MASK));
	        }
		});
		
		
		GLProfile profile = GLProfile.getDefault();
		cap = new GLCapabilities(profile);
		cap.setDoubleBuffered(true);
		mar = new JLabel();
		mar.setText("1:");
		t1 = new TableroGL(cap, width, height, col, fil, mar);
		TableroGL.canv = t1;
		setResizable(false);
		t1.setFocusable(true);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout() );
		panel.add(t1, BorderLayout.CENTER);
		panel.add(mar, BorderLayout.SOUTH);
		add(panel);
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nuevo.addActionListener(this);
		pausar.addActionListener(this);
		abrir.addActionListener(this);
		guardar.addActionListener(this);
	    salir.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
    	String s = e.getActionCommand();

        if (s.equals("nuevo") ) {
            JOptionPane.showMessageDialog(this, "Nuevo juego");
			remove(t1);
			t1 = new TableroGL(cap, width, height, col, fil, mar);
			add(t1);
        } 
		else if ( s.equals("abrir") ){
			 FileNameExtensionFilter f = new FileNameExtensionFilter(
					"Snake games", "s");
			 choose.setFileFilter(f);
			 
		     if(choose.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				SGSnake snake = Prueba.read( choose.getSelectedFile().getAbsolutePath() );
				t1.setGame(snake);
			 }
		}
		else if ( s.equals("guardar") ){
		     SGSnake snake = t1.getGame();
			 FileNameExtensionFilter f = new FileNameExtensionFilter(
					"Snake games", "s");
			 choose.setFileFilter(f);
			 choose.setDialogTitle("Especificar nombre de archivo");    
			 if (choose.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				Prueba.write(choose.getSelectedFile().getAbsolutePath()+".s", snake );
			}
		}
		else if ( s.equals("salir") ){
			     if ( JOptionPane.showConfirmDialog(null, "Cerrar aplicacion", "Estas seguro", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				    System.exit(0);
				 }
		}/*
		else if ( s.equals("pausar") ){
			 if ( t1.getEstado().equalsIgnoreCase("Pausa") ){
                t1.setEstado("Juego");
			 }			 
			 else{
				t1.setEstado("Pausa");
			 }
		}*/
		
	}
}
    
	
	
