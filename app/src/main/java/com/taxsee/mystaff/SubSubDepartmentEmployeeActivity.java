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

import static com.taxsee.mystaff.SecondActivity.subSubDepartmentEmployeeList;

public class SubSubDepartmentEmployeeActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();

        String empl_id = intent.getStringExtra(Intent.EXTRA_TEXT);

        final HashSet<String> arrEmplDep2 = new HashSet<>();
        for (SubSubDepartmentEmployee subSubDepartmentEmployee : subSubDepartmentEmployeeList) {
            if (subSubDepartmentEmployee.getSubSubDep_id() == Integer.valueOf(empl_id)) {
                arrEmplDep2.add("ID: " + subSubDepartmentEmployee.getEmp_id() + ", Name: " + subSubDepartmentEmployee.getEmp_name());
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

                String[] arrEmpdata = clickedDep.split(",");

                String[] arrId = arrEmpdata[0].split(" ");

                Intent myIntent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                myIntent.putExtra(Intent.EXTRA_TEXT, arrId[1]);
                startActivity(myIntent);


            }

        });
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
       startActivity(intent);

    }
}
