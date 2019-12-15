package com.tencent.mtt.external.reader.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.System;
import android.view.Window;

public class ScreenLockController
  implements Handler.Callback
{
  public static final int KEEP_SCREEN_OFF = 2;
  public static final int KEEP_SCREEN_ON = 1;
  public static final int KEEP_SCREEN_RESET = 3;
  public static final int KEEP_SCREEN_RESET_TIME = 360000;
  int jdField_a_of_type_Int = -2;
  Context jdField_a_of_type_AndroidContentContext;
  Handler jdField_a_of_type_AndroidOsHandler;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int = 0;
  boolean jdField_b_of_type_Boolean = false;
  int c = 0;
  
  public ScreenLockController(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper(), this);
    this.jdField_b_of_type_Int = 0;
    this.c = 0;
    configSystemLock();
  }
  
  void a()
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(3);
    this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(3, 360000L);
  }
  
  public void configSystemLock()
  {
    try
    {
      this.jdField_a_of_type_Int = Settings.System.getInt(this.jdField_a_of_type_AndroidContentContext.getContentResolver(), "screen_off_timeout");
      if (this.jdField_a_of_type_Int != -2)
      {
        Settings.System.putInt(this.jdField_a_of_type_AndroidContentContext.getContentResolver(), "screen_off_timeout", this.jdField_a_of_type_Int);
        this.jdField_b_of_type_Boolean = true;
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_AndroidOsHandler.removeCallbacksAndMessages(null);
    resetSystemTime();
    if ((this.jdField_a_of_type_Int == -2) || (!this.jdField_b_of_type_Boolean))
    {
      Context localContext = this.jdField_a_of_type_AndroidContentContext;
      if ((localContext instanceof Activity)) {
        ((Activity)localContext).getWindow().clearFlags(128);
      }
    }
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public int getScreenLockTime()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    if (this.jdField_a_of_type_Boolean) {
      return false;
    }
    switch (i)
    {
    default: 
      break;
    case 3: 
      resetSystemTime();
      break;
    case 2: 
      paramMessage = this.jdField_a_of_type_AndroidContentContext;
      if ((paramMessage instanceof Activity))
      {
        ((Activity)paramMessage).getWindow().clearFlags(128);
        this.c = 0;
      }
      break;
    case 1: 
      paramMessage = this.jdField_a_of_type_AndroidContentContext;
      if ((paramMessage instanceof Activity)) {
        ((Activity)paramMessage).getWindow().addFlags(128);
      }
      break;
    }
    return true;
  }
  
  public void resetSystemTime()
  {
    if (this.c == 0) {
      return;
    }
    try
    {
      if ((this.jdField_a_of_type_Int != -2) && (this.jdField_b_of_type_Boolean))
      {
        Settings.System.putInt(this.jdField_a_of_type_AndroidContentContext.getContentResolver(), "screen_off_timeout", this.jdField_a_of_type_Int);
        this.c = 0;
        return;
      }
      this.c = 0;
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(1);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(2);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      this.jdField_b_of_type_Boolean = false;
    }
  }
  
  public void setScreenLockTime(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
  }
  
  public void setSystemTime()
  {
    if (this.jdField_a_of_type_Int >= getScreenLockTime()) {
      return;
    }
    if (this.c == getScreenLockTime())
    {
      a();
      return;
    }
    try
    {
      if ((this.jdField_a_of_type_Int != -2) && (this.jdField_b_of_type_Boolean))
      {
        Settings.System.putInt(this.jdField_a_of_type_AndroidContentContext.getContentResolver(), "screen_off_timeout", getScreenLockTime());
        this.c = getScreenLockTime();
        a();
        return;
      }
      this.c = getScreenLockTime();
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(2);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(1);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(2, this.c);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      this.jdField_b_of_type_Boolean = false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ScreenLockController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */