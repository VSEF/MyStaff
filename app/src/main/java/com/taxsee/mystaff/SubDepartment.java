package com.taxsee.mystaff;

public class SubDepartment {

    private int dep_id;
    private String dep_name;
    private int subDep_id;
    private String subDep_name;

    public SubDepartment(int dep_id, String dep_name, int subDep_id, String subDep_name) {
        this.dep_id = dep_id;
        this.dep_name = dep_name;
        this.subDep_id = subDep_id;
        this.subDep_name = subDep_name;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public int getSubDep_id() {
        return subDep_id;
    }

    public void setSubDep_id(int subDep_id) {
        this.subDep_id = subDep_id;
    }

    public String getSubDep_name() {
        return subDep_name;
    }

    public void setSubDep_name(String subDep_name) {
        this.subDep_name = subDep_name;
    }
}
