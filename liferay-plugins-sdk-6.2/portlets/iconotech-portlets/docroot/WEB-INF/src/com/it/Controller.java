package com.it;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.com.icono.core.database.ICONOTECHConnection;

public class Controller extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> JSONROOT = new HashMap<String, Object>();
	private CrudDao dao;

	public Controller() {
		dao = new CrudDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		List<Proceso> studentList = new ArrayList<Proceso>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		response.setContentType("application/json");
		Connection con = null;

		if (action != null) {
			try {
				con = ICONOTECHConnection.getConnection();
				if (action.equals("list")) {

					// Fetch Data from User Table
					int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
					int recordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
					String studentId = request.getParameter("studentId");

					// Fetch Data from Student Table
					studentList = dao.getAllStudents(con, startPageIndex, recordsPerPage, studentId);
					// Get Total Record Count for Pagination
					int userCount = dao.getStudentCount(con, studentId);

					// Return in the format required by jTable plugin
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Records", studentList);
					JSONROOT.put("TotalRecordCount", userCount);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);
				} else if (action.equals("create") || action.equals("update")) {
					Proceso student = new Proceso();
					if (request.getParameter("studentId") != null) {
						String studentId = request.getParameter("studentId");
						student.setStudentId(studentId);
					}

					if (request.getParameter("name") != null) {
						String name = request.getParameter("name");
						student.setName(name);
					}

					if (request.getParameter("department") != null) {
						String department = request.getParameter("department");
						student.setDepartment(department);
					}

					if (request.getParameter("emailId") != null) {
						String emailId = request.getParameter("emailId");
						student.setEmailId(emailId);
					}

					if (action.equals("create")) {
						// Create new record
						dao.addStudent(con, student);
					} else if (action.equals("update")) {
						// Update existing record
						dao.updateStudent(con, student);
					}

					// Return in the format required by jTable plugin
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Record", student);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);
					response.getWriter().print(jsonArray);
				} else if (action.equals("delete")) {
					// Delete record
					if (request.getParameter("studentId") != null) {
						int studentId = Integer.parseInt(request.getParameter("studentId"));
						dao.deleteStudent(con, studentId);

						// Return in the format required by jTable plugin
						JSONROOT.put("Result", "OK");

						// Convert Java Object to Json
						String jsonArray = gson.toJson(JSONROOT);
						response.getWriter().print(jsonArray);
					}
				}
			} catch (Exception ex) {
				JSONROOT.put("Result", "ERROR");
				JSONROOT.put("Message", ex.getMessage());
				String error = gson.toJson(JSONROOT);
				response.getWriter().print(error);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}
	
	 
}