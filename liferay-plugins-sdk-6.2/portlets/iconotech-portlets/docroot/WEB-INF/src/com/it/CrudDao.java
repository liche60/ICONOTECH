package com.it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CrudDao {

	private PreparedStatement pStmt;

	public void addStudent(Connection dbConnection, Proceso student) {
		String insertQuery = "INSERT INTO STUDENT(STUDENTID, NAME, DEPARTMENT, EMAIL) VALUES (?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, student.getStudentId());
			pStmt.setString(2, student.getName());
			pStmt.setString(3, student.getDepartment());
			pStmt.setString(4, student.getEmailId());
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public void deleteStudent(Connection dbConnection, int studentId) {
		String deleteQuery = "DELETE FROM STUDENT WHERE STUDENTID = ?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, studentId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public void updateStudent(Connection dbConnection, Proceso student) throws ParseException {
		String updateQuery = "UPDATE STUDENT SET NAME = ?, DEPARTMENT = ?, EMAIL = ? WHERE STUDENTID = ?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, student.getName());
			pStmt.setString(2, student.getDepartment());
			pStmt.setString(3, student.getEmailId());
			pStmt.setString(4, student.getStudentId());
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public List<Proceso> getAllStudents(Connection dbConnection) {
		List<Proceso> students = new ArrayList<Proceso>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM core.personas ORDER BY identificacion";
		try {
			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Proceso student = new Proceso();

				student.setStudentId(rs.getString("identificacion"));
				student.setName(rs.getString("tipo_identificacion"));
				student.setDepartment(rs.getString("nombres"));
				student.setEmailId(rs.getString("apellidos"));
				students.add(student);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return students;
	}

	public List<Proceso> getAllStudents(Connection dbConnection, int startPageIndex, int recordsPerPage,
			String studentId) {
		List<Proceso> students = new ArrayList<Proceso>();
		// int range = startPageIndex + recordsPerPage;
		Statement stmt = null;
		ResultSet rs = null;
		String where = "";
		if (!studentId.equals("")) {
			where = " where nombres like '%" + studentId + "%' or  apellidos like '%" + studentId + "%' or  identificacion like '%" + studentId + "%' ";
		}
		String query = "SELECT * FROM core.personas " + where + " ORDER BY identificacion   offset " + startPageIndex + " limit "
				+ recordsPerPage + ";";
		System.out.println(query);
		try { 
			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Proceso student = new Proceso();

				student.setStudentId(rs.getString("identificacion"));
				student.setName(rs.getString("tipo_identificacion"));
				student.setDepartment(rs.getString("nombres"));
				student.setEmailId(rs.getString("apellidos"));
				students.add(student);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return students;
	}

	public int getStudentCount(Connection dbConnection, String studentId) {
		int count = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String where = "";
			if (!studentId.equals("")) {
				where = " where nombres like '%" + studentId + "%' or  apellidos like '%" + studentId + "%' or  identificacion like '%" + studentId + "%' ";
			}
			stmt = dbConnection.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM core.personas " + where + ";");
			while (rs.next()) {
				count = rs.getInt("COUNT");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}
}