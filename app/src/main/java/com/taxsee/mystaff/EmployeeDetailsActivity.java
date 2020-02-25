package com.taxsee.mystaff;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.util.logging.Logger;

import static com.taxsee.mystaff.SecondActivity.departmentEmployeeList;
import static com.taxsee.mystaff.SecondActivity.subDepartmentEmployeeList;
import static com.taxsee.mystaff.SecondActivity.subSubDepartmentEmployeeList;

public class EmployeeDetailsActivity extends AppCompatActivity {

    ImageView iv_employee_photo;
    TextView tv_department_info, tv_subDepartment_info, tv_subSubDepartment_info;
    TextView tv_employee_id_data, tv_employee_name_data, tv_employee_title_data, tv_employee_email_data, tv_employee_phone_data;

    private String login;
    private String password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        iv_employee_photo = findViewById(R.id.imageView);

        tv_department_info = findViewById(R.id.tv_department_info);
        tv_subDepartment_info = findViewById(R.id.tv_subDepartment_info);
        tv_subSubDepartment_info = findViewById(R.id.tv_subSubDepartment_info);

        tv_employee_id_data = findViewById(R.id.tv_employee_id_data);
        tv_employee_name_data = findViewById(R.id.tv_employee_name_data);
        tv_employee_title_data = findViewById(R.id.tv_employee_title_data);
        tv_employee_email_data = findViewById(R.id.tv_employee_email_data);
        tv_employee_phone_data = findViewById(R.id.tv_employee_phone_data);


        Intent intent = getIntent();

        String empl_id = intent.getStringExtra(Intent.EXTRA_TEXT);

        for (DepartmentEmployee departmentEmployee : departmentEmployeeList) {
            if (departmentEmployee.getEmp_id() == Integer.valueOf(empl_id)) {
                //  Toast.makeText(this,departmentEmployee.getEmp_id(),Toast.LENGTH_LONG).show();
                tv_department_info.setText(String.valueOf(departmentEmployee.getDep_id()));
                tv_employee_id_data.setText(String.valueOf(departmentEmployee.getEmp_id()));
                tv_employee_name_data.setText(departmentEmployee.getEmp_name());
                tv_employee_title_data.setText(departmentEmployee.getEmp_title());
                tv_employee_email_data.setText(departmentEmployee.getEmp_email());
                tv_employee_phone_data.setText(departmentEmployee.getEmp_phone());
            }
        }

        for (SubDepartmentEmployee subDepartmentEmployee : subDepartmentEmployeeList) {
            if (subDepartmentEmployee.getEmp_id() == Integer.valueOf(empl_id)) {
                //  Toast.makeText(this,departmentEmployee.getEmp_id(),Toast.LENGTH_LONG).show();
                tv_department_info.setText(String.valueOf(subDepartmentEmployee.getDep_id()));
                tv_subDepartment_info.setText(String.valueOf(subDepartmentEmployee.getSubDep_id()));
                tv_employee_id_data.setText(String.valueOf(subDepartmentEmployee.getEmp_id()));
                tv_employee_name_data.setText(subDepartmentEmployee.getEmp_name());
                tv_employee_title_data.setText(subDepartmentEmployee.getEmp_title());
                tv_employee_email_data.setText(subDepartmentEmployee.getEmp_email());
                tv_employee_phone_data.setText(subDepartmentEmployee.getEmp_phone());
            }
        }

        for (SubSubDepartmentEmployee subSubDepartmentEmployee : subSubDepartmentEmployeeList) {
            if (subSubDepartmentEmployee.getEmp_id() == Integer.valueOf(empl_id)) {
                //  Toast.makeText(this,departmentEmployee.getEmp_id(),Toast.LENGTH_LONG).show();
                tv_department_info.setText(String.valueOf(subSubDepartmentEmployee.getDep_id()));
                tv_subDepartment_info.setText(String.valueOf(subSubDepartmentEmployee.getSubDep_id()));
                tv_subSubDepartment_info.setText(String.valueOf(subSubDepartmentEmployee.getSubSubDep_id()));
                tv_employee_id_data.setText(String.valueOf(subSubDepartmentEmployee.getEmp_id()));
                tv_employee_name_data.setText(subSubDepartmentEmployee.getEmp_name());
                tv_employee_title_data.setText(subSubDepartmentEmployee.getEmp_title());
                tv_employee_email_data.setText(subSubDepartmentEmployee.getEmp_email());
                tv_employee_phone_data.setText(subSubDepartmentEmployee.getEmp_phone());
            }
        }

        if (tv_subDepartment_info.getText().toString().equalsIgnoreCase("")) {
            tv_subDepartment_info.setText("-");
        }

        if (tv_subSubDepartment_info.getText().toString().equalsIgnoreCase("")) {
            tv_subSubDepartment_info.setText("-");
        }

        if (tv_employee_email_data.getText().toString().equalsIgnoreCase("")) {
            tv_employee_email_data.setText("-");
        }

        if (tv_employee_phone_data.getText().toString().equalsIgnoreCase("")) {
            tv_employee_phone_data.setText("-");
        }

        SharedPreferences myPreferences = getSharedPreferences(MainActivity.PREFERANCE, MODE_PRIVATE);
        login = myPreferences.getString(MainActivity.LOGIN_KEY, "");
        password = myPreferences.getString(MainActivity.PASSWORD_KEY, "");

        String imageUrl = "https://contact.taxsee.com/Contacts.svc/GetWPhoto?login=" + login + "&password=" + password + "&id=" + empl_id;

        Picasso.get().load(imageUrl).into(iv_employee_photo);

        tv_employee_phone_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToEmployee();
            }
        });
    }


    public void callToEmployee() {

        if (!tv_employee_phone_data.getText().toString().trim().equalsIgnoreCase("") || !tv_employee_phone_data.getText().toString().trim().equalsIgnoreCase("-")) {

            if(ContextCompat.checkSelfPermission(EmployeeDetailsActivity.this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(EmployeeDetailsActivity.this,new String[]{Manifest.permission.CALL_PHONE}, 1);
            }else{
                String dial = "tel:" + tv_employee_phone_data.getText().toString();
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callToEmployee();
            }else{
                Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
       startActivity(intent);
    }
}
