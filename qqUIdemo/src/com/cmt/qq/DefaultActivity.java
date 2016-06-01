package com.cmt.qq;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class DefaultActivity extends Activity {
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.e("cmt", "sign Activity is creating");
		setContentView(R.layout.content);
		textView = (TextView) findViewById(R.id.item_text);
		textView.setText("做不动");
	}
}
