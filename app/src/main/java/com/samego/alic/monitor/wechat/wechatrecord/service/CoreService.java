package com.samego.alic.monitor.wechat.wechatrecord.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.util.FileUtils;
import com.samego.alic.monitor.wechat.wechatrecord.common.Constant;
import com.samego.alic.monitor.wechat.wechatrecord.configure.ApplicationCfg;
import com.samego.alic.monitor.wechat.wechatrecord.presenter.AccountPresenter;
import com.samego.alic.monitor.wechat.wechatrecord.presenter.ChatRecordPresenter;
import com.samego.alic.monitor.wechat.wechatrecord.presenter.ContactPresenter;
import com.samego.alic.monitor.wechat.wechatrecord.utils.SharedPreferencesUtil;
import com.samego.alic.monitor.wechat.wechatrecord.view.view.AnalysisServiceView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Timer;
import java.util.TimerTask;


public class CoreService extends Service implements AnalysisServiceView {
    private ContactPresenter contactPresenter;
    private AccountPresenter accountPresenter;
    private ChatRecordPresenter chatRecordPresenter;

    //@androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.init();
        this.syncTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 复制数据库
     */
    public void backup() {
        String sourcePath = SharedPreferencesUtil.get(this, Constant.SP_WECHAT_DB_NAME, null);
        String targetPath = this.getFilesDir().getPath() + "/analysis.db";
        if (FileUtils.isFileExists(targetPath)) {
            FileUtils.delete(targetPath);
        }
        FileUtils.copyFile(sourcePath, targetPath);
    }


    /**
     * 计划定时器
     * 服务于同步助手
     */
    private void syncTimer() {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            public void run() {
                backup();
                accountPresenter.syncAccount();
                contactPresenter.syncContactList();
                chatRecordPresenter.syncChatRecord();
            }
        };
        timer.schedule(task, 0, ApplicationCfg.SYNC_FREQUENCY);
    }

    @Override
    public void starting() {
        TastyToast.makeText(this, "Analysis服务正在启动", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }

    @Override
    public void networkUnavailability() {
        TastyToast.makeText(this, "请连接网络", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
    }

    @Override
    public void init() {
        this.contactPresenter = new ContactPresenter(this, this);
        this.accountPresenter = new AccountPresenter(this, this);
        this.chatRecordPresenter = new ChatRecordPresenter(this, this);
        this.starting();
    }

    @Override
    public void getDataFail() {
        TastyToast.makeText(this, "获取数据异常", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
    }
}