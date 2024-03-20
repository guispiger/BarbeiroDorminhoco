/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barbeirodorminhoco;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guispiger
 */
public class Cliente implements Runnable {

    private final String nome;
    private final SalaEspera salaEspera;
    private final Barbeiro barbeiro;
    private final Lock lock = new ReentrantLock();
    private final Condition cortePronto = lock.newCondition();
    private boolean pronto;

    private static final Logger log = Logger.getLogger("logs");

    public Cliente(String nome, SalaEspera salaEspera, Barbeiro barbeiro) {
        this.nome = nome;
        this.salaEspera = salaEspera;
        this.barbeiro = barbeiro;
    }

    @Override
    public void run() {
        pronto = false;

        if (salaEspera.clienteChega(this)) {
            log.log(Level.FINE, "Cliente {0} na sala de espera", nome);
            lock.lock();
            try {
                while (!pronto) {
                    if (barbeiro.isDormindo()) {
                        barbeiro.acordarBarbeiro();
                        cortePronto.await();
                    } else {
                        cortePronto.await();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }

        } else {
            log.log(Level.FINE, "Sala cheia, cliente {0} foi embora", nome);
        }
    }

    public void cortePronto() {
        lock.lock();
        try {
            pronto = true;
            cortePronto.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return nome;
    }

}
