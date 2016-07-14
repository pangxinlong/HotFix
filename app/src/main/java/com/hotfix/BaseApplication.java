package com.hotfix;

import com.alipay.euler.andfix.patch.PatchManager;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by password on 16-7-14.
 * Description TODO
 */
public class BaseApplication extends Application {

    private PatchManager patchManager;

    public String mToastContent;

    public static BaseApplication self;

    private String pathDir;

    private String hotfixPath;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        patchManager = new PatchManager(getApplicationContext());
        patchManager.init("1.0");//current version
        patchManager.loadPatch();

        if (existsSDCard()) {
            pathDir = getInnerSDCardPath() + "/data/"+getPackageName();
            File fileDir = new File(pathDir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            } else {
                hotfixPath=pathDir + "/out.apatch";
                File file = new File(hotfixPath);
                if (file.exists()) {
                    try {
                        patchManager.addPatch(hotfixPath);
                        Log.e("path==", hotfixPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        setToastContent();
    }

    private void setToastContent() {
        mToastContent = "test error";
    }

    /**
     * 获取内置SD卡路径
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 是否存在sd卡
     */
    public static boolean existsSDCard() {
        boolean sdCardExist =
                Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return sdCardExist;
    }
}
