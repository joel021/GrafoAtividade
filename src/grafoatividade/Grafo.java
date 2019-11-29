/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoatividade;

import java.util.ArrayList;

/**
 *
 * @author Joel
 */
public class Grafo {
    public ArrayList<Vertice> vertices;
    
    public Grafo(){
        //inicialização
        vertices    = new ArrayList<>();
    }
    
    // origem a destino, então primeiro termo é a partir da origem, o ultimo termo é o destino
    public void menorCaminhop(Vertice origem, boolean porCusto){
        
        for(Vertice v: vertices){
            v.fechado                   = false;
            v.menorCaminho.valorNos     = -1;
            v.menorCaminho.valorRotulos = -1;
            v.menorCaminho.vertice      = null;
        }
        
        // define seu menor caminho
        origem.menorCaminho.valorRotulos   = 0;
        origem.menorCaminho.valorNos       = 0;
        origem.menorCaminho.vertice        = null;
        
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        //menorCaminhoDijstra(origem, origem, caminhos);
                
         // caminho auxiliar, qual desses caminhos todos tem o menor caminho?
        Vertice origemRelativo  = origem;
        Vertice cAux;
        
        do {
            cAux            = null;
            //System.out.println("origemRelativo = "+origemRelativo.nome);
            // percorre todos os seus vizinhos
            for(Aresta a: origemRelativo.adjacentes){
                
                //System.out.println("Adjacente: "+a.destino.nome);
                
                if(origemRelativo.nome.equals(a.destino.nome)){
                    //System.out.println("continue");
                    // nesse momento eu estou percorrendo todos os adjacentes, entre eles, temos o caminho de volta, não queremos analizar esse vértice pois viemos dele
                    // então apenas ignoramos ele
                    continue;
                }
                
                if(a.destino.menorCaminho.vertice != null){
                    if(a.destino.menorCaminho.vertice.nome.equals(a.destino.nome)){
                        //System.out.println("continue");
                        // nesse momento eu estou percorrendo todos os adjacentes, entre eles, temos o caminho de volta, não queremos analizar esse vértice pois viemos dele
                        // então apenas ignoramos ele
                        continue;
                    }
                            
                }
                
                // se o vertice da aresta a tiver valor "infinito" ou
                // se o caminho atual até esse vértice for maior que o caminho que estamos, definimos o caminho que estamos
                if((a.destino.menorCaminho.valorRotulos == -1 || a.destino.menorCaminho.valorRotulos > (a.rotulo + origemRelativo.menorCaminho.valorRotulos)) && porCusto){
                    // então a menor distancia da origem até ele é o valor da aresta mesmo!
                    a.destino.menorCaminho.valorRotulos     = a.rotulo + origemRelativo.menorCaminho.valorRotulos;
                    a.destino.menorCaminho.vertice          = origemRelativo;
                }else if((a.destino.menorCaminho.valorNos == -1 || a.destino.menorCaminho.valorNos > (1 + origemRelativo.menorCaminho.valorNos)) && !porCusto){
                    // então a menor distancia da origem até ele é o valor da aresta mesmo!
                    a.destino.menorCaminho.valorNos  = 1 + origemRelativo.menorCaminho.valorNos;
                    a.destino.menorCaminho.vertice   = origemRelativo;
                }

                // adiciona o vertice atual com o seu caminho (como uma tabela de passos)
                if(!adjacentes.contains(a.destino) && !a.destino.fechado){
                    adjacentes.add(a.destino); // espero que não entre niguém repetido
                }
            }

            // verificar o melhor caminho
            for(Vertice c: adjacentes){
                if(cAux == null)
                    cAux = c;

                if(c.menorCaminho.valorRotulos < cAux.menorCaminho.valorRotulos && porCusto){
                    cAux = c;
                }else if(c.menorCaminho.valorNos < cAux.menorCaminho.valorNos && !porCusto){
                    cAux = c;
                }
                
            }
            
            adjacentes.remove(cAux);
            // agora, dentre esses rotulados, temos o de menor valor
            
            origemRelativo.setFechado(true);
            origemRelativo = cAux;
            
        }while(cAux != null);
    }
    
