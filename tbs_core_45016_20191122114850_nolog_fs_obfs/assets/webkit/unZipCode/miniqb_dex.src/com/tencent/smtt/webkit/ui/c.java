package com.tencent.smtt.webkit.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class c
  extends LinearLayout
{
  private int jdField_a_of_type_Int = 0;
  private ImageView jdField_a_of_type_AndroidWidgetImageView = null;
  private TextView jdField_a_of_type_AndroidWidgetTextView;
  private a.b jdField_a_of_type_ComTencentSmttWebkitUiA$b;
  private a jdField_a_of_type_ComTencentSmttWebkitUiA;
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  private boolean c = false;
  private boolean d = false;
  private boolean e;
  
  public c(Context paramContext, a parama, boolean paramBoolean1, String paramString, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt, int[] paramArrayOfInt)
  {
    super(paramContext);
    this.jdField_a_of_type_JavaLangString = paramString;
    this.b = (paramBoolean3 ^ true);
    this.jdField_a_of_type_Boolean = paramBoolean2;
    this.c = paramBoolean5;
    this.jdField_a_of_type_Int = paramInt;
    this.d = paramBoolean4;
    this.jdField_a_of_type_ComTencentSmttWebkitUiA = parama;
    this.e = paramBoolean1;
    a(paramContext, paramArrayOfInt);
  }
  
  private void a(Context paramContext, int[] paramArrayOfInt)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    setOrientation(0);
    setLayoutParams(localLayoutParams);
    boolean bool = this.d;
    int i = 5;
    int j;
    if (!bool)
    {
      this.jdField_a_of_type_AndroidWidgetImageView = new ImageView(paramContext);
      localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams.setMargins(0, paramArrayOfInt[1], 0, paramArrayOfInt[3]);
      localLayoutParams.setMarginStart(paramArrayOfInt[0]);
      localLayoutParams.setMarginEnd(0);
      this.jdField_a_of_type_AndroidWidgetImageView.setLayoutParams(localLayoutParams);
      if (this.b) {
        this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(3));
      } else if (this.jdField_a_of_type_Boolean) {
        this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(2));
      } else {
        this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(0));
      }
      addView(this.jdField_a_of_type_AndroidWidgetImageView);
      this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
      localLayoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0F);
      localLayoutParams.setMargins(0, paramArrayOfInt[1], 0, paramArrayOfInt[3]);
      localLayoutParams.setMarginStart(a.b);
      localLayoutParams.setMarginEnd(paramArrayOfInt[2]);
      this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
      this.jdField_a_of_type_AndroidWidgetTextView.setText(this.jdField_a_of_type_JavaLangString);
      if (this.b)
      {
        paramArrayOfInt = this.jdField_a_of_type_AndroidWidgetTextView;
        if (this.e) {
          j = e.LINE_NIGHT_COLOR;
        } else {
          j = e.LINE_COLOR;
        }
        paramArrayOfInt.setTextColor(j);
      }
      else
      {
        paramArrayOfInt = this.jdField_a_of_type_AndroidWidgetTextView;
        if (this.e) {
          j = e.TEXT_BLACK_NIGHT_COLOR;
        } else {
          j = e.TEXT_BLACK_COLOR;
        }
        paramArrayOfInt.setTextColor(j);
      }
      this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(16.0F);
      paramArrayOfInt = this.jdField_a_of_type_AndroidWidgetTextView;
      if (!a(paramContext)) {
        i = 3;
      }
      paramArrayOfInt.setGravity(i);
      addView(this.jdField_a_of_type_AndroidWidgetTextView);
      return;
    }
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
    localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLayoutParams.setMargins(0, paramArrayOfInt[1], 0, paramArrayOfInt[3]);
    localLayoutParams.setMarginStart(paramArrayOfInt[0]);
    localLayoutParams.setMarginEnd(paramArrayOfInt[2]);
    this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
    this.jdField_a_of_type_AndroidWidgetTextView.setText(this.jdField_a_of_type_JavaLangString);
    paramArrayOfInt = this.jdField_a_of_type_AndroidWidgetTextView;
    if (this.e) {
      j = e.TEXT_BLACK_NIGHT_COLOR;
    } else {
      j = e.TEXT_BLACK_COLOR;
    }
    paramArrayOfInt.setTextColor(j);
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(18.0F);
    paramArrayOfInt = this.jdField_a_of_type_AndroidWidgetTextView;
    if (!a(paramContext)) {
      i = 3;
    }
    paramArrayOfInt.setGravity(i);
    addView(this.jdField_a_of_type_AndroidWidgetTextView);
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public void a(int paramInt)
  {
    ImageView localImageView = this.jdField_a_of_type_AndroidWidgetImageView;
    if (localImageView != null)
    {
      if (this.b) {
        return;
      }
      if (paramInt != 3)
      {
        switch (paramInt)
        {
        default: 
          return;
        case 1: 
          if (this.c)
          {
            if (this.jdField_a_of_type_Boolean)
            {
              localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(0));
              return;
            }
            localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(2));
            return;
          }
          localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(2));
          return;
        }
        localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(1));
        return;
      }
      if (this.jdField_a_of_type_Boolean)
      {
        localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(2));
        return;
      }
      localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(0));
      return;
    }
  }
  
  public void a(a.b paramb)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$b = paramb;
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public boolean a()
  {
    return this.b;
  }
  
  public boolean a(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().getLayoutDirection() == 1;
  }
  
  public void b(int paramInt)
  {
    ImageView localImageView = this.jdField_a_of_type_AndroidWidgetImageView;
    if (localImageView != null) {
      localImageView.setImageDrawable(this.jdField_a_of_type_ComTencentSmttWebkitUiA.a(paramInt));
    }
  }
  
  public boolean b()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    a(paramMotionEvent.getAction());
    if (paramMotionEvent.getAction() == 1) {
      this.jdField_a_of_type_ComTencentSmttWebkitUiA$b.a(this);
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */