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
import static com.taxsee.mystaff.SecondActivity.subSubDepartmentList;

public class SubSubDepartmentActivity extends AppCompatActivity {

    private ListView listView;
    private int subSubDepId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();

        String subSubDep_id = intent.getStringExtra(Intent.EXTRA_TEXT);

        final HashSet<String> arrEmplDep2 = new HashSet<>();
        for (SubSubDepartment subSubDepartment : subSubDepartmentList) {
            if (subSubDepartment.getSubDep_id() == Integer.valueOf(subSubDep_id)) {
                arrEmplDep2.add("SUBDIVISION_ID: " + subSubDepartment.getSubSubDep_id() + ", Name: " + subSubDepartment.getSubSubDep_name());
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

                subSubDepId =Integer.valueOf(arrId[1]);

                Intent myIntent = new Intent(getApplicationContext(), SubSubDepartmentEmployeeActivity.class);
                myIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(subSubDepId));
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
