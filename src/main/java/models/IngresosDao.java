package models;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class IngresosDao {

    conexion.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.getConnection();

    public String registrar(IngresosModel cl) {
        String sql = "INSERT INTO ingresos (monto, descripcion, id_cliente, id_usuario) VALUES (?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cl.getMonto());
            ps.setString(2, cl.getDescripcion());
            ps.setInt(3, cl.getId_cliente());
            ps.setInt(4, cl.getId_usuario());
            ps.execute();
            return "registrado";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List listar(String valorBusqueda, int desde, int porPagina, int id_usuario) {
        List<IngresosModel> Lista = new ArrayList();
        String sql = "SELECT i.*, c.nombre, c.apellido FROM ingresos i INNER JOIN clientes c ON i.id_cliente = c.id WHERE id_usuario = " + id_usuario + " LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT i.*, c.nombre, c.apellido FROM ingresos i INNER JOIN clientes c ON i.id_cliente = c.id WHERE i.descripcion LIKE '%" + valorBusqueda + "%' OR c.nombre LIKE '%" + valorBusqueda + "%' AND id_usuario = " + id_usuario + " LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                IngresosModel ing = new IngresosModel();
                ing.setId(rs.getInt("id"));
                ing.setMonto(rs.getDouble("monto"));
                ing.setDescripcion(rs.getString("descripcion"));
                ing.setCliente(rs.getString("nombre") + " " + rs.getString("apellido"));
                Lista.add(ing);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM ingresos WHERE id = ?";
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

    public String modificar(IngresosModel cl) {
        String sql = "UPDATE ingresos SET monto=?, descripcion=?, id_cliente=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cl.getMonto());
            ps.setString(2, cl.getDescripcion());
            ps.setInt(3, cl.getId_cliente());
            ps.setInt(4, cl.getId());
            ps.execute();
            return "modificado";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }
    
    public double total(String valorBusqueda, int id_usuario) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM ingresos WHERE id_usuario = " + id_usuario;
        String busqueda = "SELECT COUNT(*) AS total FROM ingresos WHERE descripcion LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%' WHERE id_usuario = " + id_usuario ;        try {
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
    
    public List llenarClientes(){
        List<ClientesModel> Lista = new ArrayList();
        String sql = "SELECT * FROM clientes";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                ClientesModel pr = new ClientesModel();
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                Lista.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }
    
    public void reportes(String nombre, int id_usuario) {
        JasperReport reporte;
        con = cn.getConnection();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/reporte.jpg"));
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/report/" + nombre));
            Map parametro = new HashMap();
            parametro.put("logoEmpresa", icon.getImage());
            parametro.put("nombreEmpresa", "SISTEMAS FREE");
            parametro.put("id_usuario", id_usuario);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametro, con);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
