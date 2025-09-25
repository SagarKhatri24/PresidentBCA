package president.bca;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import president.bca.databinding.ActivityNavDemoBinding;
import president.bca.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String sGender = "";
    String sCity = "";

    ArrayList<String> arrayList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("President.db",MODE_PRIVATE,null);
        String tableCreate = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20),GENDER VARCHAR(10), CITY VARCHAR(50) )";
        db.execSQL(tableCreate);

        binding.signupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                sGender = rb.getText().toString();
            }
        });

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

        ArrayAdapter adapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        binding.signupCity.setAdapter(adapter);

        binding.signupCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    sCity = "";
                }
                else {
                    sCity = arrayList.get(i);
                    //Toast.makeText(MainActivity.this, countryArray[i], Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignupActivity.this, sCity, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.signupName.getText().toString().trim().equals("")){
                    binding.signupName.setError("Name Required");
                }
                else if(binding.signupEmail.getText().toString().trim().equals("")){
                    binding.signupEmail.setError("Email Id Required");
                }
                else if(!binding.signupEmail.getText().toString().trim().matches(emailPattern)){
                    binding.signupEmail.setError("Valid Email Id Required");
                }
                else if(binding.signupContact.getText().toString().trim().equals("")){
                    binding.signupContact.setError("Contact No. Required");
                }
                else if(binding.signupContact.getText().toString().trim().length()<10){
                    binding.signupContact.setError("Valid Contact No. Required");
                }
                else if(binding.signupPassword.getText().toString().trim().equals("")){
                    binding.signupPassword.setError("Password Required");
                }
                else if(binding.signupPassword.getText().toString().trim().length()<6){
                    binding.signupPassword.setError("Min. 6 Char Password Required");
                }
                else if(binding.signupConfirmPassword.getText().toString().trim().equals("")){
                    binding.signupConfirmPassword.setError("Confirm Password Required");
                }
                else if(binding.signupConfirmPassword.getText().toString().trim().length()<6){
                    binding.signupConfirmPassword.setError("Min. 6 Char Confirm Password Required");
                }
                else if(!binding.signupPassword.getText().toString().trim().matches(binding.signupConfirmPassword.getText().toString().trim())){
                    binding.signupConfirmPassword.setError("Password Does Not Match");
                }
                else if(binding.signupGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SignupActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if(sCity.equals("")){
                    Toast.makeText(SignupActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
                }
                else{
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+binding.signupEmail.getText().toString()+"' OR CONTACT='"+binding.signupContact.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        Toast.makeText(SignupActivity.this, "Email Id/Contact No. Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                        String insertQuery = "INSERT INTO USERS VALUES (NULL,'"+binding.signupName.getText().toString()+"','"+binding.signupEmail.getText().toString()+"','"+binding.signupContact.getText().toString()+"','"+binding.signupPassword.getText().toString()+"','"+sGender+"','"+sCity+"')";
                        db.execSQL(insertQuery);
                        Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });

    }
}