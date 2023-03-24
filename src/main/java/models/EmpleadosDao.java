package models;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmpleadosDao {
    conexion.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.getConnection();

    public String registrar(EmpleadosModel cl) {
        String consulta = "SELECT * FROM empleados WHERE nombre = ?";
        String sql = "INSERT INTO empleados (nombre, apellido, correo, direccion, clave) VALUES (?,?,?,?,?)";
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
                ps.setString(5, cl.getClave());
                ps.execute();
                return "registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List listar(String valorBusqueda, int desde, int porPagina) {
        List<EmpleadosModel> Lista = new ArrayList();
        String sql = "SELECT * FROM empleados LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT * FROM empleados WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%' LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                EmpleadosModel emp = new EmpleadosModel();
                emp.setId(rs.getInt("id"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setCorreo(rs.getString("correo"));
                emp.setDireccion(rs.getString("direccion"));
                Lista.add(emp);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
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

    public String modificar(EmpleadosModel cl) {
        String consulta = "SELECT * FROM empleados WHERE nombre = ? AND id != ?";
        String sql = "UPDATE empleados SET nombre=?, apellido=?, correo=?, direccion=? WHERE id=?";
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
                ps.setInt(5, cl.getId());
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
        String sql = "SELECT COUNT(*) AS total FROM empleados";
        String busqueda = "SELECT COUNT(*) AS total FROM empleados WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%'";
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
    
    public EmpleadosModel validar(String correo, String pass) {
        EmpleadosModel emp = new EmpleadosModel();
        String sql = "SELECT * FROM empleados WHERE correo = ? AND clave = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                emp.setId(rs.getInt("id"));
                emp.setNombre(rs.getString("nombre"));
                emp.setCorreo(rs.getString("correo"));
                emp.setClave(rs.getString("clave"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return emp;
    }
}
