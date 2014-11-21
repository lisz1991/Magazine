package com.xiaofeng.izazhi.Async;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.xiaofeng.izazhi.R;

public class AsyncHttp extends AsyncTask<String, Integer, Document> {
	private static AsyncHttp mAsyncHttp;
	private static Context mContext;
	private static ProgressDialog mDialog;
	private static int mType;
	private static Handler mHandler;

	public static final int PAGE_MAIN = 101;
	public static final int PAGE_TYPE = 102;
	public static final int PAGE_DETAIL = 103;
	public static final int PAGE_SUB = 104;
	public static final int PAGE_READ = 105;

	public static int isOK = 0;

	private AsyncHttp() {
		super();
	}

	public static AsyncHttp getInstance(Context con, Handler hand) {
		mContext = con;
		mHandler = hand;
		if (mAsyncHttp == null) {
			return new AsyncHttp();
		}
		return mAsyncHttp;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext);
		mDialog.setCancelable(false);
		mDialog.show();
		mDialog.setContentView(R.layout.progress_dialog);
	}

	@Override
	protected Document doInBackground(String... params) {
		String url = params[0];
		mType = Integer.valueOf(params[1]);
		try {
			Document doc = Jsoup.parse(new URL(url), 10000);
			isOK = 1;
			return doc;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Document result) {
		super.onPostExecute(result);
		mDialog.dismiss();
		Message msg = new Message();
		msg.what = mType;
		msg.obj = result;
		msg.arg1 = isOK;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled(Document result) {
		super.onCancelled(result);
		mDialog.dismiss();
		mHandler.obtainMessage(mType, result);
	}
}
