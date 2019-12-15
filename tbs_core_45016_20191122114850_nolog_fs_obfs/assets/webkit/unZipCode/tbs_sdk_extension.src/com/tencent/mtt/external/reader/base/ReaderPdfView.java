package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.internal.ReaderServiceProxy;
import com.tencent.mtt.external.reader.internal.ReaderServiceProxy.ICallback;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj.CommandCallback;
import com.tencent.mtt.external.reader.utils.AppEngine;
import java.util.ArrayList;

public class ReaderPdfView
  extends ReaderDefaultView
{
  private static String jdField_a_of_type_JavaLangString = "ReaderPdfView";
  private ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderServiceProxy.ICallback jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback = null;
  private ReaderMessage jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  boolean jdField_b_of_type_Boolean = false;
  boolean c = false;
  
  public ReaderPdfView(Context paramContext)
  {
    super(paramContext);
  }
  
  private void a(Message paramMessage)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback != null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback.onCommandDone(this.jdField_a_of_type_AndroidContentContext, this, paramMessage);
    }
  }
  
  private void f()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        if (19 == paramAnonymousMessage.what)
        {
          ((Integer)paramAnonymousMessage.obj).intValue();
          return;
        }
        if (203 == paramAnonymousMessage.what)
        {
          int i = Integer.parseInt(paramAnonymousMessage.obj.toString());
          if (ReaderPdfView.this.mReaderController != null) {
            ReaderPdfView.this.mReaderController.a(i);
          }
        }
        else
        {
          Object localObject;
          if (201 == paramAnonymousMessage.what)
          {
            paramAnonymousMessage = ((Bundle)paramAnonymousMessage.obj).getString("pdf_content_path");
            localObject = new Bundle();
            ((Bundle)localObject).putString("path", paramAnonymousMessage);
            ((Bundle)localObject).putString("ext", "txt");
            if (ReaderPdfView.this.mReaderController != null) {
              ReaderPdfView.this.mReaderController.b(localObject);
            }
            paramAnonymousMessage = ReaderPdfView.this;
            paramAnonymousMessage.b = true;
            paramAnonymousMessage.mViewMode = 1;
            return;
          }
          if ((202 != paramAnonymousMessage.what) && (207 != paramAnonymousMessage.what))
          {
            if (204 == paramAnonymousMessage.what)
            {
              paramAnonymousMessage = (Bundle)paramAnonymousMessage.obj;
              ReaderPdfView.this.c = paramAnonymousMessage.getBoolean("bflag");
              localObject = ReaderPdfView.this;
              ((ReaderPdfView)localObject).a(((ReaderPdfView)localObject).c);
              ReaderPdfView.this.mReaderController.doCallBackEvent(5037, paramAnonymousMessage, null);
              return;
            }
            if (205 == paramAnonymousMessage.what)
            {
              if (ReaderPdfView.this.mReaderController != null)
              {
                if (!ReaderPdfView.this.mReaderConfig.show_menu)
                {
                  localObject = new Bundle();
                  ((Bundle)localObject).putInt("REQ_FEATURE_ID", 4011);
                  ((Bundle)localObject).putParcelableArrayList("outlinedata", (ArrayList)paramAnonymousMessage.obj);
                  ReaderPdfView.this.mReaderController.doCallBackEvent(5008, localObject, null);
                  return;
                }
                ReaderPdfView.this.startService();
              }
              ReaderPdfView.a(ReaderPdfView.this, paramAnonymousMessage);
              return;
            }
            if (206 == paramAnonymousMessage.what) {
              return;
            }
            if (307 == paramAnonymousMessage.what)
            {
              LogUtils.d(ReaderPdfView.a(), "PDF_SEEK_PROGRESS ...");
              ReaderPdfView.this.doCommand(307, paramAnonymousMessage.obj, null);
            }
          }
          else
          {
            ReaderPdfView.this.a();
            if (ReaderPdfView.this.mReaderController != null) {
              ReaderPdfView.this.mReaderController.b(paramAnonymousMessage.what);
            }
          }
        }
      }
    };
    this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  void a(boolean paramBoolean)
  {
    this.mReaderConfig.pdf_contain_outline = paramBoolean;
    this.mFeatureView.initMenu(this.mReaderConfig);
  }
  
  public void active()
  {
    this.mViewMode = 0;
    super.active();
  }
  
  public int create()
  {
    super.create();
    f();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback = new ReaderServiceProxy.ICallback()
    {
      public void onCallback(int paramAnonymousInt, Bundle paramAnonymousBundle)
      {
        ReaderPdfView.a(ReaderPdfView.this).send(307, paramAnonymousBundle);
      }
    };
    startService();
    return 0;
  }
  
  public void deactive()
  {
    super.deactive();
  }
  
  public void destroy()
  {
    super.destroy();
    ReaderServiceProxy.getInstance().destroy(AppEngine.getInstance().getApplicationContext());
    this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
  }
  
  protected boolean onOtherEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    if (paramInt != 12)
    {
      if (paramInt != 19)
      {
        if (paramInt != 5015)
        {
          if (paramInt != 5036) {
            switch (paramInt)
            {
            default: 
              return false;
            case 207: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, null);
              break;
            case 206: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject1);
              break;
            case 205: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject1);
              break;
            case 204: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject1);
              break;
            case 203: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject1);
              break;
            case 202: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, null);
              break;
            case 201: 
              this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject1);
              break;
            }
          } else {
            doCommand(308, null, null);
          }
        }
        else
        {
          this.mViewMode = 0;
          if (!this.jdField_b_of_type_Boolean)
          {
            if (this.mReaderController != null) {
              this.mReaderController.a("reader_pdf_tips_relayout");
            }
            doCommand(201, null, null);
          }
          else
          {
            paramObject1 = new Bundle();
            if (this.mReaderController != null) {
              this.mReaderController.b(paramObject1);
            }
          }
        }
      }
      else {
        this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(paramInt, paramObject2);
      }
    }
    else
    {
      if (this.mViewMode == 1)
      {
        a();
        if (!this.jdField_b_of_type_Boolean)
        {
          if (this.mReaderController != null) {
            this.mReaderController.a("reader_pdf_tips_relayout");
          }
          doCommand(201, null, null);
        }
        else
        {
          paramObject1 = new Bundle();
          if (this.mReaderController != null) {
            this.mReaderController.b(paramObject1);
          }
        }
      }
      else
      {
        a();
      }
      this.mFeatureView.MenuisShow();
      if (this.mReaderController != null) {
        this.mReaderController.doCallBackEvent(12, null, null);
      }
    }
    return true;
  }
  
  protected void onScaleBegin()
  {
    if (this.mFeatureView.MenuisShow()) {
      this.mFeatureView.menuHide(true);
    }
  }
  
  protected void onScrollBegin()
  {
    if (this.mFeatureView.MenuisShow())
    {
      this.mFeatureView.menuHide(true);
      this.mFeatureView.showToastView(2);
    }
  }
  
  public void startService()
  {
    LogUtils.d(jdField_a_of_type_JavaLangString, "startService ...");
    if ((this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback != null) && (AppEngine.getInstance().isSetServicePkg()))
    {
      ReaderServiceProxy.getInstance().setCallback(this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback);
      ReaderServiceProxy.getInstance().startServerWhileNotRun(AppEngine.getInstance().getApplicationContext());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderPdfView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */