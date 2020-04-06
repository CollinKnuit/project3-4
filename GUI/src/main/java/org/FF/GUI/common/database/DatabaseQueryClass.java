package org.FF.GUI.common.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQueryClass {
	
		private ConnectionClass connection = new ConnectionClass();
		
		/**
		 * 
		 */
		public void closeConnection() {
			
			while(!connection.closeConnection());
			
		}
		
		/**
		 * 
		 * @param acountId
		 * @param plainPassword
		 * @return {@code boolean} if the plaintext password 
		 * is hased the same return true
		 * @throws SQLException
		 */
		public boolean checkPassword(int acountId, String plainPassword) throws SQLException {
			var conn = connection.getConnection();
			
			String query 	= 	"SELECT Password "
				 			+	"FROM acount "
				 			+  	"WHERE (AcountID = ?);";
			
			String stored_hash = "";
			PreparedStatement preparedStmt = null;
			
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountId);
				
				ResultSet resultSet = preparedStmt.executeQuery();	
				
				while (resultSet.next()) {
					stored_hash = resultSet.getString(1);	
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} finally {
				
		        if(preparedStmt != null )preparedStmt.close();
		        
			}
			return Password.checkPassword(plainPassword, stored_hash);
		}
		
		/**
		 * 
		 * @param acountId
		 * @return {@code Acount}
		 * @throws SQLException
		 */
		public Acount getAcountInfo(int acountId) throws SQLException {
		
			var conn = connection.getConnection();
			
			String query 	= 	"SELECT AcountID, Balance, RfidNumber "
				 			+	"FROM acount "
				 			+  	"WHERE (AcountID = ?);";
			
			Acount acount = new  Acount();
			PreparedStatement preparedStmt = null;
			
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, acountId);
				
				ResultSet resultSet = preparedStmt.executeQuery();	
				
				while (resultSet.next()) {
					acount.setAcountID(resultSet.getInt(1));	
					acount.setBalance(resultSet.getBigDecimal(2));	
					acount.setRfidNumber(resultSet.getString(3));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} finally {
				
		        if(preparedStmt != null )preparedStmt.close();
		        
			}
			return acount;
		}
		
		/**
		 * Withdraws money from a action
		 * @param acountId
		 * @param money
		 * @throws SQLException
		 */
		public void withDrawMoney(int acountId, int money) throws SQLException {
			
			var conn = connection.getConnection();
			
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
			        if(preparedStmt2 != null )preparedStmt2.close();
			        conn.setAutoCommit(true);
			        
			}
		}
		
		/**
		 * 
		 * @param rfid
		 * @return AcountID If its -1 it could not be found
		 * @throws SQLException
		 */
		public int checkRfid(String rfid) throws SQLException {

			var conn = connection.getConnection();
			
			PreparedStatement preparedStmt = null;
			int acountID = -1;
			String query =  "SELECT AcountID " + 
							"FROM acount " + 
							"WHERE (RfidNumber = ?);";
			
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, rfid);
				
				ResultSet resultSet =	preparedStmt.executeQuery();
				
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
		
		
}
