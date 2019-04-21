package com.example.dell.kickbang.Service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.ThreadPoolService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MyService extends Service {
    private Presenter presenter;
    private HttpUtils httpUtils;
    private User user ;
    private Timer timer = new Timer();
    private int testTime = 1000*5;
    private int Time = 60000*10;
    private int tid ;
    private int lastid;
    private int res;
    @Override
    public void onCreate() {
        httpUtils = HttpUtils.getHttpUtils();
        presenter = new Presenter(getBaseContext());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        user = (User) intent.getSerializableExtra("User");
        tid = user.getTid();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.i("i","Unbinder");
        super.unbindService(conn);
    }

    public class Mybinder extends Binder{
        public void updataNoti(final String tid){
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.e("BINDER",presenter.getTeamNotinum(tid,"110"));
                }
            };
            timer.schedule(timerTask,1000,testTime);
        }
    }
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("i","bind");
        return new Mybinder();
    }

}