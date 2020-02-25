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

import static com.taxsee.mystaff.SecondActivity.subDepartmentEmployeeList;

public class SubDepartmentEmployeeActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();

        String empl_id = intent.getStringExtra(Intent.EXTRA_TEXT);
        Log.i("EMPLOYEE Empl_id", empl_id);

        final HashSet<String> arrEmplDep2 = new HashSet<>();
        for (SubDepartmentEmployee subDepartmentEmployee : subDepartmentEmployeeList) {
            if (subDepartmentEmployee.getSubDep_id() == Integer.valueOf(empl_id)) {
                arrEmplDep2.add("ID: " + subDepartmentEmployee.getEmp_id() + ", Name: " + subDepartmentEmployee.getEmp_name());
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
                Log.i("POSITION", pos + ", " + clickedDep);

                String[] arrEmpdata = clickedDep.split(",");
                Log.i("Empl", arrEmpdata[0]);

                String[] arrId = arrEmpdata[0].split(" ");
                Log.i("Empl_ID", arrId[1]);

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
