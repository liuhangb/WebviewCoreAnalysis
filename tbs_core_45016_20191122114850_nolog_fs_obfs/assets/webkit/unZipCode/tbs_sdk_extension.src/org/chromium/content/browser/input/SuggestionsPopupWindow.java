package org.chromium.content.browser.input;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.ui.UiUtils;
import org.chromium.ui.base.WindowAndroid;

public abstract class SuggestionsPopupWindow
  implements View.OnClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener
{
  private int jdField_a_of_type_Int;
  private Activity jdField_a_of_type_AndroidAppActivity;
  private final Context jdField_a_of_type_AndroidContentContext;
  private DisplayMetrics jdField_a_of_type_AndroidUtilDisplayMetrics;
  private final View jdField_a_of_type_AndroidViewView;
  private LinearLayout jdField_a_of_type_AndroidWidgetLinearLayout;
  private ListView jdField_a_of_type_AndroidWidgetListView;
  private PopupWindow jdField_a_of_type_AndroidWidgetPopupWindow;
  private TextView jdField_a_of_type_AndroidWidgetTextView;
  private String jdField_a_of_type_JavaLangString;
  protected final TextSuggestionHost a;
  private WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private View jdField_b_of_type_AndroidViewView;
  private LinearLayout jdField_b_of_type_AndroidWidgetLinearLayout;
  private TextView jdField_b_of_type_AndroidWidgetTextView;
  
  public SuggestionsPopupWindow(Context paramContext, TextSuggestionHost paramTextSuggestionHost, WindowAndroid paramWindowAndroid, View paramView)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost = paramTextSuggestionHost;
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
    this.jdField_a_of_type_AndroidViewView = paramView;
    a();
    b();
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setContentView(this.jdField_a_of_type_AndroidWidgetLinearLayout);
  }
  
  private void a()
  {
    this.jdField_a_of_type_AndroidWidgetPopupWindow = new PopupWindow();
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setWidth(-2);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setHeight(-2);
    if (Build.VERSION.SDK_INT < 21) {
      this.jdField_a_of_type_AndroidWidgetPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
    }
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setInputMethodMode(2);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setFocusable(true);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setClippingEnabled(false);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setOnDismissListener(this);
  }
  
  private void b()
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)this.jdField_a_of_type_AndroidContentContext.getSystemService("layout_inflater");
    this.jdField_a_of_type_AndroidWidgetLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    int i = Build.VERSION.SDK_INT;
    this.jdField_b_of_type_Int = 0;
    this.jdField_a_of_type_AndroidWidgetListView = new ListView(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_AndroidWidgetListView.setDivider(null);
    this.jdField_b_of_type_AndroidWidgetLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_AndroidWidgetListView.addFooterView(this.jdField_b_of_type_AndroidWidgetLinearLayout, null, false);
    this.jdField_a_of_type_AndroidWidgetListView.setAdapter(new SuggestionAdapter(null));
    this.jdField_a_of_type_AndroidWidgetListView.setOnItemClickListener(this);
    this.jdField_b_of_type_AndroidViewView = new View(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_AndroidWidgetTextView.setOnClickListener(this);
    this.jdField_b_of_type_AndroidWidgetTextView = new TextView(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_b_of_type_AndroidWidgetTextView.setOnClickListener(this);
  }
  
  private void c()
  {
    Intent localIntent = new Intent("com.android.settings.USER_DICTIONARY_INSERT");
    String str2 = this.jdField_a_of_type_JavaLangString;
    String str1 = str2;
    if (Build.VERSION.SDK_INT < 19)
    {
      str1 = str2;
      if (str2.length() > 48) {
        str1 = str2.substring(0, 48);
      }
    }
    localIntent.putExtra("word", str1);
    localIntent.setFlags(localIntent.getFlags() | 0x10000000);
    this.jdField_a_of_type_AndroidContentContext.startActivity(localIntent);
  }
  
  private void d()
  {
    int i = UiUtils.a(this.jdField_a_of_type_AndroidWidgetListView.getAdapter()) + (this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingLeft() + this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingRight());
    int j = View.MeasureSpec.makeMeasureSpec(this.jdField_a_of_type_AndroidUtilDisplayMetrics.heightPixels, Integer.MIN_VALUE);
    this.jdField_a_of_type_AndroidWidgetLinearLayout.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), j);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setWidth(i);
  }
  
  private void e()
  {
    if (this.jdField_a_of_type_Int == 0)
    {
      this.jdField_b_of_type_AndroidViewView.setVisibility(8);
      return;
    }
    this.jdField_b_of_type_AndroidViewView.setVisibility(0);
  }
  
  protected void a(double paramDouble1, double paramDouble2, String paramString)
  {
    this.jdField_a_of_type_Int = getSuggestionsCount();
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_AndroidAppActivity = ((Activity)this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid.getActivity().get());
    paramString = this.jdField_a_of_type_AndroidAppActivity;
    if (paramString != null) {
      this.jdField_a_of_type_AndroidUtilDisplayMetrics = paramString.getResources().getDisplayMetrics();
    } else {
      this.jdField_a_of_type_AndroidUtilDisplayMetrics = this.jdField_a_of_type_AndroidContentContext.getResources().getDisplayMetrics();
    }
    paramString = this.jdField_a_of_type_AndroidAppActivity;
    if ((paramString != null) && (!ApiCompatibilityUtils.isInMultiWindowMode(paramString)))
    {
      paramString = new Rect();
      this.jdField_a_of_type_AndroidAppActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(paramString);
      i = paramString.top;
    }
    else
    {
      i = 0;
    }
    this.jdField_b_of_type_AndroidWidgetLinearLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
    int i = this.jdField_a_of_type_AndroidUtilDisplayMetrics.heightPixels - i - this.jdField_b_of_type_AndroidWidgetLinearLayout.getMeasuredHeight() - this.jdField_b_of_type_Int * 2 - this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingTop() - this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingBottom();
    if (i > 0) {
      i /= 0;
    } else {
      i = 0;
    }
    this.jdField_a_of_type_Int = Math.min(this.jdField_a_of_type_Int, i);
    e();
    d();
    int n = this.jdField_a_of_type_AndroidWidgetLinearLayout.getMeasuredWidth();
    i = this.jdField_a_of_type_AndroidWidgetLinearLayout.getMeasuredHeight();
    double d = n / 2.0F;
    Double.isNaN(d);
    int i1 = (int)Math.round(paramDouble1 - d);
    int j = (int)Math.round(paramDouble2);
    paramString = new int[2];
    this.jdField_a_of_type_AndroidViewView.getLocationInWindow(paramString);
    int i2 = paramString[0];
    int k = paramString[1];
    int m = this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingTop();
    n = Math.min(this.jdField_a_of_type_AndroidUtilDisplayMetrics.widthPixels - n + this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingRight(), i1 + i2);
    n = Math.max(-this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingLeft(), n);
    i = Math.min(j + k - m, this.jdField_a_of_type_AndroidUtilDisplayMetrics.heightPixels - i - this.jdField_a_of_type_AndroidWidgetLinearLayout.getPaddingTop() - this.jdField_b_of_type_Int);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.showAtLocation(this.jdField_a_of_type_AndroidViewView, 0, n, i);
  }
  
  protected void a(boolean paramBoolean)
  {
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    }
    localTextView.setVisibility(i);
  }
  
  protected abstract void applySuggestion(int paramInt);
  
  public void dismiss()
  {
    this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
  }
  
  @VisibleForTesting
  public View getContentViewForTesting()
  {
    return this.jdField_a_of_type_AndroidWidgetLinearLayout;
  }
  
  protected abstract Object getSuggestionItem(int paramInt);
  
  protected abstract SpannableString getSuggestionText(int paramInt);
  
  protected abstract int getSuggestionsCount();
  
  public boolean isShowing()
  {
    return this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing();
  }
  
  public void onClick(View paramView)
  {
    if (paramView == this.jdField_a_of_type_AndroidWidgetTextView)
    {
      c();
      this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost.onNewWordAddedToDictionary(this.jdField_a_of_type_JavaLangString);
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
      return;
    }
    if (paramView == this.jdField_b_of_type_AndroidWidgetTextView)
    {
      this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost.deleteActiveSuggestionRange();
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
    }
  }
  
  public void onDismiss()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost.onSuggestionMenuClosed(this.jdField_a_of_type_Boolean);
    this.jdField_a_of_type_Boolean = false;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramInt >= this.jdField_a_of_type_Int) {
      return;
    }
    applySuggestion(paramInt);
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
  }
  
  public void updateWindowAndroid(WindowAndroid paramWindowAndroid)
  {
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
  }
  
  private class SuggestionAdapter
    extends BaseAdapter
  {
    private LayoutInflater jdField_a_of_type_AndroidViewLayoutInflater = (LayoutInflater)SuggestionsPopupWindow.a(SuggestionsPopupWindow.this).getSystemService("layout_inflater");
    
    private SuggestionAdapter() {}
    
    public int getCount()
    {
      return SuggestionsPopupWindow.a(SuggestionsPopupWindow.this);
    }
    
    public Object getItem(int paramInt)
    {
      return SuggestionsPopupWindow.this.getSuggestionItem(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      paramViewGroup = (TextView)paramView;
      paramView = paramViewGroup;
      if (paramViewGroup == null) {
        paramView = new TextView(SuggestionsPopupWindow.a(SuggestionsPopupWindow.this));
      }
      paramView.setText(SuggestionsPopupWindow.this.getSuggestionText(paramInt));
      return paramView;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\SuggestionsPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */