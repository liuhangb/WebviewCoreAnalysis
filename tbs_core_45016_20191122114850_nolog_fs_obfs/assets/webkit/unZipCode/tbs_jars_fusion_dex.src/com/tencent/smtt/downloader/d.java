package com.tencent.smtt.downloader;

import android.os.HandlerThread;

class d
  extends HandlerThread
{
  private static d a;
  
  public d(String paramString)
  {
    super(paramString);
  }
  
  public static d a()
  {
    try
    {
      if (a == null)
      {
        a = new d("TbsHandlerThread");
        a.start();
      }
      d locald = a;
      return locald;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */