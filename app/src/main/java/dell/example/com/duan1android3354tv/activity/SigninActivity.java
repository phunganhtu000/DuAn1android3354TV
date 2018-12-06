package dell.example.com.duan1android3354tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.model.User;
import dell.example.com.duan1android3354tv.sqlite.DatabaseHelper;

public class SigninActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private List<User> list;
    private Toolbar toolbarsignup;
    private EditText edtUsername1;
    private EditText edtpass1;
    private EditText edtrepass1;
    private Button btnsignup;
    private List<User> list2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);
        view22();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsername1.getText().toString().matches("")
                        ||edtpass1.getText().toString().matches("")
                        ||edtrepass1.getText().toString().matches("")){
                    if (edtUsername1.getText().toString().matches("")) {
                        edtUsername1.setError("Do not leave empty");
                    }
                    if (edtpass1.getText().toString().matches("")) {
                        edtpass1.setError("Do not leave empty");
                    }

                    if (edtrepass1.getText().toString().matches("")) {
                        edtrepass1.setError("Do not leave empty");
                    }}else {
                    if (edtpass1.getText().toString().length() < 7 ) {
                        edtpass1.setError("More than 6 characters");
                    }else {
                        if (edtpass1.getText().toString().matches(edtrepass1.getText().toString())) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            String uname = edtUsername1.getText().toString();
                            String pass = edtpass1.getText().toString();
                            String a = null;
                            String b = "daco";
                            list2=databaseHelper.getUserAll();
                            for (int i = 0; i < list2.size(); i++) {
                                User favorite1 = list2.get(i);
                                if (favorite1.getUser_name().equals(uname)) {
                                    a = "daco";
                                } else {
                                    a = "chuaco";
                                }
                            }

                            User user4 = new User(uname, pass);
                            if (b.equals(a)) {
                                Toast.makeText(SigninActivity.this, "Account already exists", Toast.LENGTH_SHORT).show();
                            } else {
                            databaseHelper.insertUser(user4);
                                Toast.makeText(getApplicationContext(), "Register successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            }
                        } else {
                            edtrepass1.setError("Passwords are not the same");
                        }
                    }
                }


                }
        });
    }

    private void view22() {
        toolbarsignup = (Toolbar) findViewById(R.id.toolbarsignup);
        edtUsername1 = (EditText) findViewById(R.id.edtUsername1);
        edtpass1 = (EditText) findViewById(R.id.edtpass1);
        edtrepass1 = (EditText) findViewById(R.id.edtrepass1);
        btnsignup = findViewById(R.id.btnsignup);
    }

    public void backlogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }
}
