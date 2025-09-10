package president.bca;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    String[] countryArray = {"Select Country","India","Australia","UK","USA","Canada"};
    ArrayList<String> arrayList;

    GridView listView;

    AutoCompleteTextView autoTxt;

    MultiAutoCompleteTextView multiAutoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.main_spinner);

        arrayList = new ArrayList<>();
        arrayList.add("Select Country");
        arrayList.add("China");
        arrayList.add("USA");
        arrayList.add("UK");
        arrayList.add("Test");
        arrayList.add("Canda");

        arrayList.remove(4);
        arrayList.set(4,"Canada");

        arrayList.add(1,"India");

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                }
                else {
                    //Toast.makeText(MainActivity.this, countryArray[i], Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView = findViewById(R.id.listview);
        ArrayAdapter listAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                }
                else {
                    //Toast.makeText(MainActivity.this, countryArray[i], Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });

        autoTxt = findViewById(R.id.main_auto);
        ArrayAdapter autoAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        autoTxt.setThreshold(1);
        autoTxt.setAdapter(autoAdapter);

        multiAutoTxt = findViewById(R.id.main_multi_auto);
        ArrayAdapter multiautoAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        multiAutoTxt.setThreshold(1);
        multiAutoTxt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoTxt.setAdapter(multiautoAdapter);

    }
}