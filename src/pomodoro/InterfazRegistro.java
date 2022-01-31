package pomodoro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InterfazRegistro extends JFrame {

    private JPanel filtros, panel;
    private JScrollPane panelDatos;
    private JTextArea areaTexto;
    private JComboBox year, month, day;
    private String lista;

    public InterfazRegistro() {

        initWindow();
        initPanelCenter();
        initPanelNorth();

    }

    private void initWindow() {

        this.setSize(450, 400);
        this.setTitle("Historial de Registros");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new EventoVentana());
        this.getContentPane().setBackground(Interfaz.themeColor);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Interfaz.themeColor);
        setMinimumSize(new Dimension(450, 400));
        this.add(panel);
    }

    private void initPanelCenter() {
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(Interfaz.themeColor);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 18));
        panelDatos = new JScrollPane(areaTexto);
        panel.add(panelDatos, BorderLayout.CENTER);

        if (!Datos.getAllRegister().isEmpty()) {
            areaTexto.setText(Datos.toRevesStringList(Datos.getAllRegister()));
        }
    }

    private void initPanelNorth() {

        filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.setBackground(Interfaz.themeColor);
        panel.add(filtros, BorderLayout.NORTH);
        JLabel title = new JLabel("Filtrar por");
        title.setForeground(Interfaz.themeColorSecundary);
        filtros.add(title);

        JLabel yearLabel = new JLabel("Año:");
        yearLabel.setForeground(Interfaz.themeColorSecundary);
        filtros.add(yearLabel);
        year = new JComboBox();
        year.setBackground(Interfaz.themeColor.brighter());
        year.setForeground(Interfaz.themeColorSecundary);
        configuraAgno();

        filtros.add(year);

        JLabel monthLabel = new JLabel("Mes:");
        monthLabel.setForeground(Interfaz.themeColorSecundary);
        filtros.add(monthLabel);

        String meses[] = {"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        month = new JComboBox(meses);
        month.setBackground(Interfaz.themeColor.brighter());
        month.setForeground(Interfaz.themeColorSecundary);
        month.setSelectedItem(null);
        month.setEnabled(false);
        filtros.add(month);

        JLabel dayLabel = new JLabel("Día");
        dayLabel.setForeground(Interfaz.themeColorSecundary);
        filtros.add(dayLabel);
        day = new JComboBox();
        day.setBackground(Interfaz.themeColor.brighter());
        day.setForeground(Interfaz.themeColorSecundary);
        day.setEnabled(false);
        month.addActionListener((ActionEvent e) -> {
            configuraDias();
            if (month.getSelectedItem() != null) {
                if (month.getSelectedItem().equals("Todos") && year.getSelectedItem() != null) {
                    areaTexto.setText(Datos.toRevesStringList(Datos.getByYear((int) year.getSelectedItem())));
                    day.setEnabled(false);
                } else {
                    areaTexto.setText(Datos.toRevesStringList(Datos.getByMonth((int) year.getSelectedItem(), month.getSelectedIndex()-1)));
                    day.setEnabled(true);
                }
            }
        });

        year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (year.getSelectedItem().equals("Todos")) {
                    month.setSelectedIndex(-1);
                    month.setEnabled(false);
                    day.setEnabled(false);
                    areaTexto.setText(Datos.toRevesStringList(Datos.getAllRegister()));
                } else {
                    areaTexto.setText(Datos.toRevesStringList(Datos.getByYear((int) year.getSelectedItem())));
                    month.setEnabled(true);
                    month.setSelectedIndex(0);
                    day.setEnabled(true);
                }
            }
        });
        filtros.add(day);
    }

    private void configuraAgno() {

        int agnoActual = GregorianCalendar.getInstance().get(Calendar.YEAR);
        int agnoInicial = Datos.getYearFirstRegister();

        year.addItem("Todos");
        for (int i = agnoActual; i >= agnoInicial; i--) {
            year.addItem(i);
        }
    }

    private void configuraDias() {

        day.removeAllItems();

        if (month.getSelectedItem() == null) {
            return;
        }

        switch ((String) month.getSelectedItem()) {

            case "Enero","Marzo","Mayo","Julio","Agosto","Octubre","Diciembre" -> {
                for (int i = 1; i <= 31; i++) {
                    day.addItem(i);
                }
            }
            case "Abril","Junio","Septiembre","Noviembre" -> {
                for (int i = 1; i <= 30; i++) {
                    day.addItem(i);
                }
            }
            case "Febrero" -> {
                for (int i = 1; i <= 28; i++) {
                    day.addItem(i);
                }
            }

            case "Todos" -> {
                //No pone dias
            }
            default -> {
            }
        }
        day.setSelectedIndex(-1);
    }

    private class EventoVentana extends WindowAdapter {

        @Override
        public void windowClosed(WindowEvent e) {

            Interfaz.botonRegistro.setEnabled(true);
            Interfaz.botonInicio.setEnabled(true);
            Interfaz.botonParar.setEnabled(false);
        }
    }
}
