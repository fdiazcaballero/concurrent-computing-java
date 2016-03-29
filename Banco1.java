/*
*Alumno 1: @sc
*Alumno 2: @sc
*
*Descripcion:  Práctica 1 de sistemas concurrentes @solución.
*
*HE USADO LOS SIGUIENTES CERROJOS:
* Cerrojo x: Protege el valor de R y  es compartido por los hilos H1, H2 y H3 
* Cerrojo y:
* Cerrojo etc:
*            . . .
*/


   import java.awt.*;
   import javax.swing.*;
   import java.awt.event.*;
   import java.util.Random;
   import java.util.Vector;
   import java.lang.Thread;

    class OperacionBancaria implements Runnable{
   
      private int identificador;
      private Cuenta cuenta;
      //private Events events;
      private Pintar p;
   
      Random rnd=new Random();
   
       public OperacionBancaria(Cuenta c, /*Events ,e*/ Pintar pintar, int identificador){
         this.identificador=identificador;
         cuenta = c;
         //events=e;
         p=pintar;
      }
    
       public void run(){
      
         int aux;
      
         System.out.println("Tarea"+identificador+" : ingresando "+ identificador);
      
      // simulamos el tiempo que tarda el cajero en realizar la operación
         cuenta.sumar(identificador, rnd);
        
         //events.incrementarEvents();
      
      
         System.out.println("Tarea"+identificador+" : ingresando "+ identificador*10);
      
      // simulamos el tiempo que tarda el cajero en realizar la operación
         cuenta.sumar(identificador*10,rnd);
      
         //events.incrementarEvents();
      
         System.out.println("Tarea"+identificador+" saliendo");
         
         p.terminar();
      }
   }
/*
    class RefrescoInformacion extends Thread{
      private Cuenta cuenta;
      private Events events;
       
       public RefrescoInformacion(Cuenta c, Events e){
         cuenta=c;
         events=e;
      }
      
       public void run(){
         while(true){
         // Refresco la información 
         
                       //}
           
            //events.decrementarEvents();
           
         }//while
      }
   }
*/

    public class Banco implements ActionListener{
   
    //Zona de almacenamiento de variables
      static Container content;
   
      static java.awt.Panel Ppanel;
   
      static Label lInfoHora=new Label("Son las "+new java.util.Date());
      static Label lInfoCuenta=new Label("Hay  0 euros en la cuenta ");
      static Button bRestart =new Button("Iniciar Banco");
   
      static JFrame frame;
      
      private Cuenta c;
      //private Events events;
      private Pintar p;
   
      static final int Height = 300;
      static final int Width = 600;  
      static int iPeriodo=100;
   
      static String buttonEvent="";
   
      //static int cuenta=0;
   
      //static Object cerrojo= new Object();
      //static Object cerrojoCuenta= new Object();
      //static int events=0;
      
       public Banco(Cuenta cuenta, /*Events ,e*/ Pintar pintar){
         c=cuenta;
         //events=e;
         p=pintar;
      }
   
       public void actionPerformed(ActionEvent e){
      
         p.pintar();
         if(e.getActionCommand().compareTo("Iniciar Banco")==0){
           // Reinicio la cuenta a 0
            c.puestaACero();
            System.out.println("Reinicio banco");
            OperacionBancaria tarea1 = new OperacionBancaria(c,/*events,*/ p, 20);
            OperacionBancaria tarea2 = new OperacionBancaria(c,/*events,*/p, 1000);
            Thread t1= new Thread(tarea1);
            Thread t2= new Thread(tarea2);
            t1.start();
            t2.start();
           
         }
         
      }
   
    
       public static void main(String[] s){
         //Events e=new Events();
         Cuenta c= new Cuenta();
         Pintar p=new Pintar();
         Banco gc=new Banco(c,/*e,*/p);
         frame = new JFrame("Banco");
      
         frame.addWindowListener(
                new WindowAdapter(){ 
                   public void windowClosing(WindowEvent e) {
                     System.exit(0);
                  }
               });
      
      
         bRestart.addActionListener(gc);
      
         content = frame.getContentPane(); 
      
         content.add(lInfoHora, BorderLayout.SOUTH);
         content.add(lInfoCuenta, BorderLayout.CENTER);
         content.add(bRestart, BorderLayout.NORTH);
      
         frame.setSize(Width+60,Height+80);
         frame.setVisible(true);
         //RefrescoInformacion refresco= new RefrescoInformacion(c,e);
         //refresco.start();
      
      }//@Banco.main
   }//@Banco
   
    class Cuenta{
   
      private int cuenta;
   
       public Cuenta(){
         cuenta=0;
      }
   
       public synchronized void sumar (int identificador, Random rnd){
         int aux=0;
         aux=cuenta+identificador; 
         try{
            if(rnd.nextBoolean())
               Thread.sleep(1000);
         }
             catch(Exception e){};
         cuenta=aux;
         pintar();
      }
      
       public synchronized int getCuenta(){
         return cuenta;
      }
      
       public synchronized void puestaACero(){
         cuenta=0;
         pintar();
      }
      
       private void pintar(){
      
         Banco.lInfoHora.setText("Son las "+new java.util.Date());
         
            //synchronized(Banco.cerrojoCuenta){
         	
            //System.out.println("  "+events.Get());
         Banco.lInfoCuenta.setText("Hay "+cuenta+" euros en la cuenta ");
         System.out.println("Son las "+new java.util.Date()+" y hay "+cuenta+" euros en la cuenta ");
            
      
      
      }
   }
   /*
    class Events{
   
      private int events;
   
       public Events(){
         events=0;
      }
      
       public synchronized void incrementarEvents(){
      
         events++;
         this.notify();
      
      }
      
       public synchronized int Get(){ 
         return events;
      }
   		
   		
       public synchronized void decrementarEvents(){
       
      
            
         try{
            if (events==0)
               this.wait();
            events--;
         }
             catch (Exception e){};
      
      }
   }
   */
   
    class Pintar{
   
      private int cont;
   
       public Pintar(){
      
         cont=0;
      
      }
      
       public synchronized void pintar(){
         
         try{
            if(cont!=0){
            
               this.wait();
               
            }
            
            cont++;
            cont++;
         }
             catch (Exception e){};
        
      }	
   	
       public synchronized void terminar(){
         cont--;
         if(cont ==0)
            this.notify();
      }
   
   }
	
	
	
   


//@tsc2004


