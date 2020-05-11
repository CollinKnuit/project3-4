package org.FF.GUI.common.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseQueryClass {
	
		private ConnectionClass connection = new ConnectionClass();
		
		/**
		 * 
		 */
		public void closeConnection() {
			
			while(!connection.closeConnection());
			
		}
		
		/**
		 * pass in a acountID and plainPassword and this query checks if the password matches the acountID in the database
		 * 
		 * @param acountId
		 * @param plainPassword
		 * @return {@code boolean} if the plaintext password 
		 * is hased the same return true
		 * @throws SQLException
		 */
		public HashMap<Boolean, Integer> checkPassword(int acountId, String plainPassword) throws SQLException {
			var conn = connection.getConnection();
			
			String query 	= 	"SELECT Password,  Password_Atempt_Wrong "
				 			+	"FROM acount "
				 			+  	"WHERE (AcountID = ?) "
				 			+   "AND Password_Atempt_Wrong < 3;";
			
			
			
			String stored_hash = "";
			int Password_Atempt_Wrong = 3;
			PreparedStatement preparedStmt = null;
			
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountId);
				
				ResultSet resultSet = preparedStmt.executeQuery();	
				
				while (resultSet.next()) {
					stored_hash = resultSet.getString(1);	
					Password_Atempt_Wrong = resultSet.getInt(2);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {
				
		        if(preparedStmt != null )preparedStmt.close();
		        
			}
			
			boolean a = false;
			
			if(!Password.checkPassword(plainPassword, stored_hash)) {
				changeLoginNumber(acountId);
				 a = false;
				 Password_Atempt_Wrong++;
			} else {
				a = true;
			}
			
			HashMap<Boolean, Integer> map = new HashMap<Boolean, Integer>();
			
			map.put(a,Password_Atempt_Wrong);

			
			return map ;
		}
		
		
		/**
		 * pass in a acountID to increase the attribute password_attempt_wrong of the acount that matches the parameter acountID
		 * in the database
		 * 
		 * @param acountID
		 * @throws SQLException
		 */
		private void changeLoginNumber(int acountID) throws SQLException {
			var conn = connection.getConnection();
			
			String query 	= 	"UPDATE acount "
				 			+	"SET Password_Atempt_Wrong = Password_Atempt_Wrong + 1 "
				 			+  	"WHERE AcountID = ?; ";
			

			
			PreparedStatement preparedStmt = null;
			
			try {
				conn.setAutoCommit(false);
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountID);
				preparedStmt.executeUpdate();
				
				conn.commit();
				conn.setAutoCommit(true);
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				try{
					if(conn!=null) conn.rollback();
	
				} catch(SQLException se2){
					se2.printStackTrace();
				}
			
			} finally {
				
			        if(preparedStmt != null )preparedStmt.close();
			        conn.setAutoCommit(true);
			        
			}
		}
		
		/**
		 * pass in an acountID and receive the balance, rfidNumber and password_atempt_wrong
		 * and update the fields in acount with the values that you get back from the database through the query
		 * 
		 * @param acountId
		 * @return {@code Acount}
		 * @throws SQLException
		 */
		public Acount getAcountInfo(int acountId) throws SQLException {
		
			var conn = connection.getConnection();
			Acount acount = new  Acount();
			
			if(conn == null) return acount;
			
			String query 	= 	"SELECT `AcountID`, `Balance`, `RfidNumber`, Password_Atempt_Wrong  "
				 			+	"FROM acount "
				 			+  	"WHERE (`AcountID` = ?) "
				 			+   "AND `Password_Atempt_Wrong` < 3;";
			
			
			PreparedStatement preparedStmt = null;
			
			try {

				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountId);
				
				ResultSet resultSet = preparedStmt.executeQuery();	
				
				while (resultSet.next()) {
					acount.setAcountID(resultSet.getInt(1));	
					acount.setBalance(resultSet.getBigDecimal(2));	
					acount.setRfidNumber(resultSet.getString(3));
					acount.setPassword_Atempt_Wrong(resultSet.getInt(4));
					
				}
				
				setPassword_Atempt_WrongCorrect(acountId);
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {
				
		        if(preparedStmt != null )preparedStmt.close();
		        
			}
			return acount;
		}
		
		/**
		 * Withdraws money from a acount with acountID matching the parameter acountID
		 * 
		 * @param acountId
		 * @param money
		 * @throws SQLException
		 */
		public boolean withDrawMoney(int acountId, int money) throws SQLException {
			var bool = false;
			var conn = connection.getConnection();
			
			if(conn == null) return bool;
			
			String query 	= 	"INSERT INTO `transaction` "
						 	+	"(`Date`, `Amount`, `Acount_AcountID`) "
						 	+  "VALUES (now(), -?, ?);";
			
			String query1	=  "UPDATE `acount` "
						 	+  "SET `Balance` = `Balance` - ? "
						 	+  "WHERE (`AcountID` = ?);";
			
			PreparedStatement preparedStmt = null;
			PreparedStatement preparedStmt2 = null;
			
			try {
				
				conn.setAutoCommit(false);
				
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, money);
				preparedStmt.setInt(2, acountId);
				preparedStmt.executeUpdate();
				
				preparedStmt2 = conn.prepareStatement(query1);
				preparedStmt2.setInt(1, money);
				preparedStmt2.setInt(2, acountId);
				preparedStmt2.executeUpdate();
				

				conn.commit();
				conn.setAutoCommit(true);
				bool = true;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				try{
					if(conn!=null) conn.rollback();
					bool = false;
				} catch(SQLException se2){
					se2.printStackTrace();
					
				}
			
			} finally {
				
			        if(preparedStmt != null )preparedStmt.close();
			        if(preparedStmt2 != null )preparedStmt2.close();
			        conn.setAutoCommit(true);
			        
			}
			return bool;
		}
		
		/**
		 * check if the parameter rfid matches a rfid in the database and returns the matching acountID
		 * 
		 * @param rfid
		 * @return AcountID If its -1 it could not be found
		 * @throws SQLException
		 */
		public int checkRfid(String rfid) throws SQLException {
			int acountID = -1;
			var conn = connection.getConnection();
			
			if(conn == null) return acountID;
			
			PreparedStatement preparedStmt = null;
			
			String query =  "SELECT AcountID " + 
							"FROM acount " +  
							"WHERE (RfidNumber = ?)" + 
							"AND `Password_Atempt_Wrong` < 3;";
			
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, rfid);
				
				ResultSet resultSet = preparedStmt.executeQuery();
				
				while (resultSet.next()) {
					acountID = resultSet.getInt(1);	
				}
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
			} finally {
				
		        if(preparedStmt != null )preparedStmt.close();
		        
			}
			return acountID;
		}
		
		
		/**
		 * reset the password_atempt_wrong attribute in the database with acountID matching the parameter acountID
		 * 
		 * @param acountId
		 * @throws SQLException
		 */
		private void setPassword_Atempt_WrongCorrect(int acountId) throws SQLException {
			
			var conn = connection.getConnection();
			
			if(conn == null) return;
			
			String query 	= 	"UPDATE acount  "
						 	+	"SET Password_Atempt_Wrong = 0  "
						 	+   "WHERE (AcountID = ?);";
			
			
			PreparedStatement preparedStmt = null;
			
			try {
				
				conn.setAutoCommit(false);
				
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountId);
				preparedStmt.executeUpdate();
				
				conn.commit();
				conn.setAutoCommit(true);
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				try{
					if(conn!=null) conn.rollback();
	
				} catch(SQLException se2){
					se2.printStackTrace();
				}
			
			} finally {
				
			       if(preparedStmt != null )preparedStmt.close();
			       conn.setAutoCommit(true);
			        
			}
		}
		
}
