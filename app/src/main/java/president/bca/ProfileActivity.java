package president.bca;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import president.bca.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String sGender = "";
    String sCity = "";

    ArrayList<String> arrayList;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
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

        binding.profileGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        ArrayAdapter adapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        binding.profileCity.setAdapter(adapter);

        binding.profileCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    sCity = "";
                }
                else {
                    sCity = arrayList.get(i);
                    //Toast.makeText(MainActivity.this, countryArray[i], Toast.LENGTH_SHORT).show();
                    Toast.makeText(ProfileActivity.this, sCity, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);
            }
        });

        binding.profileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.profileName.getText().toString().trim().equals("")){
                    binding.profileName.setError("Name Required");
                }
                else if(binding.profileEmail.getText().toString().trim().equals("")){
                    binding.profileEmail.setError("Email Id Required");
                }
                else if(!binding.profileEmail.getText().toString().trim().matches(emailPattern)){
                    binding.profileEmail.setError("Valid Email Id Required");
                }
                else if(binding.profileContact.getText().toString().trim().equals("")){
                    binding.profileContact.setError("Contact No. Required");
                }
                else if(binding.profileContact.getText().toString().trim().length()<10){
                    binding.profileContact.setError("Valid Contact No. Required");
                }
                else if(binding.profilePassword.getText().toString().trim().equals("")){
                    binding.profilePassword.setError("Password Required");
                }
                else if(binding.profilePassword.getText().toString().trim().length()<6){
                    binding.profilePassword.setError("Min. 6 Char Password Required");
                }
                else if(binding.profileConfirmPassword.getText().toString().trim().equals("")){
                    binding.profileConfirmPassword.setError("Confirm Password Required");
                }
                else if(binding.profileConfirmPassword.getText().toString().trim().length()<6){
                    binding.profileConfirmPassword.setError("Min. 6 Char Confirm Password Required");
                }
                else if(!binding.profilePassword.getText().toString().trim().matches(binding.profileConfirmPassword.getText().toString().trim())){
                    binding.profileConfirmPassword.setError("Password Does Not Match");
                }
                else if(binding.profileGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(ProfileActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if(sCity.equals("")){
                    Toast.makeText(ProfileActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
                }
                else{
                    String selectQuery = "SELECT * FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        //Toast.makeText(profileActivity.this, "profile Successfully", Toast.LENGTH_SHORT).show();
                        String insertQuery = "UPDATE USERS SET NAME='"+binding.profileName.getText().toString()+"',EMAIL='"+binding.profileEmail.getText().toString()+"',CONTACT='"+binding.profileContact.getText().toString()+"',PASSWORD='"+binding.profilePassword.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"' WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                        db.execSQL(insertQuery);
                        Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();

                        sp.edit().putString(ConstantSp.NAME,binding.profileName.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.EMAIL,binding.profileEmail.getText().toString()).commit();
                        //sp.edit().remove(ConstantSp.CONTACT).commit();
                        sp.edit().putString(ConstantSp.CONTACT,binding.profileContact.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.PASSWORD,binding.profilePassword.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstantSp.CITY,sCity).commit();

                        setData(false);
                    }
                    else {
                        Toast.makeText(ProfileActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.profileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Account Delete");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Are You Sure Want To Delete Account!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String deleteQuery = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                        db.execSQL(deleteQuery);

                        sp.edit().clear().commit();
                        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        setData(false);

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Logout");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Are You Sure Want To Logout!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sp.edit().clear().commit();
                        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });

                builder.show();
            }
        });

    }

    private void setData(boolean b) {
        binding.profileName.setEnabled(b);
        binding.profileEmail.setEnabled(b);
        binding.profileContact.setEnabled(b);
        binding.profilePassword.setEnabled(b);
        binding.profileConfirmPassword.setEnabled(b);
        binding.profileGender.setEnabled(b);
        binding.profileCity.setEnabled(b);

        if(b){
            binding.profileConfirmPassword.setVisibility(VISIBLE);
            binding.profileSubmit.setVisibility(VISIBLE);
            binding.profileButton.setVisibility(GONE);
        }
        else{
            binding.profileConfirmPassword.setVisibility(GONE);
            binding.profileSubmit.setVisibility(GONE);
            binding.profileButton.setVisibility(VISIBLE);
        }

        binding.profileName.setText(sp.getString(ConstantSp.NAME,""));
        binding.profileEmail.setText(sp.getString(ConstantSp.EMAIL,""));
        binding.profileContact.setText(sp.getString(ConstantSp.CONTACT,""));
        binding.profilePassword.setText(sp.getString(ConstantSp.PASSWORD,""));
        binding.profileConfirmPassword.setText(sp.getString(ConstantSp.PASSWORD,""));

        sGender = sp.getString(ConstantSp.GENDER,"");
        if(sGender.equals("Male")){
            binding.profileMale.setChecked(true);
            binding.profileFemale.setChecked(false);
        }
        else if(sGender.equals("Female")){
            binding.profileMale.setChecked(false);
            binding.profileFemale.setChecked(true);
        }
        else {
            binding.profileMale.setChecked(false);
            binding.profileFemale.setChecked(false);
        }

        String sCity = sp.getString(ConstantSp.CITY,"");
        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(sCity.equals(arrayList.get(i))){
                iCityPosition = i;
                break;
            }
        }
        binding.profileCity.setSelection(iCityPosition);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

}