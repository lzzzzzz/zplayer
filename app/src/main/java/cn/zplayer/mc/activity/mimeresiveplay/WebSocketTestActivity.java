package cn.zplayer.mc.activity.mimeresiveplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import cn.zplayer.mc.R;
import cn.zplayer.mc.activity.ScanMediaActivity;

public class WebSocketTestActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_socket_test);

    }
    public static Intent getIntent(Activity act) {
        Intent intent = new Intent(act, WebSocketTestActivity.class);
        return intent;
    }
    Handler handler=new Handler()

    {

        @Override

        public void handleMessage(Message msg) {

// TODO Auto-generated method stub

            Toast.makeText(WebSocketTestActivity.this, msg.getData().getString("info"),Toast.LENGTH_SHORT).show();

        }

    };
    public void toConnect(View view) {
        URI uri=null;

        try {

            uri = new URI("ws://192.168.31.15:8080/sams/app/talkjs/echo");

        } catch (URISyntaxException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        WebSocketWorker webSocketWorker=new WebSocketWorker(uri, new Draft_17());

        try {

            webSocketWorker.connectBlocking();//此处如果用

            webSocketWorker.connect();//会出错，需要多注意

        } catch (InterruptedException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        webSocketWorker.send("text");
    }

    private class WebSocketWorker extends WebSocketClient {

        public WebSocketWorker(URI serverUri, Draft draft) {

            super(serverUri, draft);

        }

        @Override

        public void onClose(int arg0, String arg1, boolean arg2) {

// TODO Auto-generated method stub

        }

        @Override

        public void onError(Exception arg0) {

// TODO Auto-generated method stub

        }

        @Override

        public void onMessage(String arg0) {

// TODO Auto-generated method stub

            Bundle bundle=new Bundle();

            bundle.putString("info",arg0);

            Message message=new Message();

            message.setData(bundle);

            handler.sendMessage(message);

        }

        @Override

        public void onOpen(ServerHandshake arg0) {

// TODO Auto-generated method stub

        }

    }
}
