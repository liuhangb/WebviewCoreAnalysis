package com.tencent.smtt.webkit.b;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.tencent.mtt.base.utils.DeviceUtilsF;
import com.tencent.mtt.base.utils.NotchUtil;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.reflect.Field;
import org.chromium.base.ThreadUtils;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.TencentAwContent;
import org.chromium.ui.base.WindowAndroid;

public class a
  extends ProxyWebViewObserver
{
  private int jdField_a_of_type_Int = -1;
  private DisplayManager.DisplayListener jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener = null;
  private View jdField_a_of_type_AndroidViewView = null;
  private WebSettingsExtension jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private final String jdField_a_of_type_JavaLangString = "DisplayCutout";
  private TencentAwContent jdField_a_of_type_OrgChromiumTencentTencentAwContent;
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 0;
  private String jdField_b_of_type_JavaLangString = "";
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int = -1;
  private boolean jdField_c_of_type_Boolean = false;
  
  public a(TencentAwContent paramTencentAwContent, TencentWebViewProxy paramTencentWebViewProxy, WebSettingsExtension paramWebSettingsExtension)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.jdField_a_of_type_OrgChromiumTencentTencentAwContent = paramTencentAwContent;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension = paramWebSettingsExtension;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.addObserver(this);
    if (Build.VERSION.SDK_INT >= 17)
    {
      this.jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener = new DisplayManager.DisplayListener()
      {
        public void onDisplayAdded(int paramAnonymousInt) {}
        
        public void onDisplayChanged(int paramAnonymousInt)
        {
          if (a.a(a.this) == null) {
            return;
          }
          if (Math.abs(a.a(a.a(a.this).getRealWebView().getContext()) - a.a(a.this)) == 2) {
            ThreadUtils.postOnUiThreadDelayed(new Runnable()
            {
              public void run()
              {
                a.a(a.this, a.b(a.this));
              }
            }, 50L);
          }
        }
        
        public void onDisplayRemoved(int paramAnonymousInt) {}
      };
      if (a() != null) {
        a().registerDisplayListener(this.jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener, null);
      }
    }
  }
  
  private int a(int paramInt, float paramFloat)
  {
    return (int)Math.ceil(paramInt / paramFloat);
  }
  
  private DisplayManager a()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localTencentWebViewProxy == null) {
      return null;
    }
    return (DisplayManager)localTencentWebViewProxy.getContext().getSystemService("display");
  }
  
  private void a(final ViewGroup paramViewGroup, final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
  {
    if ((paramInt1 != 0) || (paramInt2 != 0) || (paramInt3 != 0) || (paramInt4 != 0)) {
      if (this.jdField_a_of_type_Int == 0) {
        ThreadUtils.postOnUiThreadDelayed(new Runnable()
        {
          public void run()
          {
            a.a(a.this);
          }
        }, 2000L);
      } else {
        b();
      }
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if ((paramInt1 != paramViewGroup.getPaddingLeft()) || (paramInt2 != paramViewGroup.getPaddingTop()) || (paramInt3 != paramViewGroup.getPaddingRight()) || (paramInt4 != paramViewGroup.getPaddingBottom())) {
          paramViewGroup.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
        }
      }
    });
  }
  
  private boolean a()
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) {
      return false;
    }
    Point localPoint = new Point();
    Object localObject = ((WindowManager)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext().getSystemService("window")).getDefaultDisplay();
    if (Build.VERSION.SDK_INT > 17) {
      ((Display)localObject).getRealSize(localPoint);
    }
    if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView() instanceof View))
    {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView();
      localObject = (ViewGroup)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getParent();
      if (localObject != null)
      {
        int j = ((ViewGroup)localObject).getHeight();
        int k = ((ViewGroup)localObject).getWidth();
        i = k;
        if (j > k) {
          i = j;
        }
        DeviceUtilsF.getSystemNaviBarHeight();
        if (i == localPoint.x) {
          break label144;
        }
        j = localPoint.x;
        break label144;
      }
    }
    int i = 0;
    label144:
    if (i == localPoint.x) {
      return true;
    }
    return !NotchUtil.isHWNotchDevice(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
  }
  
  private boolean a(int paramInt)
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) {
      return false;
    }
    if (!b()) {
      return false;
    }
    if (!NotchUtil.isNotchDevice(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext())) {
      return false;
    }
    this.jdField_a_of_type_Int = paramInt;
    if (this.jdField_c_of_type_Int == -1)
    {
      this.jdField_c_of_type_Int = b(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
      if (this.jdField_c_of_type_Int == -1) {
        return false;
      }
    }
    Rect localRect = new Rect();
    this.jdField_b_of_type_Int = e(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContext());
    Object localObject;
    if (this.jdField_a_of_type_Boolean)
    {
      localObject = this.jdField_a_of_type_AndroidViewView;
      if (localObject == null)
      {
        a(new Rect(0, 0, 0, 0));
        return false;
      }
      ((View)localObject).getGlobalVisibleRect(localRect);
      localObject = (ViewGroup)this.jdField_a_of_type_AndroidViewView;
    }
    else
    {
      localObject = (ViewGroup)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getParent();
      if (localObject == null)
      {
        a(new Rect(0, 0, 0, 0));
        return false;
      }
      ((ViewGroup)localObject).getGlobalVisibleRect(localRect);
    }
    if (this.jdField_a_of_type_Int == 2)
    {
      paramInt = this.jdField_b_of_type_Int;
      if (paramInt == 0)
      {
        a(new Rect(0, Math.max(0, this.jdField_c_of_type_Int - localRect.top), 0, 0));
      }
      else if (paramInt == 1)
      {
        a(new Rect(Math.max(0, this.jdField_c_of_type_Int - localRect.left), 0, 0, 0));
      }
      else if (paramInt == 2)
      {
        a(new Rect(0, 0, 0, Math.max(localRect.bottom - (c(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()) - this.jdField_c_of_type_Int), 0)));
      }
      else if (paramInt == 3)
      {
        paramInt = Math.max(localRect.right - (d(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()) - this.jdField_c_of_type_Int), 0);
        if (!a()) {
          paramInt = 0;
        }
        a(new Rect(0, 0, paramInt, 0));
      }
      return a((ViewGroup)localObject, 0, 0, 0, 0);
    }
    ((ViewGroup)localObject).setBackgroundColor(-1);
    a(new Rect(0, 0, 0, 0));
    paramInt = this.jdField_b_of_type_Int;
    if (paramInt == 0) {
      return a((ViewGroup)localObject, 0, Math.max(0, this.jdField_c_of_type_Int - localRect.top), 0, 0);
    }
    if (paramInt == 1) {
      return a((ViewGroup)localObject, Math.max(0, this.jdField_c_of_type_Int - localRect.left), 0, 0, 0);
    }
    if (paramInt == 2) {
      return a((ViewGroup)localObject, 0, 0, 0, Math.max(localRect.bottom - (c(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()) - this.jdField_c_of_type_Int), 0));
    }
    if (paramInt == 3)
    {
      paramInt = Math.max(localRect.right - (d(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()) - this.jdField_c_of_type_Int), 0);
      if (!a()) {
        paramInt = 0;
      }
      return a((ViewGroup)localObject, 0, 0, paramInt, 0);
    }
    return false;
  }
  
  private boolean a(ViewGroup paramViewGroup, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    if ((paramInt1 == paramViewGroup.getPaddingLeft()) && (paramInt2 == paramViewGroup.getPaddingTop()) && (paramInt3 == paramViewGroup.getPaddingRight()) && (paramInt4 == paramViewGroup.getPaddingBottom())) {
      bool = false;
    } else {
      bool = true;
    }
    a(paramViewGroup, paramInt1, paramInt2, paramInt3, paramInt4);
    return bool;
  }
  
  private int b(Context paramContext)
  {
    try
    {
      Class localClass = Class.forName("com.android.internal.R$dimen");
      Object localObject = localClass.newInstance();
      int i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      i = paramContext.getResources().getDimensionPixelSize(i);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  private void b()
  {
    try
    {
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) {
        return;
      }
      if ((!this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isShown()) && (!this.jdField_a_of_type_Boolean)) {
        return;
      }
      if (this.jdField_a_of_type_Boolean)
      {
        if (this.jdField_c_of_type_Boolean) {
          return;
        }
        this.jdField_c_of_type_Boolean = true;
      }
      else
      {
        if (this.jdField_b_of_type_Boolean) {
          return;
        }
        this.jdField_b_of_type_Boolean = true;
      }
      String str1 = TencentURLUtil.getUrlWithoutQuery(this.jdField_b_of_type_JavaLangString);
      String str2 = DeviceUtilsF.getDeviceName().trim();
      SmttServiceProxy.getInstance().onReportDisplayCutoutInfo(str1, str2, this.jdField_a_of_type_Boolean, this.jdField_b_of_type_Int, this.jdField_a_of_type_Int);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private boolean b()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension == null) {
        return false;
      }
      localObject = WindowAndroid.activityFromContext(((TencentWebViewProxy)localObject).getContext());
      if (localObject != null)
      {
        if (!NotchUtil.isFullScreenWindowLayoutInDisplayCutout(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext(), ((Activity)localObject).getWindow())) {
          return false;
        }
        int i = SmttServiceProxy.getInstance().getDisplayCutoutEnableSwitch();
        if (3 == i) {
          return false;
        }
        if (4 == i) {
          return true;
        }
        return this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension.getDisplayCutoutEnable();
      }
      return false;
    }
    return false;
  }
  
  private static int c(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    if (Build.VERSION.SDK_INT > 17)
    {
      paramContext = paramContext.getDefaultDisplay();
      localObject = new Point();
      paramContext.getRealSize((Point)localObject);
      if (((Point)localObject).x > ((Point)localObject).y) {
        return ((Point)localObject).y;
      }
      return ((Point)localObject).x;
    }
    Object localObject = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics((DisplayMetrics)localObject);
    return ((DisplayMetrics)localObject).heightPixels;
  }
  
  private static int d(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    if (Build.VERSION.SDK_INT > 17)
    {
      paramContext = paramContext.getDefaultDisplay();
      localObject = new Point();
      paramContext.getRealSize((Point)localObject);
      if (((Point)localObject).x > ((Point)localObject).y) {
        return ((Point)localObject).x;
      }
      return ((Point)localObject).y;
    }
    Object localObject = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics((DisplayMetrics)localObject);
    return ((DisplayMetrics)localObject).widthPixels;
  }
  
  private static int e(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    if (paramContext != null) {
      return paramContext.getDefaultDisplay().getOrientation();
    }
    return 0;
  }
  
  protected float a()
  {
    TencentAwContent localTencentAwContent = this.jdField_a_of_type_OrgChromiumTencentTencentAwContent;
    if (localTencentAwContent == null) {
      return 1.0F;
    }
    return localTencentAwContent.getDeviceScaleFactor();
  }
  
  public Rect a(int paramInt)
  {
    if (b())
    {
      Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
      if (localObject != null)
      {
        if (!NotchUtil.isNotchDevice(((TencentWebViewProxy)localObject).getContext())) {
          return new Rect(0, 0, 0, 0);
        }
        if (this.jdField_c_of_type_Int == -1)
        {
          this.jdField_c_of_type_Int = b(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
          if (this.jdField_c_of_type_Int == -1) {
            return new Rect(0, 0, 0, 0);
          }
        }
        localObject = new Rect();
        ViewGroup localViewGroup = (ViewGroup)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getParent();
        if (localViewGroup == null) {
          return new Rect(0, 0, 0, 0);
        }
        localViewGroup.getGlobalVisibleRect((Rect)localObject);
        int i = e(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContext());
        if (paramInt == 2) {
          return (Rect)localObject;
        }
        if (i == 1) {
          return new Rect(Math.max(0, this.jdField_c_of_type_Int - ((Rect)localObject).left), 0, ((Rect)localObject).right, ((Rect)localObject).bottom);
        }
        return new Rect(0, 0, 0, 0);
      }
    }
    return new Rect(0, 0, 0, 0);
  }
  
  public void a()
  {
    if (!b()) {
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_AndroidViewView = null;
    a(this.jdField_a_of_type_Int);
  }
  
  public void a(int paramInt)
  {
    a(paramInt);
  }
  
  public void a(Rect paramRect)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentTencentAwContent;
    if (localObject == null) {
      return;
    }
    localObject = ((TencentAwContent)localObject).getWebContents();
    if (localObject == null) {
      return;
    }
    float f = a();
    paramRect.set(a(paramRect.left, f), a(paramRect.top, f), a(paramRect.right, f), a(paramRect.bottom, f));
    ((WebContents)localObject).setDisplayCutoutSafeArea(paramRect);
    if ((paramRect.left != 0) || (paramRect.top != 0) || (paramRect.right != 0) || (paramRect.bottom != 0)) {
      b();
    }
  }
  
  public void a(View paramView)
  {
    if (!b()) {
      return;
    }
    paramView.getGlobalVisibleRect(new Rect());
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_AndroidViewView = paramView;
    a(this.jdField_a_of_type_Int);
  }
  
  public void a(String paramString)
  {
    this.jdField_b_of_type_Boolean = false;
    this.jdField_c_of_type_Boolean = false;
    this.jdField_b_of_type_JavaLangString = paramString;
  }
  
  public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!b()) {
      return false;
    }
    if (((paramInt1 > paramInt2) && (paramInt3 < paramInt4)) || ((paramInt1 < paramInt2) && (paramInt3 > paramInt4))) {
      return a(this.jdField_a_of_type_Int);
    }
    return false;
  }
  
  public void onWebViewDestroyed(Context paramContext)
  {
    if (this.jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener != null)
    {
      if (a() != null) {
        a().unregisterDisplayListener(this.jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener);
      }
      this.jdField_a_of_type_AndroidHardwareDisplayDisplayManager$DisplayListener = null;
    }
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
    this.jdField_a_of_type_OrgChromiumTencentTencentAwContent = null;
    this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension = null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */