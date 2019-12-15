package com.tencent.tbs.common.ui.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBase;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import java.util.Map;

public class TBSActivityPicker
  extends TBSDialogBase
{
  static final String QQ_PACKAGE = "com.tencent.mobileqq";
  static final String WECHAT_PACKAGE = "com.tencent.mm";
  private BrowserListAdapter mAdapter;
  private Button mButtonAlways;
  private Button mButtonOnce;
  private Intent mIntent;
  private TBSDialogBuilder.IntentFilterCallback mIntentCallback;
  private boolean mIsSystemStyle = false;
  private ListView mList;
  private BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      TBSActivityPicker.this.resetListView();
    }
  };
  private Context mResContext;
  private TextView mTitle;
  
  public TBSActivityPicker(Context paramContext)
  {
    super(paramContext);
  }
  
  private View createDefaultLayout(Context paramContext)
  {
    return createQQLayout(paramContext);
  }
  
  private View createQQLayout(Context paramContext)
  {
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    Object localObject1 = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject1).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject1);
    localObject1 = new FrameLayout(paramContext);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    Object localObject2 = new FrameLayout.LayoutParams(dip2px(280.0F), -1);
    ((FrameLayout.LayoutParams)localObject2).gravity = 17;
    localLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localLinearLayout.setBackgroundColor(-1);
    localLinearLayout.setOrientation(1);
    this.mTitle = new TextView(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-2, dip2px(64.0F));
    ((LinearLayout.LayoutParams)localObject2).weight = 0.0F;
    this.mTitle.setGravity(16);
    this.mTitle.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mTitle.setPadding(dip2px(18.0F), 0, 0, 0);
    this.mTitle.setTextColor(Color.rgb(0, 165, 224));
    this.mTitle.setTextSize(1, 18.0F);
    localObject2 = (String)this.mProperties.get(Integer.valueOf(1));
    this.mTitle.setText((CharSequence)localObject2);
    localLinearLayout.addView(this.mTitle);
    localObject2 = new ImageView(paramContext);
    Object localObject3 = new LinearLayout.LayoutParams(-1, 1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.argb(61, 0, 0, 0));
    localLinearLayout.addView((View)localObject2);
    this.mList = new ListView(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -1);
    ((LinearLayout.LayoutParams)localObject2).weight = 1.0F;
    ((LinearLayout.LayoutParams)localObject2).gravity = 16;
    this.mList.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mList.setDivider(new ColorDrawable(Color.argb(41, 0, 0, 0)));
    this.mList.setDividerHeight(1);
    localLinearLayout.addView(this.mList);
    localObject2 = new ImageView(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, 1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.argb(61, 0, 0, 0));
    localLinearLayout.addView((View)localObject2);
    localObject2 = new LinearLayout(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).setOrientation(0);
    ((LinearLayout)localObject2).setContentDescription("x5_tbs_activity_picker_btn_container");
    this.mButtonAlways = new Button(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
    ((LinearLayout.LayoutParams)localObject3).weight = 1.0F;
    this.mButtonAlways.setLayoutParams((ViewGroup.LayoutParams)localObject3);
    localObject3 = new StateListDrawable();
    Object localObject4 = new ColorDrawable(Color.rgb(242, 242, 242));
    ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, (Drawable)localObject4);
    localObject4 = new ColorDrawable(0);
    ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, (Drawable)localObject4);
    this.mButtonAlways.setBackgroundDrawable((Drawable)localObject3);
    this.mButtonAlways.setText(TBSResources.getText("x5_tbs_wechat_activity_picker_label_always"));
    localObject3 = new int[] { 16842910 };
    int i = Color.rgb(167, 167, 167);
    int j = Color.rgb(29, 29, 29);
    localObject3 = new ColorStateList(new int[][] { { -16842910 }, localObject3 }, new int[] { i, j });
    this.mButtonAlways.setTextColor((ColorStateList)localObject3);
    this.mButtonAlways.setTextSize(1, 17.0F);
    ((LinearLayout)localObject2).addView(this.mButtonAlways);
    localObject3 = new ImageView(paramContext);
    localObject4 = new LinearLayout.LayoutParams(1, -1);
    ((LinearLayout.LayoutParams)localObject4).weight = 0.0F;
    ((ImageView)localObject3).setLayoutParams((ViewGroup.LayoutParams)localObject4);
    ((ImageView)localObject3).setBackgroundColor(Color.argb(41, 0, 0, 0));
    ((LinearLayout)localObject2).addView((View)localObject3);
    this.mButtonOnce = new Button(paramContext);
    paramContext = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
    paramContext.weight = 1.0F;
    this.mButtonOnce.setLayoutParams(paramContext);
    paramContext = new StateListDrawable();
    localObject3 = new ColorDrawable(Color.rgb(242, 242, 242));
    paramContext.addState(new int[] { 16842919 }, (Drawable)localObject3);
    localObject3 = new ColorDrawable(0);
    paramContext.addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.mButtonOnce.setBackgroundDrawable(paramContext);
    this.mButtonOnce.setText(TBSResources.getText("x5_tbs_wechat_activity_picker_label_once"));
    paramContext = new int[] { 16842910 };
    i = Color.rgb(167, 167, 167);
    j = Color.rgb(29, 29, 29);
    paramContext = new ColorStateList(new int[][] { { -16842910 }, paramContext }, new int[] { i, j });
    this.mButtonOnce.setTextColor(paramContext);
    this.mButtonOnce.setTextSize(1, 17.0F);
    ((LinearLayout)localObject2).addView(this.mButtonOnce);
    localLinearLayout.addView((View)localObject2);
    ((FrameLayout)localObject1).addView(localLinearLayout);
    return (View)localObject1;
  }
  
  private View createWXLayout(Context paramContext)
  {
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    Object localObject1 = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject1).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject1);
    localObject1 = new FrameLayout(paramContext);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    Object localObject2 = new FrameLayout.LayoutParams(dip2px(280.0F), -1);
    ((FrameLayout.LayoutParams)localObject2).gravity = 17;
    localLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localLinearLayout.setBackgroundColor(-1);
    localLinearLayout.setOrientation(1);
    this.mTitle = new TextView(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-2, dip2px(65.0F));
    ((LinearLayout.LayoutParams)localObject2).weight = 0.0F;
    this.mTitle.setGravity(16);
    this.mTitle.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mTitle.setPadding(dip2px(18.0F), 0, 0, 0);
    this.mTitle.setTextColor(Color.rgb(69, 192, 26));
    this.mTitle.setTextSize(1, 18.0F);
    localObject2 = (String)this.mProperties.get(Integer.valueOf(1));
    this.mTitle.setText((CharSequence)localObject2);
    localLinearLayout.addView(this.mTitle);
    localObject2 = new ImageView(paramContext);
    Object localObject3 = new LinearLayout.LayoutParams(-1, 1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.argb(61, 0, 0, 0));
    localLinearLayout.addView((View)localObject2);
    this.mList = new ListView(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -1);
    ((LinearLayout.LayoutParams)localObject2).weight = 1.0F;
    ((LinearLayout.LayoutParams)localObject2).gravity = 16;
    this.mList.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mList.setDivider(new ColorDrawable(Color.argb(41, 0, 0, 0)));
    this.mList.setDividerHeight(1);
    localLinearLayout.addView(this.mList);
    localObject2 = new ImageView(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, 1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.argb(61, 0, 0, 0));
    localLinearLayout.addView((View)localObject2);
    localObject2 = new LinearLayout(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).setOrientation(0);
    ((LinearLayout)localObject2).setContentDescription("x5_tbs_activity_picker_btn_container");
    this.mButtonAlways = new Button(paramContext);
    localObject3 = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
    ((LinearLayout.LayoutParams)localObject3).weight = 1.0F;
    this.mButtonAlways.setLayoutParams((ViewGroup.LayoutParams)localObject3);
    localObject3 = new StateListDrawable();
    Object localObject4 = new ColorDrawable(Color.argb(41, 0, 0, 0));
    ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, (Drawable)localObject4);
    localObject4 = new ColorDrawable(0);
    ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, (Drawable)localObject4);
    this.mButtonAlways.setBackgroundDrawable((Drawable)localObject3);
    this.mButtonAlways.setText(TBSResources.getText("x5_tbs_wechat_activity_picker_label_always"));
    this.mButtonAlways.setTextColor(Color.rgb(29, 29, 29));
    this.mButtonAlways.setTextSize(1, 17.0F);
    ((LinearLayout)localObject2).addView(this.mButtonAlways);
    localObject3 = new ImageView(paramContext);
    localObject4 = new LinearLayout.LayoutParams(1, -1);
    ((LinearLayout.LayoutParams)localObject4).weight = 0.0F;
    ((ImageView)localObject3).setLayoutParams((ViewGroup.LayoutParams)localObject4);
    ((ImageView)localObject3).setBackgroundColor(Color.rgb(217, 217, 217));
    ((LinearLayout)localObject2).addView((View)localObject3);
    this.mButtonOnce = new Button(paramContext);
    paramContext = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
    paramContext.weight = 1.0F;
    this.mButtonOnce.setLayoutParams(paramContext);
    paramContext = new StateListDrawable();
    localObject3 = new ColorDrawable(Color.argb(41, 0, 0, 0));
    paramContext.addState(new int[] { 16842919 }, (Drawable)localObject3);
    localObject3 = new ColorDrawable(0);
    paramContext.addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.mButtonOnce.setBackgroundDrawable(paramContext);
    this.mButtonOnce.setText(TBSResources.getText("x5_tbs_wechat_activity_picker_label_once"));
    this.mButtonOnce.setTextColor(Color.rgb(29, 29, 29));
    this.mButtonOnce.setTextSize(1, 17.0F);
    ((LinearLayout)localObject2).addView(this.mButtonOnce);
    localLinearLayout.addView((View)localObject2);
    ((FrameLayout)localObject1).addView(localLinearLayout);
    return (View)localObject1;
  }
  
  private void resetListView()
  {
    Object localObject = this.mAdapter;
    if (localObject != null) {
      localObject = ((BrowserListAdapter)localObject).getCheckedActivityInfo();
    } else {
      localObject = null;
    }
    this.mAdapter = new BrowserListAdapter(getContext(), this.mIntent, new BrowsingActivityInfo(getContext(), this.mIntentCallback.getAppIcon(), this.mIntentCallback.getAppLabel(), this.mIntentCallback.getAppPackageName()), this.mIntentCallback, (BrowsingActivityInfo)localObject, this, this.mList);
    localObject = this.mProperties.get(Integer.valueOf(10));
    if (localObject != null)
    {
      localObject = (Map)localObject;
      this.mAdapter.setDrawables((Map)localObject);
    }
    this.mList.setAdapter(this.mAdapter);
  }
  
  void enableButtons(boolean paramBoolean)
  {
    Button localButton = this.mButtonOnce;
    if (localButton != null) {
      localButton.setEnabled(paramBoolean);
    }
    localButton = this.mButtonAlways;
    if (localButton != null) {
      localButton.setEnabled(paramBoolean);
    }
  }
  
  public void hideButtons()
  {
    Button localButton = this.mButtonOnce;
    if (localButton != null) {
      localButton.setVisibility(8);
    }
    localButton = this.mButtonAlways;
    if (localButton != null) {
      localButton.setVisibility(8);
    }
  }
  
  public void initUI()
  {
    Object localObject = (DialogInterface.OnCancelListener)this.mProperties.get(Integer.valueOf(4));
    DialogInterface.OnKeyListener localOnKeyListener = (DialogInterface.OnKeyListener)this.mProperties.get(Integer.valueOf(9));
    setOnCancelListener((DialogInterface.OnCancelListener)localObject);
    setOnKeyListener(localOnKeyListener);
    this.mIntent = ((Intent)this.mProperties.get(Integer.valueOf(5)));
    this.mIntentCallback = ((TBSDialogBuilder.IntentFilterCallback)this.mProperties.get(Integer.valueOf(7)));
    localObject = PublicSettingManager.getInstance();
    boolean bool3 = this.mIntentCallback.isDownloadIntercept();
    boolean bool2 = false;
    boolean bool1 = false;
    if (bool3)
    {
      if (((PublicSettingManager)localObject).getDownloadInterceptToQBPopMenuStyle() == 1) {
        bool1 = true;
      }
      this.mIsSystemStyle = bool1;
    }
    else
    {
      bool1 = bool2;
      if (((PublicSettingManager)localObject).getLongPressToQBPopMenuStyle() == 1) {
        bool1 = true;
      }
      this.mIsSystemStyle = bool1;
    }
    if (this.mIsSystemStyle)
    {
      setContentView(createQQLayout(getContext()));
      showButtons();
    }
    else if ("com.tencent.mm".equals(getContext().getPackageName()))
    {
      setContentView(createWXLayout(getContext()));
      showButtons();
    }
    else if ("com.tencent.mobileqq".equals(getContext().getPackageName()))
    {
      setContentView(createWXLayout(getContext()));
      showButtons();
    }
    else
    {
      setContentView(createWXLayout(getContext()));
      hideButtons();
    }
    resetListView();
    this.mButtonAlways.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = TBSActivityPicker.this.mAdapter.getCheckedActivityInfo();
        if (paramAnonymousView == null) {
          return;
        }
        if (paramAnonymousView.getResolveInfo() == null) {
          return;
        }
        if (TBSActivityPicker.this.mIntentCallback.isDownloadIntercept()) {
          if (TBSActivityPicker.this.mIsSystemStyle) {
            TBSStatManager.getInstance().userBehaviorStatistics("BWNDL3");
          } else {
            TBSStatManager.getInstance().userBehaviorStatistics("BWNDL7");
          }
        }
        if (TBSActivityPicker.this.mIntentCallback != null) {
          TBSActivityPicker.this.mIntentCallback.onSendingIntent(TBSActivityPicker.this.mIntent, paramAnonymousView.getResolveInfo(), false);
        }
        TBSActivityPicker.this.dismiss();
      }
    });
    this.mButtonOnce.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = TBSActivityPicker.this.mAdapter.getCheckedActivityInfo();
        if (paramAnonymousView == null) {
          return;
        }
        if (paramAnonymousView.getResolveInfo() == null) {
          return;
        }
        if (TBSActivityPicker.this.mIntentCallback.isDownloadIntercept()) {
          if (TBSActivityPicker.this.mIsSystemStyle) {
            TBSStatManager.getInstance().userBehaviorStatistics("BWNDL3");
          } else {
            TBSStatManager.getInstance().userBehaviorStatistics("BWNDL7");
          }
        }
        if (TBSActivityPicker.this.mIntentCallback != null) {
          TBSActivityPicker.this.mIntentCallback.onSendingIntent(TBSActivityPicker.this.mIntent, paramAnonymousView.getResolveInfo(), true);
        }
        TBSActivityPicker.this.dismiss();
      }
    });
  }
  
  public void onStart()
  {
    super.onStart();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    localIntentFilter.addDataScheme("package");
    try
    {
      getContext().registerReceiver(this.mReceiver, localIntentFilter);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public void onStop()
  {
    super.onStop();
    TBSDialogBuilder.IntentFilterCallback localIntentFilterCallback = this.mIntentCallback;
    if (localIntentFilterCallback != null) {
      localIntentFilterCallback.onDialogDismissed();
    }
    try
    {
      getContext().unregisterReceiver(this.mReceiver);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public void showButtons()
  {
    Button localButton = this.mButtonOnce;
    if (localButton != null) {
      localButton.setVisibility(0);
    }
    localButton = this.mButtonAlways;
    if (localButton != null) {
      localButton.setVisibility(0);
    }
  }
  
  public void showOtherapp()
  {
    String str = TBSResources.getString("x5_tbs_wechat_activity_picker_open_other_app_title");
    this.mTitle.setText(str);
    showButtons();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSActivityPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */