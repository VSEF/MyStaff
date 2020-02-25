package com.taxsee.mystaff;

public class SubSubDepartmentEmployee {

    private int dep_id;
    private int subDep_id;
    private int subSubDep_id;
    private int emp_id;
    private String emp_name;
    private String emp_title;
    private String emp_email;
    private String emp_phone;

    public SubSubDepartmentEmployee(int dep_id, int subDep_id, int subSubDep_id, int emp_id, String emp_name, String emp_title, String emp_email, String emp_phone) {
        this.dep_id = dep_id;
        this.subDep_id = subDep_id;
        this.subSubDep_id = subSubDep_id;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_title = emp_title;
        this.emp_email = emp_email;
        this.emp_phone = emp_phone;
    }

    public SubSubDepartmentEmployee(int dep_id, int subDep_id, int subSubDep_id, int emp_id, String emp_name, String emp_title, String emp_phone) {
        this.dep_id = dep_id;
        this.subDep_id = subDep_id;
        this.subSubDep_id = subSubDep_id;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_title = emp_title;
        this.emp_email = "";
        this.emp_phone = emp_phone;
    }

    public SubSubDepartmentEmployee(int dep_id, int subDep_id, int subSubDep_id, int emp_id, String emp_name, String emp_title) {
        this.dep_id = dep_id;
        this.subDep_id = subDep_id;
        this.subSubDep_id = subSubDep_id;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_title = emp_title;
        this.emp_email = "";
        this.emp_phone = "";
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public int getSubDep_id() {
        return subDep_id;
    }

    public void setSubDep_id(int subDep_id) {
        this.subDep_id = subDep_id;
    }

    public int getSubSubDep_id() {
        return subSubDep_id;
    }

    public void setSubSubDep_id(int subSubDep_id) {
        this.subSubDep_id = subSubDep_id;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_title() {
        return emp_title;
    }

    public void setEmp_title(String emp_title) {
        this.emp_title = emp_title;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }

}
