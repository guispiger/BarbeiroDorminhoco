/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package barbeirodorminhoco;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author guispiger
 */
public class Barbearia {

    private static final SalaEspera salaEspera = new SalaEspera(5);
    private static final Barbeiro barbeiro = new Barbeiro(salaEspera);
    private static final Recepcionista recepcionista = new Recepcionista(salaEspera, barbeiro);

    private static final Thread tBarbeiro = new Thread(barbeiro);
    private static final Thread tRecepcionista = new Thread(recepcionista);
    
    private static final Logger log = Logger.getLogger("logs");
    private static ConsoleHandler ch = null;

    public static void abrir() {
        tBarbeiro.start();
        tRecepcionista.start();
        log.log(Level.INFO, "Barbearia aberta.");
    }

    public static void fechar() {
        recepcionista.terminar();
        barbeiro.terminar();
        log.log(Level.INFO, "Fechando a barbearia.");
        try {
            tRecepcionista.join();
            tBarbeiro.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        log.log(Level.INFO, "Barbearia Fechada");
    }

    public static void aguardar(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());
        log.setLevel(Level.ALL);
        log.addHandler(ch);
        
        abrir();

        aguardar(1000 * 60 * 1);

        fechar();
    }

}
