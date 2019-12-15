package com.tencent.smtt.webkit.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.List;

public class f
  extends e
{
  private static volatile boolean b = false;
  private SharedPreferences.Editor jdField_a_of_type_AndroidContentSharedPreferences$Editor;
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private CheckBox jdField_a_of_type_AndroidWidgetCheckBox;
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean;
  
  public f(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, 2, false);
    this.mContext = paramContext;
    if (paramBoolean) {
      paramContext = "打开";
    } else {
      paramContext = "关闭";
    }
    this.jdField_a_of_type_JavaLangString = paramContext;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_AndroidContentSharedPreferences = this.mContext.getApplicationContext().getSharedPreferences("tbs_inspector_settings_prefs", 0);
    a();
  }
  
  private void a(boolean paramBoolean, long paramLong)
  {
    this.jdField_a_of_type_AndroidContentSharedPreferences$Editor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
    this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.putBoolean("tbs_inspector_keep_settings_always", paramBoolean).putLong("tbs_inspector_auto_clear_set_time", paramLong).commit();
  }
  
  public static boolean a()
  {
    try
    {
      boolean bool = b;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static boolean a(Context paramContext)
  {
    String str = paramContext.getApplicationContext().getPackageName();
    return ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName().equals(str);
  }
  
  public static boolean b(Context paramContext)
  {
    boolean bool5 = paramContext instanceof Activity;
    boolean bool4 = false;
    boolean bool1;
    boolean bool2;
    if (bool5)
    {
      bool1 = ((Activity)paramContext).isFinishing();
      bool2 = a(paramContext);
    }
    else
    {
      bool2 = false;
      bool1 = false;
    }
    boolean bool3 = bool4;
    if (bool5)
    {
      bool3 = bool4;
      if (!bool1)
      {
        bool3 = bool4;
        if (bool2) {
          bool3 = true;
        }
      }
    }
    return bool3;
  }
  
  public static boolean c(Context paramContext)
  {
    return com.tencent.smtt.webkit.e.a().s();
  }
  
  public void a()
  {
    setTitleText("TBS debug");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("确认");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
    ((StringBuilder)localObject).append("TBS inspector调试开关?");
    addSubTitle(((StringBuilder)localObject).toString());
    setButton(10000, SmttResource.getString("x5_ok", "string"), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        boolean bool = f.a(f.this).isChecked();
        SmttServiceProxy.getInstance().setIsInspectorEnabled(f.a(f.this));
        paramAnonymousDialogInterface = new StringBuilder();
        paramAnonymousDialogInterface.append("x5_inspector setting ");
        paramAnonymousDialogInterface.append(f.a(f.this));
        paramAnonymousDialogInterface.append(" successful!");
        Log.e("inspector", paramAnonymousDialogInterface.toString());
        paramAnonymousDialogInterface = f.this.mContext;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("已");
        localStringBuilder.append(f.a(f.this));
        localStringBuilder.append("TBS inspector调试开关! 重启生效! always(");
        localStringBuilder.append(bool);
        localStringBuilder.append(")");
        Toast.makeText(paramAnonymousDialogInterface, localStringBuilder.toString(), 0).show();
        long l = System.currentTimeMillis();
        f.a(f.this, bool, l);
        f.a(f.this, new HandlerThread("inspector-check-thread"));
        f.a(f.this).start();
        paramAnonymousDialogInterface = f.this;
        f.a(paramAnonymousDialogInterface, new Handler(f.a(paramAnonymousDialogInterface).getLooper()));
        f.a(f.this).post(new Runnable()
        {
          public void run()
          {
            int i = 0;
            for (;;)
            {
              if (i < 5) {}
              try
              {
                Thread.sleep(500L);
                if (SmttServiceProxy.getInstance().getIsInspectorEnabled() == f.a(f.this)) {
                  Process.killProcess(Process.myPid());
                }
                i += 1;
                continue;
                return;
              }
              catch (InterruptedException localInterruptedException)
              {
                for (;;) {}
              }
            }
          }
        });
      }
    });
    setButton(10001, SmttResource.getString("x5_cancel", "string"), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Log.e("inspector", "x5_inspector setting cancelled!");
        Toast.makeText(f.this.mContext, "已取消TBS inspector设置!", 0).show();
      }
    });
    this.jdField_a_of_type_AndroidWidgetCheckBox = new CheckBox(this.mContext);
    localObject = new LinearLayout.LayoutParams(-2, -2);
    ((LinearLayout.LayoutParams)localObject).gravity = 5;
    ((LinearLayout.LayoutParams)localObject).rightMargin = (MARGIN_TITLE_TOP / 2);
    this.jdField_a_of_type_AndroidWidgetCheckBox.setText("永久生效");
    this.jdField_a_of_type_AndroidWidgetCheckBox.setTextSize(14.0F);
    this.jdField_a_of_type_AndroidWidgetCheckBox.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.jdField_a_of_type_AndroidWidgetCheckBox.setTextColor(TEXT_BLUE_COLOR);
    this.mContentArea.addView(this.jdField_a_of_type_AndroidWidgetCheckBox);
    setBottomButtonMarginTop(0);
  }
  
  public void dismiss()
  {
    super.dismiss();
    b = false;
  }
  
  public void show()
  {
    super.show();
    b = true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */