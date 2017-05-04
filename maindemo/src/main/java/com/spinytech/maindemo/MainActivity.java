package com.spinytech.maindemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.spinytech.macore.MaApplication;
import com.spinytech.macore.router.LocalRouter;
import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterRequest;
import com.spinytech.macore.router.RouterResponse;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_local_sync_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key1", "hello");
                bundle.putString("key2", "world");
                LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(MainActivity.this,
                        RouterRequest.obtain(MainActivity.this).provider("main").action("sync").data(bundle),
                        new ResponseCallback(){

                            @Override
                            public void onInvoke(Bundle requestData, RouterResponse response) {
                                Toast.makeText(MainActivity.this, response.getData().getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        findViewById(R.id.main_local_async_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key1", "hello");
                bundle.putString("key2", "world");

                LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(MainActivity.this,
                        RouterRequest.obtain(MainActivity.this).provider("main").action("async").data(bundle),
                        new ResponseCallback(){

                            @Override
                            public void onInvoke(Bundle requestData, RouterResponse response) {
                                Toast.makeText(MainActivity.this, response.getData().getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        findViewById(R.id.main_play_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
//                final RouterRequest request = new RouterRequest.Builder(getApplicationContext())
//                        .domain("com.spinytech.maindemo")
//                        .provider("music")
//                        .action("play")
//                        .build();
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this, RouterRequest.obtain(MainActivity.this)
                                .domain("com.spinytech.maindemo")
                                .provider("music")
                                .action("play"));
            }
        });
        findViewById(R.id.main_stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this, RouterRequest.obtain(MainActivity.this)
                                .domain("com.spinytech.maindemo")
                                .provider("music")
                                .action("stop"));
            }
        });


        findViewById(R.id.main_music_shutdown_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this, RouterRequest.obtain(MainActivity.this)
                                .domain("com.spinytech.maindemo")
                                .provider("music")
                                .action("shutdown"));

            }
        });
        findViewById(R.id.main_wide_shutdown_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LocalRouter.getInstance(MaApplication.getMaApplication()).stopWideRouter();
            }
        });
        findViewById(R.id.main_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
                Bundle bundle = new Bundle();
                bundle.putString("is_big", "0");
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this,
                                RouterRequest.obtain(MainActivity.this)
                                    .provider("pic")
                                    .action("pic")
                                    .data(bundle));
            }
        });

        findViewById(R.id.main_big_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
                Bundle bundle = new Bundle();
                bundle.putString("is_big", "1");
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this,
                                RouterRequest.obtain(MainActivity.this)
                                    .provider("pic")
                                    .action("pic")
                                    .data(bundle));
            }
        });

        findViewById(R.id.main_web_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalRouter.getInstance(MaApplication.getMaApplication())
                        .route(MainActivity.this, RouterRequest.obtain(MainActivity.this)
                                .provider("web")
                                .action("web")
                        );

            }
        });
        findViewById(R.id.main_attach_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    RouterResponse response = LocalRouter.getInstance(MaApplication.getMaApplication())
//                            .route(MainActivity.this, RouterRequest.obtain(MainActivity.this)
//                                    .provider("main")
//                                    .action("attachment")
//                                    .object(findViewById(R.id.main_attach_btn))
//                            );
//                    if(response.getObject() instanceof Toast){
//                        ((Toast)response.getObject()).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

}
