package org.chromium.android_webview;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwTracingController
{
  public static final int CATEGORIES_ALL = 0;
  public static final int CATEGORIES_ANDROID_WEBVIEW = 1;
  public static final int CATEGORIES_FRAME_VIEWER = 6;
  public static final int CATEGORIES_INPUT_LATENCY = 3;
  public static final int CATEGORIES_JAVASCRIPT_AND_RENDERING = 5;
  public static final int CATEGORIES_RENDERING = 4;
  public static final int CATEGORIES_WEB_DEVELOPER = 2;
  public static final int RESULT_ALREADY_TRACING = 1;
  public static final int RESULT_INVALID_CATEGORIES = 2;
  public static final int RESULT_INVALID_MODE = 3;
  public static final int RESULT_SUCCESS = 0;
  private static final List<String> jdField_a_of_type_JavaUtilList = new ArrayList(Arrays.asList(new String[] { "*" }));
  private static final List<String> b = new ArrayList(Arrays.asList(new String[] { "android_webview", "Java", "toplevel" }));
  private static final List<String> c = new ArrayList(Arrays.asList(new String[] { "blink", "cc", "netlog", "renderer.scheduler", "toplevel", "v8" }));
  private static final List<String> d = new ArrayList(Arrays.asList(new String[] { "benchmark", "input", "evdev", "renderer.scheduler", "toplevel" }));
  private static final List<String> e = new ArrayList(Arrays.asList(new String[] { "blink", "cc", "gpu", "toplevel" }));
  private static final List<String> f = new ArrayList(Arrays.asList(new String[] { "blink", "cc", "gpu", "renderer.scheduler", "v8", "toplevel" }));
  private static final List<String> g = new ArrayList(Arrays.asList(new String[] { "blink", "cc", "gpu", "renderer.scheduler", "v8", "toplevel", "disabled-by-default-cc.debug", "disabled-by-default-cc.debug.picture", "disabled-by-default-cc.debug.display_items" }));
  private static final List<List<String>> h = new ArrayList(Arrays.asList(new List[] { jdField_a_of_type_JavaUtilList, b, c, d, e, f, g }));
  private long jdField_a_of_type_Long = nativeInit();
  private OutputStream jdField_a_of_type_JavaIoOutputStream;
  
  private String a(Collection<Integer> paramCollection, Collection<String> paramCollection1)
  {
    HashSet localHashSet = new HashSet();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      int i = ((Integer)paramCollection.next()).intValue();
      localHashSet.addAll((Collection)h.get(i));
    }
    localHashSet.addAll(paramCollection1);
    if (localHashSet.isEmpty()) {
      localHashSet.add("-*");
    }
    return TextUtils.join(",", localHashSet);
  }
  
  private static boolean a(int paramInt)
  {
    boolean bool = true;
    if (paramInt != 0)
    {
      if (paramInt == 1) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  private static boolean a(String paramString)
  {
    return (!paramString.startsWith("-")) && (!paramString.contains(","));
  }
  
  private static boolean a(Collection<String> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!a((String)paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  private native long nativeInit();
  
  private native boolean nativeIsTracing(long paramLong);
  
  private native boolean nativeStart(long paramLong, String paramString, int paramInt);
  
  private native boolean nativeStopAndFlush(long paramLong);
  
  @CalledByNative
  private void onTraceDataChunkReceived(byte[] paramArrayOfByte)
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null) {
      localOutputStream.write(paramArrayOfByte);
    }
  }
  
  @CalledByNative
  private void onTraceDataComplete()
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null) {
      localOutputStream.close();
    }
  }
  
  public boolean isTracing()
  {
    return nativeIsTracing(this.jdField_a_of_type_Long);
  }
  
  public int start(Collection<Integer> paramCollection, Collection<String> paramCollection1, int paramInt)
  {
    if (isTracing()) {
      return 1;
    }
    if (!a(paramCollection1)) {
      return 2;
    }
    if (!a(paramInt)) {
      return 3;
    }
    paramCollection = a(paramCollection, paramCollection1);
    nativeStart(this.jdField_a_of_type_Long, paramCollection, paramInt);
    return 0;
  }
  
  public boolean stopAndFlush(@Nullable OutputStream paramOutputStream)
  {
    if (!isTracing()) {
      return false;
    }
    this.jdField_a_of_type_JavaIoOutputStream = paramOutputStream;
    nativeStopAndFlush(this.jdField_a_of_type_Long);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwTracingController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */