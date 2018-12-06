package dell.example.com.duan1android3354tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dell.example.com.duan1android3354tv.R;
import dell.example.com.duan1android3354tv.model.User;
import dell.example.com.duan1android3354tv.sqlite.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbarMenu;
    private EditText edtUserName;
    private EditText edtpassword;
    private Button btnlogin;
    private Button btnsignup;
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        view2();
        initAction();
    }

    public void initAction() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString().trim();
                if (username.matches("")) {
                    edtUserName.setError(getString(R.string.notify_empty_username));
                    return;
                }
                String password = edtpassword.getText().toString().trim();
                if (username.matches("")) {
                    edtpassword.setError(getString(R.string.notify_empty_username));
                    return;
                }
                if (password.length() < 6) {
                    edtpassword.setError(getString(R.string.notify_empty_username2));
                    return;
                }
                User user = databaseHelper.getUser(username);
                if (user != null && user.getUser_name() != null) {
                    if (password.matches(user.getPassword())) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        Bundle bundle=new Bundle();
//                        bundle.putString("tenus",edtUserName.getText().toString());
//                        intent.putExtra("b",bundle);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "The account or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Do not have an account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void view2() {
        toolbarMenu = (Toolbar) findViewById(R.id.toolbar_menu);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnsignup = (Button) findViewById(R.id.btnsignup);
    }

    public void btnsignup(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);

    }
}
