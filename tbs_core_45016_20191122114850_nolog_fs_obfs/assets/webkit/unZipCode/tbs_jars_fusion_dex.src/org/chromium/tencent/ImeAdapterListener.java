package org.chromium.tencent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import org.chromium.android_webview.AwContents;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public class ImeAdapterListener
{
  private static final String TAG = "ImeAdapterListener";
  private AwContents mAwContents;
  private int mBottomHeightPixForKeyboard = 0;
  private ContentViewClientExtension mContentViewClientExtension;
  private int mDisplayHeight;
  private int mExtBarHeight = 0;
  private boolean mExtBarShow = false;
  private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
  {
    public void onGlobalLayout()
    {
      Rect localRect1 = new Rect();
      Rect localRect2 = new Rect();
      ImeAdapterListener.this.getContainerView().getWindowVisibleDisplayFrame(localRect1);
      ImeAdapterListener.this.getContainerView().getGlobalVisibleRect(localRect2);
      if (localRect1.bottom < ImeAdapterListener.this.mDisplayHeight)
      {
        ImeAdapterListener.this.getContainerView().postDelayed(new Runnable()
        {
          public void run()
          {
            Rect localRect = new Rect();
            Object localObject = new Rect();
            ImeAdapterListener.this.getContainerView().getWindowVisibleDisplayFrame(localRect);
            ImeAdapterListener.this.getContainerView().getGlobalVisibleRect((Rect)localObject);
            if (localRect.bottom < ImeAdapterListener.this.mDisplayHeight)
            {
              if (ImeAdapterListener.this.mContentViewClientExtension != null) {
                ImeAdapterListener.this.mContentViewClientExtension.onSoftKeyBoardIsShowing(true);
              }
              int i = ((Rect)localObject).height();
              int j = localRect.height();
              localObject = new int[2];
              ImeAdapterListener.this.getContainerView().getLocationOnScreen((int[])localObject);
              j = i - j + (localObject[1] - localRect.top);
              i = j;
              if (ImeAdapterListener.this.mExtBarShow) {
                i = j + ImeAdapterListener.this.mExtBarHeight;
              }
              j = i;
              if (i < 0) {
                j = 0;
              }
              ImeAdapterListener.this.setBottomHeightForKeyboard(j);
            }
          }
        }, 200L);
        return;
      }
      ImeAdapterListener.this.hideKeyboard();
    }
  };
  private boolean mSoftKeyBoardOnHiding = false;
  
  ImeAdapterListener(AwContents paramAwContents)
  {
    this.mAwContents = paramAwContents;
    this.mDisplayHeight = getDisplayHeight(paramAwContents.mContainerView.getContext());
  }
  
  private int getDisplayHeight(Context paramContext)
  {
    Object localObject = paramContext.getApplicationContext();
    paramContext = (WindowManager)((Context)localObject).getSystemService("window");
    if (Build.VERSION.SDK_INT < 13) {
      return ((Context)localObject).getResources().getDisplayMetrics().heightPixels;
    }
    localObject = new Point();
    paramContext.getDefaultDisplay().getSize((Point)localObject);
    return ((Point)localObject).y;
  }
  
  public ViewGroup getContainerView()
  {
    return this.mAwContents.mContainerView;
  }
  
  public void hideKeyboard()
  {
    if (this.mBottomHeightPixForKeyboard > 0)
    {
      this.mSoftKeyBoardOnHiding = true;
      X5ApiCompatibilityUtils.removeOnGlobalLayoutListener(getContainerView(), this.mGlobalLayoutListener);
      setBottomHeightForKeyboard(0);
    }
  }
  
  public void setBottomHeightForKeyboard(int paramInt)
  {
    if (this.mBottomHeightPixForKeyboard != paramInt)
    {
      int i = getContainerView().getWidth();
      int j = getContainerView().getHeight();
      this.mAwContents.onSizeChanged(i, j - paramInt, i, j - this.mBottomHeightPixForKeyboard);
      this.mBottomHeightPixForKeyboard = paramInt;
    }
  }
  
  public void setContentViewClientExtension(ContentViewClientExtension paramContentViewClientExtension)
  {
    this.mContentViewClientExtension = paramContentViewClientExtension;
  }
  
  public void setSoftKeyBoardOnHiding(boolean paramBoolean)
  {
    this.mSoftKeyBoardOnHiding = paramBoolean;
  }
  
  public void showInputMethodExtBar(boolean paramBoolean, int paramInt)
  {
    this.mExtBarShow = paramBoolean;
    this.mExtBarHeight = paramInt;
    if (this.mBottomHeightPixForKeyboard > 0) {
      this.mGlobalLayoutListener.onGlobalLayout();
    }
  }
  
  public void showKeyboard()
  {
    X5ApiCompatibilityUtils.removeOnGlobalLayoutListener(getContainerView(), this.mGlobalLayoutListener);
    getContainerView().getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
  }
  
  public boolean softKeyBoardOnHiding()
  {
    return this.mSoftKeyBoardOnHiding;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ImeAdapterListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */