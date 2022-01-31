package pomodoro;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Registro implements Serializable{

    private GregorianCalendar fecha;
    String tipo;

    public Registro(int repeticiones) {

        fecha = new GregorianCalendar();

        if (repeticiones % 2 == 1) {
            tipo = "Concentración";
        } else if (repeticiones % 2 == 0) {

            if (repeticiones % 8 == 0) {

                tipo = "Descanso Largo";

            } else {

                tipo = "Descanso";
            }
        }
    }
    
    public Registro(int repeticiones, GregorianCalendar g) {

        fecha = g;

        if (repeticiones % 2 == 1) {
            tipo = "Concentración";
        } else if (repeticiones % 2 == 0) {

            if (repeticiones % 8 == 0) {

                tipo = "Descanso Largo";

            } else {

                tipo = "Descanso";
            }
        }
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public int getYear() {
        return fecha.get(GregorianCalendar.YEAR);
    }

    public int getMonth() {
        return fecha.get(GregorianCalendar.MONTH);
    }

    public int getDay() {
        return fecha.get(GregorianCalendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return fecha.get(GregorianCalendar.HOUR_OF_DAY);
    }

    public int getMin() {
        return fecha.get(GregorianCalendar.MINUTE);
    }

    @Override
    public String toString() {
        return "Fecha: " + this.getYear() + "/" + (this.getMonth()+1) + "/" + this.getDay() + " Hora: " + this.getHour() + ":" + this.getMin() + " Tipo: " + tipo;
    }
}
