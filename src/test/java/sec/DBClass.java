package sec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBClass {
	public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // replace below details
        String dbhost = "host";
        String dbname = "dbname";
        String url = "jdbc:postgresql://"+dbhost+".com:5432/"+dbname;
        String username = "niru";
        String password = "test";

        try {
            Connection db = DriverManager.getConnection(url, username, password);
            // create object for the Statement class
            Statement st = db.createStatement();
            // execute the quesry on database
            ResultSet rs = st.executeQuery("select count(*)\r\n"
            		+ "from\r\n"
            		+ "    project p\r\n"
            		+ "    join github_organization go2 on p.project_id::text=go2.project_id::text\r\n"
            		+ "    join repository r on go2.organization_id::text=r.organization_id::text\r\n"
            		+ "    join snyk_issue si on r.repository_id::text=si.repository_id::text\r\n"
            		+ "where\r\n"
            		+ "    p.status = 'Active'\r\n"
            		+ "    and p.project_name <> 'The Linux Foundation'\r\n"
            		+ "    and go2.installation_id != 0\r\n"
            		+ "    and go2.project_id is not null\r\n"
            		+ "    and si.applicable = true\r\n"
            		+ "    and r.is_enable = true\r\n"
            		+ "    and si.status = 'fixed'");
            System.out.println("Data retrieved from the PostgreSQL database ");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | "+rs.getString(2) +" | "+ rs.getString(3));
            }
            rs.close();
            // close the result set
            st.close();
            //close the database connection
            db.close();
            }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}