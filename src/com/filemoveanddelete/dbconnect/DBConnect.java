package com.filemoveanddelete.dbconnect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.filemoveanddelete.model.FormatModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramin
 */
public class DBConnect {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public static Connection ConnectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:formatElement.sqlite");
            JOptionPane.showMessageDialog(null, "Connect");
            return conn;
        } catch (Exception e) {
            

        }
        return null;
    }

    private void close() {
        try {
            if (!conn.isClosed()) {
                conn.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    public void addFormat(FormatModel fm) {
        ConnectDB();
        try {

            ps = conn.prepareStatement("INSERT INTO formatnames(ID,formatname)VALUES(0,?,?)");
            ps.setInt(1, fm.getId());
            ps.setString(2, fm.getFormats());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "added");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            
               
               close();
           
        }

    }

    public List<FormatModel> SelectFormats() {
        List<FormatModel> list = new ArrayList();
        ConnectDB();
        try {
            ps = conn.prepareStatement("SELECT * FROM formatnames");
            rs = ps.executeQuery();
            while (rs.next()) {
                FormatModel model = new FormatModel();
                model.setId(rs.getInt("ID"));
                model.setFormats(rs.getString("formatname"));
                list.add(model);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close();
        }
        return list;
    }
}
