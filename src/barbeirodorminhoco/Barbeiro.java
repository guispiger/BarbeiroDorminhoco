/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barbeirodorminhoco;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guispiger
 */
public class Barbeiro implements Runnable {

    private Cliente clienteAtual;
    private final SalaEspera salaEspera;
    private final Lock lock = new ReentrantLock();
    private final Condition esperaCliente = lock.newCondition();
    private final Random r = new Random();
    private boolean aberto;
    private boolean dormindo;
    private static final Logger log = Logger.getLogger("logs");
    
    public Barbeiro(SalaEspera salaEspera) {
        this.salaEspera = salaEspera;
    }

    public void terminar() {
        aberto = false;
    }

    @Override
    public void run() {      
        aberto = true;
        dormindo = false;
        int tempo;

        lock.lock();
        try {
            while (clienteAtual == null && (aberto || !salaEspera.estaVazia())) {
                tempo = r.nextInt(11) + 1;
                if (salaEspera.estaVazia()) {
                    dormindo = true;
                    log.log(Level.FINE,"Barbeiro dormindo");
                    esperaCliente.await();
                } else {
                    dormindo = false;
                    clienteAtual = salaEspera.proximoCliente();
                    log.log(Level.FINE, "Barbeiro est\u00e1 cortando o cabelo do Cliente {0}", clienteAtual);
                    Thread.sleep(tempo);
                    log.log(Level.FINE, "Corte de cabelo do Cliente {0} finalizado.", clienteAtual);
                    clienteAtual.cortePronto();
                    clienteAtual = null;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void acordarBarbeiro() {
        lock.lock();
        try {
            esperaCliente.signal();
        } finally {
            lock.unlock();
        }
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    public boolean isDormindo() {
        return dormindo;
    }

}
