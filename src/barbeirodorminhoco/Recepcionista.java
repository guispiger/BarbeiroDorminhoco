/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barbeirodorminhoco;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guispiger
 */
public class Recepcionista implements Runnable {

    private final SalaEspera salaEspera;
    private final Barbeiro barbeiro;
    private int numero = 0;
    private boolean aberto;
    
    private static final Logger log = Logger.getLogger("logs");

    public Recepcionista(SalaEspera salaEspera, Barbeiro barbeiro) {
        this.salaEspera = salaEspera;
        this.barbeiro = barbeiro;
    }

    public void terminar() {
        aberto = false;
    }

    @Override
    public void run() {
        aberto = true;
        while (aberto) {
            try {
                numero++;
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 11));
                Cliente cliente = new Cliente(String.valueOf(numero), salaEspera, barbeiro);
                Thread tCliente = new Thread(cliente);
                tCliente.start();
                log.log(Level.FINE, "Cliente {0} chegou na barbearia", cliente);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
