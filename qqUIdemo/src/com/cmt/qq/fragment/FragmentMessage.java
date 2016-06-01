package com.cmt.qq.fragment;

import org.eclipse.jdt.annotation.Nullable;

import com.cmt.qq.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.webkit.WebView.FindListener;
import android.widget.ListView;

public class FragmentMessage extends Fragment{
	
	private ListView list_messages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fg_1, null);
		list_messages = (ListView) rootView.findViewById(R.id.list_messages);
		initData();
		return rootView;
    }

	private void initData() {
		
	}

}
