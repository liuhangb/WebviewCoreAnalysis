package org.chromium.tencent;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.lang.reflect.Method;

public class X5DropdownPopupWindow
  extends ListPopupWindow
{
  public static final int ADJUST_POSTION_DELAY_DEFAULT = 0;
  public static final int ADJUST_POSTION_DELAY_ON_SCROLL_CHANGED = 250;
  ListAdapter mAdapter;
  private Runnable mAdjustPositionRunnable;
  private final View mAnchorView;
  private final Context mContext;
  private CharSequence mDescription;
  private int mInitialSelection = -1;
  private View.OnLayoutChangeListener mLayoutChangeListener;
  private PopupWindow.OnDismissListener mOnDismissListener;
  private boolean mRtl;
  
  public X5DropdownPopupWindow(Context paramContext, View paramView, int paramInt)
  {
    super(paramContext, null, 0, paramInt);
    this.mContext = paramContext;
    this.mAnchorView = paramView;
    this.mAnchorView.setTag(this);
    this.mAdjustPositionRunnable = new Runnable()
    {
      public void run()
      {
        X5DropdownPopupWindow.this.showInternal();
      }
    };
    this.mLayoutChangeListener = new View.OnLayoutChangeListener()
    {
      public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
      {
        if (paramAnonymousView == X5DropdownPopupWindow.this.mAnchorView) {
          X5DropdownPopupWindow.this.show();
        }
      }
    };
    this.mAnchorView.addOnLayoutChangeListener(this.mLayoutChangeListener);
    super.setOnDismissListener(new PopupWindow.OnDismissListener()
    {
      public void onDismiss()
      {
        if (X5DropdownPopupWindow.this.mOnDismissListener != null) {
          X5DropdownPopupWindow.this.mOnDismissListener.onDismiss();
        }
        X5DropdownPopupWindow.this.mAnchorView.removeOnLayoutChangeListener(X5DropdownPopupWindow.this.mLayoutChangeListener);
        X5DropdownPopupWindow.this.mAnchorView.removeCallbacks(X5DropdownPopupWindow.this.mAdjustPositionRunnable);
        X5DropdownPopupWindow.this.mAnchorView.setTag(null);
      }
    });
    setAnchorView(this.mAnchorView);
    paramContext = new Rect();
    getBackground().getPadding(paramContext);
    setVerticalOffset(-paramContext.top);
  }
  
  private int measureContentWidth()
  {
    View[] arrayOfView = new View[this.mAdapter.getViewTypeCount()];
    int i = 0;
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    int j = 0;
    while (i < this.mAdapter.getCount())
    {
      int n = this.mAdapter.getItemViewType(i);
      arrayOfView[n] = this.mAdapter.getView(i, arrayOfView[n], null);
      View localView = arrayOfView[n];
      localView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
      localView.measure(k, m);
      j = Math.max(j, localView.getMeasuredWidth());
      i += 1;
    }
    return j;
  }
  
  private void showInternal()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void adjustAutofillPosition(int paramInt)
  {
    View localView = this.mAnchorView;
    if (localView != null)
    {
      localView.removeCallbacks(this.mAdjustPositionRunnable);
      this.mAnchorView.postDelayed(this.mAdjustPositionRunnable, paramInt);
    }
  }
  
  public void disableHideOnOutsideTap()
  {
    try
    {
      ListPopupWindow.class.getMethod("setForceIgnoreOutsideTouch", new Class[] { Boolean.TYPE }).invoke(this, new Object[] { Boolean.valueOf(true) });
      return;
    }
    catch (Exception localException)
    {
      Log.e("AutofillPopup", "ListPopupWindow.setForceIgnoreOutsideTouch not found", localException);
    }
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    this.mAdapter = paramListAdapter;
    super.setAdapter(paramListAdapter);
  }
  
  public void setContentDescriptionForAccessibility(CharSequence paramCharSequence)
  {
    this.mDescription = paramCharSequence;
  }
  
  public void setInitialSelection(int paramInt)
  {
    this.mInitialSelection = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setRtl(boolean paramBoolean)
  {
    this.mRtl = paramBoolean;
  }
  
  public void show()
  {
    showInternal();
    adjustAutofillPosition(0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5DropdownPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */