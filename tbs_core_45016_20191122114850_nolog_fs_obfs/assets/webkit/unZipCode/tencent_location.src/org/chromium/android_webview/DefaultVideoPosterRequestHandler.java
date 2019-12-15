package org.chromium.android_webview;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;
import java.util.concurrent.Executor;
import org.chromium.base.ThreadUtils;

public class DefaultVideoPosterRequestHandler
{
  private String jdField_a_of_type_JavaLangString = a();
  private AwContentsClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient;
  
  public DefaultVideoPosterRequestHandler(AwContentsClient paramAwContentsClient)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient = paramAwContentsClient;
  }
  
  private static InputStream a(AwContentsClient paramAwContentsClient)
    throws IOException
  {
    PipedInputStream localPipedInputStream = new PipedInputStream();
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        final Bitmap localBitmap = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient.getDefaultVideoPoster();
        if (localBitmap == null)
        {
          DefaultVideoPosterRequestHandler.a(this.jdField_a_of_type_JavaIoPipedOutputStream);
          return;
        }
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable()
        {
          /* Error */
          public void run()
          {
            // Byte code:
            //   0: aload_0
            //   1: getfield 20	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1$1:jdField_a_of_type_AndroidGraphicsBitmap	Landroid/graphics/Bitmap;
            //   4: getstatic 31	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
            //   7: bipush 100
            //   9: aload_0
            //   10: getfield 18	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1$1:jdField_a_of_type_OrgChromiumAndroid_webviewDefaultVideoPosterRequestHandler$1	Lorg/chromium/android_webview/DefaultVideoPosterRequestHandler$1;
            //   13: getfield 34	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1:a	Ljava/io/PipedOutputStream;
            //   16: invokevirtual 40	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
            //   19: pop
            //   20: aload_0
            //   21: getfield 18	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1$1:jdField_a_of_type_OrgChromiumAndroid_webviewDefaultVideoPosterRequestHandler$1	Lorg/chromium/android_webview/DefaultVideoPosterRequestHandler$1;
            //   24: getfield 34	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1:a	Ljava/io/PipedOutputStream;
            //   27: invokevirtual 45	java/io/PipedOutputStream:flush	()V
            //   30: goto +16 -> 46
            //   33: astore_1
            //   34: goto +23 -> 57
            //   37: astore_1
            //   38: ldc 47
            //   40: aconst_null
            //   41: aload_1
            //   42: invokestatic 53	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
            //   45: pop
            //   46: aload_0
            //   47: getfield 18	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1$1:jdField_a_of_type_OrgChromiumAndroid_webviewDefaultVideoPosterRequestHandler$1	Lorg/chromium/android_webview/DefaultVideoPosterRequestHandler$1;
            //   50: getfield 34	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1:a	Ljava/io/PipedOutputStream;
            //   53: invokestatic 58	org/chromium/android_webview/DefaultVideoPosterRequestHandler:a	(Ljava/io/OutputStream;)V
            //   56: return
            //   57: aload_0
            //   58: getfield 18	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1$1:jdField_a_of_type_OrgChromiumAndroid_webviewDefaultVideoPosterRequestHandler$1	Lorg/chromium/android_webview/DefaultVideoPosterRequestHandler$1;
            //   61: getfield 34	org/chromium/android_webview/DefaultVideoPosterRequestHandler$1:a	Ljava/io/PipedOutputStream;
            //   64: invokestatic 58	org/chromium/android_webview/DefaultVideoPosterRequestHandler:a	(Ljava/io/OutputStream;)V
            //   67: aload_1
            //   68: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	69	0	this	1
            //   33	1	1	localObject	Object
            //   37	31	1	localIOException	IOException
            // Exception table:
            //   from	to	target	type
            //   0	30	33	finally
            //   38	46	33	finally
            //   0	30	37	java/io/IOException
          }
        });
      }
    });
    return localPipedInputStream;
  }
  
  private static String a()
  {
    long l = new Random().nextLong();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("android-webview-video-poster:default_video_poster/");
    localStringBuilder.append(String.valueOf(l));
    return localStringBuilder.toString();
  }
  
  private static void b(OutputStream paramOutputStream)
  {
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (IOException paramOutputStream)
    {
      Log.e("DefaultVideoPosterRequestHandler", null, paramOutputStream);
    }
  }
  
  public String getDefaultVideoPosterURL()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public AwWebResourceResponse shouldInterceptRequest(String paramString)
  {
    if (!this.jdField_a_of_type_JavaLangString.equals(paramString)) {
      return null;
    }
    try
    {
      paramString = new AwWebResourceResponse("image/png", null, a(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient));
      return paramString;
    }
    catch (IOException paramString)
    {
      Log.e("DefaultVideoPosterRequestHandler", null, paramString);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\DefaultVideoPosterRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */