
package pomodoro;

import java.io.EOFException;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Pomodoro {

    public static void main(String[] args) {
        
        Interfaz programa=new Interfaz();
        programa.setVisible(true);
        programa.setAlwaysOnTop(true);
        
        //Al abrir el programa debe bajar todos los datos
        try{
            
            Datos.setAllRegister(Datos.loadData());
            
        }catch(EOFException e){
            
            //No hacer nada
            JOptionPane.showMessageDialog(null, "EOFException");
            
        }catch(IOException e){
            
            JOptionPane.showMessageDialog(null, "No se han podido cargar los registros");
            
        }catch(ClassNotFoundException e){
            
        }
    }
    
}
