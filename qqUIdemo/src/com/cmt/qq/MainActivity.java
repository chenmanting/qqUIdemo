package com.cmt.qq;


import java.util.zip.Inflater;

import com.cmt.qq.fragment.FragmentDongtai;
import com.cmt.qq.fragment.FragmentMessage;
import com.cmt.qq.fragment.FragmentFriends;
import com.cmt.qq.view.SlidingMenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnClickListener {

	private String username;
	private RelativeLayout usernameItem;
	private RelativeLayout openMemberItem;
	private RelativeLayout walletItem;
	private RelativeLayout gxzbItem;
	private RelativeLayout wdscItem;
	private RelativeLayout wdxcItem;
	private RelativeLayout wdwjItem;
	private RelativeLayout wdmpjItem;
	private SlidingMenu mLeftMenu;
	
	private Fragment contentFragment;
	private RadioGroup myTabRg;
	private FragmentMessage message;
	private FragmentFriends friends;
	private FragmentDongtai dongtai;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initEvents();
	}

	public void toggleMenu(View view){
		Log.d("cmt", "in toggle");
		mLeftMenu.toggle();
	}
	
	private void initEvents() {
		usernameItem.setOnClickListener(this);
		openMemberItem.setOnClickListener(this);
		walletItem.setOnClickListener(this);
		gxzbItem.setOnClickListener(this);
		wdscItem.setOnClickListener(this);
		wdxcItem.setOnClickListener(this);
		wdwjItem.setOnClickListener(this);
		wdmpjItem.setOnClickListener(this);
	}
	private void initViews() {
		//初始化控件
		usernameItem=(RelativeLayout) findViewById(R.id.rl_username);
		openMemberItem = (RelativeLayout) findViewById(R.id.rl_open_member);
		walletItem = (RelativeLayout) findViewById(R.id.rl_wallet);
		gxzbItem = (RelativeLayout) findViewById(R.id.rl_gxzb);
		wdscItem = (RelativeLayout) findViewById(R.id.rl_wdsc);
		wdxcItem = (RelativeLayout) findViewById(R.id.rl_wdxc);
		wdwjItem = (RelativeLayout) findViewById(R.id.rl_wdwj);
		wdmpjItem = (RelativeLayout) findViewById(R.id.rl_wdmpj);
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		
		//接受用户名
		Bundle bundle =this.getIntent().getExtras();
		username = bundle.getString("username");
		
		TextView userText = (TextView) usernameItem.getChildAt(1);
		userText.setText("用户名： "+username);
		
		//fragment切换
		message = new FragmentMessage();
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, message).commit();
		myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.fg_message:
					message = new FragmentMessage();
					getFragmentManager().beginTransaction().replace(R.id.fragment_content, message)
							.commit();
					break;
				case R.id.fg_friends:
					if (friends==null) {
						friends =new FragmentFriends();
					}
					Log.i("MyFragment", "FragmentFriends");
					getFragmentManager().beginTransaction().replace(R.id.fragment_content, friends).commit();
					break;
				case R.id.fg_dongtai:
					dongtai = new FragmentDongtai();
					getFragmentManager().beginTransaction().replace(R.id.fragment_content, dongtai)
							.commit();
					break;
				
				default:
					break;
				}

			}
		});
		
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.rl_username:
		case R.id.rl_open_member:
		case R.id.rl_wallet:
		case R.id.rl_gxzb:
		case R.id.rl_wdsc:
		case R.id.rl_wdxc:
		case R.id.rl_wdwj:
		case R.id.rl_wdmpj:
		default:
			if(mLeftMenu.getIsOpen())
				mLeftMenu.closeMenu();
			Intent intent2 = new Intent(this,DefaultActivity.class);
			startActivity(intent2);
			break;
		}
	}

	
}
