package com.tencent.smtt.webkit.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.net.http.SslCertificate.DName;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.webkit.SmttResource;
import java.util.Date;

public class d
  extends e
{
  private static final int jdField_a_of_type_Int = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_ssl_magin_middle", "dimen"));
  private TextView jdField_a_of_type_AndroidWidgetTextView;
  private SslError jdField_a_of_type_ComTencentSmttExportExternalInterfacesSslError;
  private SslErrorHandler jdField_a_of_type_ComTencentSmttExportExternalInterfacesSslErrorHandler;
  private e jdField_a_of_type_ComTencentSmttWebkitUiE;
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean;
  private String jdField_b_of_type_JavaLangString;
  private boolean jdField_b_of_type_Boolean;
  private String c;
  
  public d(Context paramContext, SslErrorHandler paramSslErrorHandler, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    super(paramContext, i, paramBoolean2);
    this.jdField_a_of_type_ComTencentSmttExportExternalInterfacesSslErrorHandler = paramSslErrorHandler;
    this.jdField_a_of_type_ComTencentSmttExportExternalInterfacesSslError = paramSslError;
    this.jdField_a_of_type_JavaLangString = paramString2;
    this.jdField_b_of_type_JavaLangString = paramString3;
    this.c = paramString1;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_b_of_type_Boolean = paramBoolean3;
  }
  
  private static String a(SslError paramSslError, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramSslError.hasError(3)) && (paramBoolean1)) {
      return SmttResource.getString("x5_ssl_error_info_untrusted_chain_error", "string");
    }
    if (paramSslError.hasError(3)) {
      return SmttResource.getString("x5_ssl_error_info_untrusted", "string");
    }
    if (paramSslError.hasError(2)) {
      return SmttResource.getString("x5_ssl_error_info_mismatch", "string");
    }
    if (paramSslError.hasError(1)) {
      return SmttResource.getString("x5_ssl_error_info_expired", "string");
    }
    if (paramSslError.hasError(0)) {
      return SmttResource.getString("x5_ssl_error_info_not_yet_valid", "string");
    }
    if ((paramSslError.hasError(4)) && (!paramBoolean2)) {
      return SmttResource.getString("x5_ssl_error_info_expired", "string");
    }
    return SmttResource.getString("x5_ssl_error_info_unknown_error", "string");
  }
  
  private String a(Date paramDate)
  {
    if (paramDate == null) {
      return "";
    }
    paramDate = android.text.format.DateFormat.getDateFormat(this.mContext).format(paramDate);
    if (paramDate == null) {
      return "";
    }
    return paramDate;
  }
  
  public void a()
  {
    boolean bool = this.jdField_a_of_type_Boolean;
    if (bool) {
      setTitleText(SmttResource.getString("x5_ssl_error_info_phone_date_error", "string"));
    } else {
      setTitleText(a(this.jdField_a_of_type_ComTencentSmttExportExternalInterfacesSslError, this.jdField_b_of_type_Boolean, bool));
    }
    setTitleTextGravity(3);
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(this.mContext);
    Object localObject = new LinearLayout.LayoutParams(-2, -2);
    ((LinearLayout.LayoutParams)localObject).setMargins(MARGIN_LEFT, jdField_a_of_type_Int, MARGIN_LEFT, MARGIN_TITLE_TOP);
    this.jdField_a_of_type_AndroidWidgetTextView.setText(SmttResource.getString("x5_ssl_check_cert_info", "string"));
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(16.0F);
    this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    localObject = this.jdField_a_of_type_AndroidWidgetTextView;
    int i;
    if (this.mIsNight) {
      i = TEXT_BLACK_NIGHT_COLOR;
    } else {
      i = TEXT_BLACK_COLOR;
    }
    ((TextView)localObject).setTextColor(i);
    addToContentArea(this.jdField_a_of_type_AndroidWidgetTextView);
    setBottomButtonMarginTop(0);
    if (this.jdField_a_of_type_Boolean)
    {
      localObject = SmttResource.getString("x5_ssl_correct_date", "string");
      if (this.mIsNight) {
        i = TEXT_BLUE_NIGHT_COLOR;
      } else {
        i = TEXT_BLUE_COLOR;
      }
      setButton(10002, (CharSequence)localObject, i, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          try
          {
            d.this.mContext.startActivity(new Intent("android.settings.DATE_SETTINGS"));
            return;
          }
          catch (Exception paramAnonymousDialogInterface)
          {
            paramAnonymousDialogInterface.printStackTrace();
          }
        }
      }, false);
    }
    if ((!this.jdField_a_of_type_Boolean) || (!com.tencent.smtt.webkit.e.a().n()))
    {
      localObject = SmttResource.getString("x5_ssl_continue_access", "string");
      if (this.mIsNight) {
        i = TEXT_RED_NIGHT_COLOR;
      } else {
        i = TEXT_RED_COLOR;
      }
      setButton(10000, (CharSequence)localObject, i, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          d.a(d.this).proceed();
        }
      });
    }
    localObject = SmttResource.getString("x5_cancel", "string");
    if (this.mIsNight) {
      i = TEXT_BLACK_NIGHT_COLOR;
    } else {
      i = TEXT_BLACK_COLOR;
    }
    setButton(10001, (CharSequence)localObject, i, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        d.a(d.this).cancel();
      }
    });
    this.jdField_a_of_type_AndroidWidgetTextView.setOnClickListener(new View.OnClickListener()
    {
      @TargetApi(8)
      public void onClick(View paramAnonymousView)
      {
        if (d.a(d.this) == null)
        {
          paramAnonymousView = d.this;
          d.a(paramAnonymousView, new e(paramAnonymousView.mContext, 0, d.this.mIsNight));
          d.a(d.this).setTitleText(SmttResource.getString("x5_ssl_cert_info_title", "string"));
          d.a(d.this).setTitleMargin(e.MARGIN_SUBTITLE_BOTTOM, SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_ssl_cert_magin_middle", "dimen")));
          Object localObject1 = d.a(d.this).getCertificate().getIssuedTo();
          int i;
          if (localObject1 != null)
          {
            paramAnonymousView = d.a(d.this);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(SmttResource.getString("x5_ssl_issued_to", "string"));
            ((StringBuilder)localObject2).append(((SslCertificate.DName)localObject1).getCName());
            localObject1 = ((StringBuilder)localObject2).toString();
            if (d.this.mIsNight) {
              i = e.TEXT_BLACK_NIGHT_COLOR;
            } else {
              i = e.TEXT_BLACK_COLOR;
            }
            paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          }
          else
          {
            paramAnonymousView = d.a(d.this);
            localObject1 = SmttResource.getString("x5_ssl_issued_to", "string");
            if (d.this.mIsNight) {
              i = e.TEXT_BLACK_NIGHT_COLOR;
            } else {
              i = e.TEXT_BLACK_COLOR;
            }
            paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          }
          localObject1 = d.a(d.this).getCertificate().getIssuedBy();
          if (localObject1 != null)
          {
            paramAnonymousView = d.a(d.this);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(SmttResource.getString("x5_ssl_issued_by", "string"));
            ((StringBuilder)localObject2).append(((SslCertificate.DName)localObject1).getCName());
            localObject1 = ((StringBuilder)localObject2).toString();
            if (d.this.mIsNight) {
              i = e.TEXT_BLACK_NIGHT_COLOR;
            } else {
              i = e.TEXT_BLACK_COLOR;
            }
            paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          }
          else
          {
            paramAnonymousView = d.a(d.this);
            localObject1 = SmttResource.getString("x5_ssl_issued_by", "string");
            if (d.this.mIsNight) {
              i = e.TEXT_BLACK_NIGHT_COLOR;
            } else {
              i = e.TEXT_BLACK_COLOR;
            }
            paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          }
          if (Build.VERSION.SDK_INT < 8)
          {
            localObject1 = d.a(d.this).getCertificate().getValidNotBefore();
            paramAnonymousView = d.a(d.this).getCertificate().getValidNotAfter();
          }
          else
          {
            paramAnonymousView = d.this;
            localObject1 = d.a(paramAnonymousView, d.a(paramAnonymousView).getCertificate().getValidNotBeforeDate());
            paramAnonymousView = d.this;
            paramAnonymousView = d.a(paramAnonymousView, d.a(paramAnonymousView).getCertificate().getValidNotAfterDate());
          }
          Object localObject2 = d.a(d.this);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(SmttResource.getString("x5_ssl_validity_period", "string"));
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append(SmttResource.getString("x5_ssl_validity_period_to", "string"));
          localStringBuilder.append(paramAnonymousView);
          paramAnonymousView = localStringBuilder.toString();
          if (d.this.mIsNight) {
            i = e.TEXT_BLACK_NIGHT_COLOR;
          } else {
            i = e.TEXT_BLACK_COLOR;
          }
          ((e)localObject2).addToContentArea(paramAnonymousView, i, 16, e.MARGIN_LEFT, 0, 3);
          paramAnonymousView = d.a(d.this);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(SmttResource.getString("x5_ssl_finger_print", "string"));
          ((StringBuilder)localObject1).append(d.a(d.this));
          localObject1 = ((StringBuilder)localObject1).toString();
          if (d.this.mIsNight) {
            i = e.TEXT_BLACK_NIGHT_COLOR;
          } else {
            i = e.TEXT_BLACK_COLOR;
          }
          paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          paramAnonymousView = d.a(d.this);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(SmttResource.getString("x5_ssl_finger_print_256", "string"));
          ((StringBuilder)localObject1).append(d.b(d.this));
          localObject1 = ((StringBuilder)localObject1).toString();
          if (d.this.mIsNight) {
            i = e.TEXT_BLACK_NIGHT_COLOR;
          } else {
            i = e.TEXT_BLACK_COLOR;
          }
          paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          paramAnonymousView = d.a(d.this);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(SmttResource.getString("x5_ssl_url", "string"));
          ((StringBuilder)localObject1).append(d.c(d.this));
          localObject1 = ((StringBuilder)localObject1).toString();
          if (d.this.mIsNight) {
            i = e.TEXT_BLACK_NIGHT_COLOR;
          } else {
            i = e.TEXT_BLACK_COLOR;
          }
          paramAnonymousView.addToContentArea((String)localObject1, i, 16, e.MARGIN_LEFT, 0, 3);
          d.a(d.this).setBottomButtonMarginTop(e.MARGIN_SUBTITLE_BOTTOM);
          d.a(d.this).setButton(10000, SmttResource.getString("x5_ok", "string"), new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              d.a(d.this).dismiss();
              d.this.show();
            }
          });
        }
        d.this.dismiss();
        d.a(d.this).show();
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */