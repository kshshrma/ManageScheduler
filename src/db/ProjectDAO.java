package db;

import model.Project;
import java.sql.*;
import java.util.*;

public class ProjectDAO {

    // 1️⃣ FETCH ALL PROJECTS
    public static List<Project> getAllProjects() throws Exception {

        List<Project> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM projects ORDER BY id");

        while (rs.next()) {
            list.add(new Project(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("deadline_days"),
                    rs.getInt("revenue")
            ));
        }

        con.close();
        return list;
    }

    // 2️⃣ ADD PROJECT
    public static void addProject(String title, int deadline, int revenue) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO projects (title, deadline_days, revenue) VALUES (?, ?, ?)"
        );

        ps.setString(1, title);
        ps.setInt(2, deadline);
        ps.setInt(3, revenue);

        ps.executeUpdate();
        con.close();
    }

    // 3️⃣ DELETE PROJECT BY ID
    public static boolean deleteProject(int id) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM projects WHERE id = ?"
        );

        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        con.close();

        return rows > 0;
    }

    // 4️⃣ UPDATE PROJECT BY ID
    public static boolean updateProject(int id, String title, int deadline, int revenue) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "UPDATE projects SET title=?, deadline_days=?, revenue=? WHERE id=?"
        );

        ps.setString(1, title);
        ps.setInt(2, deadline);
        ps.setInt(3, revenue);
        ps.setInt(4, id);

        int rows = ps.executeUpdate();
        con.close();

        return rows > 0;
    }
}