package bca;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;


public class DB {
	private Connection conn = null;
	private static DB db = new DB();

	private DB() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			Properties connectionProps = new Properties();
			connectionProps.put("user", prop.getProperty("db.user"));
			connectionProps.put("password", prop.getProperty("db.password"));

			String serverName = prop.getProperty("db.host");
			String port = prop.getProperty("db.port");
			String db = prop.getProperty("db.instance");

			conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/" + db, connectionProps);

			System.out.println("Connected to database");
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	public static ArrayList<Chemical> loadChemical() {
		ArrayList<Chemical> list = new ArrayList<>();
		String queryString = " select chemical_id, chemical_name" +
							" from chemicals" +
							" order by chemical_name ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
				ResultSet rs = queryStmt.executeQuery();) {

			while (rs.next()) {
				Chemical chemical = new Chemical(rs.getString("chemical_id"), rs.getString("chemical_name"));

				list.add(chemical);				
			}

		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return list;
	}
	public static ArrayList<Name> loadName() {
		ArrayList<Name> list = new ArrayList<>();
		String queryString = " select user_id, name" +
							" from user" +
							" order by name ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
				ResultSet rs = queryStmt.executeQuery();) {

			while (rs.next()) {
				Name name = new Name(rs.getString("user_id"), rs.getString("name"));

				list.add(name);				
			}

		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return list;
	}
	// public static ArrayList<Existing> loadExisting() {
	// 	ArrayList<Chemical> list = new ArrayList<>();
	// 	String queryString = " select chemical_id, chemical_name" +
	// 						" from chemicals" +
	// 						" order by chemical_name ";

	// 	try (
	// 			PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
	// 			ResultSet rs = queryStmt.executeQuery();) {

	// 		while (rs.next()) {
	// 			Chemical chemical = new Chemical(rs.getString("chemical_id"), rs.getString("chemical_name"));

	// 			list.add(chemical);				
	// 		}

	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}

	// 	return list;
	// }
	public static void insertChemical(String quantity,String description) {
		String query = "insert into transaction(quantity, description) values (?,?)";

		try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

			insertStmt.setString(1, quantity);
			insertStmt.setString(2, description);

			insertStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// public static void updateLeague(League league) {
	// 	String query = "update league set league_name = ? where league_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, league.getLeagueName());
	// 		updateStmt.setString(2, league.getLeagueID());

	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static void deleteLeague(String leagueID) {
	// 	String query = "delete from league where league_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, leagueID);
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static ArrayList<Coach> loadCoaches() {
	// 	ArrayList<Coach> list = new ArrayList<>();
	// 	String queryString = "select coach.coach_id, coach_last_name, coach_first_name, coach_phone_nbr, coach_email, count(team.coach_id) as used "
	// 			+
	// 			" from coach " +
	// 			" left join team on team.coach_id = coach.coach_id " +
	// 			" group by coach.coach_id, coach_last_name, coach_first_name, coach_phone_nbr, coach_email" +
	// 			" order by coach_last_name, coach_first_name";

	// 	try (
	// 			PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
	// 			ResultSet rs = queryStmt.executeQuery();) {

	// 		while (rs.next()) {
	// 			Coach c = new Coach(rs.getString("coach_id"), rs.getString("coach_last_name"),
	// 					rs.getString("coach_first_name"), rs.getString("coach_phone_nbr"),
	// 					rs.getString("coach_email"));

	// 			// A coach can be deleted if it is not used in a child table (team)
	// 			c.setCanEdit(rs.getInt("used") == 0);

	// 			list.add(c);
	// 		}

	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}

	// 	return list;
	// }

	// public static void insertCoach(Coach coach) {
	// 	String query = "insert into coach(coach_last_name, coach_first_name, coach_phone_nbr, coach_email) values (?,?,?,?)";

	// 	try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

	// 		insertStmt.setString(1, coach.getCoachLastName());
	// 		insertStmt.setString(2, coach.getCoachFirstName());
	// 		insertStmt.setString(3, coach.getCoachPhoneNbr());
	// 		insertStmt.setString(4, coach.getCoachEmail());

	// 		insertStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static void updateCoach(Coach coach) {
	// 	String query = "update coach set coach_last_name = ?, " +
	// 			" coach_first_name = ?, " +
	// 			" coach_phone_nbr = ?, " +
	// 			" coach_email = ? " +
	// 			" where coach_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, coach.getCoachLastName());
	// 		updateStmt.setString(2, coach.getCoachFirstName());
	// 		updateStmt.setString(3, coach.getCoachPhoneNbr());
	// 		updateStmt.setString(4, coach.getCoachEmail());
	// 		updateStmt.setString(5, coach.getCoachID());
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static void deleteCoach(String coachID) {
	// 	String query = "delete from coach where coach_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, coachID);
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static ArrayList<Team> loadTeams() {
	// 	ArrayList<Team> list = new ArrayList<>();
	// 	String queryString = "select team.team_id, name, team.league_id, team.coach_id, league_name, coach_last_name, coach_first_name, count(player.team_id) as used "
	// 			+
	// 			" from team " +
	// 			" left join league on team.league_id = league.league_id " +
	// 			" left join coach on team.coach_id = coach.coach_id " +
	// 			" left join player on team.team_id = player.team_id " +
	// 			" group by team.team_id, name, team.league_id, team.coach_id, league_name, coach_last_name, coach_first_name " +
	// 			" order by name ";

	// 	try (
	// 			PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
	// 			ResultSet rs = queryStmt.executeQuery();) {

	// 		while (rs.next()) {
	// 			Team t = new Team(rs.getString("team_id"), rs.getString("league_id"),
	// 					rs.getString("league_name"), rs.getString("name"),
	// 					rs.getString("coach_id"), rs.getString("coach_last_name"), rs.getString("coach_first_name"));

	// 			// A team can be deleted if it is not used in a child table (player)
	// 			t.setCanDelete(rs.getInt("used") == 0);

	// 			list.add(t);
	// 		}

	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}

	// 	return list;
	// }

	// public static void insertTeam(String leagueID, String teamName, String coachID) {
	// 	String query = "insert into team(name, league_id, coach_id) values (?,?,?)";

	// 	try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {



	// 		insertStmt.setString(1, teamName);
	// 		insertStmt.setString(2, leagueID);
	// 		insertStmt.setString(3, coachID);

	// 		insertStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static void updateTeam(Team team) {
	// 	String query = "update team set name = ?, " +
	// 			" league_id = ?, " +
	// 			" coach_id = ? " +
	// 			" where team_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, team.getTeamName());
	// 		updateStmt.setString(2, team.getLeague().getLeagueID());
	// 		updateStmt.setString(3, team.getCoach().getCoachID());
	// 		updateStmt.setString(4, team.getTeamID());
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// public static void deleteTeam(String teamID) {
	// 	String query = "delete from team where team_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, teamID);
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

}
