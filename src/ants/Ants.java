package ants;
import java.io.*;
import java.util.*;
/**
 *
 * @author jose
 */
public class Ants {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //valores de entrada 
int semilla=100;
int tamcolonia=15; //cantidad de hormigas
int iteraciones=1;
double alpha= 0.1;
double q0=0.5;
double betha=2.5;
int tamciudades=0;
String ruta="c:/b6.txt";

//variables peruanas
int dimi=dimension(ruta);
double[] cx=coordenadax(ruta,dimi);
double[] cy=coordenaday(ruta,dimi);
int []solinicial=RandomizeArray(0,dimi-1); 

//matriz distancia y heuristica
double mmdis [][]=matrizdistancia(dimi,cx,cy);
double mheu[][]=matrizheuristica(mmdis,dimi);
//mejor solucion
int []mejorsolucion=solinicial;
double costomejor = costo(mejorsolucion,mmdis);
System.out.println ("costo de la mejor solucion " + costomejor);
//matriz de feromonas
double mferomona [][]=matrizferomona(costomejor,dimi); 
//matriz de memoria
//int [][]memoria=memoria(tamcolonia,dimi);
 



        System.out.println ("mejor solucion inicial");
        for (int i = 0; i < mejorsolucion.length; i++) {
            System.out.print ( mejorsolucion[i]+" " );
        }
        System.out.println (" ");
 
        
int ciclo=0;
 
      while ( ciclo<iteraciones || (costomejor==7544.3659)) {
         //System.out.println("Esto lo verás una vez");
         ciclo++;
         
         //inicializar la memoria de las hormigas
         
         ArrayList<ArrayList<Double> > memoria = new ArrayList<ArrayList<Double> >(dimi); 
         //System.out.println("vector de vectores ");
        System.out.println ("memoria ");
        for (int i = 0; i < tamcolonia; i++) {
            ArrayList<Double> a1 = new ArrayList<Double>();
            for (int j = 0; j < dimi; j++) {
                double aux=mferomona[j][0]*Math.pow(mheu[j][0], betha);
                a1.add(aux); 
            }
            memoria.add(a1); 
        }
        
        for (int i = 0; i < memoria.size(); i++) { 
            for (int j = 0; j < memoria.get(i).size(); j++) { 
                System.out.print(memoria.get(i).get(j) + " "); 
            } 
            System.out.println(); 
        } 
         
         
        
         int colonia [][]=coloniainicial(dimi,tamcolonia);
         
         for (int i = 0; i < tamcolonia; i++) {//para cada vertice del grafo
              for (int j = 1; j <dimi ; j++) {//para cada hormiga
               //seleccionar el prox segmento del grafo usando la ec. 1
               //ArrayList<Double> a1 = new ArrayList<Double>();
               
               
                  double nrandom=randomadec(0,1,semilla);
               //System.out.print (" numero random " + nrandom);
                  if (nrandom<q0) {//entonces siguiente jo
                     //usar la ec 1 parte superior{]___Z}}xc
                     //memoria.set(i).set(0);
                    ArrayList<Double> aux = new ArrayList<Double>();
                    aux=memoria.get(i);  
                   
                    aux.set(j, 0.0);
                    
                  //  aux.set(j, 0.0);
                    colonia[i][j]=maximoa(aux);
                    //aux.set(j, 0.0);
                    //System.out.println ("el max "+maximoa(aux));
                   memoria.set(i, aux);
                   
                     // System.out.print ("es menor ");
                    
                  }
                  else  {
                      colonia[i][j]=1;//usar la funcion j
                  }
                  
              }
          
              for (int j = 0; j < dimi; j++) {//para cada hormiga
               //Actualizar la feromona en cada segmento según la Ecuación. 4  
              }
         
         }//fin for general
         
         //leer la colonia alcutal
           System.out.println (" final colonia");
         for (int i = 0; i < tamcolonia; i++) {
             int []aux=new int [dimi];
            for (int j = 0; j < dimi; j++) {
                aux[j]=colonia[i][j];
               System.out.print (colonia[i][j]+" ");
          }
            double costoaux=costo(aux,mmdis);
             if (costoaux<costomejor) {
                 costomejor=costoaux;
                 mejorsolucion=aux;
                // System.out.println (" este costo es mejor  "+costoaux+" ***");
             }
         System.out.println (" ");
         }
         System.out.println ("costo de la mejor solucion 2 " + costomejor);    
         
          if (true) {//si es encontrada una mejor solución en el actual ciclo
              //Asignar como mejor solución global a la mejor solución encontrada en la iteración actual.

          }// fin if
         
         //Actualizar la feromona en los segmento de la mejor solución global según la Ecuación. 3
      
           System.out.println ("memoria final");
           for (int i = 0; i < memoria.size(); i++) { 
            for (int j = 0; j < memoria.get(i).size(); j++) { 
                System.out.print(memoria.get(i).get(j) + " "); 
            } 
            System.out.println(); 
        } 
      }//fin while 
      
      System.out.println ("mejor solucion inicial");
        for (int i = 0; i < mejorsolucion.length; i++) {
            System.out.print ( mejorsolucion[i]+" " );
        }
        System.out.println (" ");
      
         
    } 
     
// funciones o clases
    public static double randomadec(int a, int b, int semilla){ 
    Random r = new Random();
    Double ri = b + ( a - b ) * r.nextDouble(); 
    return ri;
   }
     
    public static int[] RandomizeArray(int a, int b){
		Random rgen = new Random();   	
		int size = b-a+1;
		int[] array = new int[size];
 
		for(int i=0; i< size; i++){
			array[i] = a+i;
		}
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		
 
		return array;
	}

    private static int dimension(String ruta) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s2;
        int numTokens = 0;
        String sdimension="0";
        String dim="DIMENSION:";
        int dimension=0;
         try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         int aux=1;
         
         while((linea=br.readLine())!=null){
            System.out.println(linea);
            StringTokenizer st = new StringTokenizer (linea);
       
              while (st.hasMoreTokens()){
                   s2 = st.nextToken();
                   numTokens++;
                   //System.out.println ("    Palabra " + numTokens + " es: " + s2);
                   if (s2.equals(dim)) {//comparamos la dimension
                      sdimension=st.nextToken();//capturamos la dimension
                      dimension = Integer.parseInt(sdimension);//trans formamos la dimension a entrero
                  }
                   String saux = Integer.toString(aux);
                           
              }
         }//fin while que lee linea
         System.out.println ("la dimension es " + dimension);
        
       }//fin try
 
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }//fin trycatch
         return dimension;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static double[] coordenadax(String ruta,int dimi) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s2;
        int numTokens = 0;
        String sdimension="0";
        String dim="DIMENSION:";
        int dimension=dimi;
        ArrayList<String>  cordx  = new ArrayList<>();
        ArrayList<String>  cordy  = new ArrayList<>();
        double cordenadax []=new double [dimension];
        double cordenaday []=new double [dimension];
       try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         int aux=1;
         
         while((linea=br.readLine())!=null){
            //System.out.println(linea);
            StringTokenizer st = new StringTokenizer (linea);
       
              while (st.hasMoreTokens()){
                   s2 = st.nextToken();
                   numTokens++;
                   //System.out.println ("    Palabra " + numTokens + " es: " + s2);
                  
                   String saux = Integer.toString(aux);
                   if (s2.equals(saux)) {//comparamos la dimension
                        String x=st.nextToken();
                        String y=st.nextToken();
                       
                        cordx.add(x);
                        cordy.add(y);
                        aux++;
                    }                 
              }
         }//fin while que lee linea
        // System.out.println ("   la dimension es " + dimension);
        
       }//fin try
 
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }//fin trycatch

       for (int i = 0; i < cordx.size(); i++) {//conversion de las cordenadas de string a double y guardado en un vector
            double tempx=Double.parseDouble (cordx.get(i));
            double tempy=Double.parseDouble(cordy.get(i));
            cordenadax [i]=tempx;
            cordenaday [i]=tempy;
            //System.out.println ("  cordenada en x " + cordenadax[i]+" y en y " + cordenaday[i]);
        }//fin de la conversion
       return cordenadax;
    }
    	    
    private static double[] coordenaday(String ruta,int dimi) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s2;
        int numTokens = 0;
        String sdimension="0";
        String dim="DIMENSION:";
        int dimension=dimi;
        ArrayList<String>  cordx  = new ArrayList<>();
        ArrayList<String>  cordy  = new ArrayList<>();
        double cordenadax []=new double [dimension];
        double cordenaday []=new double [dimension];
       try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         int aux=1;
         
         while((linea=br.readLine())!=null){
            //System.out.println(linea);
            StringTokenizer st = new StringTokenizer (linea);
       
              while (st.hasMoreTokens()){
                   s2 = st.nextToken();
                   numTokens++;
                   //System.out.println ("    Palabra " + numTokens + " es: " + s2);
                  
                   String saux = Integer.toString(aux);
                   if (s2.equals(saux)) {//comparamos la dimension
                        String x=st.nextToken();
                        String y=st.nextToken();
                       
                        cordx.add(x);
                        cordy.add(y);
                        aux++;
                    }                 
              }
         }//fin while que lee linea
        // System.out.println ("   la dimension es " + dimension);
        
       }//fin try
 
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }//fin trycatch

       for (int i = 0; i < cordx.size(); i++) {//conversion de las cordenadas de string a double y guardado en un vector
            double tempx=Double.parseDouble (cordx.get(i));
            double tempy=Double.parseDouble(cordy.get(i));
            cordenadax [i]=tempx;
            cordenaday [i]=tempy;
            //System.out.println ("  cordenada en x " + cordenadax[i]+" y en y " + cordenaday[i]);
        }//fin de la conversion
       return cordenaday;
    }

    private static double[][] matrizdistancia(int dimension, double[] cx, double[] cy) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        double mdistancia [] []=new double [dimension][dimension]; 
        for (int i = 0; i <dimension; i++) {
             double x1=cx[i];
             double y1=cy[i];
            for (int j = 0; j <dimension; j++) {
                double x2=cx[j];
                double y2=cy[j];
                double result=Math.sqrt( (x2 -x1)*(x2 -x1) + (y2 -y1)*(y2 - y1) );
                mdistancia [i][j]=result;
            }
        }
           System.out.println ("          Matriz de distancia ");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                 System.out.print (String.format("%.1f  ", mdistancia[i][j]));
            }
            System.out.println (" ");
        }
        return mdistancia;
    }

    private static double[][] matrizheuristica(double[][] mdistancia, int dimension) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       double mheuristica [] []=new double [dimension][dimension]; 
       for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mheuristica[i][j]=1/mdistancia [i][j];
                if (i==j) {
                mheuristica[i][j]=0;    
                }
            }
        }
         System.out.println ("+++++++  Matriz heuristica  +++++++");
       for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                 System.out.print (String.format("%.5f  ", mheuristica[i][j]));
            }
            System.out.println (" ");
        }
       return mheuristica;
    }

    private static double[][] matrizferomona(double costomejor, int dimi ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    double[][]mferomona=new double[dimi][dimi];
     for (int i = 0; i < dimi; i++) {
            for (int j = 0; j < dimi; j++) {
               mferomona[i][j]=1/costomejor;
                if (i==j) {
                mferomona[i][j]=0;    
                }
            }
        }
         System.out.println ("+++++++  Matriz feromona  +++++++");
       for (int i = 0; i < dimi; i++) {
            for (int j = 0; j < dimi; j++) {
                 System.out.print (String.format("%.5f  ", mferomona[i][j]));
            }
            System.out.println (" ");
        }
        return mferomona;
    
    }

    private static double costo(int[] mejorsolucion, double[][] mmdis) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    double total = 0.0;
    
    int aps=1;
        for (int i = 0; i < mejorsolucion.length-1; i++) {
           double aux=mmdis[mejorsolucion[i]][mejorsolucion[aps]];           
           total=total+aux;
            //System.out.println ("sumando "+total+" + " + aux+" con el vector en la posicion "+i +"la suma de "+mejorsolucion[i] +"+"+mejorsolucion[aps]);
           aps++;
        }
       //System.out.println ("mejor solucion final coso " + mejorsolucion[mejorsolucion.length-1]);
    double finala=mejorsolucion[mejorsolucion.length-1]+mejorsolucion[0];
    total=total+finala;
     //System.out.println ("costo de la mejor solucion " + total);
    
    return total;
    }

    private static int[][] coloniainicial(int dimi,int tamcolonia) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     int []ki=RandomizeArray(0,dimi-1);
     int colonia [] []=new int [tamcolonia][dimi]; 
       for (int i = 0; i < tamcolonia; i++) {
            for (int j = 0; j < dimi; j++) {
              
                if (i<ki.length) {
                   colonia[i][0]=ki[i];
                   
                }
                else    {
                colonia[i][0]=randomanintsinsemilla(0,dimi);    
                }
                
                 
                
            }
        }
        System.out.println ("inicial colonia ");
        for (int i = 0; i < tamcolonia; i++) {
            for (int j = 0; j < dimi; j++) {
                 System.out.print (colonia[i][j]+" ");
            }
             System.out.println (" ");
        }
    return colonia;
    }
    
    public static int randomanintsinsemilla(int a, int b){ 
    Random rnd = new Random();
    int rndInd = (int)(b + ( a - b ) * rnd.nextDouble());
    return rndInd;
   }

    private static int mejorEcuacion1(double[][] mferomona, double[][] mheu,int dimi,double betha,int nodo) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   
    int pos=0;
    double []vector=new double[dimi];
    double max=vector[0];
        for (int i = 0; i < dimi; i++) {
            double aux=mferomona[i][0]*Math.pow(mheu[i][0], betha);
            
            vector[i]=aux;
            System.out.println ("aux "+aux);
        }
    //el max será el la posicion del vector
        for (int i = 0; i < dimi; i++) {
            //System.out.println ("vec pos  "+i+" tiene "+vector[i]);
            if (vector[i]>=max) {
               max=vector[i];
            }
        }
           System.out.println ("max "+max);
        for (int i = 0; i < dimi; i++) {
            if (vector[i]==max) {
                pos=i;
            }
        }
      
    return pos;
    }

    private static int[][] memoria(int tamcolonia, int dimi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static ArrayList<Double> vecec1(double[][] mferomona, double[][] mheu, int dimi, double betha) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   int dim=dimi;
    //double []vector=new double[dimi];
    ArrayList<Double> max = new ArrayList<Double>();
        for (int i = 0; i < dim; i++) {
            double aux=mferomona[i][0]*Math.pow(mheu[i][0], betha);
            max.add(aux);
            
            //System.out.println ("aux "+aux);
        }
         return max;
    }

    private static int maximoa(ArrayList<Double> arra) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        double max=arra.get(0);
        int pos=0;
        for (int i = 0; i < arra.size(); i++) {
            //System.out.println ("vec pos  "+i+" tiene "+vector[i]);
            if (arra.get(i)>max) {
               max=arra.get(i);
            }
        }
       // System.out.println ("max "+max);
        for (int i = 0; i < arra.size(); i++) {
            if (arra.get(i)==max) {
                pos=i;
            }
        }
        
       
        return pos;
    }

    private static int posmejorsol(ArrayList<Double> arra) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        double max=arra.get(0);
        int pos=0;
        for (int i = 0; i < arra.size(); i++) {
            //System.out.println ("vec pos  "+i+" tiene "+vector[i]);
            if (arra.get(i)>=max) {
               max=arra.get(i);
            }
        }
       // System.out.println ("max "+max);
        for (int i = 0; i < arra.size(); i++) {
            if (arra.get(i)==max) {
                pos=i;
            }
        }
        
       
        return pos;
    }

    private static int posmejorsolvec(double[] aim) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     double max=aim[0];
        int pos=0;
        for (int i = 0; i < aim.length; i++) {
            //System.out.println ("vec pos  "+i+" tiene "+vector[i]);
            if (aim[i]>=max) {
               max=aim[i];
            }
        }
       // System.out.println ("max "+max);
        for (int i = 0; i < aim.length; i++) {
            if (aim[i]==max) {
                pos=i;
            }
        }
        
       
        return pos;
    }
  
}
