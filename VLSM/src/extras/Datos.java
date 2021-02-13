
package extras;

public class Datos {
    String[] ip = {"0.0.0.0","0.0.0.0","0.0.0.0","0.0.0.0"};
    String mascara,nombre;
    int hostP,hostR;
    public Datos(){
    }
    public Datos(String ip[]){
        this.ip =ip;
    }
    public void setNum(int columna,String valor){
        ip[columna]= valor;
    }
    
    public String getNum(int columna){
        return ip[columna];
    }
    public void setHostP(int hostP){
        this.hostP=hostP;
    }
    public int getHostP(){
        return hostP;
    }
    public void setHostR(int hostR){
        this.hostR=hostR;
    }
    public int getHostR(){
        return hostR;
    }
    public void setMasc(String mascara){
        this.mascara=mascara;
    }
    public String getMasc(){
        return mascara;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getNombre(){
        return nombre;
    }
}
