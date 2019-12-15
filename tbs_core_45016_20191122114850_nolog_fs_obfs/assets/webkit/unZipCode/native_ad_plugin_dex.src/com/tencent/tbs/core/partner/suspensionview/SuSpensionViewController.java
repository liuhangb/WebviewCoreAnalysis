package com.tencent.tbs.core.partner.suspensionview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.reflect.Method;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;

public class SuSpensionViewController
  extends ProxyWebViewObserver
{
  public static final boolean ENABLEVIEW = false;
  private static final String FEEDBACK_TEST_URL = "http://fbtest.cs0309.imtt.qq.com/mobilefb/tbs/tree";
  public static final String FEEDBACK_URL = "http://debugtbs.qq.com/?type=1001&url=http://bbs.mb.qq.com/mobilefb/tbs/tree";
  public static final String JSOBJNAME = "SuSpensionViewController";
  private static final String KEY_SUSPENSION = "key_suspension";
  public static final String LOGTAG = "SuSViewController";
  private static final String SPNAME = "tbs_suspensionview_config";
  private Context appContext;
  private int[] boundys = null;
  int cellHeight = 43;
  int cellWidth = 120;
  String coreVersion = "0";
  boolean isExpanded = false;
  private ViewGroup layout;
  int lineMargin = 20;
  DraggableLinearLayout mLayout;
  private TencentWebViewProxy mWebview;
  int tbsSize = 40;
  
  public SuSpensionViewController(TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.mWebview = paramTencentWebViewProxy;
    this.appContext = this.mWebview.getContext().getApplicationContext();
    this.mWebview.addObserver(this);
  }
  
  public static void enterDebugpluginFeedback(View paramView)
  {
    paramView = paramView.getParent();
    if (paramView == null)
    {
      Log.e("SuSViewController", "enterDebugpluginFeedback() error,parent of child is null");
      return;
    }
    invokeInstance(paramView, "loadUrl", new Class[] { String.class }, new Object[] { "http://debugtbs.qq.com/?type=1001&url=http://bbs.mb.qq.com/mobilefb/tbs/tree" });
  }
  
  private ViewGroup generateView(Context paramContext)
  {
    Drawable localDrawable1 = SUtiles.createSelector(SmttResource.getDrawable("x5_tbs_pressed"), SmttResource.getDrawable("x5_tbs_unpressed"));
    Drawable localDrawable2 = SUtiles.createSelector(SmttResource.getDrawable("x5_tbslarge_pressed"), SmttResource.getDrawable("x5_tbslarge_unpressed"));
    Drawable localDrawable3 = SUtiles.createSelector(SmttResource.getDrawable("x5_exitbtn_pressed"), SmttResource.getDrawable("x5_exitbtn_unpressed"));
    Drawable localDrawable4 = SUtiles.createSelector(SmttResource.getDrawable("x5_ifeedback_pressed"), SmttResource.getDrawable("x5_ifeedback_unpressed"));
    final DraggableLinearLayout localDraggableLinearLayout = new DraggableLinearLayout(paramContext, this.boundys);
    localDraggableLinearLayout.setBackgroundColor(-1);
    final int i = SUtiles.dip2px(paramContext, this.cellWidth);
    final int j = SUtiles.dip2px(paramContext, this.cellHeight);
    int k = SUtiles.dip2px(paramContext, this.lineMargin);
    final int m = SUtiles.dip2px(paramContext, this.tbsSize);
    final LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    localLinearLayout.setGravity(1);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(i, j);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(i - k * 2, 3);
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(m, m);
    Button localButton1 = new Button(paramContext);
    Button localButton2 = new Button(paramContext);
    final Button localButton3 = new Button(paramContext);
    Button localButton4 = new Button(paramContext);
    localButton1.setBackgroundDrawable(localDrawable3);
    localButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = SmttResource.getString("x5_ok", "string");
        String str1 = SmttResource.getString("x5_cancel", "string");
        String str2 = SmttResource.getString("x5_pop_menu_feedback_exit_title", "string");
        String str3 = SmttResource.getString("x5_pop_menu_feedback_exit_message", "string");
        SUtiles.showAlertInfo(SuSpensionViewController.this.mWebview.getContext(), str2, str3, str1, paramAnonymousView, new SUtiles.IInfoConformListener()
        {
          public void onCancel()
          {
            SUtiles.postUserbehaveAction("BWNFB7");
          }
          
          public void onShow()
          {
            SUtiles.postUserbehaveAction("BWNFB6");
          }
          
          public void onSure()
          {
            SuSpensionViewController.this.layout.setVisibility(8);
            SharedPreferences.Editor localEditor = SuSpensionViewController.this.appContext.getSharedPreferences("tbs_suspensionview_config", 0).edit();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("key_suspension");
            localStringBuilder.append(SuSpensionViewController.this.coreVersion);
            localEditor.putBoolean(localStringBuilder.toString(), false);
            localEditor.commit();
            SUtiles.postUserbehaveAction("BWNFB8");
          }
        });
        SUtiles.postUserbehaveAction("BWNFB5");
      }
    });
    localButton2.setBackgroundDrawable(localDrawable4);
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SuSpensionViewController.this.isExpanded = false;
        paramAnonymousView = localDraggableLinearLayout;
        int i = m;
        paramAnonymousView.shrinkMedue(i, i, j * 3 - i);
        localLinearLayout.setVisibility(8);
        localButton3.setVisibility(0);
        if ((SuSpensionViewController.this.mWebview.getRealWebView() instanceof View)) {
          SuSpensionViewController.enterDebugpluginFeedback(SuSpensionViewController.this.mWebview.getRealWebView());
        } else {
          Log.e("SuSViewController", "mWebView is not a view");
        }
        SUtiles.postUserbehaveAction("BWNFB4");
      }
    });
    localButton4.setBackgroundDrawable(localDrawable2);
    localButton4.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SuSpensionViewController.this.isExpanded = false;
        paramAnonymousView = localDraggableLinearLayout;
        int i = m;
        paramAnonymousView.shrinkMedue(i, i, j * 3 - i);
        localLinearLayout.setVisibility(8);
        localButton3.setVisibility(0);
      }
    });
    localButton3.setBackgroundDrawable(localDrawable1);
    localButton3.setLayoutParams(localLayoutParams3);
    localButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SuSpensionViewController.this.isExpanded = true;
        int i = i;
        int j = m;
        int k = j;
        localDraggableLinearLayout.expandLayout(i - j, k * 3 - j);
        localButton3.setVisibility(8);
        localLinearLayout.setVisibility(0);
        SUtiles.postUserbehaveAction("BWNFB2");
        SUtiles.postUserbehaveAction("BWNFB3");
      }
    });
    localLinearLayout.addView(localButton1, localLayoutParams1);
    localLinearLayout.addView(SUtiles.getLineView(paramContext, -16777216), localLayoutParams2);
    localLinearLayout.addView(localButton2, localLayoutParams1);
    localLinearLayout.addView(SUtiles.getLineView(paramContext, -16777216), localLayoutParams2);
    localLinearLayout.addView(localButton4, localLayoutParams1);
    localLinearLayout.setVisibility(8);
    localDraggableLinearLayout.addView(localLinearLayout);
    localDraggableLinearLayout.addView(localButton3);
    localDraggableLinearLayout.setFocusableInTouchMode(true);
    localDraggableLinearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if ((!paramAnonymousBoolean) && (!paramAnonymousBoolean))
        {
          int i;
          if (SuSpensionViewController.this.isExpanded) {
            i = j * 3 - m;
          } else {
            i = 0;
          }
          paramAnonymousView = localDraggableLinearLayout;
          int j = m;
          paramAnonymousView.shrinkMedue(j, j, i);
          SuSpensionViewController.this.isExpanded = false;
          localLinearLayout.setVisibility(8);
          localButton3.setVisibility(0);
        }
      }
    });
    return localDraggableLinearLayout;
  }
  
  public static Object invokeInstance(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    try
    {
      Class localClass = paramObject.getClass();
      if (Build.VERSION.SDK_INT > 10) {
        paramString = localClass.getMethod(paramString, paramArrayOfClass);
      } else {
        paramString = localClass.getDeclaredMethod(paramString, paramArrayOfClass);
      }
      paramString.setAccessible(true);
      paramArrayOfClass = paramVarArgs;
      if (paramVarArgs.length == 0) {
        paramArrayOfClass = null;
      }
      paramObject = paramString.invoke(paramObject, paramArrayOfClass);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      paramString = new StringBuilder();
      paramString.append("invokeInstance -- exceptions:");
      paramString.append(((Throwable)paramObject).toString());
      Log.e("SuSViewController", paramString.toString());
    }
    return null;
  }
  
  private boolean isViewEnabled(Context paramContext)
  {
    return false;
  }
  
  public ViewGroup getContentView()
  {
    if (this.layout == null) {
      this.layout = generateView(this.appContext);
    }
    return this.layout;
  }
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2) {}
  
  public void onFirstScreenTime(long paramLong1, long paramLong2) {}
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onPageFinished(String paramString)
  {
    if ((isViewEnabled(this.appContext)) && (this.layout == null))
    {
      this.boundys = new int[] { this.mWebview.getRealWebView().getWidth(), this.mWebview.getRealWebView().getHeight() };
      if ((this.mWebview.getRealWebView().getParent() instanceof FrameLayout))
      {
        int i = SUtiles.dip2px(this.appContext, this.tbsSize);
        paramString = new FrameLayout.LayoutParams(-2, -2);
        paramString.gravity = 0;
        Object localObject = this.boundys;
        paramString.setMargins(localObject[0] - i, localObject[1] * 9 / 10 - i - 40, 0, 0);
        localObject = (DraggableLinearLayout)getContentView();
        ((FrameLayout)this.mWebview.getRealWebView().getParent()).addView((View)localObject, paramString);
        SUtiles.postUserbehaveAction("BWNFB1");
      }
    }
  }
  
  public void onQProxyResponseReceived(String paramString) {}
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2) {}
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2) {}
  
  public void onWebViewDestroyed()
  {
    this.mWebview = null;
    this.layout = null;
  }
  
  public void onWindowVisibilityChanged(int paramInt) {}
  
  public void setBoundys(int[] paramArrayOfInt)
  {
    this.boundys = paramArrayOfInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\suspensionview\SuSpensionViewController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */