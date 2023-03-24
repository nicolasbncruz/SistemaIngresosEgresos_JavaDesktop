package models;

public class EgresosModel {
   private int id;
    private double monto;
    private String Descripcion;
    private int id_usuario;
    private String nombre_usuario;

    public EgresosModel() {
    }

    public EgresosModel(int id, double monto, String Descripcion, int id_usuario, String nombre_usuario) {
        this.id = id;
        this.monto = monto;
        this.Descripcion = Descripcion;
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    
    
}
