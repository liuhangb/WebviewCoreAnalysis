package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.ReaderCallbackAction;
import com.tencent.mtt.external.reader.internal.ReaderFileStatistic;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuManager;

public class ReaderController
{
  Context jdField_a_of_type_AndroidContentContext = null;
  ReaderCallbackAction jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction;
  ReaderCheck jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = null;
  ReaderContentView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView = null;
  ReaderCoreWrapper jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper = null;
  ReaderMenuManager jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager = null;
  ReaderPipe jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = null;
  ReaderWindowGesture jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWindowGesture = null;
  ReaderFileStatistic jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic = null;
  MenuManager jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = null;
  private String jdField_a_of_type_JavaLangString = null;
  private String b = null;
  
  public ReaderController(Context paramContext, ReaderCheck paramReaderCheck)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = paramReaderCheck;
  }
  
  private ReaderCheck.CheckCallback a()
  {
    new ReaderCheck.CheckCallback()
    {
      public void onCheckEvent(int paramAnonymousInt1, int paramAnonymousInt2, Object paramAnonymousObject)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onCheckEvent type:");
        localStringBuilder.append(paramAnonymousInt1);
        localStringBuilder.append(" error:");
        localStringBuilder.append(paramAnonymousInt2);
        localStringBuilder.append(" reader:");
        boolean bool;
        if (paramAnonymousObject == null) {
          bool = true;
        } else {
          bool = false;
        }
        localStringBuilder.append(bool);
        LogUtils.d("ReaderController", localStringBuilder.toString());
        if ((paramAnonymousInt1 == 0) && (paramAnonymousObject != null))
        {
          if (ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck != null) {
            ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.setView(null);
          }
          ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().setIReader(paramAnonymousObject);
          ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.load(ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.getReaderPath());
          return;
        }
        if (ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe != null) {
          ReaderController.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe.send(7, Integer.valueOf(paramAnonymousInt2));
        }
      }
    };
  }
  
  static ReaderTypeView a(Context paramContext, String paramString, FrameLayout paramFrameLayout, ReaderController paramReaderController)
  {
    paramContext = (ReaderTypeView)ReaderViewCreator.getInstance().create(paramContext, paramString);
    if (paramContext != null)
    {
      paramContext.setFrameLayout(paramFrameLayout);
      if ((paramContext instanceof ReaderDefaultView)) {
        ((ReaderDefaultView)paramContext).a(paramReaderController);
      }
    }
    return paramContext;
  }
  
  private void c()
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(4, null);
    }
  }
  
  private void d()
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(5, null);
    }
  }
  
  void a()
  {
    c();
  }
  
  void a(int paramInt)
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(3, Integer.valueOf(paramInt));
    }
  }
  
  void a(int paramInt, Object paramObject)
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(paramInt, paramObject);
    }
  }
  
  void a(Object paramObject)
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(6, paramObject);
    }
  }
  
  public void active()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView;
    if (localObject != null)
    {
      ((ReaderContentView)localObject).getFrameLayout().setVisibility(0);
      localObject = (ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView();
      ((ReaderDefaultView)localObject).a(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager);
      ((ReaderDefaultView)localObject).a(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager, true);
      ((ReaderDefaultView)localObject).active();
    }
  }
  
  public boolean askCanGoback()
  {
    ReaderDefaultView localReaderDefaultView = (ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView();
    if (localReaderDefaultView != null) {
      return localReaderDefaultView.askCanGoback();
    }
    return false;
  }
  
  public void attachCallbackAction(ReaderCallbackAction paramReaderCallbackAction)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = paramReaderCallbackAction;
  }
  
  public void attachMenu(ReaderMenuManager paramReaderMenuManager)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager = paramReaderMenuManager;
    ((ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView()).a(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager);
  }
  
  public void attachMenu(MenuManager paramMenuManager)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = paramMenuManager;
    ((ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView()).a(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager, true);
  }
  
  public void attachPipe(ReaderPipe paramReaderPipe)
  {
    ReaderDefaultView localReaderDefaultView = (ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView();
    if (localReaderDefaultView != null) {
      localReaderDefaultView.a(paramReaderPipe);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = paramReaderPipe;
  }
  
  public void attachStatistic(ReaderFileStatistic paramReaderFileStatistic)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic = paramReaderFileStatistic;
  }
  
  public void attachWindowGesture(ReaderWindowGesture paramReaderWindowGesture) {}
  
  void b()
  {
    d();
  }
  
  void b(int paramInt)
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(1, Integer.valueOf(paramInt));
    }
  }
  
  void b(Object paramObject)
  {
    ReaderPipe localReaderPipe = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe;
    if (localReaderPipe != null) {
      localReaderPipe.send(2, paramObject);
    }
  }
  
  public void closeReader()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction;
    if (localObject != null)
    {
      ((ReaderCallbackAction)localObject).destroy();
      this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager;
    if (localObject != null)
    {
      ((ReaderMenuManager)localObject).destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localObject != null)
    {
      ((MenuManager)localObject).destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck;
    if (localObject != null)
    {
      ((ReaderCheck)localObject).cancel();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.close();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper;
    if (localObject != null)
    {
      ((ReaderCoreWrapper)localObject).unload();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView;
    if (localObject != null)
    {
      ((ReaderContentView)localObject).destroy();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView = null;
    }
  }
  
  public FrameLayout createReaderView(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper == null)
    {
      ReaderContentView localReaderContentView = (ReaderContentView)ReaderViewCreator.getInstance().create(this.jdField_a_of_type_AndroidContentContext, 1);
      localReaderContentView.create();
      localReaderContentView.setContentWidth(paramInt1);
      localReaderContentView.setContentHeight(paramInt2);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView = localReaderContentView;
      paramString1 = new ReaderCore(this.jdField_a_of_type_AndroidContentContext, paramString1, paramString2, localReaderContentView);
      paramString1.setReaderView(a(this.jdField_a_of_type_AndroidContentContext, paramString2, localReaderContentView.getFrameLayout(), this));
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper = new ReaderCoreWrapper(paramString1, this);
      paramString1 = localReaderContentView;
    }
    else
    {
      paramString1 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView;
    }
    if (paramString1 != null) {
      return paramString1.getFrameLayout();
    }
    return null;
  }
  
  public void deactive()
  {
    ReaderContentView localReaderContentView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView;
    if (localReaderContentView != null)
    {
      localReaderContentView.getFrameLayout().setVisibility(4);
      ((ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView()).deactive();
    }
  }
  
  public void doCallBackEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    ReaderCallbackAction localReaderCallbackAction = this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction;
    if (localReaderCallbackAction != null) {
      localReaderCallbackAction.doCallBackEvent(paramInt, paramObject1, paramObject2);
    }
  }
  
  public void doCommand(int paramInt, Object paramObject1, Object paramObject2)
  {
    ReaderCoreWrapper localReaderCoreWrapper = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper;
    if (localReaderCoreWrapper != null) {
      localReaderCoreWrapper.getReaderCore().doAction(paramInt, paramObject1, paramObject2);
    }
  }
  
  public String getFileExt()
  {
    return this.b;
  }
  
  public String getFilePath()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public int getTitleBarHeight()
  {
    ReaderCoreWrapper localReaderCoreWrapper = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper;
    if (localReaderCoreWrapper != null) {
      return localReaderCoreWrapper.getTitleBarHeight();
    }
    return 0;
  }
  
  public boolean isBarAnimation()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.isBarAnimating();
  }
  
  public int isBarShow()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.isBarShow();
  }
  
  public boolean isDocumentTop()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().isDocumentTop();
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().onSizeChanged(paramInt1, paramInt2);
  }
  
  public void openReader()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck != null)
    {
      ReaderCheck.CheckCallback localCheckCallback = a();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.setCallback(localCheckCallback);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.check();
    }
  }
  
  public void openReader(ReaderConfig paramReaderConfig)
  {
    if (paramReaderConfig == null) {
      return;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().jdField_a_of_type_JavaLangString = paramReaderConfig.tempPath;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView().setConfig(paramReaderConfig);
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck != null)
    {
      paramReaderConfig = a();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.setCallback(paramReaderConfig);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.check();
    }
  }
  
  public void setAdView(View paramView)
  {
    ReaderCoreWrapper localReaderCoreWrapper = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper;
    if (localReaderCoreWrapper != null) {
      localReaderCoreWrapper.setAdView(paramView);
    }
  }
  
  public void setColor(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().setColor(paramInt1, paramInt2, paramInt3, paramBoolean);
    ReaderDefaultView localReaderDefaultView = (ReaderDefaultView)this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.getReaderCore().getReaderView();
    if (localReaderDefaultView != null) {
      localReaderDefaultView.notifySkinChanged();
    }
  }
  
  public void setTitleBarHeight(int paramInt)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper != null)
    {
      if (paramInt > 0)
      {
        ReaderContentView localReaderContentView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView;
        if ((localReaderContentView != null) && (localReaderContentView.getFrameLayout() != null)) {
          this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderContentView.getFrameLayout().setBackgroundColor(0);
        }
      }
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCoreWrapper.setTitleBarHeight(paramInt);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */