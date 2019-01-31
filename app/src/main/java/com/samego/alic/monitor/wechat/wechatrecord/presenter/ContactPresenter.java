package com.samego.alic.monitor.wechat.wechatrecord.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.samego.alic.monitor.wechat.wechatrecord.bean.Contact;
import com.samego.alic.monitor.wechat.wechatrecord.model.ContactModel;
import com.samego.alic.monitor.wechat.wechatrecord.model.ContactModelImpl;
import com.samego.alic.monitor.wechat.wechatrecord.model.listener.OnGetContactListener;

import java.util.List;

public class ContactPresenter {
    private Context context;
    private ContactModel contactModel;
    private Handler handler;

    public ContactPresenter(Context context) {
        this.context = context;
        this.contactModel = new ContactModelImpl();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void getContactList() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                contactModel.getContactList(context, new OnGetContactListener() {
                    @Override
                    public void successful(List<Contact> contacts) {
                        for (Contact contact : contacts) {
                            Log.i("alicfeng", contact.getNickname());
                            Log.i("alicfeng", contact.getUsername());
                            Log.i("alicfeng", contact.getType());
                        }
                    }

                    @Override
                    public void fail() {

                    }
                });
            }
        });
    }
}