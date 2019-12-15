package com.tencent.smtt.webkit.nativewidget;

import android.location.Location;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.Surface;
import android.webkit.ValueCallback;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.util.HashMap;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.ContentViewCoreImpl;

@JNINamespace("tencent")
public class NativeWidgetBridge
  implements INativeWidgetHost
{
  private long jdField_a_of_type_Long;
  private ValueCallback<Location> jdField_a_of_type_AndroidWebkitValueCallback = null;
  private INativeWidget jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
  private ContentViewCoreImpl jdField_a_of_type_OrgChromiumContentBrowserContentViewCoreImpl;
  private MotionEvent.PointerCoords[] jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords = null;
  private MotionEvent.PointerProperties[] jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties = null;
  private long b;
  
  protected NativeWidgetBridge(long paramLong, ContentViewCoreImpl paramContentViewCoreImpl, String paramString)
  {
    this.b = paramLong;
    this.jdField_a_of_type_OrgChromiumContentBrowserContentViewCoreImpl = paramContentViewCoreImpl;
    this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    try
    {
      paramContentViewCoreImpl = paramString.split(";");
      int i = 0;
      while (i < paramContentViewCoreImpl.length)
      {
        paramString = paramContentViewCoreImpl[i].split(":");
        if (paramString.length == 2) {
          this.jdField_a_of_type_JavaUtilHashMap.put(paramString[0], paramString[1]);
        }
        i += 1;
      }
      return;
    }
    catch (Exception paramContentViewCoreImpl)
    {
      paramString = new StringBuilder();
      paramString.append("new NativeWidgetBridge, Exception:");
      paramString.append(paramContentViewCoreImpl);
      Log.e("native-widget", paramString.toString());
    }
  }
  
  private void a(String paramString1, String paramString2)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserContentViewCoreImpl;
    if (localObject == null) {
      return;
    }
    WebView localWebView = (WebView)((ContentViewCoreImpl)localObject).getContainerView();
    if ((localWebView != null) && (paramString1 != null))
    {
      localObject = paramString1;
      if (paramString2 != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString1);
        ((StringBuilder)localObject).append("(");
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(")");
        localObject = ((StringBuilder)localObject).toString();
      }
      localWebView.evaluateJavascript((String)localObject, null);
    }
  }
  
  @CalledByNative
  private static NativeWidgetBridge create(long paramLong, ContentViewCoreImpl paramContentViewCoreImpl, String paramString)
  {
    return new NativeWidgetBridge(paramLong, paramContentViewCoreImpl, paramString);
  }
  
  @CalledByNative
  protected void destroy()
  {
    this.b = 0L;
    if (this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget != null)
    {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getNativeWidgetManager().destroyNativeWidget(this.jdField_a_of_type_JavaUtilHashMap);
      this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget.destroy();
    }
    setLocationChangedCallback(null);
  }
  
  public void evaluateJavascript(final String paramString1, final String paramString2)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        NativeWidgetBridge.a(NativeWidgetBridge.this, paramString1, paramString2);
      }
    });
  }
  
  @CalledByNative
  protected void handleTouchEvent(int paramInt1, int paramInt2, long paramLong, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget == null) {
      return;
    }
    if (this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords == null) {
      this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords = new MotionEvent.PointerCoords[16];
    }
    if (this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties == null) {
      this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties = new MotionEvent.PointerProperties[16];
    }
    int i = 0;
    while (i < paramInt2)
    {
      Object localObject = new MotionEvent.PointerCoords();
      ((MotionEvent.PointerCoords)localObject).x = paramArrayOfInt1[i];
      ((MotionEvent.PointerCoords)localObject).y = paramArrayOfInt2[i];
      ((MotionEvent.PointerCoords)localObject).pressure = 1.0F;
      this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords[i] = localObject;
      localObject = new MotionEvent.PointerProperties();
      ((MotionEvent.PointerProperties)localObject).id = 0;
      this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties[i] = localObject;
      i += 1;
    }
    switch (paramInt1)
    {
    default: 
      Log.e("native-widget", "handleTouchEvent, invaid action!");
      return;
    case 3: 
      paramArrayOfInt1 = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 3, 1, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget.handleTouchEvent(paramArrayOfInt1);
      paramArrayOfInt1.recycle();
      return;
    case 2: 
      paramArrayOfInt1 = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 2, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget.handleTouchEvent(paramArrayOfInt1);
      paramArrayOfInt1.recycle();
      return;
    case 1: 
      if (paramInt2 > 1) {
        paramInt1 = 6;
      } else {
        paramInt1 = 1;
      }
      paramArrayOfInt1 = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, paramInt1, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget.handleTouchEvent(paramArrayOfInt1);
      paramArrayOfInt1.recycle();
      return;
    }
    if (paramInt2 > 1) {
      paramInt1 = 5;
    } else {
      paramInt1 = 0;
    }
    if (paramInt2 == 1) {
      this.jdField_a_of_type_Long = paramLong;
    }
    paramArrayOfInt1 = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, paramInt1, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
    this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget.handleTouchEvent(paramArrayOfInt1);
    paramArrayOfInt1.recycle();
  }
  
  @CalledByNative
  protected boolean init()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserContentViewCoreImpl;
    boolean bool2 = false;
    if (localObject != null)
    {
      if (((ContentViewCoreImpl)localObject).getContainerView() == null) {
        return false;
      }
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = ((TencentWebViewProxy.InnerWebView)this.jdField_a_of_type_OrgChromiumContentBrowserContentViewCoreImpl.getContainerView()).getOuterProxy();
      this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getNativeWidgetManager().createNativeWidget(this, this.jdField_a_of_type_JavaUtilHashMap);
      localObject = this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget;
      boolean bool1 = bool2;
      if (localObject != null)
      {
        bool1 = bool2;
        if (((INativeWidget)localObject).isAvailable()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  @CalledByNative
  protected void setContentRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    INativeWidget localINativeWidget = this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget;
    if (localINativeWidget != null) {
      localINativeWidget.setContentRect(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setLocationChangedCallback(ValueCallback<Location> paramValueCallback)
  {
    if ((this.jdField_a_of_type_AndroidWebkitValueCallback == null) && (paramValueCallback == null)) {
      return;
    }
    if ((this.jdField_a_of_type_AndroidWebkitValueCallback != null) && (paramValueCallback != null)) {
      return;
    }
    this.jdField_a_of_type_AndroidWebkitValueCallback = paramValueCallback;
    if (this.jdField_a_of_type_AndroidWebkitValueCallback != null)
    {
      SmttServiceProxy.getInstance().onGeolocationStartUpdating(this.jdField_a_of_type_AndroidWebkitValueCallback, null, true);
      return;
    }
    SmttServiceProxy.getInstance().onGeolocationStopUpdating();
  }
  
  @CalledByNative
  protected void setSurface(Surface paramSurface)
  {
    INativeWidget localINativeWidget = this.jdField_a_of_type_ComTencentSmttWebkitNativewidgetINativeWidget;
    if (localINativeWidget != null) {
      localINativeWidget.setSurface(paramSurface);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\nativewidget\NativeWidgetBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */