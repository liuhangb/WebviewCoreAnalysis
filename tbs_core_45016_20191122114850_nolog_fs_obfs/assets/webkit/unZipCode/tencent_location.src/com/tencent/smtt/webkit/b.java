package com.tencent.smtt.webkit;

import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.webkit.input.c;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import org.chromium.base.ThreadUtils;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.TencentAwContent;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.ui.base.EventForwarder;

public class b
{
  private int jdField_a_of_type_Int = 0;
  private IX5QQBrowserClient jdField_a_of_type_ComTencentSmttExportInternalInterfacesIX5QQBrowserClient = null;
  private WebViewChromiumExtension jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = null;
  private c jdField_a_of_type_ComTencentSmttWebkitInputC = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private TencentAwContent jdField_a_of_type_OrgChromiumTencentTencentAwContent = null;
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  private boolean c = false;
  
  b(TencentAwContent paramTencentAwContent, TencentWebViewProxy paramTencentWebViewProxy, WebViewChromiumExtension paramWebViewChromiumExtension, c paramc)
  {
    this.jdField_a_of_type_OrgChromiumTencentTencentAwContent = paramTencentAwContent;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = paramWebViewChromiumExtension;
    this.jdField_a_of_type_ComTencentSmttWebkitInputC = paramc;
  }
  
  private void a(boolean paramBoolean)
  {
    if (this.c == paramBoolean) {
      return;
    }
    this.c = paramBoolean;
    WebViewChromiumExtension localWebViewChromiumExtension = this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension;
    if (localWebViewChromiumExtension != null) {
      localWebViewChromiumExtension.onResize();
    }
  }
  
  private void a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.jdField_a_of_type_OrgChromiumTencentTencentAwContent.getContentViewCore().getWebContents().updateBrowserControlsState(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
  }
  
  private void b(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("!!!Error in updateBrowserControlsState state is");
      localStringBuilder.append(paramInt);
      Log.e("AdressBar", localStringBuilder.toString());
      return;
    case 5: 
      a(true, true, paramBoolean1, paramBoolean2);
      return;
    case 4: 
      a(true, false, paramBoolean1, paramBoolean2);
      return;
    case 3: 
      a(true, false, paramBoolean1, paramBoolean2);
      a(true, true, paramBoolean1, paramBoolean2);
      return;
    case 2: 
      a(false, true, paramBoolean1, paramBoolean2);
      return;
    }
    a(false, true, paramBoolean1, paramBoolean2);
    a(true, true, paramBoolean1, paramBoolean2);
  }
  
  public int a()
  {
    IX5QQBrowserClient localIX5QQBrowserClient = this.jdField_a_of_type_ComTencentSmttExportInternalInterfacesIX5QQBrowserClient;
    if (localIX5QQBrowserClient != null) {
      return localIX5QQBrowserClient.getTitleHeight();
    }
    return 0;
  }
  
  public void a()
  {
    a(this.jdField_a_of_type_Int, this.jdField_a_of_type_Boolean, this.b);
  }
  
  public void a(float paramFloat)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentTencentAwContent;
    if (localObject != null)
    {
      if (((TencentAwContent)localObject).getContentViewCore() == null) {
        return;
      }
      localObject = this.jdField_a_of_type_OrgChromiumTencentTencentAwContent.getTencentContentViewCore();
      if (((TencentContentViewCore)localObject).getRenderCoordinates().getContentOffsetYPix() != paramFloat)
      {
        int i = 0;
        while (i < this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getChildCount())
        {
          View localView = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getChildAt(i);
          if ((!this.jdField_a_of_type_ComTencentSmttWebkitInputC.a(localView)) && (!((TencentContentViewCore)localObject).isPopupZoomerView(localView)) && (!this.jdField_a_of_type_ComTencentSmttWebkitInputC.b(localView)) && (!(localView instanceof SurfaceView))) {
            X5ApiCompatibilityUtils.setTranslationY(localView, paramFloat);
          }
          i += 1;
        }
      }
      if ((!((TencentContentViewCore)localObject).isScrollInProgress()) && (!this.jdField_a_of_type_OrgChromiumTencentTencentAwContent.isTouching()))
      {
        if (a() == paramFloat) {
          a(true);
        } else if (0.0F == paramFloat) {
          a(false);
        }
        ((TencentContentViewCore)localObject).getWebContents().getEventForwarder().setCurrentTouchEventOffsets(0.0F, -paramFloat);
      }
      localObject = this.jdField_a_of_type_ComTencentSmttExportInternalInterfacesIX5QQBrowserClient;
      if (localObject != null) {
        ((IX5QQBrowserClient)localObject).onVisbleTitleHeightChanged((int)paramFloat);
      }
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getViewManager().b((int)paramFloat);
      return;
    }
  }
  
  public void a(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramFloat1 *= paramFloat2;
    if ((int)paramFloat1 == a()) {
      a(paramFloat1 * paramFloat3);
    }
  }
  
  public void a(final int paramInt, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    if (!ThreadUtils.runningOnUiThread())
    {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          b.this.a(paramInt, paramBoolean1, paramBoolean2);
        }
      });
      return;
    }
    TencentAwContent localTencentAwContent = this.jdField_a_of_type_OrgChromiumTencentTencentAwContent;
    if (localTencentAwContent != null)
    {
      if (localTencentAwContent.getContentViewCore() == null) {
        return;
      }
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_Boolean = paramBoolean1;
      this.b = paramBoolean2;
      if ((paramInt != 4) && (paramInt != 3))
      {
        if ((paramInt == 2) || (paramInt == 1)) {
          a(true);
        }
      }
      else {
        a(false);
      }
      b(paramInt, paramBoolean1, paramBoolean2);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().invalidate();
      return;
    }
  }
  
  public void a(IX5QQBrowserClient paramIX5QQBrowserClient)
  {
    this.jdField_a_of_type_ComTencentSmttExportInternalInterfacesIX5QQBrowserClient = paramIX5QQBrowserClient;
  }
  
  public boolean a()
  {
    return this.c;
  }
  
  public int b()
  {
    return this.jdField_a_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */