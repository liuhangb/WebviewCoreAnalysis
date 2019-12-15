package org.chromium.tencent.base;

import android.content.Context;
import org.chromium.base.PathUtils;

public abstract class TencentPathUtils
{
  private static String sNativeLibraryDirectory;
  
  public static String getDataDirectory(Context paramContext)
  {
    return PathUtils.getDirectoryPath(0);
  }
  
  public static String getNativeLibraryDirectory()
  {
    return sNativeLibraryDirectory;
  }
  
  public static void setNativeLibraryDirectory(String paramString)
  {
    sNativeLibraryDirectory = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\base\TencentPathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */