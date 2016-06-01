package com.cmt.qq;


import com.cmt.qq.tool.MyDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText editUsername;
	private EditText editPassword;
	private Button btnLogin;
	private Button btnCancle;
	private Button btnRegister;
	private CheckBox checkboxRememberInfo;
	
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
		dbHelper = new MyDatabaseHelper(this, "User.db", null, MyConstant.SQLITE_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	private void initEvents() {
		btnLogin.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		
		checkboxRememberInfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					SharedPreferences pref =getSharedPreferences("user", MODE_PRIVATE);
					String username = pref.getString("username", "");
					String password = pref.getString("password", "");
					editUsername.setText(username);
					editPassword.setText(password);
				}
			}
		});
		
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
				//将用户信息存在SharedPreferences中
				SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
				editor.putString("username", username);
				editor.putString("password", password);
				editor.putBoolean("remember", checkboxRememberInfo.isChecked());
				editor.commit();
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
		checkboxRememberInfo = (CheckBox) findViewById(R.id.checkbox_rememberInfo);
		
		SharedPreferences pref =getSharedPreferences("user", MODE_PRIVATE);
		boolean ischecked = pref.getBoolean("remember", false);
		checkboxRememberInfo.setChecked(ischecked);
		if(ischecked){
			String username = pref.getString("username", "");
			String password = pref.getString("password", "");
			editUsername.setText(username);
			editPassword.setText(password);
		}
	}
	
	
}
