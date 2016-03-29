/*PRACTICA 3 sistemas concurentes 2004
 * Cruce.java
 *Autor1:
 *Autor2:
 *COMENTARIOS.
 *He usado los siguientes monitores, inicializados tal y como sigue para resolver los
 *siguientes problemas. 
 *
 *Monitor Imaginario1. Evita el problema problema1 que sucede cuando pasa el caso1.
 *Protege el recurso 1. Lo utilizan los hilos HA y HB
 *
 *Monitor Imaginario2. Evita el problema problema2 que sucede cuando pasa el caso2.
 *Protege el recurso 2. Lo utilizan los hilos HR y HX
 *
 *
 *
 */


    public class Cruce {
    //OJO!!!, no se pueden definir attributos ni métodos en esta clase
       public static void main(String[] args) {
        //Codigo de inicialización de los hilos y estructuras de datos compartidas
         Puente cP=new Puente(15);
      
         TotalCoches tC=new TotalCoches();
         Cambiador c=new Cambiador(cP,tC);
        
         Coche [] coches=new Coche[100];
        
         for(int i=0;i<coches.length;i=i+2){
            coches[i]=new Coche(i+1,true,cP,tC);
            coches[i+1]=new Coche(i+2,false,cP,tC);
         }
        
         
         for(int i=0;i<coches.length;i++){
            coches[i].start();        
         }
         c.start();
        
      }
    
   }//@Cruce

//Defina apartir de aquí las clases auxiliares que necesite

    class Puente{
   
      private int numCoches;
      private boolean sentido;
      private int maxCoches;
      
   
       public Puente(int max){
      
         maxCoches=max;
         numCoches=0;
         sentido=true;
      }
    
       public synchronized void salir(){
         numCoches--;
         this.notify();
      }
      
       public synchronized void entrar(boolean s){
      
         if(numCoches>=maxCoches||sentido!=s) 
            try{
               this.wait();
            }
                catch(Exception e){}
         numCoches++;
        
      
      }
      
       public synchronized void cambioSentido(){
         sentido=(!sentido);
         this.notify();
      }
    
   }
   
    class Cambiador extends Thread{
      Puente p;
      TotalCoches t;
       public Cambiador(Puente p1, TotalCoches c){
         p=p1;
         t=c;
      }
      
       public void run(){
      
         while(t.getTotal()<100){
            try{
               Thread.sleep(200);
            } 
                catch (Exception e){}
            p.cambioSentido();
         }
      }
      
   	
   }
   
         
    class Coche extends Thread{
   
      private boolean sentido;
      private Puente coches;
      private int ident;
      private TotalCoches total;
     
   
       public Coche(int i, boolean direction, Puente c, TotalCoches t){
         ident=i;
         this.sentido=direction;
         coches=c;
         total=t;
         
      }
   
       public void run(){
      
      
         coches.entrar(sentido);
         
         if(sentido)
            System.out.println("Coche "+ident+" entrando en el puente, con sentido: derecha a izquierda");
         else
            System.out.println("Coche "+ident+" entrando en el puente, con sentido: izquierda a derecha");
         try{
            Thread.sleep(10);
         }
             catch (Exception e){}
         coches.salir();
         total.salir();
         
         if(sentido)
            System.out.println("Coche "+ident+" saliendo en el puente, con sentido: derecha a izquierda");
         else
            System.out.println("Coche "+ident+" saliendo en el puente, con sentido: izquierda a derecha");
      
      }
   
   
   }
   
    class TotalCoches{
   
      private int total;
   
       public TotalCoches(){
         total=0;
      }
   
       public synchronized void salir(){
         total++;
      }
   
       public synchronized int getTotal(){
         return total;
      }
   }
	
//Fin de la definición de clases auxiliares
