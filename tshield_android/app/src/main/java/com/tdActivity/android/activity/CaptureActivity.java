package com.tdActivity.android.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tdActivity.android.AppManager;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.Base32String;
import com.tdActivity.android.util.Base32String.DecodingException;
import com.tdActivity.android.util.ListUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.util.UriDecodor;
import com.tdActivity.android.util.UriUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.ViewfinderView;
import com.umeng.analytics.MobclickAgent;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.decoding.RGBLuminanceSource;
import com.zxing.decoding.Utils;

/**
 * Initial the camera 二维码扫�? 部分错误已经解决
 * 
 * @author Ryan.Tang
 */
public class CaptureActivity extends BaseActivity implements Callback, OnClickListener {
	private final int REQUEST_CODE = 234;
	private String photo_path;// 照片文件路径
	private Bitmap scanBitmap;// 选择的照�?
	private final int TOAST_INFO = 0x01;
	private CustomProgressDialog progressDialog = null;
	private static final String LOCAL_TAG = "CaptureActivity";
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;// 字符�?
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;// 是否播放声音标志
	private static final float BEEP_VOLUME = 0.20f;
	private boolean vibrate;// 是否震动标志
	private TextView tvExplain;
	private PopupWindow popupWindow;
	private View contentView;
	private ProgressDialog dialog;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private Button ivBack;
	private TextView tvTitle, tishi;
	private Button btnMore;
	private int b;
	private final int SAME_WITH_DATA = 0x0003;
	private VersionOneDao mDao;

	@SuppressLint("HandlerLeak")
	Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case TOAST_INFO:
				// 对数据进行添�?
				ProgressShowUtils.stopProgressDialog(progressDialog);
				myApplication.isChanged = true;
				if (1 == getIntent().getIntExtra("AddUserFragment", 0)) {
					Intent intent = new Intent();
					setResult(0x112, intent);
				}
				CaptureActivity.this.finish();

