package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List <PowerOutage> getPowerOutagesForNerc (Nerc nerc) {
		
		String sql = "select customers_affected, event_type_id, timestamp(date_event_finished), timestamp(date_event_began) from poweroutages where nerc_id=?"+
		" Order By date_event_finished";
		List <PowerOutage> result = new ArrayList <> ();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, nerc.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				LocalDateTime inizio = LocalDateTime.ofInstant(rs.getTimestamp("timestamp(date_event_began)").toInstant(), ZoneId.systemDefault());
				LocalDateTime fine = LocalDateTime.ofInstant(rs.getTimestamp("timestamp(date_event_finished)").toInstant(), ZoneId.systemDefault());
				int numCus = rs.getInt("customers_affected");
				int id = rs.getInt("event_type_id");
				result.add(new PowerOutage(nerc, id, inizio, fine, numCus));
			}
			
			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	

}
