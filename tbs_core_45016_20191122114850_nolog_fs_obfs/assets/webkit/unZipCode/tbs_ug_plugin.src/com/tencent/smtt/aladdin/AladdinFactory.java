package com.tencent.smtt.aladdin;

import android.content.Context;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("aladdin")
class AladdinFactory
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static boolean jdField_a_of_type_Boolean = false;
  
  public static void a(Context paramContext)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      b(paramContext);
      return;
    }
  }
  
  private static void b(Context paramContext)
  {
    if (jdField_a_of_type_Boolean) {
      return;
    }
    jdField_a_of_type_Boolean = true;
    if ((ContextUtils.getApplicationContext() == null) || (CommandLine.getInstance() == null))
    {
      ContextUtils.initApplicationContext(paramContext.getApplicationContext());
      nativeInitialize();
      CommandLine.enableNativeProxy();
    }
    AladdinThread.a().a();
  }
  
  public static native void nativeInitialize();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\AladdinFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */