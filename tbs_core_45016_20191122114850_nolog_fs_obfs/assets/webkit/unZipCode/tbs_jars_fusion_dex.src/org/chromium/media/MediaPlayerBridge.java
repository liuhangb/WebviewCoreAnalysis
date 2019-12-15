package org.chromium.media;

import android.graphics.Bitmap;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.MediaPlayer.TrackInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.Surface;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.SystemMediaPlayer;

@JNINamespace("media")
public class MediaPlayerBridge
{
  private static final String TAG = "cr.media";
  protected LoadDataUriTask mLoadDataUriTask;
  protected long mNativeMediaPlayerBridge;
  protected IMediaPlayer mPlayer;
  
  protected MediaPlayerBridge() {}
  
  protected MediaPlayerBridge(long paramLong)
  {
    this.mNativeMediaPlayerBridge = paramLong;
  }
  
  private void cancelLoadDataUriTask()
  {
    LoadDataUriTask localLoadDataUriTask = this.mLoadDataUriTask;
    if (localLoadDataUriTask != null)
    {
      localLoadDataUriTask.cancel(true);
      this.mLoadDataUriTask = null;
    }
  }
  
  private static MediaPlayerBridge create(long paramLong)
  {
    return new MediaPlayerBridge(paramLong);
  }
  
