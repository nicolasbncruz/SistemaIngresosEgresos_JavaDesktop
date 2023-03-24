package models;
public class IngresosModel {
    private int id;
    private double monto;
    private String Descripcion;
    private int id_usuario;
    private int id_cliente;
    private String usuario;
    private String cliente;

    public IngresosModel() {
    }

    public IngresosModel(int id, double monto, String Descripcion, int id_usuario, int id_cliente, String usuario, String cliente) {
        this.id = id;
        this.monto = monto;
        this.Descripcion = Descripcion;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.usuario = usuario;
        this.cliente = cliente;
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

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    
    
}
