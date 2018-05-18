/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 *
 * @author user
 */
public class AvisoBean {
    
   int id_empresa,id_aviso; 
   String nombre_empresa,ruc,pais,fecha;
   int postulaciones;
   int id_area;
   String nombre_area;

    public AvisoBean() {
        
      postulaciones=0;
        
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_aviso() {
        return id_aviso;
    }

    public void setId_aviso(int id_aviso) {
        this.id_aviso = id_aviso;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(int postulaciones) {
        this.postulaciones = postulaciones;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getNombre_area() {
        return nombre_area;
    }

    public void setNombre_area(String nombre_area) {
        this.nombre_area = nombre_area;
    }
   
    
    
}
