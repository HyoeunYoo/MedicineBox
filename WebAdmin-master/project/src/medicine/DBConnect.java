package medicine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import medicine.Medi;
import medicine.User;

public class DBConnect<Search> {
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	ResultSet rs;
	String url = "jdbc:mariadb://mariadb1.cvs2wwdrdvzr.ap-northeast-2.rds.amazonaws.com:3306/medicinebox";

	void connect() {

		try {
			String dbID = "rdsuser";
			String dbPasswd = "medicinebox1";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, dbID, dbPasswd);
			System.out.println("Database Connected!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

		
	/// DB close
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 로그인 확인
	public int loginCheck(String id, String pw) {
		connect();
		
		String sql = "select admin_pwd from admin where admin_id = ?";
		
		String dbpw = "";
		int x = -1;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dbpw = rs.getString("admin_pwd");
				
				if (dbpw.equals(pw))
					x = 1;
				else
					x = 0;
			} else {
				x = -1;
			}
			rs.close();
			
		} catch (Exception e) {
			
		}finally {
			disconnect();
		}
		return x;
	}
	
	//비밀번호 확인
	public int passwdCheck(String id, String oldPw) {
		connect();
		
		String sql = "select admin_pwd from admin where admin_id = ?";
		
		String dbpw = "";
		int x = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dbpw = rs.getString("admin_pwd");
				
				if (dbpw.equals(oldPw))
					x = 1;
				else
					x = 0;
			}
			rs.close();
			
		} catch (Exception e) {
			
		}finally {
			disconnect();
		}
		return x;
	}
	
	//비밀번호 수정
	public boolean passwdEdit(String id, String newPw) {
		connect();
		
		String sql = "UPDATE admin SET admin_pwd = ? WHERE admin_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			return false;			
		}finally {
			disconnect();
		}
		return true;
	}
	
	
	// 보관 수 5위까지
	public List<Medi> getMediStore(){
		List<Medi> datas = new ArrayList<>();
		connect();
		
		String sql = "SELECT medi_name, medi_store, DENSE_RANK() OVER (ORDER BY medi_store DESC) store_rank FROM medi limit 5";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Medi medi = new Medi();
				
				medi.setMedi_name(rs.getString("medi_name"));
				medi.setMedi_store(rs.getInt("medi_store"));
				
				datas.add(medi);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	// 검색 수 5위까지
	public List<Medi> getMediSearch(){
		List<Medi> datas = new ArrayList<>();
		connect();
		
		String sql = "SELECT medi_name, medi_search, DENSE_RANK() OVER (ORDER BY medi_search DESC) search_rank FROM medi limit 5";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Medi medi = new Medi();
				
				medi.setMedi_name(rs.getString("medi_name"));
				medi.setMedi_search(rs.getInt("medi_search"));
				
				datas.add(medi);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	
	// 사용자 전체 로우 수
	public int getUserTotalRows() {
		connect();
		
		int count = 0;
		
		String sql = "select count(user_id) from user";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("count(user_id)");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return count;
	}
	
	// 전체 사용자 정보 리스트
	public List<User> getUserList(int start, int end){
		List<User> datas = new ArrayList<>();
		connect();
		
		String sql = "select * from user limit ?, ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				
				user.setUser_id(rs.getString("user_id"));
				user.setUser_pwd(rs.getString("user_pwd"));
				user.setUser_name(rs.getString("user_name"));
				user.setUser_phone(rs.getString("user_phone"));
				user.setUser_device(rs.getString("user_device"));
				user.setUser_alarm(rs.getInt("user_alarm"));
				
				datas.add(user);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	// 사용자 정보 수정
	public boolean updateUser(String id, String name, String pwd, String phone, String device, String alarm) {
		connect();
		
		String sql = "UPDATE user SET user_name=?, user_pwd=?, user_phone=?, user_device=?, user_alarm=? WHERE user_id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pwd);
			pstmt.setString(3, phone);
			pstmt.setString(4, device);
			pstmt.setString(5, alarm);
			pstmt.setString(6, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	// 사용자 검색 리스트
	public List<User> getUser(String user){
		List<User> datas = new ArrayList<>();
		connect();
		
		String keyword = "%" + user + "%";
		
		String sql = "select * from user where user_id like ?";
		System.out.println(sql);
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User userS = new User();
				
				userS.setUser_id(rs.getString("user_id"));
				userS.setUser_pwd(rs.getString("user_pwd"));
				userS.setUser_name(rs.getString("user_name"));
				userS.setUser_phone(rs.getString("user_phone"));
				userS.setUser_device(rs.getString("user_device"));
				userS.setUser_alarm(rs.getInt("user_alarm"));
				
				datas.add(userS);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
		
	
	// 의약품 추가
	public boolean insertMedi(String name, String photo, String effect, String use) {
		connect();
		
		String sql = "INSERT INTO medi (`medi_name`, `medi_photo`, `medi_effect`, `medi_use`) VALUES (?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, photo);
			pstmt.setString(3, effect);
			pstmt.setString(4, use);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	// 의약품 삭제
	public boolean deleteMedi(String medi_num) {
		String sql = "DELETE FROM medi where medi_num = ?";
		connect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, medi_num);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return true;
	}
	
	// 의약품 정보 수정
	public boolean updateMedi(String num, String name, String photo, String effect, String use) {
		connect();
		
		if (photo == null) {
			String sql = "UPDATE medi SET medi_name=?, medi_effect=?, medi_use=? WHERE medi_num=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, effect);
				pstmt.setString(3, use);
				pstmt.setString(4, num);
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				return false;
			} finally {
				disconnect();
			}
			return true;
		} else {
			String sql = "UPDATE medi SET medi_name=?, medi_photo=?, medi_effect=?, medi_use=? WHERE medi_num=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, photo);
				pstmt.setString(3, effect);
				pstmt.setString(4, use);
				pstmt.setString(5, num);
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				return false;
			} finally {
				disconnect();
			}
			return true;
		}
	}
	
	// 의약품 전체 로우 수 
	public int getMediTotalRows() {
		connect();
		
		int count = 0;
		
		String sql = "select count(medi_num) from medi";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int index = 0;
			if (rs.next()) {
				count = rs.getInt("count(medi_num)");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return count;
	}
	
	// 전체 의약품 정보 리스트
	public List<Medi> getMediList(int start, int end){
		List<Medi> datas = new ArrayList<>();
		connect();
		
		String sql = "select * from medi limit ?, ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Medi medi = new Medi();
				
				medi.setMedi_num(rs.getInt("medi_num"));
				medi.setMedi_name(rs.getString("medi_name"));
				medi.setMedi_photo(rs.getString("medi_photo"));
				medi.setMedi_effect(rs.getString("medi_effect"));
				medi.setMedi_use(rs.getString("medi_use"));
				medi.setMedi_store(rs.getInt("medi_store"));
				medi.setMedi_search(rs.getInt("medi_search"));
				
				datas.add(medi);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	// 의약품 검색 리스트
	public List<Medi> getMedi(String medi){
		List<Medi> datas = new ArrayList<>();
		connect();
		
		String keyword = "%" + medi + "%";
		
		String sql = "select * from medi where medi_name like ?";
		System.out.println(sql);
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Medi mediS = new Medi();
				
				mediS.setMedi_num(rs.getInt("medi_num"));
				mediS.setMedi_name(rs.getString("medi_name"));
				mediS.setMedi_photo(rs.getString("medi_photo"));
				mediS.setMedi_effect(rs.getString("medi_effect"));
				mediS.setMedi_use(rs.getString("medi_use"));
				mediS.setMedi_store(rs.getInt("medi_store"));
				mediS.setMedi_search(rs.getInt("medi_search"));
				
				datas.add(mediS);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	
	// 검색어 전체 로우 수
	public int getNoneTotalRows() {
		connect();
		
		int count = 0;
		
		String sql = "select count(none_num) from none";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("count(none_num)");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return count;
	}
	
	//전체 검색어 정보 리스트
	public List<None> getNoneList(int start, int end){
		List<None> datas = new ArrayList<>();
		connect();
		
		String sql = "select * from none limit ?, ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				None none = new None();
				
				none.setNone_num(rs.getInt("none_num"));
				none.setNone_name(rs.getString("none_name"));
				none.setNone_store(rs.getInt("none_store"));
				none.setNone_search(rs.getInt("none_search"));
				
				
				datas.add(none);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return datas;
	}
	
	// 검색어 삭제
	public boolean deleteNone(String none_num) {
		String sql = "DELETE FROM none where none_num = ?";
		connect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, none_num);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return true;
	}
	
}
