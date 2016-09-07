package com.eden.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eden.R;
import com.eden.domain.AnswerEden;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;



public class ProblemCommentAdapter extends BaseAdapter{
	private Context context;
	private List<AnswerEden> answers;
	private LayoutInflater inflater;
	private List<AnswerEden> answer;
	private List<AnswerEden> replys;
	private Handler handler;
	public ProblemCommentAdapter(Context context,Handler handler){
		this.context = context;
		this.handler = handler;
		inflater = LayoutInflater.from(context);
	}
	

	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public List<AnswerEden> getAnswers() {
		return answers;
	}


	public void setAnswers(List<AnswerEden> answers) {
		this.answers = answers;
		answer = new ArrayList<AnswerEden>();
		replys = new ArrayList<AnswerEden>();
		for(int i = 0;i<answers.size();i++){

			if(answers.get(i).getAnswer().getType() == 0){
				answer.add(answers.get(i));
			}else{
				replys.add(answers.get(i));
			}
		}
	}


	public List<AnswerEden> getAnswer() {
		return answer;
	}


	public void setAnswer(List<AnswerEden> answer) {
		this.answer = answer;
	}


	public List<AnswerEden> getReplys() {
		return replys;
	}


	public void setReplys(List<AnswerEden> replys) {
		this.replys = replys;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return answer.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return answer.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView){
			convertView = inflater.inflate(R.layout.friend_comment_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(null != answer){
			holder.name.setText(answer.get(position).getUse().getUserName());
			holder.content.setText(answer.get(position).getAnswer().getContent());
			holder.time.setText(answer.get(position).getAnswer().getTime());
			MyAsyncTask asyncTask= new MyAsyncTask(holder.image, context);
			asyncTask.execute(MyGetUrl.getUrl()+answer.get(position).getUse().getUserIcon());
			List<AnswerEden> eden = new ArrayList<AnswerEden>();
			for(int i = 0;i<replys.size();i++){
				if(answer.get(position).getAnswer().getId() == replys.get(i).getAnswer().getReplyId()){
					eden.add(replys.get(i));
				}
			}
			ReplyAdapter adapter = new ReplyAdapter(context, eden, R.layout.reply_item);
			holder.replyList.setAdapter(adapter);
			TextviewClickListener tcl = new TextviewClickListener(position);
			holder.reply.setOnClickListener(tcl);
		}
		
		return convertView;
	}
	
	 class ViewHolder{
		 ImageView image;
		 TextView name;
		 TextView content;
		 TextView time;
		 TextView reply;
		 ListView replyList;
		 
		 public ViewHolder(View view){
			 image = (ImageView) view.findViewById(R.id.commentImage);
			 name = (TextView) view.findViewById(R.id.commentNickname);
			 content = (TextView) view.findViewById(R.id.commentItemContent);
			 time = (TextView) view.findViewById(R.id.commentItemTime);
			 reply = (TextView) view.findViewById(R.id.replyText);
			 replyList = (ListView) view.findViewById(R.id.replyList);
		 }
		 
	 }
	 
	
	 private final class TextviewClickListener implements OnClickListener{
			private int position;
			public TextviewClickListener(int position){
				this.position = position;
			}
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.replyText:
					handler.sendMessage(handler.obtainMessage(10, position));
					break;
				}
			}
		}

}
