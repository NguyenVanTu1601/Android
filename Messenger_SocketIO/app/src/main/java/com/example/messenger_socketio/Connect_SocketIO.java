package com.example.messenger_socketio;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connect_SocketIO {
    private Socket mSocket;
    private Activity activity;

    public Connect_SocketIO(Activity activity) {
        this.activity = activity;
    }

    public void  connectSocketIO(){
        try {
            mSocket = IO.socket("http://192.168.1.5:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.connect();
    }

    public void sendUser(String userName){
        mSocket.emit("userName",userName);
    }

    public void sendMessenger(String messenger){
        mSocket.emit("Messenger-Send",messenger);
    }

    public void getMessenger(){
        mSocket.on("server-send-messenger", emit);
    }

    private Emitter.Listener emit = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String tinnhan = object.getString("messenger");
                        Toast.makeText(activity, tinnhan, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
