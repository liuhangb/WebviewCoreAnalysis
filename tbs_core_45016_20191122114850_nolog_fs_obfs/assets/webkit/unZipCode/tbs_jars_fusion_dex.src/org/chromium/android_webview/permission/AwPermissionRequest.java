package org.chromium.android_webview.permission;

import android.net.Uri;
import org.chromium.android_webview.CleanupReference;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.android_webview.permission.TencentAwPermissionRequest;

@JNINamespace("android_webview")
public class AwPermissionRequest
{
  public static final String RESOURCE_MIDI_SYSEX = "android.webkit.resource.MIDI_SYSEX";
  private CleanupReference mCleanupReference;
  protected long mNativeAwPermissionRequest;
  private final Uri mOrigin;
  protected boolean mProcessed;
  private final long mResources;
  
  protected AwPermissionRequest(long paramLong1, Uri paramUri, long paramLong2)
  {
    this.mNativeAwPermissionRequest = paramLong1;
    this.mOrigin = paramUri;
    this.mResources = paramLong2;
    this.mCleanupReference = new CleanupReference(this, new DestroyRunnable(this.mNativeAwPermissionRequest, null));
  }
  
  @CalledByNative
  private static AwPermissionRequest create(long paramLong1, String paramString, long paramLong2)
  {
    if (paramLong1 == 0L) {
      return null;
    }
    return new TencentAwPermissionRequest(paramLong1, Uri.parse(paramString), paramLong2);
  }
  
  private static native void nativeDestroy(long paramLong);
  
  public void deny()
  {
    validate();
    long l = this.mNativeAwPermissionRequest;
    if (l != 0L)
    {
      nativeOnAccept(l, 0L);
      destroyNative();
    }
    this.mProcessed = true;
  }
  
  @CalledByNative
  protected void destroyNative()
  {
    this.mCleanupReference.cleanupNow();
    this.mCleanupReference = null;
    this.mNativeAwPermissionRequest = 0L;
  }
  
  public Uri getOrigin()
  {
    return this.mOrigin;
  }
  
  public long getResources()
  {
    return this.mResources;
  }
  
  public void grant()
  {
    validate();
    long l = this.mNativeAwPermissionRequest;
    if (l != 0L)
    {
      nativeOnAccept(l, 0L);
      destroyNative();
    }
    this.mProcessed = true;
  }
  
  protected native void nativeOnAccept(long paramLong1, long paramLong2);
  
  protected void validate()
  {
    if (ThreadUtils.runningOnUiThread())
    {
      if (!this.mProcessed) {
        return;
      }
      throw new IllegalStateException("Either grant() or deny() has been already called.");
    }
    throw new IllegalStateException("Either grant() or deny() should be called on UI thread");
  }
  
  private static final class DestroyRunnable
    implements Runnable
  {
    private final long a;
    
    private DestroyRunnable(long paramLong)
    {
      this.a = paramLong;
    }
    
    public void run()
    {
      AwPermissionRequest.nativeDestroy(this.a);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\permission\AwPermissionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */