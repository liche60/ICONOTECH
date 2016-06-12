package com.it;

public class Proceso {

        public Proceso() {
        }

        public Proceso(String procesoId, String name, String department, String emailId) {
                this.procesoId = procesoId;
                this.name = name;
                this.department = department;
                this.emailId = emailId;
        }

        private String procesoId;
        private String name;
        private String department;
        private String emailId;

        public String getStudentId() {
                return procesoId;
        }

        public String getName() {
                return name;
        }

        public String getDepartment() {
                return department;
        }

        public String getEmailId() {
                return emailId;
        }

        public void setStudentId(String studentId) {
                this.procesoId = studentId;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setDepartment(String department) {
                this.department = department;
        }

        public void setEmailId(String emailId) {
                this.emailId = emailId;
        }
}