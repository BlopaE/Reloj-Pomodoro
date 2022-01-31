package pomodoro;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.Timer;

public class Temporizador {

    private int seg, min, repeticiones;
    private boolean isWorking;
    private Timer timer;
    //AQUI ESTAN LOS MINUTOS QUE TIENE CADA SECCION
    private static final int WORK = 25, BREAK = 5, MORE_BREAK = 30;
    private Registro registro;

    public Temporizador(ActionListener acciones) {
        min = this.WORK;
        seg = 0;
        isWorking = true;
        timer = new Timer(1000, acciones);
    }

    public void iniciarTimer() {
        repeticiones = 1;
        timer.start();
    }

    public void pararTimer() {
        timer.stop();
        min = this.WORK;
        seg = 0;

    }

    public void run() {

        if (!isWorking) {
            seg = 59; //Asigna automaticamente el periodo de descanso
            if (repeticiones % 2 == 1) {
                min = this.WORK - 1;
            } else if (repeticiones % 2 == 0) {

                if (repeticiones % 8 == 0) {

                    min = this.MORE_BREAK - 1;
                } else {
                    min = this.BREAK - 1;
                }
            }
            isWorking = true; //Vuelve a "trabajar" solo para hacer las comprobaciones necesarias
        }

       // System.out.println(min + ":" + seg + " rep: " + repeticiones);

        if (seg > 0) {
            seg--;
        } else {
            if (min > 0) {
                min--;
                seg = 59;
            } else {
                //aqui termina un tiempo
                playSound();
                addRegistro(repeticiones);
                repeticiones++;
                isWorking = false;
            }
        }
    }

    public String generarTiempo() {

        String tiempo = (min <= 9 ? "0" : "") + min + ":" + (seg <= 9 ? "0" : "") + seg;
        return tiempo;
    }

    public String generarSubtitulo() {

        if (repeticiones % 2 == 1) {
            return "¡Concentrate!";

        } else if (repeticiones % 2 == 0) {

            if (repeticiones % 8 == 0) {

                return "Descanso largo";
            }
            return "Descanso";
        } 
    //se supone que nunca deberia regresar vacio
     return " ";    
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/alarma.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    public void addRegistro(int repeticiones){
        
        Registro registro = new Registro(repeticiones);
        Datos.addReg(registro);
    }
}
