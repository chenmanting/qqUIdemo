package com.cmt.qq;

import com.cmt.qq.tool.MyDatabaseHelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class RegisteActivity extends Activity {

	private EditText registeUsername;
	private EditText registePassword;
	private Button register;
	private MyDatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initViews();
		createDatabase();
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = registeUsername.getText().toString();
				String password = registePassword.getText().toString();
				dbHelper.addUser(db, username, password);
				Toast toast =Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}
	
	private boolean validate(String username, String password){
		
		Cursor cursor = dbHelper.queryUser(db, username, null);
		if(cursor.getCount() > 0){
			Toast toast =Toast.makeText(getApplicationContext(), "用户名已存在！", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		if(password==null ||"".equals(password)){
			Toast toast =Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		return true;
	}

	private void initViews() {
		registeUsername = (EditText) findViewById(R.id.edit_text_register_username);
		registePassword = (EditText) findViewById(R.id.edit_text_register_password);
		register = (Button) findViewById(R.id.button_register);
	}

	private void createDatabase() {
		dbHelper = new MyDatabaseHelper(this, "User.db", null, 1);
		db = dbHelper.getWritableDatabase();
	}

}
