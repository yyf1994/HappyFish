package com.yyf.happyfish.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wangyan
 * @name BaseActivity
 * @description 共同的UI处理及子类常用方法(所有ui包中的activity继承此类)
 * @date 20151204
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 存放所有的activity
     */
    public static List<Activity> activitys = new ArrayList<Activity>();
    /**
     * 进度条
     */
//    private CustomProgressDialog progressDialog = null;
    /**
     * 因异常终止程序（退出时间）
     */
    private long createTime;
    /**
     * onCreate生命周期
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        createTime = System.currentTimeMillis();
        // 添加Activity到容器中
        activitys.add(this);
    }

    /**
     * 跳转
     *
     * @param activityClass
     */
    public void SkipActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
//        overridePendingTransition(R.anim.grow_from_top,
//                R.anim.small_2_big);
    }

    /**
     * 拨打电话
     * */
    public void CallPhoneNum(String number) {
        Intent in2 = new Intent();
        in2.setAction(Intent.ACTION_CALL);
        in2.setData(Uri.parse(number));
        startActivity(in2);
    }
    /**
     * 退出
     */
    @Override
    public void onBackPressed() {
        // TODO 自动生成的方法存根
        super.onBackPressed();
        this.finish();
//		overridePendingTransition(R.anim.push_on_move, R.anim.push_left_out);
    }

    /**
     * 吐出一个短的消息提示
     *
     * @param message 消息内容
     */
    public void toastS(String message) {
//        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 提示错误语
     */
    public void toastSError() {
        // Toast.makeText(getBaseContext(), "无法连接服务器", Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getBaseContext(), "无法连接服务器", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 吐出一个短的消息提示
     *
     * @param messageId 消息ID
     */
    public void toastS(int messageId) {
//        Toast.makeText(getBaseContext(), getString(messageId),
//                Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getBaseContext(), getString(messageId), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 开始加载数据时候的进度
     */
/*    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage("正在加载");
        }
        if (!isFinishing()) {
            progressDialog.show();
        }
    }*/

    /**
     * 停止加载
     */
  /*  public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }*/


    /**
     * 在其他继承了BaseActivity的类里，遍历activty退出程序
     */
    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activitys) {
            activity.finish();
        }
    }

    /**
     * 因异常而终止Activity
     */
/*    public void becauseExceptionFinishActivity() {
        long currTime = System.currentTimeMillis();
        if (currTime - createTime < 1000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    overridePendingTransition(R.anim.normal,
                            R.anim.alpha_hidden);
                    finish();
                }
            }, 1000 - (currTime - createTime));
        } else {
            overridePendingTransition(R.anim.normal, R.anim.alpha_hidden);
            finish();
        }
    }*/




}
