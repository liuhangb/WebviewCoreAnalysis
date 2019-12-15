package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.tencent.mtt.external.reader.internal.QBFuncView;
import com.tencent.tbs.common.resources.TBSResources;
import java.util.Map;

public class ReaderMenuView
  extends FrameLayout
{
  Context jdField_a_of_type_AndroidContentContext = null;
  View.OnClickListener jdField_a_of_type_AndroidViewView$OnClickListener = null;
  QBFuncView jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView = null;
  boolean jdField_a_of_type_Boolean = false;
  boolean b = true;
  
  public ReaderMenuView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public int createMenus(Map<Integer, String> paramMap)
  {
    if (paramMap != null)
    {
      if (paramMap.isEmpty()) {
        return -1;
      }
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView = new QBFuncView(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView.setMenu(paramMap);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView.setClickLintener(this.jdField_a_of_type_AndroidViewView$OnClickListener);
      paramMap = new RelativeLayout(this.jdField_a_of_type_AndroidContentContext);
      addView(paramMap, new FrameLayout.LayoutParams(-1, -1));
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(11);
      localLayoutParams.addRule(12);
      localLayoutParams.bottomMargin = TBSResources.getDimensionPixelSize("reader_menu_btn_margin_bottom");
      localLayoutParams.rightMargin = TBSResources.getDimensionPixelSize("reader_menu_btn_margin_right");
      paramMap.addView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView, localLayoutParams);
      return 0;
    }
    return -1;
  }
  
  public void destory()
  {
    QBFuncView localQBFuncView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView;
    if (localQBFuncView != null)
    {
      localQBFuncView.removeAllViews();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView = null;
    }
    removeAllViews();
  }
  
  public boolean isAnimating()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean isShow()
  {
    return this.b;
  }
  
  public void setMenuClickListener(View.OnClickListener paramOnClickListener)
  {
    this.jdField_a_of_type_AndroidViewView$OnClickListener = paramOnClickListener;
  }
  
  public void setVisiable(final boolean paramBoolean1, boolean paramBoolean2)
  {
    this.b = paramBoolean1;
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView;
    if (localObject == null) {
      return;
    }
    if (!paramBoolean2)
    {
      if (paramBoolean1)
      {
        ((QBFuncView)localObject).setVisibility(0);
        return;
      }
      ((QBFuncView)localObject).setVisibility(4);
      return;
    }
    float f2 = 0.0F;
    float f1 = 1.0F;
    if (paramBoolean1)
    {
      ((QBFuncView)localObject).getWidth();
    }
    else
    {
      ((QBFuncView)localObject).getWidth();
      f2 = 1.0F;
      f1 = 0.0F;
    }
    this.jdField_a_of_type_Boolean = true;
    localObject = new AlphaAnimation(f2, f1);
    ((AlphaAnimation)localObject).setDuration(500L);
    ((AlphaAnimation)localObject).setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (paramBoolean1) {
          ReaderMenuView.this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView.setVisibility(0);
        } else {
          ReaderMenuView.this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView.setVisibility(4);
        }
        ReaderMenuView.this.jdField_a_of_type_Boolean = false;
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView.startAnimation((Animation)localObject);
  }
  
  public int updateMenu(int paramInt, String paramString)
  {
    QBFuncView localQBFuncView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBFuncView;
    if (localQBFuncView == null) {
      return -1;
    }
    return localQBFuncView.updateMenu(paramInt, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */