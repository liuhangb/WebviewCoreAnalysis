package com.tencent.tbs.core.partner.impl.wakelock;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import com.tencent.smtt.webkit.ContextHolder;
import java.util.ArrayList;

public class X5CoreWakeLockManager
{
  private static X5CoreWakeLockManager mInstance;
  PowerManager.WakeLock mBackGroundWakeLock = null;
  ArrayList<Object> mBackGroundWakeLockUsers = new ArrayList();
  ArrayList<Object> mForgruondWakeLockUsers = new ArrayList();
  
  private X5CoreWakeLockManager()
  {
    if (ContextHolder.getInstance().getApplicationContext() == null) {
      return;
    }
    this.mBackGroundWakeLock = ((PowerManager)ContextHolder.getInstance().getApplicationContext().getSystemService("power")).newWakeLock(10, "X5CoreBackGround");
  }
  
  private void acquireWakeLock(Activity paramActivity)
  {
    if (paramActivity != null) {
      paramActivity.getWindow().setFlags(128, 128);
    }
  }
  
  public static X5CoreWakeLockManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new X5CoreWakeLockManager();
    }
    return mInstance;
  }
  
  private void releaseWakeLock(Activity paramActivity)
  {
    if (paramActivity != null) {
      paramActivity.getWindow().clearFlags(128);
    }
  }
  
  public void acquireBackGroundWakeLock(Object paramObject)
  {
    if ((paramObject != null) && (!this.mBackGroundWakeLockUsers.contains(paramObject)))
    {
      this.mBackGroundWakeLockUsers.add(paramObject);
      if ((this.mBackGroundWakeLock != null) && (this.mBackGroundWakeLockUsers.size() == 1)) {
        this.mBackGroundWakeLock.acquire();
      }
    }
  }
  
  public void acquireWakeLock(Object paramObject, Activity paramActivity)
  {
    if ((paramObject != null) && (!this.mForgruondWakeLockUsers.contains(paramObject)))
    {
      this.mForgruondWakeLockUsers.add(paramObject);
      acquireWakeLock(paramActivity);
    }
  }
  
  public void releaseBackGroundWakeLock(Object paramObject)
  {
    if ((paramObject != null) && (this.mBackGroundWakeLockUsers.contains(paramObject)))
    {
      this.mBackGroundWakeLockUsers.remove(paramObject);
      if ((this.mBackGroundWakeLock != null) && (this.mBackGroundWakeLockUsers.size() == 0)) {
        this.mBackGroundWakeLock.release();
      }
    }
  }
  
  public void releaseWakeLock(Object paramObject, Activity paramActivity)
  {
    if ((paramObject != null) && (this.mForgruondWakeLockUsers.contains(paramObject)))
    {
      this.mForgruondWakeLockUsers.remove(paramObject);
      if (this.mForgruondWakeLockUsers.size() == 0) {
        releaseWakeLock(paramActivity);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\impl\wakelock\X5CoreWakeLockManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */