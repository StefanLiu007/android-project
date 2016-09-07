package com.eden.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eden.domain.User;
import com.eden.util.Calendar;
import com.eden.util.GsonUtil;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends Activity implements OnClickListener{
	EditText nickname,name,phone,signature,school,email;
	TextView birthday;
	Spinner gender;
	Button complete;
	RequestQueue rq;
	TextView date;
	Uri imageUri;
	ImageView imageView;
	LinearLayout layout;
	ImageView nnn;
	TextView photo;
	TextView cancel;
	TextView local;
	PopupWindow popup;
	public static int button1 = 1;
	public static int button2 = 2;
	public static int button3 = 3;
	private User user1;
	private TextView usernickname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalinfo);
		MyApplication.AddActivity(this);
		rq = Volley.newRequestQueue(this);
		SharedPreferences sp=getSharedPreferences("user", MODE_PRIVATE);
		user1 = (User) GsonUtil.fromJson(sp.getString("currentuser", null), User.class);
		nickname = (EditText) findViewById(R.id.nickname);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		signature = (EditText) findViewById(R.id.signature);
		school = (EditText) findViewById(R.id.school);
		email = (EditText) findViewById(R.id.email);
		gender = (Spinner) findViewById(R.id.gender);
		nnn = (ImageView) findViewById(R.id.nnn);
		
		complete = (Button) findViewById(R.id.complete);
		complete.setOnClickListener(this);
		View root = this.getLayoutInflater().inflate(R.layout.popup_main, null);
		
		photo = (TextView) root.findViewById(R.id.photo);
		local = (TextView) root.findViewById(R.id.local);
		cancel = (TextView) root.findViewById(R.id.select_cancel);
		photo.setOnClickListener(this);
		local.setOnClickListener(this);
		cancel.setOnClickListener(this);
		MyAsyncTask asyncTask= new MyAsyncTask(nnn,this);
		asyncTask.execute(MyGetUrl.getUrl()+user1.getUserIcon());
		nickname.setHint(user1.getUserNickname());
		name.setHint(user1.getUserName());
		phone.setText(user1.getUserMobile());
		email.setHint(user1.getUserMail());
		school.setHint(user1.getUserSchool());
		if(user1.getUserSex().equals("男")){
			gender.setSelection(0);
		}else{
			gender.setSelection(1);
		}
		signature.setHint(user1.getUserSignature());
		usernickname=(TextView)findViewById(R.id.usernickname);
    	usernickname.setText(user1.getUserNickname());
		popup = new PopupWindow(root,1100,1100);
		date = (TextView) findViewById(R.id.date);
		date.setHint(user1.getUserBirthday());
		date.setOnClickListener(this);
		layout = (LinearLayout) findViewById(R.id.hhh);
		imageView = (ImageView) findViewById(R.id.ppp); 
		nnn = (ImageView) findViewById(R.id.nnn);		
		imageView.setOnClickListener(this);
		layout.setOnClickListener(this);
		imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"aaa.jpg"));		
	}
		
		  @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if(RESULT_OK==resultCode){
				if (requestCode==button1) {
					InputStream is = null;
					try {
						is = getContentResolver().openInputStream(imageUri);
						Bitmap bm = BitmapFactory.decodeStream(is);
						nnn.setImageBitmap(bm);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else if (requestCode==button2) {
						//做裁剪操作
					    Intent intent = new Intent("com.android.camera.action.CROP");
					    intent.setDataAndType(imageUri, "image/*");
					    intent.putExtra("crop", "true");
					    intent.putExtra("aspectX", 2);
					    intent.putExtra("aspectY", 1);
					    intent.putExtra("outputX", 600);
					    intent.putExtra("outputY", 300);
					    intent.putExtra("scale", true);
					    intent.putExtra("return-data", false);
					    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
					    startActivityForResult(intent, button3);
					}else if(requestCode == button3){	
						InputStream is = null;
						try {
							is = getContentResolver().openInputStream(imageUri);
							//内容提供者getContentResolver()
							Bitmap  bm = BitmapFactory.decodeStream(is);
							nnn.setImageBitmap(bm);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}finally{
							try {
								is.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if(requestCode ==11){
						String name = data.getStringExtra("马志远大帅比");
						date.setText(name);
						
					}
				
				} 		
			}
		  @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			  
				Intent intent = new Intent();
				switch (arg0.getId()) {
				case R.id.ppp:
//					view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
//					LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//				ppp.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_bottom_in));
			    	popup.showAtLocation(imageView, Gravity.BOTTOM, 0,0);
					break;
	            case R.id.hhh:
	            	if (popup.isShowing()) {					
	            		popup.dismiss();

					}
					break; 
	            case R.id.select_cancel:
	            	if (popup.isShowing()) {					
	            		popup.dismiss();
					}
					break;
	            case R.id.date:
					Intent intent2 = new Intent(PersonalInfoActivity.this,Calendar.class);
					startActivityForResult(intent2,11);
					break; 
	            case R.id.local:
//	            	Toast.makeText(PersonalInfo.this, "123", Toast.LENGTH_LONG).show();
					intent.setAction(Intent.ACTION_PICK);            	
					intent.setDataAndType(imageUri, "image/*");
					intent.putExtra("crop","true");
					intent.putExtra("aspectX", 2);//比例
					intent.putExtra("acpectY", 1);
					intent.putExtra("outputX", 600);//长宽
					intent.putExtra("outputY", 300);
					intent.putExtra("scale", true);//放大缩小
					intent.putExtra("return-data", false);			
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				// 广播刷新相册  
		            Intent intentBC = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);  
		            intentBC.setData(imageUri);  
		            this.sendBroadcast(intentBC);  			              
					intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
					startActivityForResult(intent, button1);
					if (popup.isShowing()) {					
	            		popup.dismiss();
					}
					break;
		        case R.id.photo:
//		        	Toast.makeText(PersonalInfo.this, "456", Toast.LENGTH_LONG).show();
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, button2);
					if (popup.isShowing()) {					
	            		popup.dismiss();
					}
					break;	
		        case R.id.complete:
//					Toast.makeText(PersonalInfoActivity.this, "ok", Toast.LENGTH_LONG).show();
					File file = new File(Environment.getExternalStorageDirectory(),"aaa.jpg");
					System.out.println(file.getPath());
					Gson g = new Gson();
					final User user = new User(nickname.getText().toString(), name.getText().toString(), signature.getText().toString(), date.getText().toString(),
							gender.getSelectedItem().toString(),school.getText().toString(), phone.getText().toString(), email.getText().toString());
					final String json = g.toJson(user);
					HttpUtils httpUtils = new HttpUtils();
					RequestParams paramsFile = new RequestParams();
					paramsFile.addBodyParameter("json",json);
					paramsFile.addBodyParameter("file", file);
					httpUtils.send(HttpMethod.POST, "http://10.202.1.44:8080/eden/SendPersonalInfoServlet",paramsFile,new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							Toast.makeText(PersonalInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
						}
						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(PersonalInfoActivity.this, "修改失败", Toast.LENGTH_LONG).show();				
						}
					});
					RequestParams requestParams = new RequestParams();
					try {
						requestParams.setBodyEntity(new StringEntity(json, "utf-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					httpUtils.send(HttpMethod.POST,  "http://10.202.1.44:8080/SendPersonalInfoServlet", requestParams,new RequestCallBack<String>() {
	
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							Toast.makeText(PersonalInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
						}
	
						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
						}
					});
					break; 
				default:
					break;
				}
				
			}
		   
		  @Override
			protected void onDestroy() {
				MyApplication.getHttpQueue().cancelAll("picture");
				super.onDestroy();
			}

			@Override
			protected void onStop() {
				MyApplication.getHttpQueue().cancelAll("picture");
				super.onStop();
			}
}

