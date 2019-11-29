/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoatividade;

import grafoatividade.Grafo.Vertice;

/**
 *
 * @author Joel
 */
public class GrafoAtividade {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Grafo g = new Grafo();
        Vertice s = g.addVertice("s");
        Vertice t = g.addVertice("t");
        Vertice y = g.addVertice("y");
        Vertice z = g.addVertice("z");
        
        g.addAresta(s, t, 20);
        g.addAresta(t, s, 20);
        g.addAresta(s, y, 15);
        g.addAresta(t, y, 10);
        g.addAresta(y, t, 3);
        g.addAresta(t, z, 3);
        
        g.imprime();
        
        // menor caminho pelo custo
        g.menorCaminhop(s, true); // a partir de s, têm-se os menores caminhos para cada vértice
        
        // percorrer caminho de s para s
        g.percorreCaminhoCusto(z);
        
        
        // menor caminho pelo custo
        g.menorCaminhop(s, false); // a partir de s, têm-se os menores caminhos para cada vértice
        
        // percorrer caminho de s para s
        g.percorreCaminhoNos(z);
        
    }
    
    
}