  private boolean hasTrack(int paramInt)
  {
    try
    {
      MediaPlayer.TrackInfo[] arrayOfTrackInfo = getLocalPlayer().getTrackInfo();
      if (arrayOfTrackInfo.length == 0) {
        return true;
      }
      int j = arrayOfTrackInfo.length;
      int i = 0;
      while (i < j)
      {
        MediaPlayer.TrackInfo localTrackInfo = arrayOfTrackInfo[i];
        if (paramInt == localTrackInfo.getTrackType()) {
          return true;
        }
        int k = localTrackInfo.getTrackType();
        if (k == 0) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    catch (Throwable localThrowable) {}
    return true;
  }
  
  @CalledByNative
  protected void destroy()
  {
    cancelLoadDataUriTask();
    this.mNativeMediaPlayerBridge = 0L;
  }
  
  @CalledByNative
  protected AllowedOperations getAllowedOperations()
  {
    Object localObject1 = getLocalPlayer();
    boolean bool3 = true;
    boolean bool4 = true;
    boolean bool5 = true;
    boolean bool1;
    label262:
    boolean bool2;
    try
    {
      localObject2 = localObject1.getClass().getDeclaredMethod("getMetadata", new Class[] { Boolean.TYPE, Boolean.TYPE });
      ((Method)localObject2).setAccessible(true);
      localObject1 = ((Method)localObject2).invoke(localObject1, new Object[] { Boolean.valueOf(false), Boolean.valueOf(false) });
      if (localObject1 != null)
      {
        localObject2 = localObject1.getClass();
        Method localMethod1 = ((Class)localObject2).getDeclaredMethod("has", new Class[] { Integer.TYPE });
        Method localMethod2 = ((Class)localObject2).getDeclaredMethod("getBoolean", new Class[] { Integer.TYPE });
        int i = ((Integer)((Class)localObject2).getField("PAUSE_AVAILABLE").get(null)).intValue();
        int j = ((Integer)((Class)localObject2).getField("SEEK_FORWARD_AVAILABLE").get(null)).intValue();
        int k = ((Integer)((Class)localObject2).getField("SEEK_BACKWARD_AVAILABLE").get(null)).intValue();
        localMethod1.setAccessible(true);
        localMethod2.setAccessible(true);
        if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(i) })).booleanValue())
        {
          bool1 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(i) })).booleanValue();
          if (!bool1)
          {
            bool1 = false;
            break label262;
          }
        }
        bool1 = true;
        try
        {
          if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(j) })).booleanValue())
          {
            bool2 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(j) })).booleanValue();
            if (!bool2)
            {
              bool2 = false;
              break label332;
            }
          }
          bool2 = true;
          label332:
          bool3 = bool5;
          try
          {
            if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(k) })).booleanValue())
            {
              bool3 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(k) })).booleanValue();
              if (bool3) {
                bool3 = bool5;
              } else {
                bool3 = false;
              }
            }
            bool4 = bool2;
            bool2 = bool3;
            bool3 = bool4;
          }
          catch (NoSuchFieldException localNoSuchFieldException1)
          {
            break label488;
          }
          catch (IllegalAccessException localIllegalAccessException1)
          {
            break label542;
          }
          catch (InvocationTargetException localInvocationTargetException1)
          {
            break label596;
          }
          catch (NoSuchMethodException localNoSuchMethodException1)
          {
            break label650;
          }
          bool2 = true;
        }
        catch (NoSuchFieldException localNoSuchFieldException2)
        {
          break label485;
        }
        catch (IllegalAccessException localIllegalAccessException2)
        {
          break label539;
        }
        catch (InvocationTargetException localInvocationTargetException2)
        {
          break label593;
        }
        catch (NoSuchMethodException localNoSuchMethodException2) {}
      }
      bool1 = true;
      bool4 = bool3;
      bool3 = bool2;
      bool2 = bool4;
    }
    catch (NoSuchFieldException localNoSuchFieldException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot find matching fields in Metadata class: ");
      ((StringBuilder)localObject2).append(localNoSuchFieldException3);
      Log.e("cr.media", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (IllegalAccessException localIllegalAccessException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot access metadata: ");
      ((StringBuilder)localObject2).append(localIllegalAccessException3);
      Log.e("cr.media", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (InvocationTargetException localInvocationTargetException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot invoke MediaPlayer.getMetadata() method: ");
      ((StringBuilder)localObject2).append(localInvocationTargetException3);
      Log.e("cr.media", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (NoSuchMethodException localNoSuchMethodException3)
    {
      label485:
      label488:
      label539:
      label542:
      label593:
      label596:
      bool1 = true;
      bool2 = true;
      label650:
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot find getMetadata() method: ");
      ((StringBuilder)localObject2).append(localNoSuchMethodException3);
      Log.e("cr.media", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    return new AllowedOperations(bool1, bool2, bool3);
  }
  
  @CalledByNative
  protected int getCurrentPosition()
  {
    return getLocalPlayer().getCurrentPosition();
  }
  
  @CalledByNative
  protected int getDuration()
  {
    return getLocalPlayer().getDuration();
  }
  
  protected IMediaPlayer getLocalPlayer()
  {
    if (this.mPlayer == null) {
      this.mPlayer = new SystemMediaPlayer();
    }
    return this.mPlayer;
  }
  
  @CalledByNative
  protected int getVideoHeight()
  {
    return getLocalPlayer().getVideoHeight();
  }
  
  @CalledByNative
  protected int getVideoWidth()
  {
    return getLocalPlayer().getVideoWidth();
  }
  
  @CalledByNative
  protected boolean hasAudio()
  {
    return hasTrack(2);
  }
  
  @CalledByNative
  protected boolean hasVideo()
  {
    return hasTrack(1);
  }
  
  @CalledByNative
  protected boolean isPlaying()
  {
    return getLocalPlayer().isPlaying();
  }
  
  protected native void nativeCreateSurfaceTexture(long paramLong);
  
  protected native void nativeEvaluateJavascript(long paramLong, String paramString);
  
  protected native void nativeExitFullscreenByMediaPlayer(long paramLong);
  
  protected native int nativeGetPlayType(long paramLong);
  
  protected native void nativeOnDidSetDataUriDataSource(long paramLong, boolean paramBoolean);
  
  protected native void nativeOnHaveVideoData(long paramLong);
  
  protected native void nativeOnMediaInterruptedByRemote(long paramLong);
  
  protected native void nativeOnMediaPlayeInfo(long paramLong, int paramInt1, int paramInt2);
  
  protected native void nativeOnMediaPlayerEnterFullScreen(long paramLong);
  
  protected native void nativeOnMediaPlayerExitFullScreen(long paramLong);
  
  protected native void nativeOnNoVideoData(long paramLong);
  
  protected native void nativeOnSnapshotReady(long paramLong, Bitmap paramBitmap);
  
  protected native void nativeOnVideoRenderModeChanged(long paramLong);
  
  protected native void nativeOnVideoUrlRedirect(long paramLong, String paramString);
  
  protected native void nativePauseByMediaPlayer(long paramLong, int paramInt, boolean paramBoolean);
  
  protected native void nativePlayByMediaPlayer(long paramLong);
  
  protected native void nativeReleaseSurfaceTexture(long paramLong);
  
  protected native void nativeSeekByMediaPlayer(long paramLong, int paramInt1, int paramInt2);
  
  protected native void nativeSetPlayedByRemote(long paramLong, boolean paramBoolean);
  
  @CalledByNative
  protected void pause()
  {
    getLocalPlayer().pause();
  }
  
  @CalledByNative
  protected boolean prepareAsync()
  {
    try
    {
      getLocalPlayer().prepareAsync();
      return true;
    }
    catch (Exception localException)
    {
      Log.e("cr.media", "Unable to prepare MediaPlayer.", new Object[] { localException });
      return false;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr.media", "Unable to prepare MediaPlayer.", new Object[] { localIllegalStateException });
    }
    return false;
  }
  
  @CalledByNative
  protected void release()
  {
    cancelLoadDataUriTask();
    getLocalPlayer().release();
  }
  
  @CalledByNative
  protected void seekTo(int paramInt)
    throws IllegalStateException
  {
    getLocalPlayer().seekTo(paramInt);
  }
  
  @CalledByNative
  protected boolean setDataSource(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    paramString1 = Uri.parse(paramString1);
    HashMap localHashMap = new HashMap();
    if (paramBoolean) {
      localHashMap.put("x-hide-urls-from-log", "true");
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localHashMap.put("Cookie", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localHashMap.put("User-Agent", paramString3);
    }
    if (Build.VERSION.SDK_INT > 19) {
      localHashMap.put("allow-cross-domain-redirect", "false");
    }
    try
    {
      getLocalPlayer().setDataSource(ContextUtils.getApplicationContext(), paramString1, localHashMap);
      return true;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    return false;
  }
  
  @CalledByNative
  protected boolean setDataSourceFromFd(int paramInt, long paramLong1, long paramLong2)
  {
    try
    {
      ParcelFileDescriptor localParcelFileDescriptor = X5ApiCompatibilityUtils.adoptFd(paramInt);
      getLocalPlayer().setDataSource(localParcelFileDescriptor.getFileDescriptor(), paramLong1, paramLong2);
      localParcelFileDescriptor.close();
      return true;
    }
    catch (IOException localIOException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Failed to set data source from file descriptor: ");
      localStringBuilder.append(localIOException);
      Log.e("cr.media", localStringBuilder.toString(), new Object[0]);
    }
    return false;
  }
  
  @CalledByNative
  protected boolean setDataUriDataSource(String paramString)
  {
    cancelLoadDataUriTask();
    if (!paramString.startsWith("data:")) {
      return false;
    }
    int i = paramString.indexOf(',');
    if (i == -1) {
      return false;
    }
    Object localObject = paramString.substring(0, i);
    paramString = paramString.substring(i + 1);
    localObject = ((String)localObject).substring(5).split(";");
    if (localObject.length != 2) {
      return false;
    }
    if (!"base64".equals(localObject[1])) {
      return false;
    }
    this.mLoadDataUriTask = new LoadDataUriTask(paramString);
    this.mLoadDataUriTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    return true;
  }
  
  protected void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    getLocalPlayer().setOnBufferingUpdateListener(paramOnBufferingUpdateListener);
  }
  
  protected void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener)
  {
    getLocalPlayer().setOnCompletionListener(paramOnCompletionListener);
  }
  
  protected void setOnErrorListener(MediaPlayer.OnErrorListener paramOnErrorListener)
  {
    getLocalPlayer().setOnErrorListener(paramOnErrorListener);
  }
  
  protected void setOnPreparedListener(MediaPlayer.OnPreparedListener paramOnPreparedListener)
  {
    getLocalPlayer().setOnPreparedListener(paramOnPreparedListener);
  }
  
  protected void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    getLocalPlayer().setOnSeekCompleteListener(paramOnSeekCompleteListener);
  }
  
  protected void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    getLocalPlayer().setOnVideoSizeChangedListener(paramOnVideoSizeChangedListener);
  }
  
  @CalledByNative
  protected void setSurface(Surface paramSurface)
  {
    getLocalPlayer().setSurface(paramSurface);
  }
  
  @CalledByNative
  protected void setVolume(double paramDouble)
  {
    IMediaPlayer localIMediaPlayer = getLocalPlayer();
    float f = (float)paramDouble;
    localIMediaPlayer.setVolume(f, f);
  }
  
  @CalledByNative
  protected void start()
  {
    getLocalPlayer().start();
  }
  
  protected static class AllowedOperations
  {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    
    public AllowedOperations(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      this.a = paramBoolean1;
      this.b = paramBoolean2;
      this.c = paramBoolean3;
    }
    
    @CalledByNative("AllowedOperations")
    private boolean canPause()
    {
      return this.a;
    }
    
    @CalledByNative("AllowedOperations")
    private boolean canSeekBackward()
    {
      return this.c;
    }
    
    @CalledByNative("AllowedOperations")
    private boolean canSeekForward()
    {
      return this.b;
    }
  }
  
  protected class LoadDataUriTask
    extends AsyncTask<Void, Void, Boolean>
  {
    private File jdField_a_of_type_JavaIoFile;
    private final String jdField_a_of_type_JavaLangString;
    
    public LoadDataUriTask(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    /* Error */
    protected Boolean a(Void... paramVarArgs)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 4
      //   3: aconst_null
      //   4: astore_1
      //   5: aload_1
      //   6: astore_3
      //   7: aload_0
      //   8: ldc 37
      //   10: ldc 39
      //   12: invokestatic 45	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
      //   15: putfield 47	org/chromium/media/MediaPlayerBridge$LoadDataUriTask:jdField_a_of_type_JavaIoFile	Ljava/io/File;
      //   18: aload_1
      //   19: astore_3
      //   20: new 49	java/io/FileOutputStream
      //   23: dup
      //   24: aload_0
      //   25: getfield 47	org/chromium/media/MediaPlayerBridge$LoadDataUriTask:jdField_a_of_type_JavaIoFile	Ljava/io/File;
      //   28: invokespecial 52	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   31: astore_1
      //   32: new 54	android/util/Base64InputStream
      //   35: dup
      //   36: new 56	java/io/ByteArrayInputStream
      //   39: dup
      //   40: aload_0
      //   41: getfield 32	org/chromium/media/MediaPlayerBridge$LoadDataUriTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
      //   44: invokestatic 62	org/chromium/base/ApiCompatibilityUtils:getBytesUtf8	(Ljava/lang/String;)[B
      //   47: invokespecial 65	java/io/ByteArrayInputStream:<init>	([B)V
      //   50: iconst_0
      //   51: invokespecial 68	android/util/Base64InputStream:<init>	(Ljava/io/InputStream;I)V
      //   54: astore_3
      //   55: sipush 1024
      //   58: newarray <illegal type>
      //   60: astore 4
      //   62: aload_3
      //   63: aload 4
      //   65: invokevirtual 72	android/util/Base64InputStream:read	([B)I
      //   68: istore_2
      //   69: iload_2
      //   70: iconst_m1
      //   71: if_icmpeq +14 -> 85
      //   74: aload_1
      //   75: aload 4
      //   77: iconst_0
      //   78: iload_2
      //   79: invokevirtual 76	java/io/FileOutputStream:write	([BII)V
      //   82: goto -20 -> 62
      //   85: aload_3
      //   86: invokevirtual 79	android/util/Base64InputStream:close	()V
      //   89: aload_1
      //   90: invokestatic 85	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   93: iconst_1
      //   94: invokestatic 91	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   97: areturn
      //   98: astore_3
      //   99: goto +25 -> 124
      //   102: goto +13 -> 115
      //   105: astore 4
      //   107: aload_3
      //   108: astore_1
      //   109: aload 4
      //   111: astore_3
      //   112: goto +12 -> 124
      //   115: aload_1
      //   116: invokestatic 85	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   119: iconst_0
      //   120: invokestatic 91	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   123: areturn
      //   124: aload_1
      //   125: invokestatic 85	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   128: aload_3
      //   129: athrow
      //   130: astore_1
      //   131: aload 4
      //   133: astore_1
      //   134: goto -19 -> 115
      //   137: astore_3
      //   138: goto -36 -> 102
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	141	0	this	LoadDataUriTask
      //   0	141	1	paramVarArgs	Void[]
      //   68	11	2	i	int
      //   6	80	3	localObject1	Object
      //   98	10	3	localObject2	Object
      //   111	18	3	localObject3	Object
      //   137	1	3	localIOException	IOException
      //   1	75	4	arrayOfByte	byte[]
      //   105	27	4	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   32	62	98	finally
      //   62	69	98	finally
      //   74	82	98	finally
      //   85	89	98	finally
      //   7	18	105	finally
      //   20	32	105	finally
      //   7	18	130	java/io/IOException
      //   20	32	130	java/io/IOException
      //   32	62	137	java/io/IOException
      //   62	69	137	java/io/IOException
      //   74	82	137	java/io/IOException
      //   85	89	137	java/io/IOException
    }
    
    public void a()
    {
      Object localObject = this.jdField_a_of_type_JavaIoFile;
      if (localObject == null) {
        return;
      }
      if (!((File)localObject).delete())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Failed to delete temporary file: ");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaIoFile);
        Log.e("cr.media", ((StringBuilder)localObject).toString(), new Object[0]);
        if (jdField_a_of_type_Boolean) {
          return;
        }
        throw new AssertionError();
      }
    }
    
    protected void a(Boolean paramBoolean)
    {
      if (isCancelled())
      {
        a();
        return;
      }
      Boolean localBoolean = paramBoolean;
      if (paramBoolean.booleanValue()) {}
      try
      {
        MediaPlayerBridge.this.getLocalPlayer().setDataSource(ContextUtils.getApplicationContext(), Uri.fromFile(this.jdField_a_of_type_JavaIoFile));
        localBoolean = paramBoolean;
      }
      catch (Exception paramBoolean)
      {
        for (;;) {}
      }
      localBoolean = Boolean.valueOf(false);
      a();
      if ((!jdField_a_of_type_Boolean) && (MediaPlayerBridge.this.mNativeMediaPlayerBridge == 0L)) {
        throw new AssertionError();
      }
      paramBoolean = MediaPlayerBridge.this;
      paramBoolean.nativeOnDidSetDataUriDataSource(paramBoolean.mNativeMediaPlayerBridge, localBoolean.booleanValue());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaPlayerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */