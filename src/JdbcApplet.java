import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class JdbcApplet extends Applet {
	private Connection conn;

	private Timestamp created = new Timestamp(System.currentTimeMillis());

	public void init() {
		this.setBackground(Color.blue);
		try {
			System.out
					.println("init(): loading OracleDriver for applet created at "
							+ created.toString());
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("init(): getting connection");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@220.125.154.207:1521:cdc", "cdc",
					"cdcdnsdud123");
		} catch (ClassNotFoundException e) {
			System.err.println("init(): ClassNotFoundException: "
					+ e.getMessage());
		} catch (SQLException e) {
			System.err.println("init(): SQLException: " + e.getMessage());
		}
	}

	public void start() {
		System.out.println("start(): ");
	}

	public void stop() {
		System.out.println("stop(): ");
	}

	public void paint(Graphics g) {
		System.out.println("paint(): querying the database");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select 'Hello '||initcap(USER) result from dual");
			while (rset.next())
				g.drawString(rset.getString(1), 10, 10);
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("paint(): SQLException: " + e.getMessage());
		}
	}

	public void destroy() {
		System.out
				.println("destroy(): closing connection for applet created at "
						+ created.toString());
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("destroy: SQLException: " + e.getMessage());
		}
	}
}