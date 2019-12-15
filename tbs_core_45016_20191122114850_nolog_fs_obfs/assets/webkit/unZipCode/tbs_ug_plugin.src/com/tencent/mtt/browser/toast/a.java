package com.tencent.mtt.browser.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;

final class a
  extends ContextWrapper
{
  @NonNull
  private Toast jdField_a_of_type_AndroidWidgetToast;
  @Nullable
  private BadTokenListener jdField_a_of_type_ComTencentMttBrowserToastBadTokenListener;
  
  a(@NonNull Context paramContext, @NonNull Toast paramToast)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidWidgetToast = paramToast;
  }
  
  public void a(@NonNull BadTokenListener paramBadTokenListener)
  {
    this.jdField_a_of_type_ComTencentMttBrowserToastBadTokenListener = paramBadTokenListener;
  }
  
  public Context getApplicationContext()
  {
    return new a(getBaseContext().getApplicationContext(), null);
  }
  
  private final class a
    extends ContextWrapper
  {
    private a(@NonNull Context paramContext)
    {
      super();
    }
    
    public Object getSystemService(@NonNull String paramString)
    {
      if ("window".equals(paramString)) {
        return new a.b(a.this, (WindowManager)getBaseContext().getSystemService(paramString), null);
      }
      return super.getSystemService(paramString);
    }
  }
  
  private final class b
    implements WindowManager
  {
    @NonNull
    private final WindowManager jdField_a_of_type_AndroidViewWindowManager;
    
    private b(@NonNull WindowManager paramWindowManager)
    {
      this.jdField_a_of_type_AndroidViewWindowManager = paramWindowManager;
    }
    
    public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
    {
      try
      {
        this.jdField_a_of_type_AndroidViewWindowManager.addView(paramView, paramLayoutParams);
        return;
      }
      catch (Throwable paramView) {}catch (WindowManager.BadTokenException paramView) {}
      Log.e("WindowManagerWrapper", "[addView]", paramView);
      return;
      paramView.getMessage();
      if (a.a(a.this) != null) {
        a.a(a.this).onBadTokenCaught(a.a(a.this));
      }
    }
    
    public Display getDefaultDisplay()
    {
      return this.jdField_a_of_type_AndroidViewWindowManager.getDefaultDisplay();
    }
    
    public void removeView(View paramView)
    {
      this.jdField_a_of_type_AndroidViewWindowManager.removeView(paramView);
    }
    
    public void removeViewImmediate(View paramView)
    {
      this.jdField_a_of_type_AndroidViewWindowManager.removeViewImmediate(paramView);
    }
    
    public void updateViewLayout(View paramView, ViewGroup.LayoutParams paramLayoutParams)
    {
      this.jdField_a_of_type_AndroidViewWindowManager.updateViewLayout(paramView, paramLayoutParams);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\toast\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */