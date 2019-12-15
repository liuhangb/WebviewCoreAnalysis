package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.internal.ReaderFileStatistic;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuConstants;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj.CommandCallback;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj.MenuActionListener;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuManager;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;

public class ReaderDefaultView
  extends ReaderTypeView
  implements MenuEventObj.MenuActionListener
{
  private static String jdField_a_of_type_JavaLangString = "ReaderDefaultView";
  Context jdField_a_of_type_AndroidContentContext = null;
  private ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  ReaderPipe jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = null;
  MenuEventObj.CommandCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback = null;
  boolean jdField_a_of_type_Boolean = false;
  IReaderEvent jdField_b_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
  private String jdField_b_of_type_JavaLangString = null;
  private int e = -1;
  protected ReaderFeatureWrapper mFeatureView = null;
  public ReaderController mReaderController = null;
  
  public ReaderDefaultView(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.mFeatureView = new ReaderFeatureWrapper(paramContext);
    d();
    setEvent(this.jdField_b_of_type_ComTencentMttExternalReaderBaseIReaderEvent);
    f();
  }
  
  private void a(int paramInt)
  {
    ReaderController localReaderController = this.mReaderController;
    if (localReaderController != null)
    {
      if (localReaderController.a == null) {
        return;
      }
      this.mReaderController.a.setErrCode(paramInt);
      this.mReaderController.a.setOpenFinished();
      if (paramInt != 0) {
        switch (paramInt)
        {
        default: 
          this.mReaderController.a.setOpenResult(4);
          break;
        case -100: 
          this.mReaderController.a.setNeedPassword(true);
          this.mReaderController.a.setOpenResult(9);
          break;
        case -101: 
          this.mReaderController.a.setOpenResult(10);
          break;
        }
      } else {
        this.mReaderController.a.setOpenResult(1);
      }
      this.mReaderController.a.addToStatManager(false);
      return;
    }
  }
  
  private void f()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        Object localObject;
        if (19 == paramAnonymousMessage.what)
        {
          int i = ((Integer)paramAnonymousMessage.obj).intValue();
          ReaderDefaultView.a(ReaderDefaultView.this, i);
          if (i != 0)
          {
            if (ReaderDefaultView.this.mReaderController != null)
            {
              if ((-100 == i) && (ReaderDefaultView.this.a(312)) && (ReaderDefaultView.this.a())) {
                return;
              }
              ReaderDefaultView.this.mReaderController.b(i);
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("ReaderDefaultView:onMessage:NOTIFY_ERRORCODE so err:");
              ((StringBuilder)localObject).append(i);
              localObject = ((StringBuilder)localObject).toString();
              ReaderDefaultView.this.mReaderController.a.doReport("ReaderDefaultView:onMessage", 4, (String)localObject);
            }
            TBSStatManager.getInstance().userBehaviorStatistics("AHNG821");
          }
          else
          {
            if ((ReaderDefaultView.a(ReaderDefaultView.this) != null) && (!ReaderDefaultView.a(ReaderDefaultView.this).equalsIgnoreCase("pdf")))
            {
              ReaderDefaultView.this.mFeatureView.initMenu(ReaderDefaultView.this.mReaderConfig);
              ReaderDefaultView.this.mFeatureView.menuFocus();
            }
            TBSStatManager.getInstance().userBehaviorStatistics("AHNG820");
          }
          ReaderDefaultView.this.e();
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("NOTIFY_ERRORCODE,errorCode=");
          ((StringBuilder)localObject).append(i);
          LogUtils.d("ReaderDefaultView", ((StringBuilder)localObject).toString());
          ReaderDefaultView.this.mReaderController.doCallBackEvent(paramAnonymousMessage.what, Integer.valueOf(i), null);
          ReaderDefaultView.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(3014, 5000);
          return;
        }
        if (3014 == paramAnonymousMessage.what)
        {
          ReaderDefaultView.this.c();
          LogUtils.d("ReaderDefaultView", "READER_EVENT_STAT_REQ :1");
          TbsWupManager.getInstance().doMultiWupRequest();
          LogUtils.d("ReaderDefaultView", "READER_EVENT_STAT_REQ :2");
          if (ReaderDefaultView.this.mReaderController != null) {
            ReaderDefaultView.this.mReaderController.a(11, null);
          }
        }
        else if (12 == paramAnonymousMessage.what)
        {
          localObject = ReaderDefaultView.this;
          ((ReaderDefaultView)localObject).jdField_a_of_type_Boolean = true;
          if (!((ReaderDefaultView)localObject).onOtherEvent(12, null, null))
          {
            ReaderDefaultView.this.a();
            ReaderDefaultView.this.mReaderController.doCallBackEvent(paramAnonymousMessage.what, null, null);
            ReaderDefaultView.this.mFeatureView.updateScreenLockTime();
          }
        }
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  void a()
  {
    ReaderController localReaderController = this.mReaderController;
    if (localReaderController != null) {
      localReaderController.a();
    }
  }
  
  final void a(float paramFloat)
  {
    this.mFeatureView.updateScrollView(paramFloat);
  }
  
  void a(Bundle paramBundle, Object paramObject)
  {
    if (paramBundle != null)
    {
      ReaderController localReaderController = this.mReaderController;
      if (localReaderController == null) {
        return;
      }
      localReaderController.a("reader_decryption_txt");
      doCommand(309, paramBundle, paramObject);
      return;
    }
  }
  
  void a(ReaderController paramReaderController)
  {
    this.mReaderController = paramReaderController;
    ReaderController localReaderController = this.mReaderController;
    if (localReaderController != null) {
      this.jdField_b_of_type_JavaLangString = localReaderController.getFileExt();
    }
    this.mFeatureView.a(paramReaderController);
  }
  
  void a(ReaderMenuManager paramReaderMenuManager)
  {
    if (paramReaderMenuManager != null) {
      paramReaderMenuManager.setEventHandler(this.jdField_b_of_type_ComTencentMttExternalReaderBaseIReaderEvent);
    }
    this.mFeatureView.a(paramReaderMenuManager);
  }
  
  void a(ReaderPipe paramReaderPipe)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = paramReaderPipe;
  }
  
  void a(MenuEventObj paramMenuEventObj)
  {
    paramMenuEventObj.executeAction(this.jdField_a_of_type_AndroidContentContext, this);
  }
  
  void a(MenuManager paramMenuManager, boolean paramBoolean)
  {
    if (paramMenuManager != null) {
      paramMenuManager.setEventHandler(this.jdField_b_of_type_ComTencentMttExternalReaderBaseIReaderEvent);
    }
    this.mFeatureView.a(paramMenuManager, true);
  }
  
  boolean a()
  {
    if (this.mReaderController == null) {
      return false;
    }
    a();
    Bundle localBundle = new Bundle();
    this.mReaderController.doCallBackEvent(5060, null, localBundle);
    if (!localBundle.getBoolean("encrypt_support", false)) {
      return false;
    }
    this.mReaderController.doCallBackEvent(5061, null, null);
    return true;
  }
  
  boolean a(int paramInt)
  {
    Bundle localBundle = new Bundle();
    if (312 == paramInt)
    {
      doCommand(312, null, localBundle);
      if ((localBundle.containsKey("encryptEnabled")) && (localBundle.getBoolean("encryptEnabled"))) {
        return true;
      }
    }
    return false;
  }
  
  public void active()
  {
    LogUtils.d(jdField_a_of_type_JavaLangString, "active ...");
  }
  
  public boolean askCanGoback()
  {
    return false;
  }
  
  void b()
  {
    ReaderController localReaderController = this.mReaderController;
    if (localReaderController != null) {
      localReaderController.b();
    }
  }
  
  void c()
  {
    MenuEventObj localMenuEventObj = this.mFeatureView.getSnapshotEventObj();
    if (localMenuEventObj != null)
    {
      localMenuEventObj.setActionListener(this);
      localMenuEventObj.executeAction(this.jdField_a_of_type_AndroidContentContext, this);
    }
  }
  
  public int create()
  {
    int i = this.mReaderConfig.select_bar_with;
    int j = this.mReaderConfig.select_bar_height;
    Bitmap localBitmap2 = TBSResources.getBitmap(this.mReaderConfig.select_holder_resouce_id);
    boolean bool = true;
    if (localBitmap2 != null)
    {
      if ((localBitmap2.getWidth() != i) || (localBitmap2.getHeight() != j)) {
        try
        {
          Bitmap localBitmap3 = Bitmap.createScaledBitmap(localBitmap2, i, j, true);
          Bitmap localBitmap1 = localBitmap3;
          if (localBitmap3.equals(localBitmap2)) {
            break label123;
          }
          localBitmap2.recycle();
          localBitmap1 = localBitmap3;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          this.mReaderController.a.doReportException("create", localOutOfMemoryError);
          localOutOfMemoryError.printStackTrace();
        }
      } else {
        localObject = localBitmap2;
      }
    }
    else {
      localObject = null;
    }
    label123:
    doCommand(31, localObject, null);
    Object localObject = new Bundle();
    doCommand(59, null, localObject);
    i = ((Bundle)localObject).getInt("screen_timeOut");
    if (i == 0)
    {
      i = 120000;
      bool = false;
    }
    else if (i <= 0)
    {
      bool = false;
    }
    this.mFeatureView.setScreenLockTime(i, bool);
    return 0;
  }
  
  final void d()
  {
    this.jdField_b_of_type_ComTencentMttExternalReaderBaseIReaderEvent = new IReaderEvent()
    {
      public boolean onUiEvent(int paramAnonymousInt, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Object localObject;
        switch (paramAnonymousInt)
        {
        default: 
          switch (paramAnonymousInt)
          {
          default: 
            int j = 0;
            switch (paramAnonymousInt)
            {
            default: 
              switch (paramAnonymousInt)
              {
              default: 
                switch (paramAnonymousInt)
                {
                default: 
                  switch (paramAnonymousInt)
                  {
                  default: 
                    switch (paramAnonymousInt)
                    {
                    default: 
                      switch (paramAnonymousInt)
                      {
                      default: 
                        boolean bool;
                        switch (paramAnonymousInt)
                        {
                        default: 
                          switch (paramAnonymousInt)
                          {
                          default: 
                            bool = ReaderDefaultView.this.onOtherEvent(paramAnonymousInt, paramAnonymousObject1, paramAnonymousObject2);
                            if ((!bool) && (ReaderDefaultView.this.mReaderController != null))
                            {
                              localObject = new Bundle();
                              ((Bundle)localObject).putInt("action_type", paramAnonymousInt);
                              if ((paramAnonymousObject1 instanceof Bundle))
                              {
                                ((Bundle)localObject).putBundle("action_args", (Bundle)paramAnonymousObject1);
                              }
                              else if (paramAnonymousObject1 != null)
                              {
                                ((Bundle)localObject).putBoolean("action_has_args", true);
                                break label450;
                              }
                              paramAnonymousObject1 = paramAnonymousObject2;
                              ReaderDefaultView.this.mReaderController.doCallBackEvent(5049, localObject, paramAnonymousObject1);
                            }
                            return bool;
                          case 10005: 
                            if ((paramAnonymousObject1 instanceof MenuEventObj))
                            {
                              ReaderDefaultView.this.a((MenuEventObj)paramAnonymousObject1);
                              return true;
                            }
                            break;
                          case 5062: 
                            ReaderDefaultView.this.a((Bundle)paramAnonymousObject1, paramAnonymousObject2);
                            return true;
                          case 5032: 
                            ReaderDefaultView.this.mFeatureView.activityPause();
                            return true;
                          case 3015: 
                            ReaderDefaultView.this.mReaderController.doCallBackEvent(5070, paramAnonymousObject1, paramAnonymousObject2);
                            ReaderDefaultView.this.doCommand(33, null, null);
                            return true;
                          case 3012: 
                            ReaderDefaultView.this.mReaderController.doCallBackEvent(5004, paramAnonymousObject1, null);
                            return true;
                          case 1000: 
                            ReaderDefaultView.this.mFeatureView.loadUrl((String)paramAnonymousObject1);
                            return true;
                          case 312: 
                            if (ReaderDefaultView.this.mReaderController != null)
                            {
                              ReaderDefaultView.this.mReaderController.doCallBackEvent(5060, paramAnonymousObject1, paramAnonymousObject2);
                              return true;
                            }
                            break;
                          case 220: 
                            ReaderDefaultView.this.a();
                            if (ReaderDefaultView.this.mReaderController != null)
                            {
                              ReaderDefaultView.this.mReaderController.doCallBackEvent(5063, paramAnonymousObject1, paramAnonymousObject2);
                              return true;
                            }
                            break;
                          case 210: 
                            if ((paramAnonymousObject1 instanceof Bundle))
                            {
                              paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
                              paramAnonymousInt = ((Bundle)paramAnonymousObject1).getInt("errcode");
                              paramAnonymousObject1 = ((Bundle)paramAnonymousObject1).getString("errmsg");
                              ReaderDefaultView.this.mReaderController.a.doReport("ReaderDefaultView:onUiEvent", paramAnonymousInt, (String)paramAnonymousObject1);
                              return true;
                            }
                            break;
                          case 5: 
                            ReaderDefaultView.this.mFeatureView.destroyWebview();
                            return true;
                          }
                          break;
                        case 6003: 
                          localObject = new Bundle();
                          ((Bundle)localObject).putString("query", "mode");
                          Bundle localBundle = new Bundle();
                          ReaderDefaultView.this.doCommand(10003, localObject, localBundle);
                          if (localBundle.containsKey("fitscreen"))
                          {
                            bool = localBundle.getBoolean("fitscreen") ^ true;
                            ((Bundle)localObject).clear();
                            ((Bundle)localObject).putBoolean("mode", bool);
                            ReaderDefaultView.this.doCommand(10003, localObject, null);
                            if ((paramAnonymousObject2 instanceof Bundle))
                            {
                              ((Bundle)paramAnonymousObject2).putBoolean("mode", bool);
                              return true;
                            }
                          }
                          else
                          {
                            ReaderDefaultView.this.onOtherEvent(paramAnonymousInt, paramAnonymousObject1, paramAnonymousObject2);
                            return true;
                          }
                          break;
                        case 6002: 
                          if ((paramAnonymousObject2 instanceof Bundle))
                          {
                            paramAnonymousObject1 = new Bundle();
                            ((Bundle)paramAnonymousObject1).putString("query", "support");
                            ReaderDefaultView.this.doCommand(10003, paramAnonymousObject1, paramAnonymousObject2);
                            return true;
                          }
                          break;
                        case 6001: 
                          if ((paramAnonymousObject2 instanceof Bundle))
                          {
                            paramAnonymousObject1 = new Bundle();
                            ReaderDefaultView.this.doCommand(311, null, paramAnonymousObject1);
                            if ((((Bundle)paramAnonymousObject1).containsKey("findSupport")) && (((Bundle)paramAnonymousObject1).getBoolean("findSupport")))
                            {
                              ((Bundle)paramAnonymousObject2).putBoolean("support", true);
                              return true;
                            }
                          }
                          break;
                        }
                        break;
                      case 5041: 
                        ReaderDefaultView.this.mFeatureView.clearMatches();
                        ReaderDefaultView.this.doCommand(310, Boolean.valueOf(false), null);
                        return true;
                      case 5040: 
                        ReaderDefaultView.this.mFeatureView.findNext(false);
                        ReaderDefaultView.this.doCommand(322, null, null);
                        return true;
                      case 5039: 
                        ReaderDefaultView.this.mFeatureView.findNext(true);
                        ReaderDefaultView.this.doCommand(323, null, null);
                        return true;
                      case 5038: 
                        ReaderDefaultView.this.doCommand(310, Boolean.valueOf(true), null);
                        localObject = ReaderDefaultView.this.mFeatureView;
                        paramAnonymousObject1 = (String)paramAnonymousObject1;
                        ((ReaderFeatureWrapper)localObject).findAllAsync((String)paramAnonymousObject1);
                        localObject = new Bundle();
                        ((Bundle)localObject).putString("find_text", (String)paramAnonymousObject1);
                        ReaderDefaultView.this.doCommand(321, localObject, paramAnonymousObject2);
                        return true;
                      }
                      break;
                    case 3009: 
                      ReaderDefaultView.this.doCommand(33, null, null);
                      return true;
                    case 3008: 
                      if (((paramAnonymousObject1 instanceof String)) && (!TextUtils.isEmpty((String)paramAnonymousObject1)))
                      {
                        ReaderDefaultView.this.mReaderController.doCallBackEvent(5003, paramAnonymousObject1, null);
                        ReaderDefaultView.this.doCommand(33, null, null);
                        return true;
                      }
                      ReaderDefaultView.this.doCommand(10005, null, null);
                      return true;
                    }
                    break;
                  case 3006: 
                    paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
                    if (paramAnonymousObject1 != null)
                    {
                      ReaderDefaultView.this.mFeatureView.setScrollRatio(((Bundle)paramAnonymousObject1).getFloat("ratio"));
                      return true;
                    }
                    break;
                  case 3005: 
                    ReaderDefaultView.this.onScaleEnd(0.0F);
                    return true;
                  case 3004: 
                    ReaderDefaultView.this.onScaleBegin();
                    return true;
                  case 3003: 
                    paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
                    if (paramAnonymousObject1 != null)
                    {
                      ReaderDefaultView.this.a(((Bundle)paramAnonymousObject1).getFloat("ratio"));
                      return true;
                    }
                    break;
                  case 3002: 
                    ReaderDefaultView.this.onScrollEnd();
                    return true;
                  case 3001: 
                    ReaderDefaultView.this.onScrollBegin();
                    return true;
                  case 3000: 
                    paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
                    int i;
                    if (paramAnonymousObject1 != null)
                    {
                      paramAnonymousInt = ((Bundle)paramAnonymousObject1).getInt("tap_x");
                      i = ((Bundle)paramAnonymousObject1).getInt("tap_y");
                    }
                    else
                    {
                      i = 0;
                      paramAnonymousInt = j;
                    }
                    if (ReaderDefaultView.this.jdField_a_of_type_Boolean)
                    {
                      ReaderDefaultView.this.onSingleTap(paramAnonymousInt, i);
                      return true;
                    }
                    break;
                  }
                  break;
                case 102: 
                  if ((paramAnonymousObject1 instanceof Bundle))
                  {
                    paramAnonymousObject1 = ((Bundle)paramAnonymousObject1).getString("copy", "");
                    if (!TextUtils.isEmpty((CharSequence)paramAnonymousObject1))
                    {
                      ReaderDefaultView.this.mReaderController.doCallBackEvent(5003, paramAnonymousObject1, null);
                      ReaderDefaultView.this.doCommand(33, null, null);
                      return true;
                    }
                  }
                  break;
                case 101: 
                  if (ReaderDefaultView.this.mReaderController != null)
                  {
                    ReaderDefaultView.this.mReaderController.doCallBackEvent(6000, paramAnonymousObject1, paramAnonymousObject2);
                    return true;
                  }
                  break;
                case 100: 
                  localObject = new StringBuilder();
                  ((StringBuilder)localObject).append("NOTIFY_Common_Info args: ");
                  ((StringBuilder)localObject).append(paramAnonymousObject1);
                  LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
                  if (ReaderDefaultView.this.mReaderController != null)
                  {
                    ReaderDefaultView.this.mReaderController.doCallBackEvent(5049, paramAnonymousObject1, paramAnonymousObject2);
                    return true;
                  }
                  break;
                }
                break;
              case 51: 
                paramAnonymousObject2 = new StringBuilder();
                ((StringBuilder)paramAnonymousObject2).append("NOTIFY_AddContentAll args: ");
                ((StringBuilder)paramAnonymousObject2).append(paramAnonymousObject1);
                LogUtils.d("allenhan", ((StringBuilder)paramAnonymousObject2).toString());
                if (ReaderDefaultView.this.mReaderController != null)
                {
                  ReaderDefaultView.this.mReaderController.a(12, null);
                  return true;
                }
                break;
              case 50: 
                paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
                ((Bundle)paramAnonymousObject1).getFloat("touch_x");
                float f = ((Bundle)paramAnonymousObject1).getFloat("touch_y");
                ReaderDefaultView.this.mReaderController.doCallBackEvent(5046, new Integer(-(int)f), null);
                if (ReaderDefaultView.this.mFeatureView != null)
                {
                  ReaderDefaultView.this.mFeatureView.updateScreenLockTime();
                  return true;
                }
                break;
              }
              break;
            case 23: 
              ReaderDefaultView.this.mFeatureView.destroySelectView();
              return true;
            case 22: 
              ReaderDefaultView.this.mFeatureView.enterSelect();
              return true;
            case 21: 
              ReaderDefaultView.this.mFeatureView.setSelectViewHidden(true);
              return true;
            case 20: 
              LogUtils.d("ReaderDefaultView", "onUiEvent SHOW_SELECTMENU ...");
              paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
              if (paramAnonymousObject1 != null)
              {
                paramAnonymousObject2 = new Rect(((Bundle)paramAnonymousObject1).getInt("left"), ((Bundle)paramAnonymousObject1).getInt("top"), ((Bundle)paramAnonymousObject1).getInt("right"), ((Bundle)paramAnonymousObject1).getInt("bottom"));
                ReaderDefaultView.this.doCommand(32, paramAnonymousObject1, null);
                localObject = ((Bundle)paramAnonymousObject1).getString("selectContent");
                if (ReaderDefaultView.this.mFeatureView.a == null) {
                  ReaderDefaultView.this.mFeatureView.createSelectView(ReaderDefaultView.this.mParentLayout, (Bundle)paramAnonymousObject1);
                }
                ReaderDefaultView.this.mFeatureView.a.setEvent(ReaderDefaultView.this.b);
                ReaderDefaultView.this.mFeatureView.setSelectViewHidden(false);
                ReaderDefaultView.this.mFeatureView.showSelectView((Rect)paramAnonymousObject2, (String)localObject);
                return true;
              }
              if (((paramAnonymousObject2 instanceof Boolean)) && (!((Boolean)paramAnonymousObject2).booleanValue()))
              {
                ReaderDefaultView.this.mFeatureView.setSelectViewHidden(true);
                return true;
              }
              break;
            case 19: 
              ReaderDefaultView.this.onOtherEvent(paramAnonymousInt, paramAnonymousObject1, paramAnonymousObject2);
              ReaderDefaultView.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramAnonymousInt, paramAnonymousObject2);
              return true;
            }
            break;
          case 12: 
            ReaderDefaultView.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramAnonymousInt, null);
            return true;
          case 11: 
            ((Bundle)paramAnonymousObject2).putBoolean("gesture", ReaderDefaultView.this.mFeatureView.MenuisShow() ^ true);
            return true;
          }
          break;
        case 3: 
          ReaderDefaultView.this.mFeatureView.addJSI(paramAnonymousObject1, (String)paramAnonymousObject2);
          return true;
        case 2: 
          ReaderDefaultView.this.mFeatureView.createWebview(ReaderDefaultView.this.mParentLayout, ReaderDefaultView.this.b);
          ReaderDefaultView.this.b();
          return true;
        case 1: 
          label450:
          if (!ReaderDefaultView.this.onOtherEvent(paramAnonymousInt, paramAnonymousObject1, paramAnonymousObject2))
          {
            ReaderDefaultView.this.mFeatureView.createToastView(ReaderDefaultView.this.mParentLayout);
            localObject = (Bundle)paramAnonymousObject1;
            ReaderDefaultView.a(ReaderDefaultView.this, ((Bundle)localObject).getInt("cur_page"));
            paramAnonymousInt = ((Bundle)localObject).getInt("page_count");
            ReaderDefaultView.this.mFeatureView.SetToastText(ReaderDefaultView.a(ReaderDefaultView.this), paramAnonymousInt);
            ReaderDefaultView.this.mFeatureView.hideToastView();
            if (ReaderDefaultView.this.mReaderController != null) {
              ReaderDefaultView.this.mReaderController.doCallBackEvent(5048, paramAnonymousObject1, paramAnonymousObject2);
            }
          }
          break;
        }
        return true;
      }
    };
  }
  
  public void deactive()
  {
    LogUtils.d(jdField_a_of_type_JavaLangString, "deactive ...");
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    a(null);
    a(null);
    a(null, true);
    this.jdField_a_of_type_AndroidContentContext = null;
    setEvent(null);
    this.mFeatureView.destroy();
  }
  
  void e()
  {
    Object localObject1 = this.jdField_a_of_type_AndroidContentContext;
    if (localObject1 == null) {
      return;
    }
    if ((Configuration.getInstance(((Context)localObject1).getApplicationContext()).isDebugMiniQB()) && (getFrameLayout() != null))
    {
      Object localObject2 = new FrameLayout.LayoutParams(-1, -2);
      ((FrameLayout.LayoutParams)localObject2).topMargin = 200;
      localObject1 = new TextView(this.jdField_a_of_type_AndroidContentContext);
      ((TextView)localObject1).setGravity(17);
      ((TextView)localObject1).setTextColor(-65536);
      ((TextView)localObject1).setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
      getFrameLayout().addView((View)localObject1, (ViewGroup.LayoutParams)localObject2);
      localObject2 = new Bundle();
      doCommand(10000, null, localObject2);
      localObject2 = ((Bundle)localObject2).getString("version");
      ((TextView)localObject1).setText(String.format("file:%s_%s,", new Object[] { this.jdField_b_of_type_JavaLangString, localObject2 }));
      ((TextView)localObject1).bringToFront();
    }
  }
  
  public int getCurPageID()
  {
    return this.e;
  }
  
  public void notifySkinChanged()
  {
    this.mFeatureView.notifySkinChanged();
  }
  
  public void onLaunchQB()
  {
    String str = this.mFeatureView.getSnapshotStatistics(MenuConstants.MENU_STATISTICS_INTO_BROWSER);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, launch QB:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  public void onMenuEventExecurte()
  {
    String str = this.mFeatureView.getSnapshotStatistics(MenuConstants.MENU_STATISTICS_INTO_DIALOG);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, On click:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  protected boolean onOtherEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    if (paramInt != 3013) {
      return false;
    }
    this.mFeatureView.menuHide(false);
    return true;
  }
  
  public void onRequestInstallQB()
  {
    String str = this.mFeatureView.getSnapshotStatistics(MenuConstants.MENU_STATISTICS_INTO_DIALOG);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, show dialog:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  protected void onScaleBegin()
  {
    this.mFeatureView.showScrollBar();
  }
  
  protected void onScaleEnd(float paramFloat)
  {
    this.mFeatureView.hideScrollBar();
  }
  
  protected void onScrollBegin()
  {
    this.mFeatureView.createScrollView(this.mParentLayout);
    this.mFeatureView.showScrollBar();
    if (this.mFeatureView.MenuisShow())
    {
      this.mFeatureView.menuHide(true);
      this.mFeatureView.showToastView(2);
    }
    this.mFeatureView.updateScreenLockTime();
  }
  
  protected void onScrollEnd()
  {
    this.mFeatureView.hideScrollBar();
    this.mFeatureView.updateScreenLockTime();
  }
  
  protected void onSingleTap(int paramInt1, int paramInt2)
  {
    if (this.mFeatureView.isAnimating()) {
      return;
    }
    if (this.mFeatureView.MenuisShow())
    {
      this.mFeatureView.showToastView(2);
      this.mFeatureView.menuHide(true);
    }
    else
    {
      this.mFeatureView.showToastView(1);
      this.mFeatureView.menuShow(true);
    }
    this.mFeatureView.updateScreenLockTime();
  }
  
  public void setMenuCommandCallback(MenuEventObj.CommandCallback paramCommandCallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback = paramCommandCallback;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderDefaultView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */