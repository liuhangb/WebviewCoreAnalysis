package com.tencent.smtt.jscore;

import android.util.Log;

public class DebugUtils
{
  public static final String TAG = "X5JsCore";
  public static final boolean TRACE_METHOD_CALL = false;
  
  public static void traceMethodCall(String paramString)
  {
    Log.e("X5JsCore", paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\DebugUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */