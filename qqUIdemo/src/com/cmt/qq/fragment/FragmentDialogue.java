package com.cmt.qq.fragment;

import java.util.*;

import org.eclipse.jdt.annotation.Nullable;

import com.cmt.qq.R;
import com.cmt.qq.adapter.DialogueAdapter;
import com.cmt.qq.model.Dialogue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FragmentDialogue extends Fragment{
	
	private List<Dialogue> dialogueList =  new ArrayList<>();
	private String[] dialogueUsers = {"六花","镜音玲","镜音连","雪初音","南小鸟","东条希",
					"小香香","西木野","果皇","海未","绘绘里","花阳"};
	private String[] dialogueMessages = {"消息1","消息2","消息3","消息4",
			"消息5","消息6","消息7","消息8","消息9","消息10",
			"消息11","消息12",};
	
	private ListView listView ;
	private DialogueAdapter adapter;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	

	@Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fg_1, null);
		listView = (ListView) rootView.findViewById(R.id.list_dialogues);
		
		initData();
		
		System.out.println("dialogue list  size : " + dialogueList.size());
		
		adapter = new DialogueAdapter(getActivity(),R.layout.dialogue_item, dialogueList);
		listView.setAdapter(adapter);
		
		return rootView;
    }

	private void initData() {
		for(int i=0; i<12; ++i){
			Dialogue dialogue = new Dialogue();
			dialogue.setUser(dialogueUsers[i]);
			dialogue.setMessage(dialogueMessages[i]);
			dialogueList.add(dialogue);
		}
		dialogueList.get(0).setImageId(R.drawable.head1);
		dialogueList.get(1).setImageId(R.drawable.head2);
		dialogueList.get(2).setImageId(R.drawable.head3);
		dialogueList.get(3).setImageId(R.drawable.head4);
		dialogueList.get(4).setImageId(R.drawable.head5);
		dialogueList.get(5).setImageId(R.drawable.head6);
		dialogueList.get(6).setImageId(R.drawable.head7);
		dialogueList.get(7).setImageId(R.drawable.head8);
		dialogueList.get(8).setImageId(R.drawable.head9);
		dialogueList.get(9).setImageId(R.drawable.head10);
		dialogueList.get(10).setImageId(R.drawable.head11);
		dialogueList.get(11).setImageId(R.drawable.head12);
	}
}
