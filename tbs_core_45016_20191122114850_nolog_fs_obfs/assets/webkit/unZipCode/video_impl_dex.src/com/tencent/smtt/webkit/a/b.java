package com.tencent.smtt.webkit.a;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.util.ArrayList;
import java.util.List;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;

public class b
  extends ProxyWebViewObserver
{
  private Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private a jdField_a_of_type_ComTencentSmttWebkitAA = null;
  private a jdField_a_of_type_ComTencentSmttWebkitAB$a = a.jdField_a_of_type_ComTencentSmttWebkitAB$a;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private final String jdField_a_of_type_JavaLangString = "tbs_bar";
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  private boolean c = false;
  private boolean d = false;
  
  public b(TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.addObserver(this);
    if (ContextHolder.getInstance().isThirdPartyApp(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext())) {
      this.jdField_a_of_type_Boolean = true;
    } else {
      this.jdField_a_of_type_Boolean = false;
    }
    this.b = SmttServiceProxy.getInstance().isBottomBarEnabled(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
  }
  
  private List<View> a(View paramView, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (((paramView instanceof ViewGroup)) && (paramView.hashCode() != paramInt) && (paramView.getHeight() > 0) && (paramView.getWidth() > 0))
    {
      paramView = (ViewGroup)paramView;
      int i = 0;
      while (i < paramView.getChildCount())
      {
        View localView = paramView.getChildAt(i);
        if ((localView.hashCode() != paramInt) && (localView.getHeight() > 0) && (localView.getWidth() > 0))
        {
          localArrayList.add(localView);
          localArrayList.addAll(a(localView, paramInt));
        }
        i += 1;
      }
    }
    return localArrayList;
  }
  
  private boolean a()
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitAA;
    if (localObject1 == null) {
      return false;
    }
    int k = ((a)localObject1).a().hashCode();
    localObject1 = new Rect();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getGlobalVisibleRect((Rect)localObject1);
    int m = ((Rect)localObject1).bottom;
    localObject1 = new Rect();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWindowVisibleDisplayFrame((Rect)localObject1);
    if (m < ((Rect)localObject1).bottom)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitAA.a().h();
      return true;
    }
    localObject1 = new int[2];
    this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getLocationOnScreen((int[])localObject1);
    Object localObject2;
    if (this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getHeight() > 0)
    {
      localObject2 = this.jdField_a_of_type_AndroidGraphicsRect;
      int n = localObject1[0];
      if (m == localObject1[1]) {
        i = localObject1[1] - this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getHeight();
      } else {
        i = localObject1[1];
      }
      int i1 = localObject1[0];
      int i2 = this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getWidth();
      if (m == localObject1[1]) {
        j = localObject1[1];
      } else {
        j = localObject1[1] + this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getHeight();
      }
      ((Rect)localObject2).set(n, i, i1 + i2, j);
    }
    localObject1 = a(((Activity)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()).getWindow().getDecorView(), k);
    int j = ((List)localObject1).size();
    int i = 0;
    while (i < j)
    {
      localObject2 = (View)((List)localObject1).get(i);
      int[] arrayOfInt = new int[2];
      ((View)localObject2).getLocationOnScreen(arrayOfInt);
      Rect localRect = new Rect();
      localRect.set(arrayOfInt[0], arrayOfInt[1], arrayOfInt[0] + ((View)localObject2).getWidth(), arrayOfInt[1] + ((View)localObject2).getHeight());
      if ((a(localRect, this.jdField_a_of_type_AndroidGraphicsRect)) && (localObject2.hashCode() != k) && (arrayOfInt[1] >= m / 2))
      {
        this.jdField_a_of_type_ComTencentSmttWebkitAA.a().h();
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private boolean a(Rect paramRect1, Rect paramRect2)
  {
    return (paramRect1.left + paramRect1.width() > paramRect2.left) && (paramRect2.left + paramRect2.width() > paramRect1.left) && (paramRect1.top + paramRect1.height() > paramRect2.top) && (paramRect2.top + paramRect2.height() > paramRect1.top);
  }
  
  public void onContentPageSwapIn(String paramString) {}
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2)
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitAA;
    if ((locala != null) && (locala.a() != null))
    {
      this.jdField_a_of_type_ComTencentSmttWebkitAA.a().b();
      this.jdField_a_of_type_ComTencentSmttWebkitAA.a().a();
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentHeight() <= this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight())
      {
        this.jdField_a_of_type_ComTencentSmttWebkitAB$a = a.jdField_a_of_type_ComTencentSmttWebkitAB$a;
        return;
      }
      this.jdField_a_of_type_ComTencentSmttWebkitAB$a = a.b;
    }
  }
  
  public void onDetectSpecialSite(String paramString, int paramInt) {}
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9)
  {
    return false;
  }
  
  public void onFirstScreenTime(long paramLong1, long paramLong2)
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitAA;
    if ((locala != null) && (locala.a() != null)) {
      this.jdField_a_of_type_ComTencentSmttWebkitAA.a().c();
    }
  }
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!this.d) && (this.jdField_a_of_type_Boolean) && (this.b))
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitAA == null)
      {
        this.jdField_a_of_type_ComTencentSmttWebkitAA = new a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext(), this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy);
        if (this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getParent() == null)
        {
          this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.removeObserver(this);
          this.jdField_a_of_type_ComTencentSmttWebkitAA = null;
          return;
        }
      }
      this.d = true;
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitAA != null) {
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getYVelocity() > 2000)
      {
        if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentHeight() <= this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight()) && (!this.jdField_a_of_type_ComTencentSmttWebkitAA.a().a()))
        {
          if ((this.jdField_a_of_type_ComTencentSmttWebkitAB$a == a.b) && (!this.jdField_a_of_type_ComTencentSmttWebkitAA.a().b())) {
            this.jdField_a_of_type_ComTencentSmttWebkitAA.a();
          }
        }
        else if (!this.jdField_a_of_type_ComTencentSmttWebkitAA.a().b()) {
          this.jdField_a_of_type_ComTencentSmttWebkitAA.a();
        }
      }
      else if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getYVelocity() < -20) {
        this.jdField_a_of_type_ComTencentSmttWebkitAA.b();
      }
    }
  }
  
  public void onPageFinished(String paramString)
  {
    if ((!this.d) && (this.jdField_a_of_type_Boolean) && (this.b))
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitAA == null)
      {
        this.jdField_a_of_type_ComTencentSmttWebkitAA = new a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext(), this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy);
        if (this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getParent() == null)
        {
          this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.removeObserver(this);
          this.jdField_a_of_type_ComTencentSmttWebkitAA = null;
          return;
        }
        this.jdField_a_of_type_ComTencentSmttWebkitAA.a().c();
        this.jdField_a_of_type_ComTencentSmttWebkitAA.a().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            if (!b.a(b.this))
            {
              b.b(b.this);
              b.a(b.this, true);
            }
          }
        });
      }
      this.d = true;
    }
    paramString = this.jdField_a_of_type_ComTencentSmttWebkitAA;
    if ((paramString != null) && (paramString.a() != null)) {
      this.jdField_a_of_type_ComTencentSmttWebkitAA.a().a();
    }
    if ((this.d) && (this.jdField_a_of_type_Boolean) && (this.b)) {
      a();
    }
  }
  
  public void onPageStarted(String paramString)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitAB$a = a.jdField_a_of_type_ComTencentSmttWebkitAB$a;
  }
  
  public void onQProxyResponseReceived(String paramString) {}
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2) {}
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError) {}
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 > 10) {
      this.jdField_a_of_type_ComTencentSmttWebkitAB$a = a.b;
    }
  }
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2) {}
  
  public void onWebViewCreated(TencentWebViewProxy paramTencentWebViewProxy) {}
  
  public void onWebViewDestroyed() {}
  
  public void onWebViewDestroyed(Context paramContext) {}
  
  public void onWebViewPaused(Context paramContext) {}
  
  public void onWebViewVisibilityChanged(Context paramContext, View paramView, int paramInt) {}
  
  public void onWindowVisibilityChanged(int paramInt) {}
  
  public WebView webView()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView();
  }
  
  public static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitAB$a = new a("CANNOTSCROLL", 0);
      b = new a("CANSCROLL", 1);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitAB$a = new a[] { jdField_a_of_type_ComTencentSmttWebkitAB$a, b };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */