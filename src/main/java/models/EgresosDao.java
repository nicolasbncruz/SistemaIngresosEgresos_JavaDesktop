package models;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EgresosDao {
   conexion.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.getConnection();

    public String registrar(EgresosModel cl) {
        String sql = "INSERT INTO egresos (monto, descripcion, id_usuario) VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cl.getMonto());
            ps.setString(2, cl.getDescripcion());
            ps.setInt(3, cl.getId_usuario());
            ps.execute();
            return "registrado";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List listar(String valorBusqueda, int desde, int porPagina, int id_usuario) {
        List<EgresosModel> Lista = new ArrayList();
        String sql = "SELECT * FROM egresos WHERE id_usuario = " + id_usuario + " LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT * FROM egresos WHERE descripcion LIKE '%" + valorBusqueda + "%' AND id_usuario = " + id_usuario + " LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                EgresosModel egr = new EgresosModel();
                egr.setId(rs.getInt("id"));
                egr.setMonto(rs.getDouble("monto"));
                egr.setDescripcion(rs.getString("descripcion"));
                Lista.add(egr);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM egresos WHERE id = ?";
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

    public String modificar(EgresosModel cl) {
        String sql = "UPDATE egresos SET monto=?, descripcion=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cl.getMonto());
            ps.setString(2, cl.getDescripcion());
            ps.setInt(3, cl.getId());
            ps.execute();
            return "modificado";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    } 
    
    public double total(String valorBusqueda, int id_usuario) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM egresos WHERE id_usuario = " + id_usuario;
        String busqueda = "SELECT COUNT(*) AS total FROM egresos WHERE descripcion LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%' WHERE id_usuario = " + id_usuario;
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
}
