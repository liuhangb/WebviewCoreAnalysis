package com.tencent.smtt.webkit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.smtt.listbox.MttScrollView;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.util.ArrayList;

public class a
  extends Dialog
  implements DialogInterface.OnDismissListener
{
  public static final int b;
  public static final int c;
  public static final int d;
  public static final int e;
  public static final int f;
  public static final int g;
  public static final int h;
  public static final int i;
  public int a;
  private Context jdField_a_of_type_AndroidContentContext = getContext();
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable = null;
  private LinearLayout jdField_a_of_type_AndroidWidgetLinearLayout;
  public TextView a;
  private MttScrollView jdField_a_of_type_ComTencentSmttListboxMttScrollView;
  private a jdField_a_of_type_ComTencentSmttWebkitUiA$a;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private ArrayList<c> jdField_a_of_type_JavaUtilArrayList;
  private boolean jdField_a_of_type_Boolean = false;
  private String[] jdField_a_of_type_ArrayOfJavaLangString;
  private boolean[] jdField_a_of_type_ArrayOfBoolean;
  private Drawable jdField_b_of_type_AndroidGraphicsDrawableDrawable = null;
  public TextView b;
  private boolean jdField_b_of_type_Boolean = false;
  private boolean[] jdField_b_of_type_ArrayOfBoolean;
  private Drawable jdField_c_of_type_AndroidGraphicsDrawableDrawable = null;
  private boolean jdField_c_of_type_Boolean;
  private boolean[] jdField_c_of_type_ArrayOfBoolean;
  private Drawable d;
  private int j;
  
  static
  {
    jdField_b_of_type_Int = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_listbox_single_grid", "dimen"));
    int k = jdField_b_of_type_Int;
    jdField_c_of_type_Int = k * 3;
    jdField_d_of_type_Int = k * 4;
    double d1 = k;
    Double.isNaN(d1);
    e = (int)(d1 * 3.5D);
    d1 = k;
    Double.isNaN(d1);
    f = (int)(d1 * 0.5D);
    d1 = k;
    Double.isNaN(d1);
    g = (int)(d1 * 1.5D);
    h = k * 3;
    i = k * 2;
  }
  
  public a(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, TencentWebViewProxy paramTencentWebViewProxy, String[] paramArrayOfString, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, boolean[] paramArrayOfBoolean3)
  {
    super(paramContext);
    this.jdField_d_of_type_AndroidGraphicsDrawableDrawable = null;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_Boolean = paramBoolean2;
    this.jdField_c_of_type_Boolean = paramBoolean1;
    this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
    this.jdField_a_of_type_ArrayOfBoolean = paramArrayOfBoolean2;
    this.jdField_b_of_type_ArrayOfBoolean = paramArrayOfBoolean1;
    this.jdField_c_of_type_ArrayOfBoolean = paramArrayOfBoolean3;
    a();
  }
  
  public int a()
  {
    return this.j;
  }
  
  public Drawable a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    case 2: 
      return this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
    case 1: 
      return this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    }
    return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  }
  
  public View a(int paramInt, boolean paramBoolean)
  {
    View localView = new View(this.jdField_a_of_type_AndroidContentContext);
    LinearLayout.LayoutParams localLayoutParams;
    if (paramBoolean) {
      localLayoutParams = new LinearLayout.LayoutParams(1, -1);
    } else {
      localLayoutParams = new LinearLayout.LayoutParams(-1, 1);
    }
    localView.setLayoutParams(localLayoutParams);
    localView.setBackgroundColor(paramInt);
    return localView;
  }
  
  public TextView a(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    TextView localTextView = new TextView(this.jdField_a_of_type_AndroidContentContext);
    localTextView.setTextSize(paramInt1);
    localTextView.setTextColor(paramInt2);
    localTextView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0F));
    localTextView.setText(paramString);
    localTextView.setFocusable(true);
    localTextView.setId(paramInt3);
    if (this.jdField_c_of_type_Boolean) {
      e.setViewBackground(localTextView, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_dialog_press_night_background", "drawable")));
    } else {
      e.setViewBackground(localTextView, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_dialog_press_background", "drawable")));
    }
    localTextView.setGravity(17);
    return localTextView;
  }
  
  public void a()
  {
    this.jdField_a_of_type_Int = e.DIALOG_WIDTH;
    requestWindowFeature(1);
    setCanceledOnTouchOutside(true);
    getWindow().setBackgroundDrawable(e.getImageDrawable("x5_tbs_dialog_backgroud", this.jdField_c_of_type_Boolean, 1));
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a = new a(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.setHorizontalFadingEdgeEnabled(false);
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.setVerticalFadingEdgeEnabled(false);
    Object localObject1 = new LinearLayout.LayoutParams(-1, -2);
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.setOrientation(1);
    setContentView(this.jdField_a_of_type_ComTencentSmttWebkitUiA$a);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView = new MttScrollView(this.jdField_a_of_type_AndroidContentContext, this.jdField_c_of_type_Boolean);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.setHorizontalFadingEdgeEnabled(false);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.setVerticalFadingEdgeEnabled(false);
    localObject1 = new LinearLayout.LayoutParams(-1, -2);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.addView(this.jdField_a_of_type_ComTencentSmttListboxMttScrollView);
    b();
    c();
    if (this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidWidgetLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
      localObject1 = new LinearLayout.LayoutParams(-1, -2);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject1);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.setOrientation(1);
      this.jdField_a_of_type_ComTencentSmttWebkitUiA$a.addView(this.jdField_a_of_type_AndroidWidgetLinearLayout);
      localObject1 = this.jdField_a_of_type_AndroidWidgetLinearLayout;
      int k;
      if (this.jdField_c_of_type_Boolean) {
        k = e.LINE_NIGHT_COLOR;
      } else {
        k = e.LINE_COLOR;
      }
      ((LinearLayout)localObject1).addView(a(k, false));
      localObject1 = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
      Object localObject2 = new LinearLayout.LayoutParams(-1, e.BOTTOM_BUTTON_HEIGHT);
      ((LinearLayout)localObject1).setOrientation(0);
      ((LinearLayout)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.addView((View)localObject1);
      localObject2 = SmttResource.getString("x5_cancel", "string");
      if (this.jdField_c_of_type_Boolean) {
        k = e.TEXT_BLACK_NIGHT_COLOR;
      } else {
        k = e.TEXT_BLACK_COLOR;
      }
      this.jdField_b_of_type_AndroidWidgetTextView = a((String)localObject2, 18, k, 10001);
      ((LinearLayout)localObject1).addView(this.jdField_b_of_type_AndroidWidgetTextView);
      if (this.jdField_c_of_type_Boolean) {
        k = e.LINE_NIGHT_COLOR;
      } else {
        k = e.LINE_COLOR;
      }
      ((LinearLayout)localObject1).addView(a(k, true));
      localObject2 = SmttResource.getString("x5_ok", "string");
      if (this.jdField_c_of_type_Boolean) {
        k = e.TEXT_BLUE_NIGHT_COLOR;
      } else {
        k = e.TEXT_BLUE_COLOR;
      }
      this.jdField_a_of_type_AndroidWidgetTextView = a((String)localObject2, 18, k, 10000);
      ((LinearLayout)localObject1).addView(this.jdField_a_of_type_AndroidWidgetTextView);
      this.jdField_b_of_type_AndroidWidgetTextView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          a.a(a.this, true);
          a.this.dismiss();
        }
      });
      this.jdField_a_of_type_AndroidWidgetTextView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          a.this.dismiss();
        }
      });
    }
    setOnDismissListener(this);
  }
  
  public void a(int paramInt)
  {
    this.j = paramInt;
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public int[] a(int paramInt, boolean paramBoolean)
  {
    int[] arrayOfInt = new int[4];
    int k = jdField_c_of_type_Int;
    arrayOfInt[0] = k;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = k;
    arrayOfInt[3] = 0;
    if (paramBoolean)
    {
      if (paramInt == 0) {
        arrayOfInt[1] = jdField_d_of_type_Int;
      } else {
        arrayOfInt[1] = e;
      }
      arrayOfInt[3] = f;
      return arrayOfInt;
    }
    if (paramInt == 0) {
      arrayOfInt[1] = jdField_d_of_type_Int;
    } else {
      arrayOfInt[1] = g;
    }
    if ((paramInt == this.jdField_a_of_type_ArrayOfJavaLangString.length - 1) && (!this.jdField_a_of_type_Boolean))
    {
      arrayOfInt[3] = jdField_d_of_type_Int;
      return arrayOfInt;
    }
    arrayOfInt[3] = g;
    return arrayOfInt;
  }
  
  public void b()
  {
    if (this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_multi_normal", this.jdField_c_of_type_Boolean, 3);
      this.jdField_b_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_multi_pressed", this.jdField_c_of_type_Boolean, 3);
      this.jdField_c_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_multi_selected", this.jdField_c_of_type_Boolean, 3);
      this.jdField_d_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_multi_disable", this.jdField_c_of_type_Boolean, 3);
      return;
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_single_normal", this.jdField_c_of_type_Boolean, 3);
    this.jdField_b_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_single_pressed", this.jdField_c_of_type_Boolean, 3);
    this.jdField_c_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_single_selected", this.jdField_c_of_type_Boolean, 3);
    this.jdField_d_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_single_disable", this.jdField_c_of_type_Boolean, 3);
  }
  
  public void c()
  {
    b localb = new b(this);
    Object localObject1 = new LinearLayout.LayoutParams(-1, -2);
    LinearLayout localLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    localLinearLayout.setHorizontalFadingEdgeEnabled(false);
    localLinearLayout.setVerticalFadingEdgeEnabled(false);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    int k = 0;
    for (;;)
    {
      Object localObject2 = this.jdField_a_of_type_ArrayOfJavaLangString;
      if (k >= localObject2.length) {
        break;
      }
      localObject1 = this.jdField_a_of_type_AndroidContentContext;
      boolean bool = this.jdField_c_of_type_Boolean;
      localObject2 = localObject2[k];
      int m = this.jdField_b_of_type_ArrayOfBoolean[k];
      int n = this.jdField_a_of_type_ArrayOfBoolean[k];
      boolean[] arrayOfBoolean = this.jdField_c_of_type_ArrayOfBoolean;
      localObject1 = new c((Context)localObject1, this, bool, (String)localObject2, m, n, arrayOfBoolean[k], this.jdField_a_of_type_Boolean, k, a(k, arrayOfBoolean[k]));
      ((c)localObject1).a(localb);
      if (this.jdField_b_of_type_ArrayOfBoolean[k] == 1)
      {
        a(k);
        localb.a(k, (c)localObject1);
      }
      ((c)localObject1).setVerticalFadingEdgeEnabled(false);
      ((c)localObject1).setHorizontalFadingEdgeEnabled(false);
      localLinearLayout.addView((View)localObject1);
      this.jdField_a_of_type_JavaUtilArrayList.add(localObject1);
      if ((k == this.jdField_a_of_type_ArrayOfJavaLangString.length - 1) && (this.jdField_a_of_type_Boolean))
      {
        localObject1 = new View(this.jdField_a_of_type_AndroidContentContext);
        ((View)localObject1).setLayoutParams(new LinearLayout.LayoutParams(-1, jdField_d_of_type_Int - g));
        localLinearLayout.addView((View)localObject1);
      }
      k += 1;
    }
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.addView(localLinearLayout, new ViewGroup.LayoutParams(-1, -2));
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) {
      return;
    }
    if (a())
    {
      if (!this.jdField_b_of_type_Boolean)
      {
        int i1 = this.jdField_a_of_type_JavaUtilArrayList.size();
        paramDialogInterface = new boolean[i1];
        int n = 0;
        int k = 0;
        int m;
        for (;;)
        {
          m = n;
          if (k >= i1) {
            break;
          }
          paramDialogInterface[k] = 0;
          k += 1;
        }
        while (m < i1)
        {
          c localc = (c)this.jdField_a_of_type_JavaUtilArrayList.get(m);
          if (localc.b()) {
            paramDialogInterface[localc.a()] = 1;
          }
          m += 1;
        }
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.replyMultiListBox(i1, paramDialogInterface);
      }
    }
    else {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.replyListBox(a());
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = null;
    this.jdField_b_of_type_AndroidGraphicsDrawableDrawable = null;
    this.jdField_c_of_type_AndroidGraphicsDrawableDrawable = null;
    this.jdField_d_of_type_AndroidGraphicsDrawableDrawable = null;
  }
  
  public void show()
  {
    getWindow().setLayout(this.jdField_a_of_type_Int, -2);
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localTencentWebViewProxy != null) && (((X5WebViewAdapter)localTencentWebViewProxy).getSettingsExtension() != null)) {
      this.jdField_c_of_type_Boolean = (((X5WebViewAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy).getSettingsExtension().getDayOrNight() ^ true);
    }
    b();
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.a(0);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.a(this.jdField_c_of_type_Boolean);
    this.jdField_a_of_type_ComTencentSmttListboxMttScrollView.b();
    super.show();
  }
  
  class a
    extends LinearLayout
  {
    public a(Context paramContext)
    {
      super();
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if (a.a(a.this) != null)
      {
        int j = View.MeasureSpec.getSize(paramInt2);
        paramInt2 = 0;
        int k = View.MeasureSpec.makeMeasureSpec(j, 0);
        int i = paramInt2;
        if (a.a(a.this))
        {
          i = paramInt2;
          if (a.a(a.this) != null)
          {
            a.a(a.this).measure(paramInt1, k);
            i = 1;
          }
        }
        a.a(a.this).measure(paramInt1, k);
        if (i != 0) {
          paramInt2 = a.a(a.this).getMeasuredHeight() + a.a(a.this).getMeasuredHeight();
        } else {
          paramInt2 = a.a(a.this).getMeasuredHeight();
        }
        j = j * 2 / 3;
        if (paramInt2 > j)
        {
          if (i != 0)
          {
            a.a(a.this).getLayoutParams().height = (j - a.a(a.this).getMeasuredHeight());
            paramInt2 = j;
          }
          else
          {
            a.a(a.this).getLayoutParams().height = j;
            paramInt2 = j;
          }
        }
        else {
          a.a(a.this).getLayoutParams().height = -2;
        }
        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824));
      }
    }
  }
  
  public class b
  {
    private int jdField_a_of_type_Int;
    private c jdField_a_of_type_ComTencentSmttWebkitUiC;
    private a b;
    
    public b(a parama)
    {
      this.b = parama;
    }
    
    public void a(int paramInt, c paramc)
    {
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_ComTencentSmttWebkitUiC = paramc;
    }
    
    public void a(View paramView)
    {
      paramView = (c)paramView;
      if (paramView.a() == true) {
        return;
      }
      if ((this.jdField_a_of_type_ComTencentSmttWebkitUiC != null) && (!this.b.a()))
      {
        c localc = this.jdField_a_of_type_ComTencentSmttWebkitUiC;
        if (localc != paramView)
        {
          localc.a(false);
          this.jdField_a_of_type_ComTencentSmttWebkitUiC.b(0);
        }
      }
      this.jdField_a_of_type_Int = paramView.a();
      this.jdField_a_of_type_ComTencentSmttWebkitUiC = paramView;
      if (this.b.a() == true)
      {
        paramView = this.jdField_a_of_type_ComTencentSmttWebkitUiC;
        paramView.a(paramView.b() ^ true);
      }
      else
      {
        this.jdField_a_of_type_ComTencentSmttWebkitUiC.a(true);
      }
      this.b.a(this.jdField_a_of_type_Int);
      if (!this.b.a()) {
        this.b.dismiss();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */