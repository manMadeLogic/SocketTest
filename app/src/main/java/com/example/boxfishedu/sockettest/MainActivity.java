package com.example.boxfishedu.sockettest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1;//按钮的引用
    EditText editText;//文本框的引用
    TextView textView;//文本的引用

    public Handler handler=null;//声明Handler对象
    String str=null;//textView中要显示的内容
    Socket s = null;
    DataOutputStream dout = null;
    DataInputStream din = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);//得到布局中的按钮引用
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button1.setOnClickListener(this);//添加监听
        this.handler=new Handler();//创建属于主线程的handler
    }

    public Runnable runnableUi=new  Runnable(){//构建Runnable对象，在runnable中更新界面
        public void run(){ 				//更新界面
            textView.setText("服务器发来的消息："+str);//设置textView中的内容
        }};

    @Override
    public void onClick(View view) {
        if (view == button1) {
            new Thread() {
                public boolean flag = false;

                public void run() {
                    try {
                        s = new Socket("192.168.77.99", 8888);
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    while (flag) {
                        try {
                            dout = new DataOutputStream(s.getOutputStream());
                            din = new DataInputStream(s.getInputStream());
                            dout.writeUTF(editText.getText().toString());
                            str = din.readUTF();
                            if (dout != null) {
                                dout.close();
                            }
                            if (din != null) {
                                din.close();
                            }
                            flag = false;
                            if (s != null) {
                                s.close();
                            }
                            MainActivity.this.handler.post(MainActivity.this.runnableUi);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
