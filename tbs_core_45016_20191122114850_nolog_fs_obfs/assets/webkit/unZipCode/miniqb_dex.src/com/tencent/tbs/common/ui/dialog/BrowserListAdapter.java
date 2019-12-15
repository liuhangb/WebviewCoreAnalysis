package com.tencent.tbs.common.ui.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.utils.IntentUtils;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.DownloadListener;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BrowserListAdapter
  extends ArrayAdapter<BrowsingActivityInfo>
  implements View.OnClickListener, ListAdapter
{
  private static final int ID_CHECK = 104;
  private static final int ID_CHECKED_BG = 106;
  private static final int ID_DETAIL = 103;
  private static final int ID_ICON = 101;
  private static final int ID_INNER_CONTAINER = 105;
  private static final int ID_LABEL = 102;
  private static final int ID_OPERATION = 107;
  private static final int ID_PROGRESSBAR = 108;
  private static final int MESSAGE_TYPE_REFRESH_RECOMMEND_ITEM = 1;
  String STAT_KEY_DOWNLOAD_INTERCEPT_GUIDE_NEWBIE1 = "ARTF1";
  String STAT_KEY_DOWNLOAD_INTERCEPT_GUIDE_NEWBIE2 = "ARTF2";
  String STAT_KEY_DOWNLOAD_INTERCEPT_GUIDE_NEWBIE3 = "ARTF3";
  private ArrayList<BrowsingActivityInfo> mActivityInfos;
  private TBSDialogBuilder.IntentFilterCallback mCallback;
  private BrowsingActivityInfo mChecked = null;
  private WeakReference<TBSActivityPicker> mDialog;
  private boolean mDownloadIntercept = false;
  private boolean mDownloadPausable = false;
  private Map<String, Drawable> mDrawables;
  private Handler mHandler;
  private Intent mIntent = null;
  private boolean mIsSystemStyle = false;
  private long mLastInstallTime = 0L;
  private WeakReference<ListView> mListView;
  private boolean mOpenBrowserInLongPress = false;
  private BrowsingActivityInfo mRecommend;
  private Context mResContext;
  private boolean mSearchInLongPress = false;
  private BrowserListAdapter obj = this;
  private String[] recommendStringToTrim = null;
  
  public BrowserListAdapter(Context paramContext, Intent paramIntent, BrowsingActivityInfo paramBrowsingActivityInfo1, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback, BrowsingActivityInfo paramBrowsingActivityInfo2, TBSActivityPicker paramTBSActivityPicker, ListView paramListView)
  {
    super(paramContext, 0);
    setDialog(paramTBSActivityPicker);
    setListView(paramListView);
    boolean bool3 = "com.tencent.mobileqq".equals(paramContext.getPackageName());
    boolean bool2 = true;
    boolean bool1 = true;
    if (bool3) {
      this.mDownloadPausable = true;
    } else if ("com.tencent.mm".equals(paramContext.getPackageName())) {
      this.mDownloadPausable = false;
    } else {
      this.mDownloadPausable = true;
    }
    this.mIntent = paramIntent;
    this.mCallback = paramIntentFilterCallback;
    initListener();
    this.mRecommend = paramBrowsingActivityInfo1;
    this.mDownloadIntercept = this.mCallback.isDownloadIntercept();
    this.mOpenBrowserInLongPress = this.mCallback.isOpenBrowserInLongPress();
    this.mSearchInLongPress = this.mCallback.isSearchInLongPress();
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          return;
        }
        String str = (String)paramAnonymousMessage.obj;
        int i = paramAnonymousMessage.arg1;
        BrowserListAdapter.this.refreshRecommend(str, i);
      }
    };
    this.recommendStringToTrim = new String[2];
    this.recommendStringToTrim[0] = getString("x5_tbs_activity_picker_recommend_to_trim", "");
    this.recommendStringToTrim[1] = getString("x5_tbs_activity_picker_recommend_with_chinese_brace_to_trim", "");
    paramIntent = PublicSettingManager.getInstance();
    if (this.mDownloadIntercept)
    {
      if (paramIntent.getDownloadInterceptToQBPopMenuStyle() != 1) {
        bool1 = false;
      }
      this.mIsSystemStyle = bool1;
    }
    else
    {
      if (paramIntent.getLongPressToQBPopMenuStyle() == 1) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.mIsSystemStyle = bool1;
    }
    if (this.mIsSystemStyle) {
      resetDataForSystem(paramContext, paramBrowsingActivityInfo2);
    } else if ("com.tencent.mm".equals(paramContext.getPackageName())) {
      resetDataForSystem(paramContext, paramBrowsingActivityInfo2);
    } else if ("com.tencent.mobileqq".equals(paramContext.getPackageName())) {
      resetDataForSystem(paramContext, paramBrowsingActivityInfo2);
    } else {
      resetDataForCustom(paramContext, paramBrowsingActivityInfo2);
    }
    if (this.mDownloadIntercept)
    {
      TBSStatManager.getInstance().userBehaviorStatistics(this.STAT_KEY_DOWNLOAD_INTERCEPT_GUIDE_NEWBIE1);
      if (this.mIsSystemStyle)
      {
        TBSStatManager.getInstance().userBehaviorStatistics("BWNDL1");
        return;
      }
      TBSStatManager.getInstance().userBehaviorStatistics("BWNDL4");
    }
  }
  
  private View createDefaultLayout(Context paramContext)
  {
    return createQQLayout(paramContext);
  }
  
  private View createQQLayout(Context paramContext)
  {
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
    localLinearLayout.setOrientation(1);
    Object localObject1 = new StateListDrawable();
    Object localObject2 = new ColorDrawable(Color.rgb(242, 242, 242));
    ((StateListDrawable)localObject1).addState(new int[] { 16842919 }, (Drawable)localObject2);
    localObject2 = new ColorDrawable(0);
    ((StateListDrawable)localObject1).addState(new int[] { -16842919 }, (Drawable)localObject2);
    localLinearLayout.setBackgroundDrawable((Drawable)localObject1);
    localObject1 = new RelativeLayout(paramContext);
    ((RelativeLayout)localObject1).setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(70.0F)));
    localObject2 = new ImageView(paramContext);
    ((ImageView)localObject2).setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    ((ImageView)localObject2).setBackgroundColor(Color.rgb(242, 242, 242));
    ((ImageView)localObject2).setId(106);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new ImageView(paramContext);
    Object localObject3 = new RelativeLayout.LayoutParams(dip2px(48.0F), dip2px(48.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(9);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(dip2px(20.0F), 0, dip2px(12.0F), 0);
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setId(101);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new LinearLayout(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(-2, -2);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).addRule(1, 101);
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).setOrientation(1);
    localObject3 = new TextView(paramContext);
    ((TextView)localObject3).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    ((TextView)localObject3).setMaxLines(1);
    ((TextView)localObject3).setTextColor(Color.rgb(34, 34, 34));
    ((TextView)localObject3).setTextSize(1, 15.0F);
    ((TextView)localObject3).setId(102);
    ((LinearLayout)localObject2).addView((View)localObject3);
    localObject3 = new TextView(paramContext);
    ((TextView)localObject3).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    ((TextView)localObject3).setText(this.mCallback.getFileTips());
    ((TextView)localObject3).setTextColor(Color.rgb(169, 215, 78));
    ((TextView)localObject3).setTextSize(1, 13.0F);
    ((TextView)localObject3).setId(103);
    ((LinearLayout)localObject2).addView((View)localObject3);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new ImageView(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(dip2px(24.0F), dip2px(24.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(11);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(0, 0, dip2px(40.0F), 0);
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setImageDrawable(TBSResources.getDrawable("x5_tbs_mobileqq_activity_picker_check"));
    ((ImageView)localObject2).setId(104);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new Button(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(-2, dip2px(30.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(11);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(0, 0, dip2px(20.0F), 0);
    ((Button)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((Button)localObject2).setTextColor(Color.rgb(0, 165, 224));
    ((Button)localObject2).setTextSize(1, 15.0F);
    localObject3 = new StateListDrawable();
    float f = dip2px(3.0F);
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setColor(0);
    localGradientDrawable.setStroke(1, Color.argb(82, 0, 0, 0));
    localGradientDrawable.setCornerRadius(f);
    ((StateListDrawable)localObject3).addState(new int[] { -16842910 }, localGradientDrawable);
    localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setColor(Color.rgb(242, 242, 242));
    localGradientDrawable.setStroke(1, Color.argb(82, 0, 0, 0));
    localGradientDrawable.setCornerRadius(f);
    ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, localGradientDrawable);
    localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setColor(0);
    localGradientDrawable.setStroke(1, Color.argb(82, 0, 0, 0));
    localGradientDrawable.setCornerRadius(f);
    ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, localGradientDrawable);
    ((Button)localObject2).setBackgroundDrawable((Drawable)localObject3);
    ((Button)localObject2).setId(107);
    ((Button)localObject2).setPadding(dip2px(16.0F), 0, dip2px(16.0F), 0);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    paramContext = new ProgressBar(paramContext, null, 16842872);
    localObject2 = new RelativeLayout.LayoutParams(-2, dip2px(5.0F));
    ((RelativeLayout.LayoutParams)localObject2).addRule(5, 107);
    ((RelativeLayout.LayoutParams)localObject2).addRule(7, 107);
    ((RelativeLayout.LayoutParams)localObject2).addRule(3, 107);
    ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(5.0F), 0, 0);
    paramContext.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    paramContext.setMax(100);
    paramContext.setProgress(0);
    localObject2 = new GradientDrawable();
    ((GradientDrawable)localObject2).setColor(Color.rgb(242, 242, 242));
    ((GradientDrawable)localObject2).setCornerRadius(dip2px(3.0F));
    localObject3 = new GradientDrawable();
    ((GradientDrawable)localObject3).setColor(Color.rgb(169, 215, 78));
    ((GradientDrawable)localObject3).setCornerRadius(dip2px(3.0F));
    localObject3 = new ClipDrawable((Drawable)localObject3, 3, 1);
    localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setColor(Color.rgb(169, 215, 78));
    localObject2 = new LayerDrawable(new Drawable[] { localObject2, new ClipDrawable(localGradientDrawable, 3, 1), localObject3 });
    ((LayerDrawable)localObject2).setId(0, 16908288);
    ((LayerDrawable)localObject2).setId(1, 16908303);
    ((LayerDrawable)localObject2).setId(2, 16908301);
    paramContext.setProgressDrawable((Drawable)localObject2);
    paramContext.setId(108);
    ((RelativeLayout)localObject1).addView(paramContext);
    ((RelativeLayout)localObject1).setId(105);
    localLinearLayout.addView((View)localObject1);
    return localLinearLayout;
  }
  
  private View createWXLayout(Context paramContext)
  {
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
    localLinearLayout.setOrientation(1);
    Object localObject1 = new StateListDrawable();
    Object localObject2 = new ColorDrawable(Color.argb(41, 0, 0, 0));
    ((StateListDrawable)localObject1).addState(new int[] { 16842919 }, (Drawable)localObject2);
    localObject2 = new ColorDrawable(0);
    ((StateListDrawable)localObject1).addState(new int[] { -16842919 }, (Drawable)localObject2);
    localLinearLayout.setBackgroundDrawable((Drawable)localObject1);
    localObject1 = new RelativeLayout(paramContext);
    ((RelativeLayout)localObject1).setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(64.0F)));
    localObject2 = new ImageView(paramContext);
    Object localObject3 = new RelativeLayout.LayoutParams(dip2px(48.0F), dip2px(48.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(9);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    int i = dip2px(8.0F);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(i, i, i, i);
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setId(101);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new LinearLayout(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(-2, -2);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).addRule(1, 101);
    ((LinearLayout)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((LinearLayout)localObject2).setOrientation(1);
    localObject3 = new TextView(paramContext);
    ((TextView)localObject3).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    ((TextView)localObject3).setMaxLines(1);
    ((TextView)localObject3).setTextColor(Color.rgb(29, 29, 29));
    ((TextView)localObject3).setTextSize(1, 17.0F);
    ((TextView)localObject3).setId(102);
    ((LinearLayout)localObject2).addView((View)localObject3);
    localObject3 = new TextView(paramContext);
    ((TextView)localObject3).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    ((TextView)localObject3).setText(this.mCallback.getFileTips());
    ((TextView)localObject3).setTextColor(Color.rgb(153, 153, 153));
    ((TextView)localObject3).setTextSize(1, 14.0F);
    ((TextView)localObject3).setId(103);
    ((LinearLayout)localObject2).addView((View)localObject3);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new ImageView(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(dip2px(24.0F), dip2px(24.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(11);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(0, 0, dip2px(14.0F), 0);
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setImageDrawable(TBSResources.getDrawable("x5_tbs_activity_picker_check"));
    ((ImageView)localObject2).setId(104);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    localObject2 = new Button(paramContext);
    localObject3 = new RelativeLayout.LayoutParams(-2, dip2px(31.0F));
    ((RelativeLayout.LayoutParams)localObject3).addRule(11);
    ((RelativeLayout.LayoutParams)localObject3).addRule(15);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(0, 0, dip2px(8.0F), 0);
    ((Button)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    if (this.mCallback.isOpenBrowserInLongPress()) {
      ((Button)localObject2).setTextColor(-1);
    } else {
      ((Button)localObject2).setTextColor(Color.rgb(50, 50, 50));
    }
    ((Button)localObject2).setTextSize(1, 14.0F);
    localObject3 = new StateListDrawable();
    float f = dip2px(2.0F);
    Object localObject4 = new RoundRectShape(new float[] { f, f, f, f, f, f, f, f }, null, null);
    ShapeDrawable localShapeDrawable = new ShapeDrawable((Shape)localObject4);
    localShapeDrawable.getPaint().setColor(Color.argb(128, 69, 192, 26));
    ((StateListDrawable)localObject3).addState(new int[] { -16842910 }, localShapeDrawable);
    localShapeDrawable = new ShapeDrawable((Shape)localObject4);
    localShapeDrawable.getPaint().setColor(Color.rgb(41, 132, 9));
    ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, localShapeDrawable);
    localObject4 = new ShapeDrawable((Shape)localObject4);
    ((ShapeDrawable)localObject4).getPaint().setColor(Color.rgb(69, 192, 26));
    ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, (Drawable)localObject4);
    ((Button)localObject2).setBackgroundDrawable((Drawable)localObject3);
    ((Button)localObject2).setId(107);
    ((Button)localObject2).setPadding(dip2px(8.0F), 0, dip2px(8.0F), 0);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    paramContext = new ProgressBar(paramContext, null, 16842872);
    localObject2 = new RelativeLayout.LayoutParams(-2, dip2px(5.0F));
    ((RelativeLayout.LayoutParams)localObject2).addRule(5, 107);
    ((RelativeLayout.LayoutParams)localObject2).addRule(7, 107);
    ((RelativeLayout.LayoutParams)localObject2).addRule(3, 107);
    ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(5.0F), 0, 0);
    paramContext.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    paramContext.setMax(100);
    paramContext.setProgress(0);
    localObject2 = new GradientDrawable();
    ((GradientDrawable)localObject2).setColor(Color.rgb(242, 242, 242));
    ((GradientDrawable)localObject2).setCornerRadius(dip2px(3.0F));
    localObject3 = new GradientDrawable();
    ((GradientDrawable)localObject3).setColor(Color.rgb(169, 215, 78));
    ((GradientDrawable)localObject3).setCornerRadius(dip2px(3.0F));
    localObject3 = new ClipDrawable((Drawable)localObject3, 3, 1);
    localObject4 = new GradientDrawable();
    ((GradientDrawable)localObject4).setColor(Color.rgb(169, 215, 78));
    localObject2 = new LayerDrawable(new Drawable[] { localObject2, new ClipDrawable((Drawable)localObject4, 3, 1), localObject3 });
    ((LayerDrawable)localObject2).setId(0, 16908288);
    ((LayerDrawable)localObject2).setId(1, 16908303);
    ((LayerDrawable)localObject2).setId(2, 16908301);
    paramContext.setProgressDrawable((Drawable)localObject2);
    paramContext.setId(108);
    ((RelativeLayout)localObject1).addView(paramContext);
    ((RelativeLayout)localObject1).setId(105);
    localLinearLayout.addView((View)localObject1);
    return localLinearLayout;
  }
  
  private int dip2px(float paramFloat)
  {
    TBSActivityPicker localTBSActivityPicker = (TBSActivityPicker)this.mDialog.get();
    if (localTBSActivityPicker == null) {
      return (int)paramFloat;
    }
    return localTBSActivityPicker.dip2px(paramFloat);
  }
  
  private void enableOpenBrowserButtons(boolean paramBoolean)
  {
    Object localObject = this.mDialog;
    if (localObject == null) {
      return;
    }
    localObject = (TBSActivityPicker)((WeakReference)localObject).get();
    if (localObject != null) {
      ((TBSActivityPicker)localObject).enableButtons(paramBoolean);
    }
  }
  
  private String getString(String paramString1, String paramString2)
  {
    String str = TBSResources.getString(paramString1);
    paramString1 = str;
    if (str == null) {
      paramString1 = paramString2;
    }
    return paramString1;
  }
  
  private void initListener()
  {
    this.mCallback.onSetListener(new TBSDialogBuilder.DownloadListener()
    {
      public void onFinished(File paramAnonymousFile)
      {
        if (("com.tencent.mtt".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
          TBSStatManager.getInstance().userBehaviorStatistics("BZWQ016");
        }
        if (("com.tencent.FileManager".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
          TBSStatManager.getInstance().userBehaviorStatistics("BZTW010");
        }
        if (BrowserListAdapter.this.mCallback.isOpenBrowserInLongPress()) {
          TBSStatManager.getInstance().userBehaviorStatistics("BZCA008");
        }
        paramAnonymousFile = BrowserListAdapter.this.mIntent.getStringExtra("hostBeforeInterceptDownload");
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("mDownloadIntercept:");
        localStringBuilder.append(BrowserListAdapter.this.mDownloadIntercept);
        localStringBuilder.append(",hostBeforeInterceptDownload:");
        localStringBuilder.append(paramAnonymousFile);
        localStringBuilder.toString();
        if ((BrowserListAdapter.this.mDownloadIntercept) && (!TextUtils.isEmpty(paramAnonymousFile)))
        {
          QBDownloadManager.getInstance().setTimeStampOfStartInstall(System.currentTimeMillis());
          QBDownloadManager.getInstance().setHostBeforeInterceptDownload(paramAnonymousFile);
        }
        if (BrowserListAdapter.this.mCallback.isDownloaded())
        {
          paramAnonymousFile = BrowserListAdapter.this;
          paramAnonymousFile.refreshRecommend(paramAnonymousFile.getString("x5_tbs_wechat_activity_picker_label_install", "安装"), -1);
          if (("com.tencent.mtt".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
            TBSStatManager.getInstance().userBehaviorStatistics("BZWQ017");
          }
          if (("com.tencent.FileManager".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
            TBSStatManager.getInstance().userBehaviorStatistics("BZTW011");
          }
        }
        else
        {
          Log.e("BrowserListAdapter", "error+++++++++++download file is not exist");
        }
      }
      
      public void onProgress(int paramAnonymousInt)
      {
        BrowserListAdapter.this.refreshRecommend(null, paramAnonymousInt);
      }
      
      public void onStarted()
      {
        BrowserListAdapter.this.refreshRecommend(null, 0);
        if (BrowserListAdapter.this.mOpenBrowserInLongPress)
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AATQB100");
          return;
        }
        if (BrowserListAdapter.this.mSearchInLongPress) {
          TBSStatManager.getInstance().userBehaviorStatistics("AATQB102");
        }
      }
    });
  }
  
  private void installQB(int paramInt, Boolean paramBoolean)
  {
    installQBDirectly(paramInt, true);
  }
  
  private void installQBDirectly(int paramInt, boolean paramBoolean)
  {
    long l1 = System.currentTimeMillis();
    long l2 = this.mLastInstallTime;
    this.mLastInstallTime = l1;
    Object localObject;
    if (l1 - l2 < 3000L)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("error+++++++++++time interval not enough, mLastInstallTime:");
      ((StringBuilder)localObject).append(this.mLastInstallTime);
      Log.e("BrowserListAdapter", ((StringBuilder)localObject).toString());
      return;
    }
    if (paramBoolean)
    {
      localObject = new Bundle();
      ((Bundle)localObject).putString("param_key_positionid", "0");
      if (this.mDownloadIntercept)
      {
        ((Bundle)localObject).putString("param_key_functionid", "1101");
        ((Bundle)localObject).putString("param_key_featureid", "download");
      }
      else
      {
        ((Bundle)localObject).putString("param_key_functionid", "3003");
        ((Bundle)localObject).putString("param_key_featureid", "webview");
      }
      this.mCallback.installDownloadFile(getContext(), "AATQB76", "AATQB77", (Bundle)localObject);
    }
    SysInstallReceiver.realInstall = Boolean.valueOf(paramBoolean);
    SysInstallReceiver.getInstance(this.obj).registerReceiver(getContext());
    if (this.mDownloadIntercept)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTDL10");
      return;
    }
    if (this.mOpenBrowserInLongPress)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AATQB101");
      return;
    }
    if (this.mSearchInLongPress)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AATQB104");
      return;
    }
  }
  
  private void refreshView(final BrowsingActivityInfo paramBrowsingActivityInfo, View paramView, String paramString, int paramInt)
  {
    if (paramView != null)
    {
      if (paramBrowsingActivityInfo == null) {
        return;
      }
      Object localObject1 = (ImageView)paramView.findViewById(101);
      TextView localTextView2 = (TextView)paramView.findViewById(102);
      TextView localTextView1 = (TextView)paramView.findViewById(103);
      ImageView localImageView = (ImageView)paramView.findViewById(104);
      View localView1 = paramView.findViewById(105);
      View localView2 = paramView.findViewById(106);
      final Button localButton = (Button)paramView.findViewById(107);
      ProgressBar localProgressBar = (ProgressBar)paramView.findViewById(108);
      ((ImageView)localObject1).setImageDrawable(paramBrowsingActivityInfo.getIcon(this.mDrawables));
      localObject1 = paramBrowsingActivityInfo.getLabel().trim();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(' ');
      ((StringBuilder)localObject2).append("");
      localObject2 = ((String)localObject1).replaceAll(((StringBuilder)localObject2).toString(), "");
      localObject1 = this.recommendStringToTrim;
      int i = localObject1.length;
      int j = 0;
      while (j < i)
      {
        String str = localObject1[j];
        if ((str != null) && (str.length() > 0)) {
          localObject2 = ((String)localObject2).replaceAll(str, "");
        }
        j += 1;
      }
      localTextView2.setText((CharSequence)localObject2);
      if (paramBrowsingActivityInfo.getResolveInfo() == null)
      {
        localObject1 = getContext().getPackageManager().queryIntentActivities(this.mIntent, 65536).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (ResolveInfo)((Iterator)localObject1).next();
          if (paramBrowsingActivityInfo.getPackageName().equals(((ResolveInfo)localObject2).activityInfo.packageName)) {
            paramBrowsingActivityInfo.setResolveInfo((ResolveInfo)localObject2);
          }
        }
      }
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramAnonymousView = paramAnonymousView.getParent();
          if (paramAnonymousView != null)
          {
            if (!(paramAnonymousView instanceof View)) {
              return;
            }
            paramAnonymousView = (View)paramAnonymousView;
            if (paramAnonymousView.getTag() == BrowserListAdapter.this.mRecommend) {
              BrowserListAdapter.this.onClick(paramAnonymousView);
            }
            return;
          }
        }
      });
      if (this.mCallback.getAppPackageName().equals(paramBrowsingActivityInfo.getPackageName())) {
        localTextView1.setVisibility(0);
      } else {
        localTextView1.setVisibility(8);
      }
      if (paramBrowsingActivityInfo.getResolveInfo() != null)
      {
        localView1.setClickable(false);
        localView1.setEnabled(false);
        if (paramBrowsingActivityInfo == this.mChecked)
        {
          localImageView.setVisibility(0);
          if (localView2 != null) {
            localView2.setVisibility(0);
          }
        }
        else
        {
          localImageView.setVisibility(8);
          if (localView2 != null) {
            localView2.setVisibility(8);
          }
        }
        localButton.setVisibility(8);
        localButton.setOnClickListener(null);
        if (localProgressBar != null) {
          localProgressBar.setVisibility(8);
        }
      }
      else if (TextUtils.isEmpty(paramBrowsingActivityInfo.getPackageName()))
      {
        localView1.setClickable(false);
        localView1.setEnabled(false);
        localImageView.setVisibility(8);
        localButton.setVisibility(8);
        if (localProgressBar != null) {
          localProgressBar.setVisibility(8);
        }
        if (localView2 != null) {
          localView2.setVisibility(8);
        }
      }
      else
      {
        localView1.setClickable(true);
        localView1.setEnabled(true);
        localImageView.setVisibility(8);
        if (localView2 != null) {
          localView2.setVisibility(8);
        }
        localButton.setVisibility(0);
        if (paramString != null)
        {
          localButton.setText(paramString);
          localButton.setEnabled(true);
        }
        else if (this.mCallback.isInstalled())
        {
          localButton.setText(getString("x5_tbs_wechat_activity_picker_label_open", "打开"));
          localButton.setEnabled(true);
        }
        else if (this.mCallback.isDownloaded())
        {
          localButton.setText(getString("x5_tbs_wechat_activity_picker_label_download", "下载"));
          localButton.setEnabled(true);
        }
        else if (this.mCallback.isDownloadStarted())
        {
          localButton.setText(getString("x5_tbs_wechat_activity_picker_label_downloading", "下载中..."));
          localButton.setEnabled(false);
        }
        else
        {
          localButton.setText(getString("x5_tbs_wechat_activity_picker_label_download", "下载"));
          localButton.setEnabled(true);
        }
        if (localProgressBar != null) {
          if (this.mCallback.isDownloadStarted())
          {
            localProgressBar.setVisibility(0);
            if (paramInt >= 0) {
              localProgressBar.setProgress(paramInt);
            }
          }
          else
          {
            localProgressBar.setVisibility(8);
          }
        }
        localButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (BrowserListAdapter.this.mDownloadIntercept)
            {
              TBSStatManager.getInstance().userBehaviorStatistics(BrowserListAdapter.this.STAT_KEY_DOWNLOAD_INTERCEPT_GUIDE_NEWBIE2);
              if (BrowserListAdapter.this.mIsSystemStyle) {
                TBSStatManager.getInstance().userBehaviorStatistics("BWNDL2");
              } else {
                TBSStatManager.getInstance().userBehaviorStatistics("BWNDL5");
              }
            }
            if (BrowserListAdapter.this.mDownloadIntercept) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTDL7");
            } else if (BrowserListAdapter.this.mCallback.isOpenBrowserInLongPress()) {
              TBSStatManager.getInstance().userBehaviorStatistics("BZCA007");
            }
            if ("com.tencent.FileManager".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) {
              TBSStatManager.getInstance().userBehaviorStatistics("BZTW005");
            }
            if (("com.tencent.mtt".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
              TBSStatManager.getInstance().userBehaviorStatistics("BZWQ012");
            }
            if (BrowserListAdapter.this.mCallback.isInstalled())
            {
              BrowserListAdapter.this.mCallback.openApp();
              return;
            }
            if (BrowserListAdapter.this.mCallback.isDownloaded())
            {
              if ("com.tencent.mm".equals(BrowserListAdapter.this.getContext().getApplicationInfo().packageName)) {
                localButton.setText(BrowserListAdapter.this.getString("x5_tbs_wechat_activity_picker_label_install", "安装"));
              }
              if (("com.tencent.mtt".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
                TBSStatManager.getInstance().userBehaviorStatistics("BZWQ017");
              }
              if (("com.tencent.FileManager".equals(BrowserListAdapter.this.mCallback.getAppPackageName())) && (BrowserListAdapter.this.mDownloadIntercept)) {
                TBSStatManager.getInstance().userBehaviorStatistics("BZTW011");
              }
            }
            if (!BrowserListAdapter.this.mCallback.isDownloadStarted()) {
              BrowserListAdapter.this.mCallback.onStartDownload(paramBrowsingActivityInfo.getPackageName(), null);
            }
          }
        });
      }
      paramView.setTag(paramBrowsingActivityInfo);
      paramView.setOnClickListener(this);
      return;
    }
  }
  
  private void setChecked(BrowsingActivityInfo paramBrowsingActivityInfo)
  {
    this.mChecked = paramBrowsingActivityInfo;
    paramBrowsingActivityInfo = this.mChecked;
    boolean bool;
    if ((paramBrowsingActivityInfo != null) && (paramBrowsingActivityInfo.getResolveInfo() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    enableOpenBrowserButtons(bool);
  }
  
  public void dismiss()
  {
    WeakReference localWeakReference = this.mDialog;
    if ((localWeakReference != null) && (localWeakReference.get() != null)) {
      ((TBSActivityPicker)this.mDialog.get()).dismiss();
    }
  }
  
  public BrowsingActivityInfo getCheckedActivityInfo()
  {
    return this.mChecked;
  }
  
  public int getCount()
  {
    return this.mActivityInfos.size();
  }
  
  public Intent getIntentObj()
  {
    return this.mIntent;
  }
  
  public boolean getIsdownloadintercept()
  {
    return this.mDownloadIntercept;
  }
  
  public BrowsingActivityInfo getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mActivityInfos.size())) {
      return (BrowsingActivityInfo)this.mActivityInfos.get(paramInt);
    }
    return null;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = getItem(paramInt);
    if (paramViewGroup == null) {
      return null;
    }
    if (paramView == null)
    {
      paramView = getContext().getApplicationInfo().packageName;
      if ("com.tencent.mm".equals(paramView)) {
        paramView = createWXLayout(getContext());
      } else if ("com.tencent.mobileqq".equals(paramView)) {
        paramView = createWXLayout(getContext());
      } else {
        paramView = createDefaultLayout(getContext());
      }
    }
    refreshView(paramViewGroup, paramView, null, -1);
    return paramView;
  }
  
  public void onClick(View paramView)
  {
    Object localObject = paramView.getTag();
    if ((localObject != null) && ((localObject instanceof BrowsingActivityInfo)))
    {
      BrowsingActivityInfo localBrowsingActivityInfo1 = (BrowsingActivityInfo)localObject;
      if (localBrowsingActivityInfo1 != this.mChecked) {
        if (TextUtils.isEmpty(localBrowsingActivityInfo1.getPackageName()))
        {
          resetDataForOpenOtherApp(getContext(), this.mChecked);
          notifyDataSetChanged();
          paramView = this.mDialog;
          if (paramView != null)
          {
            paramView = (TBSActivityPicker)paramView.get();
            if (paramView != null)
            {
              if (this.mDownloadIntercept) {
                TBSStatManager.getInstance().userBehaviorStatistics("BWNDL6");
              }
              if ("com.tencent.FileManager".equals(this.mCallback.getAppPackageName())) {
                TBSStatManager.getInstance().userBehaviorStatistics("BZTW006");
              }
              paramView.showOtherapp();
            }
          }
        }
        else
        {
          localObject = paramView.getParent();
          if ((localObject instanceof View)) {
            localObject = (View)localObject;
          } else {
            localObject = null;
          }
          if (localObject == null) {
            return;
          }
          BrowsingActivityInfo localBrowsingActivityInfo2 = this.mChecked;
          setChecked(localBrowsingActivityInfo1);
          refreshView(localBrowsingActivityInfo2, ((View)localObject).findViewWithTag(localBrowsingActivityInfo2), null, -1);
          refreshView(this.mChecked, paramView, null, -1);
        }
      }
    }
  }
  
  void refreshRecommend(String paramString, int paramInt)
  {
    if (Looper.myLooper() != Looper.getMainLooper())
    {
      this.mHandler.obtainMessage(1, paramInt, 0, paramString).sendToTarget();
      return;
    }
    Object localObject = (ListView)this.mListView.get();
    if (localObject == null) {
      return;
    }
    localObject = ((ListView)localObject).findViewWithTag(this.mRecommend);
    if (localObject != null) {
      refreshView(this.mRecommend, (View)localObject, paramString, paramInt);
    }
  }
  
  void resetDataForCustom(Context paramContext, BrowsingActivityInfo paramBrowsingActivityInfo)
  {
    this.mActivityInfos = new ArrayList();
    paramContext = new BrowsingActivityInfo(paramContext, null, TBSResources.getString("x5_tbs_wechat_activity_picker_open_other_app_title"), "");
    this.mActivityInfos.add(paramContext);
    paramContext = this.mRecommend;
    if (paramContext != null) {
      this.mActivityInfos.add(0, paramContext);
    }
  }
  
  void resetDataForOpenOtherApp(Context paramContext, BrowsingActivityInfo paramBrowsingActivityInfo)
  {
    this.mActivityInfos = new ArrayList();
    Iterator localIterator = paramContext.getPackageManager().queryIntentActivities(this.mIntent, 65536).iterator();
    for (int i = 0; localIterator.hasNext(); i = 1)
    {
      label34:
      BrowsingActivityInfo localBrowsingActivityInfo = new BrowsingActivityInfo(paramContext, (ResolveInfo)localIterator.next());
      this.mActivityInfos.add(localBrowsingActivityInfo);
      if ((i != 0) || (paramBrowsingActivityInfo == null) || (!localBrowsingActivityInfo.getPackageName().equals(paramBrowsingActivityInfo.getPackageName()))) {
        break label34;
      }
      setChecked(localBrowsingActivityInfo);
    }
    if ((i == 0) && (this.mActivityInfos.size() > 0)) {
      setChecked((BrowsingActivityInfo)this.mActivityInfos.get(0));
    }
  }
  
  void resetDataForSystem(Context paramContext, BrowsingActivityInfo paramBrowsingActivityInfo)
  {
    this.mActivityInfos = new ArrayList();
    Iterator localIterator = paramContext.getPackageManager().queryIntentActivities(this.mIntent, 65536).iterator();
    int j = 0;
    int i = 0;
    while (localIterator.hasNext())
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
      BrowsingActivityInfo localBrowsingActivityInfo1 = new BrowsingActivityInfo(paramContext, localResolveInfo);
      BrowsingActivityInfo localBrowsingActivityInfo2 = this.mRecommend;
      int k;
      if ((localBrowsingActivityInfo2 != null) && (localBrowsingActivityInfo2.getPackageName().equals(localResolveInfo.activityInfo.packageName)))
      {
        this.mActivityInfos.add(0, localBrowsingActivityInfo1);
        k = 1;
      }
      else
      {
        this.mActivityInfos.add(localBrowsingActivityInfo1);
        k = j;
      }
      j = k;
      if (i == 0)
      {
        j = k;
        if (paramBrowsingActivityInfo != null)
        {
          j = k;
          if (localBrowsingActivityInfo1.getPackageName().equals(paramBrowsingActivityInfo.getPackageName()))
          {
            setChecked(localBrowsingActivityInfo1);
            i = 1;
            j = k;
          }
        }
      }
    }
    if (j == 0)
    {
      paramContext = this.mRecommend;
      if (paramContext != null) {
        this.mActivityInfos.add(0, paramContext);
      }
    }
    if ((i == 0) && (this.mActivityInfos.size() > 0)) {
      setChecked((BrowsingActivityInfo)this.mActivityInfos.get(0));
    }
  }
  
  void setDialog(TBSActivityPicker paramTBSActivityPicker)
  {
    this.mDialog = new WeakReference(paramTBSActivityPicker);
  }
  
  public void setDrawables(Map<String, Drawable> paramMap)
  {
    this.mDrawables = paramMap;
  }
  
  void setListView(ListView paramListView)
  {
    this.mListView = new WeakReference(paramListView);
  }
  
  private static class SysInstallReceiver
    extends BroadcastReceiver
  {
    private static BrowserListAdapter mobj;
    public static Boolean realInstall;
    private static SysInstallReceiver sInstance;
    
    static SysInstallReceiver getInstance(BrowserListAdapter paramBrowserListAdapter)
    {
      mobj = paramBrowserListAdapter;
      try
      {
        if (sInstance == null) {
          sInstance = new SysInstallReceiver();
        }
        return sInstance;
      }
      finally {}
    }
    
    private void notifyJSInstallSucc(Context paramContext)
    {
      Intent localIntent = mobj.getIntentObj();
      if (localIntent == null) {
        return;
      }
      try
      {
        Uri localUri = localIntent.getData();
        String str = paramContext.getApplicationInfo().packageName;
        if (!IntentUtils.openUrlInQQBrowserWithReport(paramContext, localUri.toString(), "", str, "")) {
          break label79;
        }
        if (mobj != null)
        {
          if (mobj.getIsdownloadintercept()) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTDL11");
          }
          mobj.dismiss();
        }
        return;
      }
      catch (Exception paramContext)
      {
        label79:
        for (;;) {}
      }
      localIntent.setPackage(null);
    }
    
    private void registerReceiver(Context paramContext)
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      localIntentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
      localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      localIntentFilter.addDataScheme("package");
      paramContext.registerReceiver(this, localIntentFilter);
    }
    
    private void unregisterReceiver(Context paramContext)
    {
      paramContext.unregisterReceiver(this);
    }
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")) && (paramIntent.getData() != null) && ("package".equals(paramIntent.getData().getScheme())) && (mobj.mCallback.getAppPackageName().equals(paramIntent.getData().getSchemeSpecificPart())))
      {
        unregisterReceiver(paramContext);
        paramIntent = realInstall;
        if ((paramIntent != null) && (paramIntent.booleanValue())) {
          notifyJSInstallSucc(paramContext);
        }
        realInstall = null;
        if (mobj.getIsdownloadintercept())
        {
          if ("com.tencent.mtt".equals(mobj.mCallback.getAppPackageName()))
          {
            TBSStatManager.getInstance().userBehaviorStatistics("BZWQ018");
            return;
          }
          if ("com.tencent.FileManager".equals(mobj.mCallback.getAppPackageName())) {
            TBSStatManager.getInstance().userBehaviorStatistics("BZTW012");
          }
        }
        else if ((mobj.mCallback != null) && (mobj.mCallback.isOpenBrowserInLongPress()))
        {
          TBSStatManager.getInstance().userBehaviorStatistics("BZCA010");
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\BrowserListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */