package com.cmt.qq;


import com.cmt.qq.tool.MyDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText editUsername;
	private EditText editPassword;
	private Button btnLogin;
	private Button btnCancle;
	private Button btnRegister;
	private MyDatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		initViews();
		initEvents();
	}

	private void createDatabase() {
		dbHelper = new MyDatabaseHelper(this, "User.db", null, 1);
		db = dbHelper.getWritableDatabase();
	}

	private void initEvents() {
		btnLogin.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		createDatabase();
		int id = v.getId();
		switch (id) {
		case R.id.button_login:
			String username = editUsername.getText().toString();
			String password = editPassword.getText().toString();
			
			boolean login = login(username, password);
			if(login){
				Intent intent = new Intent(this,MainActivity.class);
				//传输用户名
				Bundle bundle = new Bundle();
				bundle.putString("username", editUsername.getText().toString());
				intent.putExtras(bundle);
				this.finish();
				startActivity(intent);
			}else{
				Toast toast =Toast.makeText(getApplicationContext(), "登录失败！请检查用户名以及密码", Toast.LENGTH_SHORT);
				toast.show();
			}
			break;
		case R.id.button_cancle:
			editUsername.setText("");
			editPassword.setText("");
			break;
		case R.id.button_register:
			Intent intent2 = new Intent(this,RegisteActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	private boolean login(String username, String password) {
		Cursor cursor = dbHelper.queryUser(db, username, password);
		if(cursor.getCount()>0)
			return true;
		return false;
	}

	private void initViews() {
		editUsername = (EditText) findViewById(R.id.edit_text_username);
		editPassword = (EditText) findViewById(R.id.edit_text_password);
		btnLogin = (Button) findViewById(R.id.button_login);
		btnCancle = (Button) findViewById(R.id.button_cancle);
		btnRegister = (Button) findViewById(R.id.button_register);
	}
	
	
}