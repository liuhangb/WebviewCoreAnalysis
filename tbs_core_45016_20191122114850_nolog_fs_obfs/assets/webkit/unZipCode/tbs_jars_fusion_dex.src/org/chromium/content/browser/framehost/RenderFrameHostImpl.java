package org.chromium.content.browser.framehost;

import org.chromium.base.Callback;
import org.chromium.base.UnguessableToken;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.RenderFrameHost;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.UntypedHandle;
import org.chromium.mojo.system.impl.CoreImpl;
import org.chromium.services.service_manager.InterfaceProvider;

@JNINamespace("content")
public class RenderFrameHostImpl
  implements RenderFrameHost
{
  private long jdField_a_of_type_Long;
  private final RenderFrameHostDelegate jdField_a_of_type_OrgChromiumContentBrowserFramehostRenderFrameHostDelegate;
  private final InterfaceProvider jdField_a_of_type_OrgChromiumServicesService_managerInterfaceProvider;
  private final boolean jdField_a_of_type_Boolean;
  
  private RenderFrameHostImpl(long paramLong, RenderFrameHostDelegate paramRenderFrameHostDelegate, boolean paramBoolean, int paramInt)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumContentBrowserFramehostRenderFrameHostDelegate = paramRenderFrameHostDelegate;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_OrgChromiumServicesService_managerInterfaceProvider = new InterfaceProvider(CoreImpl.a().acquireNativeHandle(paramInt).toMessagePipeHandle());
    this.jdField_a_of_type_OrgChromiumContentBrowserFramehostRenderFrameHostDelegate.renderFrameCreated(this);
  }
  
  @CalledByNative
  private void clearNativePtr()
  {
    this.jdField_a_of_type_Long = 0L;
    this.jdField_a_of_type_OrgChromiumContentBrowserFramehostRenderFrameHostDelegate.renderFrameDeleted(this);
  }
  
  @CalledByNative
  private static RenderFrameHostImpl create(long paramLong, RenderFrameHostDelegate paramRenderFrameHostDelegate, boolean paramBoolean, int paramInt)
  {
    return new RenderFrameHostImpl(paramLong, paramRenderFrameHostDelegate, paramBoolean, paramInt);
  }
  
  private native UnguessableToken nativeGetAndroidOverlayRoutingToken(long paramLong);
  
  private native void nativeGetCanonicalUrlForSharing(long paramLong, Callback<String> paramCallback);
  
  private native String nativeGetLastCommittedURL(long paramLong);
  
  private native void nativeSetHasReceivedUserGesture(long paramLong);
  
  public UnguessableToken getAndroidOverlayRoutingToken()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return null;
    }
    return nativeGetAndroidOverlayRoutingToken(l);
  }
  
  public void getCanonicalUrlForSharing(Callback<String> paramCallback)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L)
    {
      paramCallback.onResult(null);
      return;
    }
    nativeGetCanonicalUrlForSharing(l, paramCallback);
  }
  
  public String getLastCommittedURL()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return null;
    }
    return nativeGetLastCommittedURL(l);
  }
  
  public InterfaceProvider getRemoteInterfaces()
  {
    return this.jdField_a_of_type_OrgChromiumServicesService_managerInterfaceProvider;
  }
  
  public RenderFrameHostDelegate getRenderFrameHostDelegate()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserFramehostRenderFrameHostDelegate;
  }
  
  public boolean isIncognito()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void setHasReceivedUserGesture()
  {
    nativeSetHasReceivedUserGesture(this.jdField_a_of_type_Long);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\framehost\RenderFrameHostImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */