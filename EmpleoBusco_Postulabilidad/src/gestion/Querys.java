/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import beans.AvisoBean;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class Querys {

    List<AvisoBean> avisos;
    List<AvisoBean> avisos_stock;
    String[] fechas1;
    String sql; 
    
    public Querys() {

        avisos = new ArrayList<AvisoBean>();
        avisos_stock = new ArrayList<AvisoBean>();
         fechas1 = new String[366];
        
    }

    public void llenarFechas1() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String fecha = dateFormat.format(date);
        String annio = fecha.substring(0, 4);
        int aux = Integer.parseInt(annio) - 1;

        fechas1[0] = aux + "-12-31";

        int contador = 0;

        for (int i = 1; i <= 12; i++) {

            String mes = "-0";

            if (i >= 10) {
                mes = "-";
            }

            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {

                for (int j = 1; j <= 31; j++) {
                    contador++;

                    if (j < 10) {

                        fechas1[contador] = annio + mes + i + "-0" + j;

                    } else {

                        fechas1[contador] = annio + mes + i + "-" + j;

                    }

                }

            } else if (i == 2) {

                for (int j = 1; j <= 28; j++) {
                    contador++;

                    if (j < 10) {

                        fechas1[contador] = annio + mes + i + "-0" + j;

                    } else {

                        fechas1[contador] = annio + mes + i + "-" + j;

                    }

                }

            } else {

                for (int j = 1; j <= 30; j++) {
                    contador++;

                    if (j < 10) {

                        fechas1[contador] = annio + mes + i + "-0" + j;

                    } else {

                        fechas1[contador] = annio + mes + i + "-" + j;

                    }

                }

            }

        }

    }

    public void listarfechas1() {

        for (int i = 0; i < fechas1.length; i++) {

            System.out.println(fechas1[i]);
        }

    }

    public void conseguirAvisos() throws SQLException {
        
         Conexion objCon = new Conexion();
          objCon.conectar();
        sql="select a.id as id_empresa, b.id as id_aviso, a.slug_empresa as nombre_empresa,"
              
           +"a.ruc as ruc, b.slug_pais as pais, b.id_area as id_area, b.puesto as puesto, b.id_nivel_puesto as nivel_puesto, " 
           +" substring(b.fh_creacion,1,10) as fecha"
                + " from db_empleobusco_prod.empresa as a"
           +" join db_empleobusco_prod.anuncio_web as b " 
           + " on a.id=b.id_empresa " 
           + " where b.fh_creacion>='2018-01-01'"     
              ;
          
        PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        int contador=0;
        while (rs.next()) {
            
            contador++;
            
            AvisoBean aviso= new AvisoBean();
            
            int id_empresa= rs.getInt(1);
            int id_aviso= rs.getInt(2);
            String nombre_empresa= rs.getString(3);
            String ruc= rs.getString(4);
            String pais= "No Definido";
            
            
            if (rs.getString(5)!=null) {
                pais=rs.getString(5);
            }
            
            int id_area= rs.getInt(6);
            
            String puesto=rs.getString(7);
            int id_nivel_puesto=rs.getInt(8);
            
            String fecha= rs.getString(9);
            
            aviso.setId_empresa(id_empresa);
            aviso.setId_aviso(id_aviso);
            aviso.setNombre_empresa(nombre_empresa);
            aviso.setRuc(ruc);
            aviso.setPais(pais);
            aviso.setId_area(id_area);
            
            puesto= puesto.replaceAll("ó", "o");
            puesto= puesto.replaceAll("é", "e");
            puesto= puesto.replaceAll("í", "i");
            puesto= puesto.replaceAll("ñ", "n");
            puesto=puesto.replaceAll("á", "a");
            puesto=puesto.replaceAll("ú","u");
            puesto=puesto.replaceAll("Á","A");
            puesto= puesto.replaceAll("–", "/");
            puesto=puesto.replaceAll("ü", "u");
            puesto=puesto.replaceAll("Ó", "O");
            puesto=puesto.replaceAll("É", "E");
            
            
            aviso.setPuesto(puesto);
            aviso.setId_nivel_puesto(id_nivel_puesto);
            aviso.setFecha(fecha);
            
            avisos.add(aviso);
            
            
            System.out.println(contador +  " : " +rs.getInt(1)+" : "+pais);
            
        }
        
        
          objCon.desconectar(); 

    }
    
    
    public void listarAvisos(){
        int contador=0;
      
        for (AvisoBean aviso: avisos) {
            contador++;
        System.out.println(contador+ " : id_empresa: "+ aviso.getId_empresa()
        +" id_Aviso: "+aviso.getId_aviso()
        +" Nombre_Empresa: "+aviso.getNombre_empresa()
        + " RUC: "+aviso.getRuc()
        + " Pais: " +aviso.getPais()
        + " Id_Area*****: "+aviso.getId_area()
        +" Puesto: " + aviso.getPuesto()
        + " id_nivel_puesto: " + aviso.getId_nivel_puesto());    
             
            
        }
        
        
    }
    
    
    public void completarAreas() throws SQLException{
       int id_area=1;
       String nombre_area="No definido";
       
        Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0;
       
        for (AvisoBean aviso: avisos) {
            
        id_area=aviso.getId_area();
     
          
          sql="select slug as nombre_area from db_empleobusco_prod.area "
              + " where id= " + id_area  ;
          
          PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
          
         contador++;
        
        while (rs.next()) {
            
            nombre_area=rs.getString(1);
            aviso.setNombre_area(nombre_area);
            System.out.println(contador+" : "+nombre_area);
        }
          
        
        }
        
        
        
          objCon.desconectar();  
        
    }
    
    public void completarNivel_Puesto() throws SQLException{
        
    int id_nivel_puesto=1;
    String nivel_puesto="No Definido";
   
       Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0; 
       
        for (AvisoBean aviso: avisos) {
            
          id_nivel_puesto= aviso.getId_nivel_puesto();
            
          sql="select slug as nivel_puesto from db_empleobusco_prod.nivel_puesto "
              + " where id= " + id_nivel_puesto  ;  
          
           PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
          
         contador++;
         
          while (rs.next()) {
             
               
            nivel_puesto=rs.getString(1);
            aviso.setNivel_puesto(nivel_puesto);
            System.out.println(contador+" : "+nivel_puesto);
              
              
              
          }
         
         
            
            
        }
       
       
        objCon.desconectar();  
        
        
    }
    
    public void conseguirPostulaciones(int num_dia) throws SQLException{
        
        String fecha= fechas1[num_dia-1];
        System.out.println("postulaciones: "+fecha);
        int id_aviso;
        
         Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0;
        for (AvisoBean aviso: avisos) {
            
          id_aviso=aviso.getId_aviso();
        sql=" select count(*) as cantidad from db_empleobusco_prod.postulacion "
               + " where id_anuncio_web="+ id_aviso;
            System.out.println(""+sql);
        PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        contador++;
         
        while (rs.next()) {
            
            aviso.setPostulaciones(rs.getInt("cantidad"));
        //  aviso.setFecha(fecha);
            System.out.println(contador+" : "+aviso.getPostulaciones());
        }
        
     }
        
         objCon.desconectar(); 
        
    }
    
    
    public void listarPostulaciones(){
        int contador=0;
      
        for (AvisoBean aviso: avisos) {
            contador++;
        System.out.println(contador+ " : id_empresa: "+ aviso.getId_empresa()
        +" id_Aviso: "+aviso.getId_aviso()
        +" Nombre_Empresa: "+aviso.getNombre_empresa()
        + " RUC: "+aviso.getRuc()
        + " Pais: " +aviso.getPais()
        + " Fecha: " +aviso.getFecha()
        + " Postulaciones: "+aviso.getPostulaciones());    
             
            
        }
        
        
    }
    
    
     public void conseguirStock() throws SQLException {
        
         Conexion objCon = new Conexion();
          objCon.conectar();
        sql="select a.id as id_empresa, b.id as id_aviso, a.slug_empresa as nombre_empresa,"
           +"a.ruc as ruc, b.slug_pais as pais, b.id_area as id_area, b.puesto as puesto, b.id_nivel_puesto as nivel_puesto " 
           + " from db_empleobusco_prod.empresa as a"
           +" join db_empleobusco_prod.anuncio_web as b " 
           + " on a.id=b.id_empresa " 
           + " where b.online=1 and b.estado='pagado' and b.borrador=0 and b.eliminado=0"     
              ;
          
        PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        int contador=0;
        while (rs.next()) {
            
            contador++;
            
            AvisoBean aviso= new AvisoBean();
            
            int id_empresa= rs.getInt(1);
            int id_aviso= rs.getInt(2);
            String nombre_empresa= rs.getString(3);
            String ruc= rs.getString(4);
            String pais= "No Definido";
            
            
            if (rs.getString(5)!=null) {
                pais=rs.getString(5);
            }
            
            int id_area= rs.getInt(6);
            
            String puesto=rs.getString(7);
            int id_nivel_puesto=rs.getInt(8);
            
            aviso.setId_empresa(id_empresa);
            aviso.setId_aviso(id_aviso);
            aviso.setNombre_empresa(nombre_empresa);
            aviso.setRuc(ruc);
            aviso.setPais(pais);
            aviso.setId_area(id_area);
            
            puesto= puesto.replaceAll("ó", "o");
            puesto= puesto.replaceAll("é", "e");
            puesto= puesto.replaceAll("í", "i");
            puesto= puesto.replaceAll("ñ", "n");
            puesto=puesto.replaceAll("á", "a");
            puesto=puesto.replaceAll("ú","u");
            puesto=puesto.replaceAll("Á","A");
            puesto= puesto.replaceAll("–", "/");
            puesto=puesto.replaceAll("ü", "u");
            puesto=puesto.replaceAll("Ó", "O");
            puesto=puesto.replaceAll("É", "E");
            
            
            aviso.setPuesto(puesto);
            aviso.setId_nivel_puesto(id_nivel_puesto);
            
            avisos_stock.add(aviso);
            
            
            System.out.println(contador +  " : " +rs.getInt(1)+" : "+pais);
            
        }
        
        
          objCon.desconectar(); 

    }
     
     public void listarAvisos_Stock(){
        int contador=0;
      
        for (AvisoBean aviso: avisos_stock) {
            contador++;
        System.out.println(contador+ " : id_empresa: "+ aviso.getId_empresa()
        +" id_Aviso: "+aviso.getId_aviso()
        +" Nombre_Empresa: "+aviso.getNombre_empresa()
        + " RUC: "+aviso.getRuc()
        + " Pais: " +aviso.getPais()
        + " Id_Area*****: "+aviso.getId_area()
        +" Puesto: " + aviso.getPuesto()
        + " id_nivel_puesto: " + aviso.getId_nivel_puesto());    
             
            
        }
        
        
    }
     
     public void completarAreas_Stock() throws SQLException{
       int id_area=1;
       String nombre_area="No definido";
       
        Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0;
       
        for (AvisoBean aviso: avisos_stock) {
            
        id_area=aviso.getId_area();
     
          
          sql="select slug as nombre_area from db_empleobusco_prod.area "
              + " where id= " + id_area  ;
          
          PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
          
         contador++;
        
        while (rs.next()) {
            
            nombre_area=rs.getString(1);
            aviso.setNombre_area(nombre_area);
            System.out.println(contador+" : "+nombre_area);
        }
          
        
        }
        
        
        
          objCon.desconectar();  
        
    }
     
     public void completarNivel_Puesto_Stock() throws SQLException{
        
    int id_nivel_puesto=1;
    String nivel_puesto="No Definido";
   
       Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0; 
       
        for (AvisoBean aviso: avisos_stock) {
            
          id_nivel_puesto= aviso.getId_nivel_puesto();
            
          sql="select slug as nivel_puesto from db_empleobusco_prod.nivel_puesto "
              + " where id= " + id_nivel_puesto  ;  
          
           PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
          
         contador++;
         
          while (rs.next()) {
             
               
            nivel_puesto=rs.getString(1);
            aviso.setNivel_puesto(nivel_puesto);
            System.out.println(contador+" : "+nivel_puesto);
              
              
              
          }
         
         
            
            
        }
       
       
        objCon.desconectar();  
        
        
    }
     
     public void conseguirPostulaciones_Stock(int num_dia) throws SQLException{
        
        String fecha= fechas1[num_dia-1];
        System.out.println("postulaciones: "+fecha);
        int id_aviso;
        
         Conexion objCon = new Conexion();
          objCon.conectar();
       int contador=0;
        for (AvisoBean aviso: avisos_stock) {
            
          id_aviso=aviso.getId_aviso();
        sql=" select count(*) as cantidad from db_empleobusco_prod.postulacion "
               + " where id_anuncio_web="+ id_aviso +" and substring(fh,1,10)= " +"'"+fecha+"'" ;
            System.out.println(""+sql);
        PreparedStatement stm = objCon.getCon().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        contador++;
         
        while (rs.next()) {
            
            aviso.setPostulaciones(rs.getInt("cantidad"));
            aviso.setFecha(fecha);
            System.out.println(contador+" : "+aviso.getPostulaciones());
        }
        
     }
        
         objCon.desconectar(); 
        
    }
    
     public void listarPostulaciones_Stock(){
        int contador=0;
      
        for (AvisoBean aviso: avisos_stock) {
            contador++;
        System.out.println(contador+ " : id_empresa: "+ aviso.getId_empresa()
        +" id_Aviso: "+aviso.getId_aviso()
        +" Nombre_Empresa: "+aviso.getNombre_empresa()
        + " RUC: "+aviso.getRuc()
        + " Pais: " +aviso.getPais()
        + " Fecha: " +aviso.getFecha()
        + " Postulaciones: "+aviso.getPostulaciones());    
             
            
        }
        
        
    }

    public List<AvisoBean> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<AvisoBean> avisos) {
        this.avisos = avisos;
    }

    public String[] getFechas1() {
        return fechas1;
    }

    public void setFechas1(String[] fechas1) {
        this.fechas1 = fechas1;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<AvisoBean> getAvisos_stock() {
        return avisos_stock;
    }

    public void setAvisos_stock(List<AvisoBean> avisos_stock) {
        this.avisos_stock = avisos_stock;
    }
    
    
    
}