    private void imprimeVertices(ArrayList<Vertice> c){
        for(Vertice c1: c){
            System.out.print(" "+c1.nome);
        }
    }
    
    public void imprime() {
        for (Vertice u : vertices) {
            for (Aresta e : u.adjacentes) {
                Aresta a = e;
                System.out.println(a.destino.nome + "(" + a.rotulo + ") ");
            }
        }
    }
    public void percorreCaminhoCusto(Vertice aux){
        
        System.out.println("Custo total = "+aux.menorCaminho.valorRotulos);
        System.out.println("Nome : "+aux.nome);
        while(aux.menorCaminho.vertice != null) {
            System.out.println("Nome: "+aux.menorCaminho.vertice.nome);
            aux = aux.menorCaminho.vertice; // menor caminho de onde ele está até o ponto inicial.
        }
    }
    
    public void percorreCaminhoNos(Vertice aux){
        
        System.out.println("Nos total = "+aux.menorCaminho.valorNos);
        System.out.println("Nome : "+aux.nome);
        
        while(aux.menorCaminho.vertice != null) {
            System.out.println("Nome: "+aux.menorCaminho.vertice.nome);
            aux = aux.menorCaminho.vertice; // menor caminho de onde ele está até o ponto inicial.
        }
    }
    
    public Vertice addVertice(String nome){
        
        Vertice v = new Vertice(nome);
        // necessário para o algoritmo de dijkstra
        v.menorCaminho.valorRotulos   = -1;
        v.menorCaminho.valorNos       = -1;
        v.menorCaminho.vertice        = null;
        
        vertices.add(v);
       
        
        return v;
    }
    
    // adicionar uma aresta na lista do vertice origem
    public void addAresta(Vertice origem, Vertice destino, int rotulo ){
        
        Aresta ab = new Aresta(destino, rotulo);
        origem.addAdjacente(ab);
        
        //Aresta ba = new Aresta(origem, rotulo);
        //destino.addAdjacente(ba);
    }
    
    public class Vertice implements Comparable<Vertice>{
        private String nome;
        private ArrayList<Aresta> adjacentes;
        private Caminho menorCaminho;
        private boolean fechado;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public ArrayList<Aresta> getAdjacentes() {
            return adjacentes;
        }

        public void setAdjacentes(ArrayList<Aresta> adjacentes) {
            this.adjacentes = adjacentes;
        }

        public Caminho getMenorCaminho() {
            return menorCaminho;
        }

        public void setMenorCaminho(Caminho menorCaminho) {
            this.menorCaminho = menorCaminho;
        }

        public boolean isFechado() {
            return fechado;
        }

        public void setFechado(boolean fechado) {
            this.fechado = fechado;
        }
        
        public Vertice(String nome){
            this.nome       = nome;
            this.adjacentes = new ArrayList<>();
            menorCaminho    = new Caminho();
        }
        
        public void addAdjacente(Aresta a){
            adjacentes.add(a);
        }

        @Override
        public int compareTo(Vertice o) {
            if(o.nome.equals(nome)){
                return 0;
            }
            
            return 1;
        }
    }
    
    public class Aresta {

        public Vertice getDestino() {
            return destino;
        }

        public void setDestino(Vertice destino) {
            this.destino = destino;
        }

        public int getRotulo() {
            return rotulo;
        }

        public void setRotulo(int rotulo) {
            this.rotulo = rotulo;
        }
        private Vertice destino;
        
        private int rotulo;
        
        public Aresta(Vertice destino, int rotulo){
            this.destino    = destino;
            this.rotulo     = rotulo;
        }
    }
    
    public class Caminho {

        public Vertice getVertice() {
            return vertice;
        }

        public void setVertice(Vertice vertice) {
            this.vertice = vertice;
        }

        public int getValorRotulos() {
            return valorRotulos;
        }

        public void setValorRotulos(int valorRotulos) {
            this.valorRotulos = valorRotulos;
        }

        public int getValorNos() {
            return valorNos;
        }

        public void setValorNos(int valorNos) {
            this.valorNos = valorNos;
        }
        private Vertice vertice;
        private int valorRotulos;
        private int valorNos;
        
        public Caminho(){
            
        }

    }
    
    
    
}
