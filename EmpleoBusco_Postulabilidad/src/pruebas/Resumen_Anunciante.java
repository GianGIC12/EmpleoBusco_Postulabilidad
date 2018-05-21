/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebas;

import archivos.ExportarCSV;
import gestion.Querys;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author user
 */
public class Resumen_Anunciante {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        // TODO code application logic here
        
         Calendar calendar = Calendar.getInstance();

        int num_dia = calendar.get(Calendar.DAY_OF_YEAR);

        
        System.out.println("" + num_dia);

        System.out.println("estamos en el día: " + num_dia);
        
        
        
        Querys q= new Querys();
        
        q.llenarFechas1();
        q.listarfechas1();
        
        System.out.println("Estamos en la fecha: " +q.getFechas1()[num_dia]);
        
        q.conseguirAvisos();
        
        System.out.println("***Listar Avisos***");
        
        q.listarAvisos();
        
        //Toco code logic here
        
        q.completarAreas();
        q.completarNivel_Puesto();
        
        System.out.println("***Completar postulaciones****");
        
        q.conseguirPostulaciones(num_dia);
        q.listarPostulaciones();
        
        ExportarCSV e= new ExportarCSV();
        int fechita=num_dia-1;
        e.exportarResultados(q.getAvisos(),q.getFechas1()[fechita]);
        
        System.out.println("*****Implementando el STOCK******************");
        
        q.conseguirStock();
        
        System.out.println("***Listar Avisos***");
        
        q.listarAvisos_Stock();
        
         
        //Toco code logic here
        
        q.completarAreas_Stock();
        q.completarNivel_Puesto_Stock();
        
        System.out.println("***Completar postulaciones****");
        
        q.conseguirPostulaciones_Stock(num_dia);
        q.listarPostulaciones_Stock();
        
        e.exportarResultados_Stock(q.getAvisos_stock(),q.getFechas1()[fechita]);
        
    }
    
}
