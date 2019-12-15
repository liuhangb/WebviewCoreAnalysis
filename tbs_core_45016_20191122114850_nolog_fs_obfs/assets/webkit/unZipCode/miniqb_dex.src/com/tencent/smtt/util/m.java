package com.tencent.smtt.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class m
{
  private static m jdField_a_of_type_ComTencentSmttUtilM;
  volatile boolean jdField_a_of_type_Boolean = true;
  volatile boolean b = false;
  
  private m()
  {
    a();
  }
  
  public static m a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttUtilM == null) {
        jdField_a_of_type_ComTencentSmttUtilM = new m();
      }
      m localm = jdField_a_of_type_ComTencentSmttUtilM;
      return localm;
    }
    finally {}
  }
  
  private void a()
  {
    this.jdField_a_of_type_Boolean = SmttServiceProxy.getInstance().isOpenDebugTbsEnabled();
  }
  
  private boolean a(Context paramContext)
  {
    boolean bool1 = ContextHolder.getInstance().isThirdPartyApp(paramContext);
    boolean bool3 = false;
    if (!bool1) {
      return false;
    }
    if (this.jdField_a_of_type_Boolean) {
      bool1 = a().b(paramContext);
    } else {
      bool1 = false;
    }
    boolean bool2 = bool3;
    if (this.jdField_a_of_type_Boolean)
    {
      bool2 = bool3;
      if (bool1) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private void b()
  {
    try
    {
      this.b = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private boolean b(Context paramContext)
  {
    System.currentTimeMillis();
    try
    {
      localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.tencent.tbssuite", 0);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        try
        {
          PackageInfo localPackageInfo;
          if (!"3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(paramContext.getPackageManager().getPackageInfo("com.tencent.tbssuite", 64).signatures[0].toCharsString()))
          {
            Toast.makeText(paramContext, "tbssuit sig is wrong!!!", 1).show();
            return false;
          }
          return true;
        }
        catch (Throwable paramContext) {}
        localNameNotFoundException = localNameNotFoundException;
      }
    }
    localPackageInfo = null;
    return localPackageInfo != null;
  }
  
  public String a(Context paramContext, String paramString)
  {
    if (paramString != null) {
      try
      {
        if ((!paramString.startsWith("http://res.imtt.qq.com/april_v3/setTitle.html?title=")) && (!paramString.startsWith("http://debugx5.qq.com")))
        {
          if (this.b)
          {
            if (b(paramContext)) {
              return "http://debugtbs.qq.com?from=core";
            }
            return null;
          }
          return null;
        }
      }
      finally {}
    }
    return null;
  }
  
  public void a(final View paramView)
  {
    if (a(paramView.getContext())) {
      paramView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          paramAnonymousMotionEvent.getAction();
          if ((paramAnonymousMotionEvent.getPointerCount() == 5) && (!m.a().a()))
          {
            m.a(m.a());
            paramView.post(new Runnable()
            {
              public void run()
              {
                Toast.makeText(m.1.this.a.getContext(), "已开启调试功能,点击网页内部按钮或链接即可进入调试界面", 1).show();
              }
            });
          }
          return false;
        }
      });
    }
  }
  
  public boolean a()
  {
    try
    {
      boolean bool = this.b;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */