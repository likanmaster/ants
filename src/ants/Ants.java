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
        
int semilla=1;
int tamcolonia=100; //cantidad de hormigas
int iteraciones=5;
double alpha= 0.1;
double q0=0.9;
double betha=2.5;
int tamciudades;
String ruta="c:/berlin52.tsp.txt";


int dimi=dimension(ruta);
double[] cx=coordenadax(ruta,dimi);
double[] cy=coordenaday(ruta,dimi);
    
double mmdis [][]=matrizdistancia(dimi,cx,cy);
double mheu[][]=matrizheuristica(mmdis,dimi);
int []solinicial=RandomizeArray(0,dimi-1); 

int []mejorsolucion=solinicial;   
double costomejor = costo(mejorsolucion,mmdis);
double mferomona [][]=matrizferomona(costomejor,dimi); 
int coloniai [][]=coloniainicial(dimi,tamcolonia);

       // for (int i = 0; i < mejorsolucion.length; i++) {
        //     System.out.print (" " + mejorsolucion[i]);
      //  }
    } 
     
// funciones o clases
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
     System.out.println ("costo de la mejor solucion " + total);
    
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
    	 
}
