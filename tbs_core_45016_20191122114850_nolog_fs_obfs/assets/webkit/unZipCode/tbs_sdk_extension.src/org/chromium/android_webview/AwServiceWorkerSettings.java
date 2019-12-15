package org.chromium.android_webview;

import android.content.Context;
import android.os.Process;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwServiceWorkerSettings
{
  private static final String jdField_a_of_type_JavaLangString = "AwServiceWorkerSettings";
  private int jdField_a_of_type_Int = -1;
  private final Object jdField_a_of_type_JavaLangObject;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  private boolean c;
  private final boolean d;
  
  public AwServiceWorkerSettings(Context arg1)
  {
    boolean bool2 = true;
    this.jdField_a_of_type_Boolean = true;
    this.b = true;
    this.jdField_a_of_type_JavaLangObject = new Object();
    if (???.checkPermission("android.permission.INTERNET", Process.myPid(), Process.myUid()) == 0) {
      bool1 = true;
    }
    for (boolean bool1 = false;; bool1 = false) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        this.d = bool1;
        if (!bool1)
        {
          bool1 = bool2;
          this.c = bool1;
          return;
        }
      }
    }
  }
  
  public boolean getAllowContentAccess()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      boolean bool = this.jdField_a_of_type_Boolean;
      return bool;
    }
  }
  
  public boolean getAllowFileAccess()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      boolean bool = this.b;
      return bool;
    }
  }
  
  public boolean getBlockNetworkLoads()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      boolean bool = this.c;
      return bool;
    }
  }
  
  public int getCacheMode()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      int i = this.jdField_a_of_type_Int;
      return i;
    }
  }
  
  public void setAllowContentAccess(boolean paramBoolean)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Boolean != paramBoolean) {
        this.jdField_a_of_type_Boolean = paramBoolean;
      }
      return;
    }
  }
  
  public void setAllowFileAccess(boolean paramBoolean)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.b != paramBoolean) {
        this.b = paramBoolean;
      }
      return;
    }
  }
  
  public void setBlockNetworkLoads(boolean paramBoolean)
  {
    Object localObject1 = this.jdField_a_of_type_JavaLangObject;
    if (!paramBoolean) {}
    try
    {
      if (!this.d) {
        throw new SecurityException("Permission denied - application missing INTERNET permission");
      }
      this.c = paramBoolean;
      return;
    }
    finally {}
  }
  
  public void setCacheMode(int paramInt)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Int != paramInt) {
        this.jdField_a_of_type_Int = paramInt;
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwServiceWorkerSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */