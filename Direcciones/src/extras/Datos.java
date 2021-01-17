
package extras;

public class Datos {
    String[] ip = {"0.0.0.0","0.0.0.0","0.0.0.0","0.0.0.0"};
    int indice;
    public Datos(int indice){
        this.indice=indice;
    }
    public Datos(String ip[], int indice){
        this.indice=indice;
        this.ip =ip;
    }
    
    
    public void setNum(int columna,String valor){
        ip[columna]= valor;
    }
    
    public String getNum(int columna){
        return ip[columna];
    }
}
