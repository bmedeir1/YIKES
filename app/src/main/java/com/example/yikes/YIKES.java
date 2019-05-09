package com.example.yikes;

import android.app.Activity;
import android.app.Application;

public class YIKES extends Application {

    //This was an attempt to have the app check what activity was active so that it would not reopen
    // the call activity. However, we ended up not needing it so the class and it's references can be removed.

    public void onCreate() {
        super.onCreate();
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
}
