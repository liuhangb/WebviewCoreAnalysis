package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.http.Apn;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;

public class FreeWifiGuideView
  extends LinearLayout
  implements View.OnClickListener, WifiScanner.SeekQbFreeWifiListener
{
  public static final int STYLE_BOTTOM_BANNER = 2;
  public static final int STYLE_ICON = 1;
  private static final String TAG = "FreeWifiGuideView";
  private final int MSG_FREE_WIFI_GOT = 1;
  private final int UI_TYPE_CONN_WATCH_VIDEO = 2;
  private final int UI_TYPE_N_AVAILABLE = 3;
  private final int UI_TYPE_ONE_KEY_CONN_WIFI = 1;
  private final int UI_TYPE_UNKNOWN = -1;
  private IWifiGuideActionListener mActionListener;
  private LinearLayout mContentView;
  private int mStyle = 1;
  private TextView mTvDesc;
  private Handler mUiHandler;
  private int mUiType = -1;
  
  public FreeWifiGuideView(Context paramContext)
  {
    this(paramContext, 1);
  }
  
  public FreeWifiGuideView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mStyle = paramInt;
    this.mUiHandler = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          return;
        }
        if (WifiBusinessUtils.isWifiQbInstalled(ContextHolder.getAppContext()))
        {
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("handleMessage() MSG_FREE_WIFI_GOT freeWifi.size: ");
          ((StringBuilder)localObject).append(paramAnonymousMessage.arg1);
          ((StringBuilder)localObject).toString();
          if (paramAnonymousMessage.arg1 > 0)
          {
            localObject = null;
            if (FreeWifiGuideView.this.mStyle == 1) {
              localObject = TBSResources.getText("wifi_free_wifi_connectable");
            } else if (FreeWifiGuideView.this.mStyle == 2) {
              localObject = TBSResources.getText("wifi_free_wifi_connectable_watch_free");
            }
            if (localObject != null)
            {
              FreeWifiGuideView.this.mTvDesc.setText(String.format(((CharSequence)localObject).toString(), new Object[] { String.valueOf(paramAnonymousMessage.arg1) }));
              FreeWifiGuideView.access$202(FreeWifiGuideView.this, 3);
            }
            else
            {
              if (FreeWifiGuideView.this.mStyle == 1) {
                FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_conn_wifi_watch_video"));
              } else if (FreeWifiGuideView.this.mStyle == 2) {
                FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_conn_wifi_watch_video_free"));
              }
              FreeWifiGuideView.access$202(FreeWifiGuideView.this, 2);
            }
          }
          else
          {
            if (FreeWifiGuideView.this.mStyle == 1) {
              FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_conn_wifi_watch_video"));
            } else if (FreeWifiGuideView.this.mStyle == 2) {
              FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_conn_wifi_watch_video_free"));
            }
            FreeWifiGuideView.access$202(FreeWifiGuideView.this, 2);
          }
        }
        else
        {
          if (FreeWifiGuideView.this.mStyle == 1) {
            FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_sniffer_hint"));
          } else if (FreeWifiGuideView.this.mStyle == 2) {
            FreeWifiGuideView.this.mTvDesc.setText(TBSResources.getText("wifi_one_key_conn_watch_free"));
          }
          FreeWifiGuideView.access$202(FreeWifiGuideView.this, 1);
        }
        FreeWifiGuideView.this.showWifiIcon();
      }
    };
    initSelf();
    initContent();
  }
  
  private void initContent()
  {
    this.mContentView = new LinearLayout(getContext())
    {
      public boolean onTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        if (Build.VERSION.SDK_INT >= 11) {
          if (paramAnonymousMotionEvent.getAction() == 0) {
            setAlpha(0.5F);
          } else if (1 == paramAnonymousMotionEvent.getAction()) {
            setAlpha(1.0F);
          }
        }
        return super.onTouchEvent(paramAnonymousMotionEvent);
      }
    };
    int i = this.mStyle;
    if (i == 1)
    {
      localObject = new ViewGroup.LayoutParams(-2, -1);
      this.mContentView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mContentView.setOrientation(0);
      this.mContentView.setGravity(16);
      i = TBSResources.getDimensionPixelSize("wifi_dp_10");
      this.mContentView.setPadding(i, 0, i, 0);
      this.mContentView.setBackgroundDrawable(TBSResources.getDrawable("wifi_sniffer_bg"));
      localObject = new ImageView(getContext());
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams.rightMargin = TBSResources.getDimensionPixelSize("wifi_dp_4");
      ((ImageView)localObject).setLayoutParams(localLayoutParams);
      ((ImageView)localObject).setScaleType(ImageView.ScaleType.CENTER);
      ((ImageView)localObject).setImageDrawable(TBSResources.getDrawable("wifi_sniffer"));
      this.mContentView.addView((View)localObject);
    }
    else if (i == 2)
    {
      localObject = new ViewGroup.LayoutParams(-1, -1);
      this.mContentView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mContentView.setOrientation(0);
      this.mContentView.setGravity(17);
      this.mContentView.setBackgroundDrawable(TBSResources.getDrawable("wifi_sniffer_banner_bg"));
    }
    this.mContentView.setOnClickListener(this);
    this.mTvDesc = new TextView(getContext());
    Object localObject = new LinearLayout.LayoutParams(-2, -2);
    this.mTvDesc.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mTvDesc.setGravity(17);
    this.mTvDesc.setTextColor(-1);
    i = this.mStyle;
    if (i == 1) {
      this.mTvDesc.setTextSize(0, TBSResources.getDimensionPixelSize("wifi_dp_12"));
    } else if (i == 2) {
      this.mTvDesc.setTextSize(0, TBSResources.getDimensionPixelSize("wifi_dp_13"));
    }
    this.mContentView.addView(this.mTvDesc);
  }
  
  private void initSelf()
  {
    int i = this.mStyle;
    ViewGroup.LayoutParams localLayoutParams;
    if (i == 1) {
      localLayoutParams = new ViewGroup.LayoutParams(-2, TBSResources.getDimensionPixelSize("wifi_dp_25"));
    } else if (i == 2) {
      localLayoutParams = new ViewGroup.LayoutParams(-1, TBSResources.getDimensionPixelSize("wifi_dp_34"));
    } else {
      localLayoutParams = null;
    }
    setLayoutParams(localLayoutParams);
    setOrientation(0);
    setGravity(17);
    setBackgroundColor(0);
  }
  
  void hideWifiIcon()
  {
    if (this.mContentView.getParent() != null) {
      removeView(this.mContentView);
    }
  }
  
  public void onClick(View paramView)
  {
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF007");
    switch (this.mUiType)
    {
    default: 
      break;
    case 3: 
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF009");
      break;
    case 2: 
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF008");
    }
    WifiBusinessUtils.activateOrInst4Wifi(getContext(), 1, this.mActionListener);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    WifiScanner.getInstance().removeQbSeekListener(this);
  }
  
  public void onFreeWifiFound(int paramInt)
  {
    WifiScanner.getInstance().removeQbSeekListener(this);
    this.mUiHandler.removeMessages(1);
    Message localMessage = this.mUiHandler.obtainMessage();
    localMessage.what = 1;
    if (paramInt > 0) {
      localMessage.arg1 = paramInt;
    }
    localMessage.sendToTarget();
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWindowVisibilityChanged() visibility: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
    super.onWindowVisibilityChanged(paramInt);
    if (paramInt == 0) {
      requestWifi();
    }
  }
  
  public void requestWifi()
  {
    Object localObject = ContextHolder.getAppContext();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("requestWifi() isWifiQbInstalled:");
    localStringBuilder.append(WifiBusinessUtils.isWifiQbInstalled((Context)localObject));
    localStringBuilder.toString();
    if (WifiBusinessUtils.isWifiQbInstalled((Context)localObject))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("requestWifi() isWifiShow_QB:");
      ((StringBuilder)localObject).append(PublicSettingManager.getInstance().isWifiShow_QB());
      ((StringBuilder)localObject).toString();
      if (PublicSettingManager.getInstance().isWifiShow_QB()) {}
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("requestWifi() isWifiShow_NOQB:");
      ((StringBuilder)localObject).append(PublicSettingManager.getInstance().isWifiShow_NOQB());
      ((StringBuilder)localObject).toString();
      if (!PublicSettingManager.getInstance().isWifiShow_NOQB()) {
        return;
      }
    }
    localObject = Apn.getActiveNetworkInfo(false);
    if (localObject == null)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF038");
      return;
    }
    int i = ((NetworkInfo)localObject).getType();
    if (i == 0)
    {
      if (!WifiUtils.isWifiEnabled())
      {
        this.mUiHandler.removeMessages(1);
        this.mUiHandler.sendEmptyMessage(1);
        return;
      }
      if (WifiBusinessUtils.isNewWifiQbInstalled(ContextHolder.getAppContext()))
      {
        WifiScanner.getInstance().seekThruNewQbCheck(this);
        return;
      }
      this.mUiHandler.removeMessages(1);
      this.mUiHandler.sendEmptyMessage(1);
      return;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("requestWifi() 不处于移动网络 type: ");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF039");
  }
  
  public void setWifiGuideActionListener(IWifiGuideActionListener paramIWifiGuideActionListener)
  {
    this.mActionListener = paramIWifiGuideActionListener;
  }
  
  void showWifiIcon()
  {
    if (this.mContentView.getParent() == null)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF004");
      switch (this.mUiType)
      {
      default: 
        break;
      case 3: 
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF006");
        break;
      case 2: 
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF005");
        break;
      case 1: 
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF041");
      }
      addView(this.mContentView);
    }
  }
  
  public static abstract interface IWifiGuideActionListener
  {
    public abstract void jumpTo(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\FreeWifiGuideView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */