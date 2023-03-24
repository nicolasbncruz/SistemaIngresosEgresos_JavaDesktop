package models;

import conexion.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class AdminDao {
    conexion.Conexion cn = new Conexion();
    ResultSet rs;
    Connection con = cn.getConnection();
    
    final String series1 = "Ingresos";
        final String series2 = "Egresos";
    
    public void reporteGrafico(JPanel panel, String anio, int id_usuario) {
        String desde = anio + "-01-01 00:00:00";
        String hasta = anio + "-12-31 23:59:59";
        try {
            String sql1 = "SELECT SUM(IF(MONTH(fecha) = 1, monto, 0)) AS ene, "
                    + "SUM(IF(MONTH(fecha) = 2, monto, 0)) AS feb, "
                    + "SUM(IF(MONTH(fecha) = 3, monto, 0)) AS mar, "
                    + "SUM(IF(MONTH(fecha) = 4, monto, 0)) AS abr, "
                    + "SUM(IF(MONTH(fecha) = 5, monto, 0)) AS may, "
                    + "SUM(IF(MONTH(fecha) = 6, monto, 0)) AS jun, "
                    + "SUM(IF(MONTH(fecha) = 7, monto, 0)) AS jul, "
                    + "SUM(IF(MONTH(fecha) = 8, monto, 0)) AS ago, "
                    + "SUM(IF(MONTH(fecha) = 9, monto, 0)) AS sep, "
                    + "SUM(IF(MONTH(fecha) = 10, monto, 0)) AS oct, "
                    + "SUM(IF(MONTH(fecha) = 11, monto, 0)) AS nov, "
                    + "SUM(IF(MONTH(fecha) = 12, monto, 0)) AS dic FROM ingresos WHERE fecha BETWEEN '" + desde + "' AND '" + hasta + "' AND id_usuario = " + id_usuario;
            String sql2 = "SELECT SUM(IF(MONTH(fecha) = 1, monto, 0)) AS ene, "
                    + "SUM(IF(MONTH(fecha) = 2, monto, 0)) AS feb, "
                    + "SUM(IF(MONTH(fecha) = 3, monto, 0)) AS mar, "
                    + "SUM(IF(MONTH(fecha) = 4, monto, 0)) AS abr, "
                    + "SUM(IF(MONTH(fecha) = 5, monto, 0)) AS may, "
                    + "SUM(IF(MONTH(fecha) = 6, monto, 0)) AS jun, "
                    + "SUM(IF(MONTH(fecha) = 7, monto, 0)) AS jul, "
                    + "SUM(IF(MONTH(fecha) = 8, monto, 0)) AS ago, "
                    + "SUM(IF(MONTH(fecha) = 9, monto, 0)) AS sep, "
                    + "SUM(IF(MONTH(fecha) = 10, monto, 0)) AS oct, "
                    + "SUM(IF(MONTH(fecha) = 11, monto, 0)) AS nov, "
                    + "SUM(IF(MONTH(fecha) = 12, monto, 0)) AS dic FROM egresos WHERE fecha BETWEEN '" + desde + "' AND '" + hasta + "' AND id_usuario = " + id_usuario;
            con = cn.getConnection();
            PreparedStatement ps1 = con.prepareStatement(sql1);            
            rs = ps1.executeQuery();
            DefaultCategoryDataset ds = new DefaultCategoryDataset();
            if (rs.next()) {
                ds.addValue(rs.getInt("ene"), series1, "Ene");
                ds.addValue(rs.getInt("feb"), series1, "Feb");
                ds.addValue(rs.getInt("mar"), series1, "Mar");
                ds.addValue(rs.getInt("abr"), series1, "Abr");
                ds.addValue(rs.getInt("may"), series1, "May");
                ds.addValue(rs.getInt("jun"), series1, "Jun");
                ds.addValue(rs.getInt("jul"), series1, "Jul");
                ds.addValue(rs.getInt("ago"), series1, "Ago");
                ds.addValue(rs.getInt("sep"), series1, "Sep");
                ds.addValue(rs.getInt("oct"), series1, "Oct");
                ds.addValue(rs.getInt("nov"), series1, "Nov");
                ds.addValue(rs.getInt("dic"), series1, "Dic");
            }
            PreparedStatement ps2 = con.prepareStatement(sql2);
            rs = ps2.executeQuery();
            if (rs.next()) {                
                ds.addValue(rs.getInt("ene"), series2, "Ene");
                ds.addValue(rs.getInt("feb"), series2, "Feb");
                ds.addValue(rs.getInt("mar"), series2, "Mar");
                ds.addValue(rs.getInt("abr"), series2, "Abr");
                ds.addValue(rs.getInt("may"), series2, "May");
                ds.addValue(rs.getInt("jun"), series2, "Jun");
                ds.addValue(rs.getInt("jul"), series2, "Jul");
                ds.addValue(rs.getInt("ago"), series2, "Ago");
                ds.addValue(rs.getInt("sep"), series2, "Sep");
                ds.addValue(rs.getInt("oct"), series2, "Oct");
                ds.addValue(rs.getInt("nov"), series2, "Nov");
                ds.addValue(rs.getInt("dic"), series2, "Dic");
            }
            
            JFreeChart jf = ChartFactory.createBarChart3D("INGRESOS E EGRESOS", "", "Total", ds, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel f = new ChartPanel(jf);
            f.setSize(780, 250);
            panel.add(f);
            

        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public int totalDatos(String table) {
        double total = 0;
        String sql = "SELECT COUNT(*) AS total FROM " + table;
        try {
            PreparedStatement ps = con.prepareStatement(sql);        
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return (int) total;
    }
}
