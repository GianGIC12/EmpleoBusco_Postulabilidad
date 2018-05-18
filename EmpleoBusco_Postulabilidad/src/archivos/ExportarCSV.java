/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package archivos;

import beans.AvisoBean;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public class ExportarCSV {

    public ExportarCSV() {
    }
    
    public void exportarResultados(List<AvisoBean> avisos, String fechita) throws IOException{
        
         String outputFile = "C:/Users/user/Google Drive/Digital_Balance_EmpleoBusco/Base_postulabilidad/bd_empleo_postulabilidad_" + fechita + ".csv";

          boolean alreadyExists = new File(outputFile).exists();

        if (alreadyExists) {
            File bd_empleo_postulabilidad = new File(outputFile);
            bd_empleo_postulabilidad.delete();
        }
         
         CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ';');

        csvOutput.write("id_empresa");
        csvOutput.write("id_aviso");
        csvOutput.write("nombre_empresa");
        csvOutput.write("ruc");

        csvOutput.write("pais");
        csvOutput.write("fecha");
        csvOutput.write("postulaciones");
        csvOutput.write("id_area");
        csvOutput.write("nombre_area");

        csvOutput.endRecord();
        
        for (AvisoBean aviso : avisos) {
            
            csvOutput.write(aviso.getId_empresa() + "");
                csvOutput.write(aviso.getId_aviso() + "");
                csvOutput.write(aviso.getNombre_empresa());
                csvOutput.write(aviso.getRuc());

                csvOutput.write(aviso.getPais());
                csvOutput.write(aviso.getFecha() + "");
                
                csvOutput.write(aviso.getPostulaciones() + "");
                
                csvOutput.write(aviso.getId_area() + "");
                csvOutput.write(aviso.getNombre_area() + "");

                csvOutput.endRecord();
            
            
            
        }
        
        csvOutput.close(); 
        
    }
    
    
}
