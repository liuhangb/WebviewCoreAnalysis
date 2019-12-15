package com.tencent.mtt.browser.toast;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;
import java.lang.reflect.Field;

public final class ToastCompat
  extends Toast
{
  @NonNull
  private final Toast a;
  
  private ToastCompat(Context paramContext, @NonNull Toast paramToast)
  {
    super(paramContext);
    this.a = paramToast;
  }
  
  private static void a(@NonNull View paramView, @NonNull Context paramContext)
  {
    if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT <= 25)) {
      try
      {
        Field localField = View.class.getDeclaredField("mContext");
        localField.setAccessible(true);
        localField.set(paramView, paramContext);
        return;
      }
      catch (Throwable paramView)
      {
        paramView.printStackTrace();
      }
    }
  }
  
  public static Toast makeText(Context paramContext, @StringRes int paramInt1, int paramInt2)
    throws Resources.NotFoundException
  {
    return makeText(paramContext, paramContext.getResources().getText(paramInt1), paramInt2);
  }
  
  public static ToastCompat makeText(Context paramContext, CharSequence paramCharSequence, int paramInt)
  {
    paramCharSequence = Toast.makeText(paramContext, paramCharSequence, paramInt);
    a(paramCharSequence.getView(), new a(paramContext, paramCharSequence));
    return new ToastCompat(paramContext, paramCharSequence);
  }
  
  @NonNull
  public Toast getBaseToast()
  {
    return this.a;
  }
  
  public int getDuration()
  {
    return this.a.getDuration();
  }
  
  public int getGravity()
  {
    return this.a.getGravity();
  }
  
  public float getHorizontalMargin()
  {
    return this.a.getHorizontalMargin();
  }
  
  public float getVerticalMargin()
  {
    return this.a.getVerticalMargin();
  }
  
  public View getView()
  {
    return this.a.getView();
  }
  
  public int getXOffset()
  {
    return this.a.getXOffset();
  }
  
  public int getYOffset()
  {
    return this.a.getYOffset();
  }
  
  @NonNull
  public ToastCompat setBadTokenListener(@NonNull BadTokenListener paramBadTokenListener)
  {
    Context localContext = getView().getContext();
    if ((localContext instanceof a)) {
      ((a)localContext).a(paramBadTokenListener);
    }
    return this;
  }
  
  public void setDuration(int paramInt)
  {
    this.a.setDuration(paramInt);
  }
  
  public void setGravity(int paramInt1, int paramInt2, int paramInt3)
  {
    this.a.setGravity(paramInt1, paramInt2, paramInt3);
  }
  
  public void setMargin(float paramFloat1, float paramFloat2)
  {
    this.a.setMargin(paramFloat1, paramFloat2);
  }
  
  public void setText(int paramInt)
  {
    this.a.setText(paramInt);
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.a.setText(paramCharSequence);
  }
  
  public void setView(View paramView)
  {
    this.a.setView(paramView);
    a(paramView, new a(paramView.getContext(), this));
  }
  
  public void show()
  {
    this.a.show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\toast\ToastCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */