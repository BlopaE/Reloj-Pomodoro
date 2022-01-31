package pomodoro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class Datos implements Serializable {

    private static File file = new File("src/resources/data.dat");
    
    //Lista que se guarda en un archivo
    private static ArrayList<Registro> registros = new ArrayList<Registro>();

    public static void setAllRegister(ArrayList<Registro> r) {
        registros = r;
    }

    public static ArrayList<Registro> getAllRegister() {
        return registros;
    }

    public static void addReg(Registro reg) {
        registros.add(reg);
    }

    public static ArrayList<Registro> getByYear(int year) {

        ArrayList<Registro> registroPorAgno = new ArrayList<Registro>();

        for (Registro r : registros) {

            if (r.getYear() == year) {
                registroPorAgno.add(r);
            }
        }
        return registroPorAgno;
    }

    public static ArrayList<Registro> getByMonth(int year, int month) {

        ArrayList<Registro> registroPorMes = new ArrayList<Registro>();

        for (Registro r : registros) {
            if (r.getYear() == year && r.getMonth() == month) {
                registroPorMes.add(r);
            }
        }
        return registroPorMes;
    }

    public static ArrayList<Registro> getByDay(int year, int month, int day) {

        ArrayList<Registro> registroPorDia = new ArrayList<Registro>();

        for (Registro r : registros) {

            if (r.getYear() == year && r.getMonth() == month && r.getDay() == day) {
                registroPorDia.add(r);
            }
        }
        return registroPorDia;
    }
    
    public static String toStringList(ArrayList<Registro> ar){
        
        String list="";
        for(Registro reg:ar){
            list+=reg.toString()+"\n";
        }
        return list;
    }
    
    public static String toRevesStringList(ArrayList<Registro> ar){
        String list="";
        //Recorre la lista del final al incio
        for(int i=ar.size()-1;i>=0;i--){
            
            list+=ar.get(i).toString()+"\n";
        }
        return list;
    }
    
    public static int getYearFirstRegister(){
        
        if(!registros.isEmpty()){
            return registros.get(0).getYear();
        }else{
            return GregorianCalendar.getInstance().get(Calendar.YEAR);
        }
    }
    
    //Métodos para cargar y guardar los registros
    public static void saveData() throws FileNotFoundException, IOException {

        if (!file.exists()) {
            file.createNewFile();
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(registros);
    }

    public static ArrayList<Registro> loadData() throws FileNotFoundException, IOException, ClassNotFoundException {

        if (!file.exists()) {
            file.createNewFile();
        } else {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Registro> aux = new ArrayList<>();
            aux = (ArrayList<Registro>) ois.readObject();
            return aux;
        }
        return new ArrayList<Registro>();
    }
    
    public Object[][] generateTable(ArrayList<Registro> ar){
        
        Object [][] table=new Object[ar.size()][3];
        
        for (int i=0;i<ar.size();i++){
            
            table[i][0]=ar.get(i).getYear()+"/"+ar.get(i).getMonth()+"/"+ar.get(i).getDay();
            table[i][1]=ar.get(i).getHour()+":"+ar.get(i).getMin();
            table[i][2]=ar.get(i).getTipo();   
        }
        return table;
    }
}
