package org.chromium.base;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil
{
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable == null) {
      return;
    }
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\StreamUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */