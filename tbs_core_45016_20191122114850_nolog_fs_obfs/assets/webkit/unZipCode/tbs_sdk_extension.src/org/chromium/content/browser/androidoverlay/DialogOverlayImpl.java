package org.chromium.content.browser.androidoverlay;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.Surface;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.gfx.mojom.Rect;
import org.chromium.media.mojom.AndroidOverlay;
import org.chromium.media.mojom.AndroidOverlayClient;
import org.chromium.media.mojom.AndroidOverlayConfig;
import org.chromium.mojo.common.mojom.UnguessableToken;
import org.chromium.mojo.system.MojoException;

@JNINamespace("content")
public class DialogOverlayImpl
  implements DialogOverlayCore.Host, AndroidOverlay
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private DialogOverlayCore jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore;
  private final ThreadHoppingHost jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayThreadHoppingHost;
  private AndroidOverlayClient jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient;
  private final int[] jdField_a_of_type_ArrayOfInt = new int[2];
  private Runnable jdField_b_of_type_JavaLangRunnable;
  private boolean jdField_b_of_type_Boolean;
  
  public DialogOverlayImpl(final AndroidOverlayClient paramAndroidOverlayClient, final AndroidOverlayConfig paramAndroidOverlayConfig, final Handler paramHandler, Runnable paramRunnable, final boolean paramBoolean)
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient = paramAndroidOverlayClient;
    this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
    this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore = new DialogOverlayCore();
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayThreadHoppingHost = new ThreadHoppingHost(this);
    this.jdField_a_of_type_Long = nativeInit(paramAndroidOverlayConfig.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken.jdField_a_of_type_Long, paramAndroidOverlayConfig.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken.b, paramAndroidOverlayConfig.jdField_b_of_type_Boolean);
    if (this.jdField_a_of_type_Long == 0L)
    {
      this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient.onDestroyed();
      a();
      return;
    }
    paramAndroidOverlayClient = this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore;
    paramHandler = ContextUtils.getApplicationContext();
    nativeGetCompositorOffset(this.jdField_a_of_type_Long, paramAndroidOverlayConfig.jdField_a_of_type_OrgChromiumGfxMojomRect);
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        paramAndroidOverlayClient.initialize(paramHandler, paramAndroidOverlayConfig, DialogOverlayImpl.a(DialogOverlayImpl.this), paramBoolean);
        ThreadUtils.postOnUiThread(new Runnable()
        {
          public void run()
          {
            if (DialogOverlayImpl.a(DialogOverlayImpl.this) != 0L) {
              DialogOverlayImpl.a(DialogOverlayImpl.this, DialogOverlayImpl.a(DialogOverlayImpl.this));
            }
          }
        });
      }
    });
    this.jdField_b_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        paramAndroidOverlayClient.release();
      }
    };
  }
  
  private void a()
  {
    ThreadUtils.assertOnUiThread();
    int i = this.jdField_a_of_type_Int;
    if (i != 0)
    {
      nativeUnregisterSurface(i);
      this.jdField_a_of_type_Int = 0;
    }
    long l = this.jdField_a_of_type_Long;
    if (l != 0L)
    {
      nativeDestroy(l);
      this.jdField_a_of_type_Long = 0L;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore = null;
    AndroidOverlayClient localAndroidOverlayClient = this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient;
    if (localAndroidOverlayClient != null) {
      localAndroidOverlayClient.close();
    }
    this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient = null;
  }
  
  private void a(final IBinder paramIBinder)
  {
    ThreadUtils.assertOnUiThread();
    final DialogOverlayCore localDialogOverlayCore = this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore;
    if (localDialogOverlayCore != null) {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          localDialogOverlayCore.onWindowToken(paramIBinder);
        }
      });
    }
  }
  
  private native void nativeCompleteInit(long paramLong);
  
  private native void nativeDestroy(long paramLong);
  
  private native void nativeGetCompositorOffset(long paramLong, Rect paramRect);
  
  private native long nativeInit(long paramLong1, long paramLong2, boolean paramBoolean);
  
  static native Surface nativeLookupSurfaceForTesting(int paramInt);
  
  private static native int nativeRegisterSurface(Surface paramSurface);
  
  private static native void nativeUnregisterSurface(int paramInt);
  
  @CalledByNative
  private void onPowerEfficientState(boolean paramBoolean)
  {
    
    if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore == null) {
      return;
    }
    AndroidOverlayClient localAndroidOverlayClient = this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient;
    if (localAndroidOverlayClient == null) {
      return;
    }
    localAndroidOverlayClient.onPowerEfficientState(paramBoolean);
  }
  
  @CalledByNative
  private static void receiveCompositorOffset(Rect paramRect, int paramInt1, int paramInt2)
  {
    paramRect.jdField_a_of_type_Int += paramInt1;
    paramRect.b += paramInt2;
  }
  
  public void close()
  {
    
    if (this.jdField_b_of_type_Boolean) {
      return;
    }
    this.jdField_b_of_type_Boolean = true;
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayThreadHoppingHost.onClose();
    Runnable localRunnable = this.jdField_b_of_type_JavaLangRunnable;
    if (localRunnable != null)
    {
      this.jdField_a_of_type_AndroidOsHandler.post(localRunnable);
      this.jdField_b_of_type_JavaLangRunnable = null;
      a();
    }
    this.jdField_a_of_type_JavaLangRunnable.run();
  }
  
  public void enforceClose()
  {
    close();
  }
  
  public void onConnectionError(MojoException paramMojoException)
  {
    ThreadUtils.assertOnUiThread();
    close();
  }
  
  @CalledByNative
  public void onDismissed()
  {
    ThreadUtils.assertOnUiThread();
    AndroidOverlayClient localAndroidOverlayClient = this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient;
    if (localAndroidOverlayClient != null) {
      localAndroidOverlayClient.onDestroyed();
    }
    a(null);
    a();
  }
  
  public void onOverlayDestroyed()
  {
    
    if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore == null) {
      return;
    }
    AndroidOverlayClient localAndroidOverlayClient = this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient;
    if (localAndroidOverlayClient != null) {
      localAndroidOverlayClient.onDestroyed();
    }
    a();
  }
  
  public void onSurfaceReady(Surface paramSurface)
  {
    
    if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore != null)
    {
      if (this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient == null) {
        return;
      }
      this.jdField_a_of_type_Int = nativeRegisterSurface(paramSurface);
      this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient.onSurfaceReady(this.jdField_a_of_type_Int);
      return;
    }
  }
  
  @CalledByNative
  public void onWindowToken(IBinder paramIBinder)
  {
    
    if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore == null) {
      return;
    }
    a(paramIBinder);
  }
  
  public void scheduleLayout(final Rect paramRect)
  {
    
    if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore == null) {
      return;
    }
    nativeGetCompositorOffset(this.jdField_a_of_type_Long, paramRect);
    final DialogOverlayCore localDialogOverlayCore = this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore;
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        localDialogOverlayCore.layoutSurface(paramRect);
      }
    });
  }
  
  public void waitForClose()
  {
    if (jdField_a_of_type_Boolean) {
      return;
    }
    throw new AssertionError("Not reached");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\androidoverlay\DialogOverlayImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */