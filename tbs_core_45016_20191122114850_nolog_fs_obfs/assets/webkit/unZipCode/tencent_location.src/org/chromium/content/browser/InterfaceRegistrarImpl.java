package org.chromium.content.browser;

import android.content.Context;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.androidoverlay.AndroidOverlayProviderImpl.Factory;
import org.chromium.content_public.browser.InterfaceRegistrar;
import org.chromium.content_public.browser.InterfaceRegistrar.Registry;
import org.chromium.content_public.browser.RenderFrameHost;
import org.chromium.content_public.browser.WebContents;
import org.chromium.media.mojom.AndroidOverlayProvider;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.UntypedHandle;
import org.chromium.mojo.system.impl.CoreImpl;
import org.chromium.services.service_manager.InterfaceRegistry;

@JNINamespace("content")
class InterfaceRegistrarImpl
{
  private static boolean a;
  
  private static void a()
  {
    if (a) {
      return;
    }
    a = true;
    InterfaceRegistrar.Registry.a(new ContentContextInterfaceRegistrar(null));
  }
  
  @CalledByNative
  static void createInterfaceRegistryForContext(int paramInt)
  {
    a();
    InterfaceRegistrar.Registry.a(InterfaceRegistry.a(CoreImpl.a().acquireNativeHandle(paramInt).toMessagePipeHandle()));
  }
  
  @CalledByNative
  static void createInterfaceRegistryForRenderFrameHost(int paramInt, RenderFrameHost paramRenderFrameHost)
  {
    a();
    InterfaceRegistrar.Registry.a(InterfaceRegistry.a(CoreImpl.a().acquireNativeHandle(paramInt).toMessagePipeHandle()), paramRenderFrameHost);
  }
  
  @CalledByNative
  static void createInterfaceRegistryForWebContents(int paramInt, WebContents paramWebContents)
  {
    a();
    InterfaceRegistrar.Registry.a(InterfaceRegistry.a(CoreImpl.a().acquireNativeHandle(paramInt).toMessagePipeHandle()), paramWebContents);
  }
  
  private static class ContentContextInterfaceRegistrar
    implements InterfaceRegistrar<Context>
  {
    public void registerInterfaces(InterfaceRegistry paramInterfaceRegistry, Context paramContext)
    {
      paramInterfaceRegistry.a(AndroidOverlayProvider.a, new AndroidOverlayProviderImpl.Factory(paramContext));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\InterfaceRegistrarImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */