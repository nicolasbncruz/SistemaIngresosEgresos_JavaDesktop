package models;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientesDao {
    conexion.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.getConnection();

    public String registrar(ClientesModel cl) {
        String consulta = "SELECT * FROM clientes WHERE nombre = ?";
        String sql = "INSERT INTO clientes (nombre, apellido, correo, direccion, telefono) VALUES (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getApellido());
                ps.setString(3, cl.getCorreo());
                ps.setString(4, cl.getDireccion());
                ps.setString(5, cl.getTelefono());
                ps.execute();
                return "registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List listar(String valorBusqueda, int desde, int porPagina) {
        List<ClientesModel> Lista = new ArrayList();
        String sql = "SELECT * FROM clientes LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT * FROM clientes WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%' LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ClientesModel cl = new ClientesModel();
                cl.setId(rs.getInt("id"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setCorreo(rs.getString("correo"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                Lista.add(cl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }    

    public String modificar(ClientesModel cl) {
        String consulta = "SELECT * FROM clientes WHERE nombre = ? AND id != ?";
        String sql = "UPDATE clientes SET nombre=?, apellido=?, correo=?, direccion=?, telefono=? WHERE id=?";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getNombre());
            ps.setInt(2, cl.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getApellido());
                ps.setString(3, cl.getCorreo());
                ps.setString(4, cl.getDireccion());
                ps.setString(5, cl.getTelefono());
                ps.setInt(6, cl.getId());
                ps.execute();
                return "modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }
    
    public double total(String valorBusqueda) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM clientes";
        String busqueda = "SELECT COUNT(*) AS total FROM clientes WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%'";
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }
    
    public ClientesModel buscar(int id) {
        ClientesModel cl = new ClientesModel();
        String sql = "SELECT c.id, c.nombre, c.apellido FROM ingresos i INNER JOIN clientes c ON i.id_cliente = c.id WHERE i.id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setNombre(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
}
