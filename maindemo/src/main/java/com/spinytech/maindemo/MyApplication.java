package com.spinytech.maindemo;

import com.spinytech.macore.MaApplication;
import com.spinytech.musicdemo.MusicApplicationLogic;
import com.spinytech.picdemo.PicApplicationLogic;
import com.spinytech.webdemo.WebApplicationLogic;

/**
 * Created by wanglei on 2016/11/29.
 */

public class MyApplication extends MaApplication {


    @Override
    protected void initializeLogic() {
        registerApplicationLogic(getPackageName(),999, MainApplicationLogic.class);
        registerApplicationLogic(getPackageName(),998, WebApplicationLogic.class);
        registerApplicationLogic(getPackageName(),999, MusicApplicationLogic.class);
        registerApplicationLogic(getPackageName(),999, PicApplicationLogic.class);
    }


}
