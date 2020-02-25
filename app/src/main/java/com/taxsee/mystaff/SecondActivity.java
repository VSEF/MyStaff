package com.taxsee.mystaff;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private String login;
    private String password;

    public static List<Department> departmentList = new ArrayList<>();
    public static List<SubDepartment> subDepartmentList = new ArrayList<>();
    public static List<SubSubDepartment> subSubDepartmentList = new ArrayList<>();
    public static List<DepartmentEmployee> departmentEmployeeList = new ArrayList<>();
    public static List<SubDepartmentEmployee> subDepartmentEmployeeList = new ArrayList<>();
    public static List<SubSubDepartmentEmployee> subSubDepartmentEmployeeList = new ArrayList<>();

    private int depID;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);

        SharedPreferences myPreferences = getSharedPreferences(MainActivity.PREFERANCE, MODE_PRIVATE);
        login = myPreferences.getString(MainActivity.LOGIN_KEY, "");
        password = myPreferences.getString(MainActivity.PASSWORD_KEY, "");

        DownloadTaskDepartments downloadTaskDepartments = new DownloadTaskDepartments();

        downloadTaskDepartments.execute("https://contact.taxsee.com/Contacts.svc/GetAll?login=" + login + "&password=" + password);


    }

    public class DownloadTaskDepartments extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) throws NullPointerException {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);
                String deps = jsonObject.getString("Departments");
                JSONArray arr = new JSONArray(deps);

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject jsonObjectDeps = arr.getJSONObject(i);
                    int dep_id = jsonObjectDeps.getInt("ID");
                    String dep_name = jsonObjectDeps.getString("Name");
                    departmentList.add(new Department(jsonObjectDeps.getInt("ID"), jsonObjectDeps.getString("Name")));

                    if(jsonObjectDeps.has("Employees")){
                        JSONArray jsonArrayDepEmployees = new JSONArray(jsonObjectDeps.getString("Employees"));

                        for(int j=0;j<jsonArrayDepEmployees.length();j++){
                            JSONObject jsonObjectEmployee = jsonArrayDepEmployees.getJSONObject(j);
                            departmentEmployeeList.add(new DepartmentEmployee(jsonObjectDeps.getInt("ID"), jsonObjectEmployee.getInt("ID"),jsonObjectEmployee.getString("Name"), jsonObjectEmployee.getString("Title"),
                                    jsonObjectEmployee.getString("Email"), jsonObjectEmployee.getString("Phone")));
                        }
                    }

                    if(jsonObjectDeps.has("Departments")){

                        JSONArray jsonArraySubDepartments = new JSONArray(jsonObjectDeps.getString("Departments"));

                        for(int t=0;t<jsonArraySubDepartments.length();t++){

                            JSONObject jsonObjectSubDepartments = jsonArraySubDepartments.getJSONObject(t);

                            subDepartmentList.add(new SubDepartment(jsonObjectDeps.getInt("ID"),jsonObjectDeps.getString("Name"), jsonObjectSubDepartments.getInt("ID"),
                                    jsonObjectSubDepartments.getString("Name")));

                            if(jsonObjectSubDepartments.has("Employees")){

                                JSONArray jsonArraySubDepEmployees = new JSONArray(jsonObjectSubDepartments.getString("Employees"));

                                for(int s=0;s<jsonArraySubDepEmployees.length();s++){

                                    JSONObject jsonObjectSubDepEmployees = jsonArraySubDepEmployees.getJSONObject(s);

                                    if(jsonObjectSubDepEmployees.has("Email")) {

                                        subDepartmentEmployeeList.add(new SubDepartmentEmployee(dep_id, jsonObjectSubDepartments.getInt("ID"), jsonObjectSubDepEmployees.getInt("ID"),
                                                jsonObjectSubDepEmployees.getString("Name"),jsonObjectSubDepEmployees.getString("Title"), jsonObjectSubDepEmployees.getString("Email"),
                                                jsonObjectSubDepEmployees.getString("Phone")));

                                    }else if (jsonObjectSubDepEmployees.has("Phone")){
                                        subDepartmentEmployeeList.add(new SubDepartmentEmployee(dep_id, jsonObjectSubDepartments.getInt("ID"), jsonObjectSubDepEmployees.getInt("ID"),
                                                jsonObjectSubDepEmployees.getString("Name"),jsonObjectSubDepEmployees.getString("Title"), jsonObjectSubDepEmployees.getString("Phone")));

                                    }else{
                                        subDepartmentEmployeeList.add(new SubDepartmentEmployee(dep_id, jsonObjectSubDepartments.getInt("ID"), jsonObjectSubDepEmployees.getInt("ID"),
                                                jsonObjectSubDepEmployees.getString("Name"),jsonObjectSubDepEmployees.getString("Title")));
                                    }
                                }
                            }

                            if(jsonObjectSubDepartments.has("Departments")) {

                                JSONArray jsonArraySubSubDepartments = new JSONArray(jsonObjectSubDepartments.getString("Departments"));

                                for(int g=0;g<jsonArraySubSubDepartments.length();g++){

                                    JSONObject jsonObjectSubSubDepartments = jsonArraySubSubDepartments.getJSONObject(g);

                                    subSubDepartmentList.add(new SubSubDepartment(jsonObjectDeps.getInt("ID"),jsonObjectDeps.getString("Name"), jsonObjectSubDepartments.getInt("ID"),
                                            jsonObjectSubDepartments.getString("Name"), jsonObjectSubSubDepartments.getInt("ID"), jsonObjectSubSubDepartments.getString("Name")));

                                    if(jsonObjectSubSubDepartments.has("Employees")) {

                                        JSONArray jsonArraySubSubDepEmployees = new JSONArray(jsonObjectSubSubDepartments.getString("Employees"));

                                        for(int c =0;c<jsonArraySubSubDepEmployees.length();c++){

                                            JSONObject jsonObjectSubSubDepEmployees = jsonArraySubSubDepEmployees.getJSONObject(c);

                                            if(jsonObjectSubSubDepEmployees.has("Email")) {

                                                subSubDepartmentEmployeeList.add(new SubSubDepartmentEmployee(jsonObjectDeps.getInt("ID"), jsonObjectSubDepartments.getInt("ID"),
                                                        jsonObjectSubSubDepartments.getInt("ID"), jsonObjectSubSubDepEmployees.getInt("ID"),
                                                        jsonObjectSubSubDepEmployees.getString("Name"), jsonObjectSubSubDepEmployees.getString("Title"), jsonObjectSubSubDepEmployees.getString("Email"),
                                                        jsonObjectSubSubDepEmployees.getString("Phone")));

                                            }else if (jsonObjectSubSubDepEmployees.has("Phone")) {

                                                subSubDepartmentEmployeeList.add(new SubSubDepartmentEmployee(jsonObjectDeps.getInt("ID"), jsonObjectSubDepartments.getInt("ID"),
                                                        jsonObjectSubSubDepartments.getInt("ID"), jsonObjectSubSubDepEmployees.getInt("ID"),
                                                        jsonObjectSubSubDepEmployees.getString("Name"), jsonObjectSubSubDepEmployees.getString("Title"),
                                                        jsonObjectSubSubDepEmployees.getString("Phone")));

                                            }else{

                                                subSubDepartmentEmployeeList.add(new SubSubDepartmentEmployee(jsonObjectDeps.getInt("ID"), jsonObjectSubDepartments.getInt("ID"),
                                                        jsonObjectSubSubDepartments.getInt("ID"), jsonObjectSubSubDepEmployees.getInt("ID"),
                                                        jsonObjectSubSubDepEmployees.getString("Name"), jsonObjectSubSubDepEmployees.getString("Title")));

                                            }

                                        }

                                    }

                                }
                            }

                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final HashSet<String> arrDep2 = new HashSet<>();
            for(Department department:departmentList){
                arrDep2.add("DEPARTMENT ID: "+department.getDep_id() +", Name: "+ department.getDep_name());
            }
            final List<String> arrDep = new ArrayList<>(arrDep2);

            Collections.sort(arrDep);

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrDep);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int pos = (int) listView.getItemIdAtPosition(position);
                    String clickedDep = arrDep.get(pos);

                    String[] arrEmpdata = clickedDep.split(",");

                    String[] arrId = arrEmpdata[0].split(" ");

                    depID =Integer.valueOf(arrId[2]);


                    findNoEmployeesDepartments();


                    for(SubSubDepartmentEmployee subSubDepartmentEmployee:subSubDepartmentEmployeeList){
                        if(subSubDepartmentEmployee.getDep_id()==depID){
                            Intent myIntent = new Intent(getApplicationContext(), SubDepartmentActivity.class);
                            myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(depID));
                            startActivity(myIntent);
                        }
                    }

                    for(SubDepartmentEmployee subDepartmentEmployee:subDepartmentEmployeeList){
                        if(subDepartmentEmployee.getDep_id()==depID){
                            Intent myIntent = new Intent(getApplicationContext(), SubDepartmentActivity.class);
                            myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(depID));
                            startActivity(myIntent);
                        }
                    }

                   for(DepartmentEmployee departmentEmployee:departmentEmployeeList){
                       if(departmentEmployee.getDep_id()==depID){
                           Intent myIntent = new Intent(getApplicationContext(), DepartmentEmployeeAcitvity.class);
                           myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(depID));
                           startActivity(myIntent);

                       }

                   }

                }

            });


        }


    }

    public void findNoEmployeesDepartments(){
        int number=0;

        for(SubSubDepartmentEmployee subSubDepartmentEmployee:subSubDepartmentEmployeeList) {
            if (subSubDepartmentEmployee.getDep_id() == depID) {
                number += 1;
            }
        }
        for(SubDepartmentEmployee subDepartmentEmployee:subDepartmentEmployeeList) {
            if (subDepartmentEmployee.getDep_id() == depID) {
                number += 1;
            }
        }
        for(DepartmentEmployee departmentEmployee:departmentEmployeeList){

            if(departmentEmployee.getDep_id()==depID){
                number+=1;
            }
        }

        if(number==0){
            Toast.makeText(getApplicationContext(),"No Employees in the department", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Next step");
        alert.setCancelable(true);
        alert.setMessage("Do you want to log out?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }

        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();

    }
}
