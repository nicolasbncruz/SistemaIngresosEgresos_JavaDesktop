package models;
public class ClientesModel {
    private int id;
    private String nombre;
    private String Apellido;
    private String correo;
    private String direccion;
    private String telefono;
    
    public ClientesModel() {
    }

    public ClientesModel(int id, String nombre, String Apellido, String correo, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
