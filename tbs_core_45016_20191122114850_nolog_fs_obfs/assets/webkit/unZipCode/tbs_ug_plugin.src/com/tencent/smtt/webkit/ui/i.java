package com.tencent.smtt.webkit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.smtt.webkit.SmttResource;
import java.util.HashMap;
import java.util.Map;

public class i
  extends Dialog
{
  private static float jdField_a_of_type_Float = -1.0F;
  private static float jdField_b_of_type_Float = -1.0F;
  private static float jdField_c_of_type_Float = -1.0F;
  private static final String jdField_c_of_type_JavaLangString = SmttResource.getString("x5_translate_provider", "string");
  private static final String d = SmttResource.getString("x5_copy_result", "string");
  private static final String e = SmttResource.getString("x5_ok", "string");
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences = null;
  View.OnClickListener jdField_a_of_type_AndroidViewView$OnClickListener;
  private Button jdField_a_of_type_AndroidWidgetButton;
  TriggerLinearout.OnSizeChangeCallback jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback;
  String jdField_a_of_type_JavaLangString;
  private Map<Integer, Object> jdField_a_of_type_JavaUtilMap = new HashMap();
  private boolean jdField_a_of_type_Boolean = true;
  View.OnClickListener jdField_b_of_type_AndroidViewView$OnClickListener;
  private Button jdField_b_of_type_AndroidWidgetButton;
  String jdField_b_of_type_JavaLangString;
  
  public i(Context paramContext, String paramString1, String paramString2, TriggerLinearout.OnSizeChangeCallback paramOnSizeChangeCallback)
  {
    super(paramContext, 16973835);
    paramContext.getPackageManager();
    a(paramContext.getApplicationContext());
    this.jdField_b_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_JavaLangString = paramString2;
    this.jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback = paramOnSizeChangeCallback;
  }
  
  private View a(Context paramContext)
  {
    if (this.jdField_a_of_type_Boolean) {
      i = 110;
    } else {
      i = 20;
    }
    int j = b(jdField_a_of_type_Float);
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    Object localObject1 = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject1).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject1);
    localObject1 = new FrameLayout(paramContext);
    TriggerLinearout localTriggerLinearout = new TriggerLinearout(paramContext, this.jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback);
    Object localObject2 = new FrameLayout.LayoutParams(a(j - 88), -1);
    float f = i;
    ((FrameLayout.LayoutParams)localObject2).setMargins(0, a(f), 0, a(f));
    ((FrameLayout.LayoutParams)localObject2).gravity = 17;
    localTriggerLinearout.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localTriggerLinearout.setBackgroundColor(-1);
    localTriggerLinearout.setOrientation(1);
    int i = a(21.0F);
    localObject2 = new LinearLayout(paramContext);
    ((LinearLayout)localObject2).setOrientation(1);
    Object localObject3 = new LinearLayout.LayoutParams(-1, -1);
    Object localObject5 = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject5).gravity = 1;
    ((LinearLayout.LayoutParams)localObject5).setMargins(i, i, i, 0);
    Object localObject4 = new TextView(paramContext);
    ((TextView)localObject4).setTextColor(Color.rgb(102, 102, 102));
    ((TextView)localObject4).setLayoutParams((ViewGroup.LayoutParams)localObject5);
    ((TextView)localObject4).setTextSize(1, 14.0F);
    ((TextView)localObject4).setLineSpacing(0.0F, 1.2F);
    ((TextView)localObject4).setText(this.jdField_b_of_type_JavaLangString);
    TextView localTextView = new TextView(paramContext);
    localTextView.setTextColor(Color.rgb(36, 36, 36));
    localTextView.setLayoutParams((ViewGroup.LayoutParams)localObject5);
    localTextView.setTextSize(1, 18.0F);
    localTextView.setLineSpacing(0.0F, 1.2F);
    localTextView.setText(this.jdField_a_of_type_JavaLangString);
    Object localObject6 = new LinearLayout.LayoutParams(-2, a(20.0F));
    ((LinearLayout.LayoutParams)localObject6).gravity = 5;
    ((LinearLayout.LayoutParams)localObject6).setMargins(0, a(12.0F), i, a(12.0F));
    localObject5 = new TextView(paramContext);
    ((TextView)localObject5).setLayoutParams((ViewGroup.LayoutParams)localObject6);
    ((TextView)localObject5).setText("");
    ((TextView)localObject5).setTextSize(1, 12.2F);
    ((TextView)localObject5).setGravity(16);
    ((TextView)localObject5).setTextColor(Color.rgb(170, 170, 170));
    localObject6 = SmttResource.getDrawable("x5_translate_logo");
    ((Drawable)localObject6).setBounds(0, 0, a(106.0F), a(14.0F));
    ((TextView)localObject5).setCompoundDrawables((Drawable)localObject6, null, null, null);
    ((TextView)localObject5).setCompoundDrawablePadding(a(5.0F));
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).addView((View)localObject4);
    ((LinearLayout)localObject2).addView(localTextView);
    ((LinearLayout)localObject2).addView((View)localObject5);
    localObject3 = new ScrollView(paramContext);
    ((ScrollView)localObject3).setScrollbarFadingEnabled(true);
    localObject4 = new LinearLayout.LayoutParams(-1, -1);
    ((LinearLayout.LayoutParams)localObject4).weight = 1.0F;
    ((LinearLayout.LayoutParams)localObject4).gravity = 16;
    ((LinearLayout.LayoutParams)localObject4).setMargins(10, 10, 10, 10);
    ((ScrollView)localObject3).setLayoutParams((ViewGroup.LayoutParams)localObject4);
    ((ScrollView)localObject3).addView((View)localObject2);
    localTriggerLinearout.addView((View)localObject3);
    localObject2 = new ImageView(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, 1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.argb(61, 0, 0, 0));
    localTriggerLinearout.addView((View)localObject2);
    localObject2 = new LinearLayout(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).setOrientation(0);
    ((LinearLayout)localObject2).setContentDescription("x5_tbs_activity_picker_btn_container");
    this.jdField_a_of_type_AndroidWidgetButton = new Button(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, a(49.0F));
    ((LinearLayout.LayoutParams)localObject3).weight = 1.0F;
    this.jdField_a_of_type_AndroidWidgetButton.setLayoutParams((ViewGroup.LayoutParams)localObject3);
    localObject3 = new StateListDrawable();
    localObject4 = new ColorDrawable(Color.argb(41, 0, 0, 0));
    ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, (Drawable)localObject4);
    localObject4 = new ColorDrawable(0);
    ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, (Drawable)localObject4);
    this.jdField_a_of_type_AndroidWidgetButton.setBackgroundDrawable((Drawable)localObject3);
    this.jdField_a_of_type_AndroidWidgetButton.setText(d);
    this.jdField_a_of_type_AndroidWidgetButton.setTextColor(Color.rgb(102, 102, 102));
    this.jdField_a_of_type_AndroidWidgetButton.setTextSize(1, 18.0F);
    ((LinearLayout)localObject2).addView(this.jdField_a_of_type_AndroidWidgetButton);
    localObject3 = new ImageView(paramContext);
    localObject4 = new LinearLayout.LayoutParams(1, -1);
    ((LinearLayout.LayoutParams)localObject4).weight = 0.0F;
    ((ImageView)localObject3).setLayoutParams((ViewGroup.LayoutParams)localObject4);
    ((ImageView)localObject3).setBackgroundColor(Color.rgb(217, 217, 217));
    ((LinearLayout)localObject2).addView((View)localObject3);
    this.jdField_b_of_type_AndroidWidgetButton = new Button(paramContext);
    paramContext = new LinearLayout.LayoutParams(-1, a(49.0F));
    paramContext.weight = 1.0F;
    this.jdField_b_of_type_AndroidWidgetButton.setLayoutParams(paramContext);
    paramContext = new StateListDrawable();
    localObject3 = new ColorDrawable(Color.argb(41, 0, 0, 0));
    paramContext.addState(new int[] { 16842919 }, (Drawable)localObject3);
    localObject3 = new ColorDrawable(0);
    paramContext.addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.jdField_b_of_type_AndroidWidgetButton.setBackgroundDrawable(paramContext);
    this.jdField_b_of_type_AndroidWidgetButton.setText(e);
    this.jdField_b_of_type_AndroidWidgetButton.setTextColor(Color.rgb(76, 154, 250));
    this.jdField_b_of_type_AndroidWidgetButton.setTextSize(1, 18.0F);
    ((LinearLayout)localObject2).addView(this.jdField_b_of_type_AndroidWidgetButton);
    localTriggerLinearout.addView((View)localObject2);
    ((FrameLayout)localObject1).addView(localTriggerLinearout);
    return (View)localObject1;
  }
  
  private void a(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    boolean bool;
    if (paramContext.getDefaultDisplay().getOrientation() % 2 == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    if (jdField_c_of_type_Float < 0.0F)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      jdField_c_of_type_Float = localDisplayMetrics.density;
      int i;
      if (this.jdField_a_of_type_Boolean) {
        i = localDisplayMetrics.widthPixels;
      } else {
        i = localDisplayMetrics.heightPixels;
      }
      jdField_a_of_type_Float = i;
      if (this.jdField_a_of_type_Boolean) {
        i = localDisplayMetrics.heightPixels;
      } else {
        i = localDisplayMetrics.widthPixels;
      }
      jdField_b_of_type_Float = i;
    }
  }
  
  public int a(float paramFloat)
  {
    return (int)(paramFloat * jdField_c_of_type_Float + 0.5F);
  }
  
  public void a()
  {
    setContentView(a(getContext()));
    this.jdField_a_of_type_AndroidWidgetButton.setOnClickListener(this.jdField_a_of_type_AndroidViewView$OnClickListener);
    this.jdField_b_of_type_AndroidWidgetButton.setOnClickListener(this.jdField_b_of_type_AndroidViewView$OnClickListener);
  }
  
  public void a(View.OnClickListener paramOnClickListener)
  {
    this.jdField_a_of_type_AndroidViewView$OnClickListener = paramOnClickListener;
  }
  
  public int b(float paramFloat)
  {
    return (int)(paramFloat / jdField_c_of_type_Float + 0.5F);
  }
  
  public void b(View.OnClickListener paramOnClickListener)
  {
    this.jdField_b_of_type_AndroidViewView$OnClickListener = paramOnClickListener;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    a();
  }
  
  public void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
  }
  
  public Bundle onSaveInstanceState()
  {
    return super.onSaveInstanceState();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */