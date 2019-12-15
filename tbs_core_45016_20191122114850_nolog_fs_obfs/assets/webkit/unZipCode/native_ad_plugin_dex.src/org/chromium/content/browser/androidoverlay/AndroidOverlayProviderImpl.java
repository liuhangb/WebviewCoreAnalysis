package org.chromium.content.browser.androidoverlay;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.media.mojom.AndroidOverlay;
import org.chromium.media.mojom.AndroidOverlayClient;
import org.chromium.media.mojom.AndroidOverlayConfig;
import org.chromium.media.mojom.AndroidOverlayProvider;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.system.MojoException;
import org.chromium.services.service_manager.InterfaceFactory;

@JNINamespace("content")
public class AndroidOverlayProviderImpl
  implements AndroidOverlayProvider
{
  private int jdField_a_of_type_Int;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      AndroidOverlayProviderImpl.a(AndroidOverlayProviderImpl.this);
    }
  };
  
  private void a()
  {
    if (this.jdField_a_of_type_AndroidOsHandlerThread != null) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("AndroidOverlayThread");
    this.jdField_a_of_type_AndroidOsHandlerThread.start();
    this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper());
  }
  
  @CalledByNative
  private static boolean areOverlaysSupported()
  {
    return true;
  }
  
  private void b()
  {
    
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int <= 0)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Int -= 1;
  }
  
  public void close() {}
  
  public void createOverlay(InterfaceRequest<AndroidOverlay> paramInterfaceRequest, AndroidOverlayClient paramAndroidOverlayClient, AndroidOverlayConfig paramAndroidOverlayConfig)
  {
    
    if (this.jdField_a_of_type_Int >= 1)
    {
      paramAndroidOverlayClient.onDestroyed();
      paramAndroidOverlayClient.close();
      return;
    }
    a();
    this.jdField_a_of_type_Int += 1;
    paramAndroidOverlayClient = new DialogOverlayImpl(paramAndroidOverlayClient, paramAndroidOverlayConfig, this.jdField_a_of_type_AndroidOsHandler, this.jdField_a_of_type_JavaLangRunnable, false);
    DialogOverlayImpl.a.bind(paramAndroidOverlayClient, paramInterfaceRequest);
  }
  
  public void onConnectionError(MojoException paramMojoException) {}
  
  public static class Factory
    implements InterfaceFactory<AndroidOverlayProvider>
  {
    private static AndroidOverlayProviderImpl a;
    
    public Factory(Context paramContext) {}
    
    public AndroidOverlayProvider createImpl()
    {
      if (a == null) {
        a = new AndroidOverlayProviderImpl();
      }
      return a;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\androidoverlay\AndroidOverlayProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */