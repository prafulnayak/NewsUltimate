package org.sairaa.newsultimate;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class DialogAction {
    private Context mContext;
    ProgressDialog progressDialog;
    public DialogAction(Context context) {
        mContext = context;

    }
    public void showDialog(String title, String msg){
        Log.i("show", "called");
        progressDialog = ProgressDialog.show(mContext, title, msg, true, false);
    }
    public  void hideDialog(){
        Log.i("hide", "called");
        if(progressDialog != null) {
            Log.i("hide", "called2");
            progressDialog.dismiss();
            progressDialog = null;
        }

    }
}
