/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barbeirodorminhoco;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author guispiger
 */
public class SalaEspera {

    private final BlockingQueue<Cliente> cadeiras;

    public SalaEspera(int capacidade) {
        cadeiras = new ArrayBlockingQueue<>(capacidade);
    }

    public boolean clienteChega(Cliente cliente) {
        return cadeiras.offer(cliente);
    }

    public Cliente proximoCliente() throws InterruptedException {
        return cadeiras.poll();
    }

    public BlockingQueue<Cliente> getCadeiras() {
        return cadeiras;
    }

    public boolean estaVazia() {
        return cadeiras.isEmpty();
    }
}
