package pomodoro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Interfaz extends JFrame {
    
    private JPanel panel,panelCentral,panelSouthPlay;
    private JLabel titulo, subtitulo, instrucciones;
    private JLabel tiempo;
    public static JButton botonInicio, botonParar;
    public static JButton botonRegistro, botonSettings;
    private InterfazRegistro registros;
    public static Color themeColor=new Color(55,55,55);
    public static Color themeColorSecundary=new Color(255,255,255);
    private ActionListener acciones = new ActionListener() {//Clase anonima

        @Override
        public void actionPerformed(ActionEvent e) { //Aqui va lo que hará el timer
            
            temporizador.run();   
            //Método que actualiza la etiqueta tiempo
            actualizarLabel();

        }
    };
     
     private Temporizador temporizador=new Temporizador(acciones);


    public Interfaz() {

        crearVentana();
        crearPanel();
        crearEtiquetaTitulo();
        crearBotones();
        //crearEtiquetaInstrucciones();
        crearTiempo(100);
        crearEtiquetaSubtitulo();
    }

    public void crearVentana() {
        
     
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Pomodoro");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/resources/pomodoro.png"));
        setResizable(true);
        setMinimumSize(new Dimension(300,300));
        addWindowListener(new EventoVentana());
        addComponentListener(new EventoVentanaResizable());
    }

    public void crearPanel() {

        //Crea el panel principal con disposicion border Layout
        panel = new JPanel();
        panel.setLayout(new BorderLayout(20,20)); 
        panel.setBackground(themeColor); //color oscuro
        this.add(panel);
        
        //crea el panel central con disposicion gridLayout
        panelCentral=new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setBackground(themeColor);
        panel.add(panelCentral,BorderLayout.CENTER);
        
    }

    public void crearEtiquetaTitulo() {

        titulo = new JLabel("RELOJ POMODORO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.PLAIN, 18));
        titulo.setForeground(themeColorSecundary);
        
        panel.add(titulo,BorderLayout.NORTH);
    }

    public void crearEtiquetaSubtitulo() {

        subtitulo = new JLabel("¿Listo?", SwingConstants.CENTER);
        subtitulo.setOpaque(true);
        subtitulo.setBackground(themeColor);
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 38));
        subtitulo.setForeground(themeColorSecundary);
        subtitulo.setSize(75,200);
        panelCentral.add(subtitulo,BorderLayout.SOUTH);
    }
    
    @Deprecated
    public void crearEtiquetaInstrucciones(){
        
        String texto="<html><body>Un Pomodoro es una sesión de 25 minutos de trabajo más 5 de descanso "
                + "Al terminar 4 Pomodoros podrá descansar 30 minutos, puede consultar sus"
                + " resultados en la pestaña registro, si detiene el temporizador se "
                + "reiniciará la sesión</body></html>";
        
        instrucciones=new JLabel(texto,SwingConstants.LEFT);
        instrucciones.setFont(new Font("Arial",Font.PLAIN,12));
        instrucciones.setForeground(new Color(250,250,250));
        instrucciones.setBounds(5,30,390,60);
        panel.add(instrucciones);
    }
 
    
    public void crearBotones(){
        
        JPanel panelSouth=new JPanel(new BorderLayout());
        panelSouth.setBackground(themeColor);
        
        Color c = themeColor;
        
        
        
        panelSouthPlay=new JPanel(); //panel para los botones con distribucion FlowLayout
        panelSouthPlay.setBackground(c);
        
        
        //Boton Play
        ImageIcon play=new ImageIcon("src/resources/play.png");
        botonInicio=new JButton();
        botonInicio.setSize(50, 50);
        botonInicio.setIcon(new ImageIcon(play.getImage().getScaledInstance(botonInicio.getWidth(), botonInicio.getHeight(), Image.SCALE_SMOOTH)));
        botonInicio.setBorder(null);
        botonInicio.setOpaque(true);
        botonInicio.setBackground(c);
        botonInicio.addMouseListener(new EventoBotones(botonInicio));
        panelSouthPlay.add(botonInicio);
        
        //Boton stop
        ImageIcon stop=new ImageIcon("src/resources/stop.png");
        botonParar=new JButton();
        botonParar.setSize(50, 50);
        botonParar.setIcon(new ImageIcon(stop.getImage().getScaledInstance(botonParar.getWidth(), botonParar.getHeight(), Image.SCALE_SMOOTH)));
        botonParar.setBorder(null);
        botonParar.setOpaque(true);
        botonParar.setBackground(c);
        botonParar.setEnabled(false);
        botonParar.addMouseListener(new EventoBotones(botonParar));
        panelSouthPlay.add(botonParar);
        
        //Boton registro
        ImageIcon reg=new ImageIcon("src/resources/register.png");
        botonRegistro=new JButton();
        botonRegistro.setSize(40,40);
        botonRegistro.setIcon(new ImageIcon(reg.getImage().getScaledInstance(botonRegistro.getWidth(), botonRegistro.getHeight(), Image.SCALE_SMOOTH)));
        botonRegistro.setBorder(null);
        botonRegistro.setOpaque(true);
        botonRegistro.setBackground(c);
        botonRegistro.addMouseListener(new EventoBotones(botonRegistro));
        
        botonRegistro.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (botonRegistro.isEnabled()){
                    registros=new InterfazRegistro();
                    registros.setVisible(true);
                    botonInicio.setEnabled(false);
                    botonRegistro.setEnabled(false);
                }
            }
        });        
        
        //botonConfig
        ImageIcon settings=new ImageIcon("src/resources/settings.png");
        botonSettings=new JButton();
        botonSettings.setSize(40,40);
        botonSettings.setIcon(new ImageIcon(settings.getImage().getScaledInstance(botonSettings.getWidth(), botonSettings.getHeight(), Image.SCALE_AREA_AVERAGING)));
        botonSettings.setBorder(null);
        botonSettings.setOpaque(true);
        botonSettings.setBackground(c);
        botonSettings.addMouseListener(new EventoBotones(botonSettings));
        
        botonSettings.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });
        
        panelSouth.add(botonSettings,BorderLayout.WEST);
        panelSouth.add(panelSouthPlay, BorderLayout.CENTER);
        panelSouth.add(botonRegistro,BorderLayout.EAST);
        
        panel.add(panelSouth,BorderLayout.SOUTH);
        
        
    }
    
    private class EventoBotones extends MouseAdapter{
        
        private JButton boton;
        private Color c = panelSouthPlay.getBackground();
        
        public EventoBotones(JButton boton){
            this.boton=boton;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            
            if (e.getSource().equals(botonInicio) && botonInicio.isEnabled()){
                
                temporizador.iniciarTimer();
                botonInicio.setEnabled(false);
                botonParar.setEnabled(true);
                botonRegistro.setEnabled(false);
                
            }else if (e.getSource().equals(botonParar) && botonParar.isEnabled()){
                
                temporizador.pararTimer();
                botonParar.setEnabled(false);
                botonInicio.setEnabled(true);
                botonRegistro.setEnabled(true);
                actualizarLabel();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
            if (boton.isEnabled()){
                boton.setBackground(panelSouthPlay.getBackground().brighter());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
            boton.setBackground(themeColor);
        }
    }
    public void crearTiempo(int fontSize){
        
        tiempo=new JLabel("00:00",SwingConstants.CENTER);
        tiempo.setFont(new Font("Tahoma",Font.PLAIN,fontSize));
        tiempo.setForeground(themeColorSecundary);
        
        tiempo.setOpaque(true);
        tiempo.setBackground(themeColor);
        
        //tiempo.setSize(20, 100);
        //tiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(tiempo,BorderLayout.CENTER);
    }
    
    public void actualizarLabel(){
       
        tiempo.setText(temporizador.generarTiempo());
        subtitulo.setText(temporizador.generarSubtitulo());
    }
    
    private class EventoVentana extends WindowAdapter{

        @Override
        public void windowClosing(WindowEvent e) {
            
            try{
                Datos.saveData();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private class EventoVentanaResizable extends ComponentAdapter{

        @Override
        public void componentResized(ComponentEvent e) {
            
            //Toma el tamaño del ancho de la ventana y lo divide entre 3, lo castea a int y ese es el nuevo tamaño de la fuente
            int size=(int)e.getComponent().getSize().getWidth()/3;
            tiempo.setFont(new Font("Tahoma",Font.PLAIN,size));
        }
        
        
    }
}
    