				// Bundle b=msg.getData();
				// String uriStr=b.getString("strUri");
				// parseSecret(uriStr);
				break;
			case 0x0002:// 无法识别
				ProgressShowUtils.stopProgressDialog(progressDialog);
				AlertDialog.Builder dialog = new AlertDialog.Builder(CaptureActivity.this);
				dialog.setMessage("无法识别");
				dialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						viewfinderView.setVisibility(View.VISIBLE);
						handler.restartPreviewAndDecode();

					}
				});
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						CaptureActivity.this.finish();
					}
				});
				dialog.show();
				break;
			case SAME_WITH_DATA:// 有一样的数据
				ProgressShowUtils.stopProgressDialog(progressDialog);
				Toast.makeText(CaptureActivity.this, "已存在", Toast.LENGTH_SHORT).show();
				handler.restartPreviewAndDecode();
				break;
			default:
				break;
			}

		};
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.capture);
		b = getIntent().getIntExtra("AddUserFragment", 0);
		AppManager.getAppManager().addActivity(this);// 入栈
		CameraManager.init(getApplication());
		mDao = myApplication.getDbHelper();
		back_addu_btn = (ImageButton) findViewById(R.id.back_addu_btn);
		back_addu_btn.setBackgroundResource(R.drawable.backnew);
		click_back_btn = (TextView) findViewById(R.id.click_back_btn);
		tvTitle = (TextView) findViewById(R.id.tv_headadd);
		btnMore = (Button) findViewById(R.id.save_btn);
		tvTitle.setText("二维码扫描");
		btnMore.setText("相册");
		btnMore.setTextColor(Color.WHITE);
		btnMore.setTextSize(18);
		click_back_btn.setOnClickListener(this);
		btnMore.setOnClickListener(this);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		contentView = LayoutInflater.from(CaptureActivity.this).inflate(R.layout.testactivity_main, null);// 继续扫描
		contentView.findViewById(R.id.mytrue).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
					viewfinderView.setVisibility(View.VISIBLE);
					handler.restartPreviewAndDecode();
				}
			}
		});

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		 MobclickAgent.onPageStart("CaptureActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		    MobclickAgent.onResume(this); 
		
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);

		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("CaptureActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
	    MobclickAgent.onPause(this);
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		CaptureActivity.this.finish();
		popupWindow = null;
		dialog = null;
		System.gc();
		super.onDestroy();

	}

	/**
	 * Handler scan result
	 * 
	 * @param result
	 * @param barcode
	 */

	// 进行二维码解析（这个方法是如何自动调用的？是回调�?,相当于回调，CaptureActivityHandler中进行了调用�?
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (UriUtils.uriMatch(resultString)) {
			// 使用正则表达式进行提前验�?
			// otpauth://totp/Google:yryznet@gmail.com?secret=lkrndeqhkfspp5npwqhp6u2tlogktdlu&issuer=Google
			progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, CaptureActivity.this,
					ProgressShowUtils.CAPTURE);
			threadParseSecret(resultString);
		} else {
			handler2.sendEmptyMessage(0x0002);
		}

	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(CaptureActivity.this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 初始化铃�?
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	// 按后�?�?
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			// quit();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Parses a secret value from a URI. The format will be:
	 * 
	 * otpauth://totp/user@example.com?secret=FFF...
	 * otpauth://hotp/user@example.com?secret=FFF...&counter=123
	 * 
	 * @param uri
	 *            The URI containing the secret key
	 * @param confirmBeforeSave
	 *            a boolean to indicate if the user should be prompted for
	 *            confirmation before updating the otp account information.
	 */
	private static final String OTP_SCHEME = "otpauth";
	private static final String TOTP = "totp"; // time-based
	private static final String SECRET_PARAM = "secret";

	private void showError(boolean flag) {
		String message = null;
		if (flag) {
			message = "账号已存在";
		} else {
			message = "二维码格式不正确";
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Toast.makeText(CaptureActivity.this, message, Toast.LENGTH_SHORT).show();
		handler.restartPreviewAndDecode();// 重新能够扫描

	}

	// 进行二维码中的数据进行验证（）这中有许多问题，换成自己的处理方式 //应为已经对数据进行了正则表达是判断，�?有不用再进行判断�?
	private void parseSecret(String uriStr) {

	}

	private void threadParseSecret(final String uriStr) {
		final UserInfo info = UriDecodor.getInfoFromUrl(uriStr);
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				if (MyApplication.getDbHelper().userInfoHasDate(info)) {// 如果已经存在
					handler2.sendEmptyMessageDelayed(SAME_WITH_DATA, 2000);
				} else {
					// 返回
					myApplication.userInfos.add(info);//直接添加
					// TODO 进行写临时文件
					ArrayList<UserInfo> userInfos=(ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
					handler2.sendEmptyMessage(TOAST_INFO);
					int index = MyApplication.getDbHelper().userInfoGetCount();
					MyApplication.getDbHelper().userInfoInsert(info, index);
//					myApplication.userInfos = MyApplication.getDbHelper().userInfosFrom();// 重新赋值‘
					LocalSynchUtils.updateCacheFileNoThread(myApplication,userInfos);//因为是直接在线程中进行更新且只有更新完成之后才能操作 不存在多个线程同操作一个对象的可能
					
				}
			}

		});

	}

	private static byte[] decodeKey(String secret) throws DecodingException {
		return Base32String.decode(secret);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_btn:
			photo();
			break;
		case R.id.click_back_btn:
			finish();
			break;
		default:
			break;
		}
	}

	private void photo() {
		// 针对不同SDK版本进行intent区分，但是目的是�?样的，在相册中�?�择照片
		Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
		if (Build.VERSION.SDK_INT < 19) {// API<19使用ACTION_GET_CONTENT
			// API>=19使用ACTION_OPEN_DOCUMENT
			// 打开相册
			innerIntent.setAction(Intent.ACTION_GET_CONTENT);
		} else {
			innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		// innerIntent.setAction(Intent.ACTION_GET_CONTENT);

		innerIntent.setType("image/*");

		Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
		startActivityForResult(wrapperIntent, REQUEST_CODE);
	}

	// 回调
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case REQUEST_CODE:

				String[] proj = { MediaStore.Images.Media.DATA };
				// 获取选中图片的路�?
				Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);

				if (cursor.moveToFirst()) {

					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					photo_path = cursor.getString(column_index);
					if (photo_path == null) {
						photo_path = Utils.getPath(getApplicationContext(), data.getData());// 获取路径
					}

				}

				cursor.close();
				progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, CaptureActivity.this,
						ProgressShowUtils.CAPTURE);
				// 打开线程去进行解�?
				fixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {

						Result result = scanningImage(photo_path);
						// String result = decode(photo_path);
						if (result == null) {
							handler2.sendEmptyMessage(0x0002);
						} else {// 得到数据
							String recode = recode(result.toString());
							// 对扫描到得二维码进行�?�?
							if (UriUtils.uriMatch(recode)) {// 二维码符�?
								progressDialog = ProgressShowUtils.startProgressDialog(progressDialog,
										CaptureActivity.this, ProgressShowUtils.CAPTURE);
								threadParseSecret(recode);

							} else {
								handler2.sendEmptyMessage(0x0002);
							}
							// finish();
						}
					}
				});
				break;

			}

		}
	}

	protected Result scanningImage(String path) {
		if (TextUtils.isEmpty(path)) {

			return null;

		}
		// DecodeHintType 和EncodeHintType
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 先获取原大小
		scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // 获取新的大小

		int sampleSize = (int) (options.outHeight / (float) 200);

		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);

		// --------------测试的解析方�?---PlanarYUVLuminanceSource-这几行代码对project没作�?----------

		LuminanceSource source1 = new PlanarYUVLuminanceSource(rgb2YUV(scanBitmap), scanBitmap.getWidth(),
				scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(), scanBitmap.getHeight(), false);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source1));
		MultiFormatReader reader1 = new MultiFormatReader();
		Result result1;
		try {
			result1 = reader1.decode(binaryBitmap);
			String content = result1.getText();
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		// ----------------------------

		RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {

			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {

			e.printStackTrace();

		} catch (ChecksumException e) {

			e.printStackTrace();

		} catch (FormatException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 中文乱码
	 * 
	 * 暂时解决大部分的中文乱码但是还有部分的乱码无法解�?.
	 * 
	 * 如果您有好的解决方式 请联�? 2221673069@qq.com
	 * 
	 * 我会很乐意向您请�? 谢谢�?
	 * 
	 * @return
	 */
	private String recode(String str) {
		String formart = "";

		try {
			boolean ISO = Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
			if (ISO) {
				formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
			} else {
				formart = str;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return formart;
	}

	/**
	 * 
	 * @param bitmap
	 *            转换的图�?
	 * @return YUV数据
	 */
	public byte[] rgb2YUV(Bitmap bitmap) {
		// 该方法来自QQ空间
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int len = width * height;
		byte[] yuv = new byte[len * 3 / 2];
		int y, u, v;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = pixels[i * width + j] & 0x00FFFFFF;

				int r = rgb & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 16) & 0xFF;

				y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
				u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
				v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

				y = y < 16 ? 16 : (y > 255 ? 255 : y);
				u = u < 0 ? 0 : (u > 255 ? 255 : u);
				v = v < 0 ? 0 : (v > 255 ? 255 : v);

				yuv[i * width + j] = (byte) y;
				// yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
				// yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
			}
		}
		return yuv;
	}
}