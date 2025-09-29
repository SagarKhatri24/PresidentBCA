package president.bca;

import android.content.Intent;
import android.content.SharedPreferences;
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

import president.bca.databinding.ActivityLoginBinding;
import president.bca.databinding.ActivityNavDemoBinding;
import president.bca.databinding.ActivitySignupBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("President.db",MODE_PRIVATE,null);
        String tableCreate = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20),GENDER VARCHAR(10), CITY VARCHAR(50) )";
        db.execSQL(tableCreate);

        binding.loginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.loginEmail.getText().toString().trim().equals("")){
                    binding.loginEmail.setError("Email Id/Contact No. Required");
                }

                else if(binding.loginPassword.getText().toString().trim().equals("")){
                    binding.loginPassword.setError("Password Required");
                }
                else if(binding.loginPassword.getText().toString().trim().length()<6) {
                    binding.loginPassword.setError("Min. 6 Char Password Required");
                }
                else{
                    String selectQuery = "SELECT * FROM USERS WHERE (EMAIL='"+binding.loginEmail.getText().toString()+"' OR CONTACT='"+binding.loginEmail.getText().toString()+"') AND PASSWORD='"+binding.loginPassword.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        while (cursor.moveToNext()){
                            String sUserId = cursor.getString(0);
                            String sName = cursor.getString(1);
                            String sEmail = cursor.getString(2);
                            String sContact = cursor.getString(3);
                            String sPassword = cursor.getString(4);
                            String sGender = cursor.getString(5);
                            String sCity = cursor.getString(6);

                            sp.edit().putString(ConstantSp.USERID,sUserId).commit();
                            sp.edit().putString(ConstantSp.NAME,sName).commit();
                            sp.edit().putString(ConstantSp.EMAIL,sEmail).commit();
                            //sp.edit().remove(ConstantSp.CONTACT).commit();
                            sp.edit().putString(ConstantSp.CONTACT,sContact).commit();
                            sp.edit().putString(ConstantSp.PASSWORD,sPassword).commit();
                            sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                            sp.edit().putString(ConstantSp.CITY,sCity).commit();
                        }
                        Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}