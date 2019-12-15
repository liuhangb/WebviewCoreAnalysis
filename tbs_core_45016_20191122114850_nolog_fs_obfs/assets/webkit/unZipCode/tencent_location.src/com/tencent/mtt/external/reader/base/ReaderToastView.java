package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.resources.TBSResources;

public class ReaderToastView
  extends ReaderTypeView
{
  public static final int MENU_TYPE_DISABLE = 0;
  public static final int MENU_TYPE_HIDE = 2;
  public static final int MENU_TYPE_SHOW = 1;
  Context jdField_a_of_type_AndroidContentContext = null;
  TranslateAnimation jdField_a_of_type_AndroidViewAnimationTranslateAnimation = null;
  TextView jdField_a_of_type_AndroidWidgetTextView = null;
  private ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  boolean jdField_a_of_type_Boolean = false;
  boolean b = true;
  int e = 0;
  int f = 0;
  int g = 0;
  int h = 0;
  int i = 0;
  private int j = 0;
  
  public ReaderToastView(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  private void c()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          return;
        }
        ReaderToastView.this.b();
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  public void SetToastText(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > 0) && (paramInt2 > 0))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramInt1);
      ((StringBuilder)localObject).append("/");
      ((StringBuilder)localObject).append(paramInt2);
      localObject = ((StringBuilder)localObject).toString();
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder((CharSequence)localObject);
      localSpannableStringBuilder.setSpan(new ForegroundColorSpan(TBSResources.getColor("reader_bg_color")), 0, ((String)localObject).length(), 33);
      this.jdField_a_of_type_AndroidWidgetTextView.setText(localSpannableStringBuilder);
    }
  }
  
  TranslateAnimation a(final View paramView, final int paramInt)
  {
    TranslateAnimation localTranslateAnimation1 = new TranslateAnimation(0.0F, 0.0F, 0.0F, paramInt);
    localTranslateAnimation1.setDuration(250L);
    final TranslateAnimation localTranslateAnimation2 = new TranslateAnimation(0.0F, 0.0F, 0.0F, 0.0F);
    localTranslateAnimation2.setDuration(0L);
    localTranslateAnimation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        paramView.startAnimation(localTranslateAnimation2);
        if (ReaderToastView.this.jdField_a_of_type_AndroidWidgetTextView != null)
        {
          FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)ReaderToastView.this.jdField_a_of_type_AndroidWidgetTextView.getLayoutParams();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("topMargin:");
          localStringBuilder.append(localLayoutParams.topMargin);
          localStringBuilder.append(",OffsetY=");
          localStringBuilder.append(paramInt);
          LogUtils.d("ReaderToastView", localStringBuilder.toString());
          localLayoutParams.topMargin += paramInt;
          ReaderToastView.this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
          ReaderToastView.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(1);
          ReaderToastView.this.hideToastView();
        }
        ReaderToastView.this.jdField_a_of_type_Boolean = false;
        paramAnonymousAnimation.setAnimationListener(null);
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
        ReaderToastView.this.jdField_a_of_type_Boolean = true;
      }
    });
    return localTranslateAnimation1;
  }
  
  void a()
  {
    if (this.jdField_a_of_type_AndroidWidgetTextView == null)
    {
      this.jdField_a_of_type_AndroidWidgetTextView = new TextView(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
      try
      {
        this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_pdf_toast_bg"));
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        localOutOfMemoryError.printStackTrace();
      }
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
      this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, TBSResources.getDimensionPixelSize("reader_fontsize_t2"));
      localLayoutParams.gravity = 3;
      localLayoutParams.leftMargin = this.f;
      localLayoutParams.topMargin = (this.g + this.i);
      this.mParentLayout.addView(this.jdField_a_of_type_AndroidWidgetTextView, localLayoutParams);
      this.jdField_a_of_type_AndroidWidgetTextView.setPadding(TBSResources.getDimensionPixelSize("reader_dp_6"), 0, TBSResources.getDimensionPixelSize("reader_dp_6"), 0);
    }
  }
  
  void b()
  {
    if (this.jdField_a_of_type_AndroidWidgetTextView == null) {
      return;
    }
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    localAlphaAnimation.setDuration(500L);
    localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (ReaderToastView.this.jdField_a_of_type_AndroidWidgetTextView != null)
        {
          ReaderToastView.this.jdField_a_of_type_AndroidWidgetTextView.setVisibility(4);
          ReaderToastView.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(1);
        }
        paramAnonymousAnimation.setAnimationListener(null);
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    this.jdField_a_of_type_AndroidWidgetTextView.startAnimation(localAlphaAnimation);
  }
  
  public int create()
  {
    this.f = TBSResources.getDimensionPixelSize("reader_pdf_toast_left_offset");
    this.g = TBSResources.getDimensionPixelSize("reader_pdf_toast_top_offset");
    this.h = TBSResources.getDimensionPixelSize("reader_titlebar_height");
    c();
    a();
    return 0;
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    if (getFrameLayout() != null)
    {
      getFrameLayout().removeView(this.jdField_a_of_type_AndroidWidgetTextView);
      this.jdField_a_of_type_AndroidWidgetTextView = null;
    }
    this.jdField_a_of_type_AndroidViewAnimationTranslateAnimation = null;
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public void hideToastView()
  {
    if (this.jdField_a_of_type_AndroidWidgetTextView != null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(1, 3000);
    }
  }
  
  public boolean isAnimating()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void setEnableMenuShowHideAnim(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public void setTilteBarHeight(int paramInt)
  {
    this.i = paramInt;
  }
  
  public void showToastView(int paramInt)
  {
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    int m = 0;
    int k = 0;
    if (localTextView != null)
    {
      localTextView.setVisibility(0);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(1);
    }
    if ((paramInt == 1) && (this.j != 1))
    {
      LogUtils.d("ReaderToastView", "MENU_TYPE_SHOW:");
      localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
      if (localTextView != null)
      {
        this.j = 1;
        paramInt = k;
        if (this.b) {
          paramInt = this.h;
        }
        this.jdField_a_of_type_AndroidViewAnimationTranslateAnimation = a(localTextView, paramInt);
        this.jdField_a_of_type_AndroidWidgetTextView.startAnimation(this.jdField_a_of_type_AndroidViewAnimationTranslateAnimation);
      }
    }
    else if ((paramInt == 2) && (this.j == 1))
    {
      LogUtils.d("ReaderToastView", "MENU_TYPE_HIDE:");
      localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
      if (localTextView != null)
      {
        this.j = 2;
        paramInt = m;
        if (this.b) {
          paramInt = -this.h;
        }
        this.jdField_a_of_type_AndroidViewAnimationTranslateAnimation = a(localTextView, paramInt);
        this.jdField_a_of_type_AndroidWidgetTextView.startAnimation(this.jdField_a_of_type_AndroidViewAnimationTranslateAnimation);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderToastView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */