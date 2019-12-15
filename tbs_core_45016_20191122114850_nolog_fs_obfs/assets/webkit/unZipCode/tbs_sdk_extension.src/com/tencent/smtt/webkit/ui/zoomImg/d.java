package com.tencent.smtt.webkit.ui.zoomImg;

import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public class d
{
  public static int a(FileDescriptor paramFileDescriptor)
  {
    if (paramFileDescriptor == null) {
      return 0;
    }
    try
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        int i = new ExifInterface(paramFileDescriptor).getAttributeInt("Orientation", 1);
        if (i != 3)
        {
          if (i != 6)
          {
            if (i != 8) {
              return 0;
            }
            return 270;
          }
          return 90;
        }
        return 180;
      }
      return 0;
    }
    catch (IOException paramFileDescriptor)
    {
      paramFileDescriptor.printStackTrace();
    }
    return 0;
  }
  
  public static int a(InputStream paramInputStream)
  {
    if (paramInputStream == null) {
      return 0;
    }
    try
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        int i = new ExifInterface(paramInputStream).getAttributeInt("Orientation", 1);
        if (i != 3)
        {
          if (i != 6)
          {
            if (i != 8) {
              return 0;
            }
            return 270;
          }
          return 90;
        }
        return 180;
      }
      return 0;
    }
    catch (Throwable paramInputStream)
    {
      paramInputStream.printStackTrace();
    }
    return 0;
  }
  
  public static int a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return 0;
    }
    try
    {
      int i = new ExifInterface(paramString).getAttributeInt("Orientation", 1);
      if (i != 3)
      {
        if (i != 6)
        {
          if (i != 8) {
            return 0;
          }
          return 270;
        }
        return 90;
      }
      return 180;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */