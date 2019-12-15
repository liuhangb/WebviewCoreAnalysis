package android.webview.chromium;

public abstract class GraphicsUtils
{
  public static long getDrawGLFunctionTable()
  {
    return nativeGetDrawGLFunctionTable();
  }
  
  public static long getDrawSWFunctionTable()
  {
    return nativeGetDrawSWFunctionTable();
  }
  
  private static native long nativeGetDrawGLFunctionTable();
  
  private static native long nativeGetDrawSWFunctionTable();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\GraphicsUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */