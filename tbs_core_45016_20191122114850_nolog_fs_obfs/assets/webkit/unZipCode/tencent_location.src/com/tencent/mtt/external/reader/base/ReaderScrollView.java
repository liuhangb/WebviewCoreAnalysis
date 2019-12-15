package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.tencent.tbs.common.resources.TBSResources;

public class ReaderScrollView
  extends ReaderTypeView
{
  static final int f = TBSResources.getDimensionPixelSize("control_scrollbar_min_height");
  float jdField_a_of_type_Float = 0.0F;
  Context jdField_a_of_type_AndroidContentContext = null;
  Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable = null;
  View jdField_a_of_type_AndroidViewView = null;
  private ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  int e = 0;
  final int g = TBSResources.getDimensionPixelSize("control_scrollbar_width");
  final int h = TBSResources.getDimensionPixelSize("control_scrollbar_left_offset");
  
  public ReaderScrollView(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = TBSResources.getDrawable("reader_scrollbar_vertical_fg_normal");
    this.e = 0;
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
        if (ReaderScrollView.this.e > 0)
        {
          paramAnonymousMessage = ReaderScrollView.this;
          paramAnonymousMessage.e -= 20;
          if (ReaderScrollView.this.e < 0) {
            ReaderScrollView.this.e = 0;
          }
          ReaderScrollView.this.b();
          ReaderScrollView.this.a.send(1);
        }
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  void a()
  {
    if ((this.jdField_a_of_type_AndroidViewView == null) && (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null))
    {
      this.jdField_a_of_type_AndroidViewView = new ImageView(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha(this.e);
      this.jdField_a_of_type_AndroidViewView.setBackgroundDrawable(this.jdField_a_of_type_AndroidGraphicsDrawableDrawable);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
      localLayoutParams.gravity = 5;
      this.mParentLayout.addView(this.jdField_a_of_type_AndroidViewView, localLayoutParams);
      this.jdField_a_of_type_AndroidViewView.setVisibility(4);
      a(this.jdField_a_of_type_AndroidViewView.getWidth(), 0, 0, 0);
    }
  }
  
  void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = this.jdField_a_of_type_AndroidViewView;
    if (localObject != null)
    {
      localObject = (FrameLayout.LayoutParams)((View)localObject).getLayoutParams();
      ((FrameLayout.LayoutParams)localObject).leftMargin = paramInt1;
      ((FrameLayout.LayoutParams)localObject).topMargin = paramInt2;
      ((FrameLayout.LayoutParams)localObject).width = (paramInt3 - paramInt1);
      ((FrameLayout.LayoutParams)localObject).height = (paramInt4 - paramInt2);
      this.jdField_a_of_type_AndroidViewView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.jdField_a_of_type_AndroidViewView.bringToFront();
    }
  }
  
  void b()
  {
    if (this.jdField_a_of_type_AndroidViewView != null)
    {
      Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      if (localDrawable != null)
      {
        localDrawable.setAlpha(this.e);
        this.jdField_a_of_type_AndroidViewView.invalidate();
      }
    }
  }
  
  public int create()
  {
    c();
    a();
    return 0;
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    if (this.mParentLayout != null)
    {
      View localView = this.jdField_a_of_type_AndroidViewView;
      if (localView != null)
      {
        if (localView.getParent() != null) {
          this.mParentLayout.removeView(this.jdField_a_of_type_AndroidViewView);
        }
        this.mParentLayout = null;
      }
    }
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public void hideScrollBar()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(1, 500);
  }
  
  public void setScrollRatio(float paramFloat)
  {
    this.jdField_a_of_type_Float = paramFloat;
  }
  
  public void showScrollBar()
  {
    View localView = this.jdField_a_of_type_AndroidViewView;
    if (localView == null) {
      return;
    }
    localView.setVisibility(0);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(1);
    this.e = 100;
    b();
  }
  
  public void updateScrollView(float paramFloat)
  {
    if (this.mParentLayout.getHeight() > 0)
    {
      int j = (int)(this.mParentLayout.getHeight() * this.jdField_a_of_type_Float);
      int k = f;
      int i = j;
      if (j < k) {
        i = k;
      }
      float f1 = this.jdField_a_of_type_Float;
      j = this.mParentLayout.getWidth() - this.g + this.h;
      k = (int)((this.mParentLayout.getHeight() - i) * paramFloat);
      a(j, k, this.g + j, i + k);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */