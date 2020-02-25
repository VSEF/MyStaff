package com.taxsee.mystaff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.taxsee.mystaff.SecondActivity.departmentEmployeeList;
import static com.taxsee.mystaff.SecondActivity.subDepartmentEmployeeList;
import static com.taxsee.mystaff.SecondActivity.subDepartmentList;
import static com.taxsee.mystaff.SecondActivity.subSubDepartmentEmployeeList;

public class SubDepartmentActivity extends AppCompatActivity {

    private ListView listView;
    private int subDepID;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();

        String empl_id = intent.getStringExtra(Intent.EXTRA_TEXT);
        Log.i("EMPLOYEE Empl_id", empl_id);

        final HashSet<String> arrEmplDep2 = new HashSet<>();
        for(SubDepartment subDepartment:subDepartmentList){
            if(subDepartment.getDep_id()==Integer.valueOf(empl_id)) {
                arrEmplDep2.add("DIVISION_ID: " + subDepartment.getSubDep_id() + ", Name: " + subDepartment.getSubDep_name());
            }
        }

        final List<String> arrEmplDep = new ArrayList<>(arrEmplDep2);

        Collections.sort(arrEmplDep);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrEmplDep);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos = (int) listView.getItemIdAtPosition(position);
                String clickedDep = arrEmplDep.get(pos);
                Log.i("POSITION", pos + ", "+clickedDep);

                String[] arrEmpdata = clickedDep.split(",");
                Log.i("Empl", arrEmpdata[0]);

                String[] arrId = arrEmpdata[0].split(" ");
                Log.i("Empl_ID_Sec_Act", arrId[1]);

                subDepID =Integer.valueOf(arrId[1]);

                for(SubSubDepartmentEmployee subSubDepartmentEmployee:subSubDepartmentEmployeeList){
                    if(subSubDepartmentEmployee.getSubDep_id()==subDepID){
                        Intent myIntent = new Intent(getApplicationContext(), SubSubDepartmentActivity.class);
                        myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(subDepID));
                        startActivity(myIntent);
                    }
                }

                for(SubDepartmentEmployee subDepartmentEmployee:subDepartmentEmployeeList){
                    if(subDepartmentEmployee.getSubDep_id()==subDepID){
                        Intent myIntent = new Intent(getApplicationContext(), SubDepartmentEmployeeActivity.class);
                        myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(subDepID));
                        startActivity(myIntent);
                    }
                }


            }

        });

    }

    @Override
    public void onBackPressed() {
      Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
      startActivity(intent);
    }
}
