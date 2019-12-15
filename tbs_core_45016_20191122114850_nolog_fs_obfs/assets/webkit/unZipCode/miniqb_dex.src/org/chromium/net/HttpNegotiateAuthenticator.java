package org.chromium.net;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import java.io.IOException;
import org.chromium.base.ApplicationStatus;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("net::android")
public class HttpNegotiateAuthenticator
{
  private Bundle jdField_a_of_type_AndroidOsBundle;
  private final String jdField_a_of_type_JavaLangString;
  
  protected HttpNegotiateAuthenticator(String paramString)
  {
    if ((!jdField_a_of_type_Boolean) && (TextUtils.isEmpty(paramString))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  private void a(Context paramContext, Activity paramActivity, RequestData paramRequestData, String[] paramArrayOfString)
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 23) {
      bool = true;
    } else {
      bool = false;
    }
    String str;
    if (bool) {
      str = "android.permission.MANAGE_ACCOUNTS";
    } else {
      str = "android.permission.GET_ACCOUNTS";
    }
    if (a(paramContext, str, bool))
    {
      Log.e("net_auth", "ERR_MISCONFIGURED_AUTH_ENVIRONMENT: %s permission not granted. Aborting authentication", new Object[] { str });
      nativeSetResult(paramRequestData.jdField_a_of_type_Long, 65193, null);
      return;
    }
    paramRequestData.jdField_a_of_type_AndroidAccountsAccountManager.getAuthTokenByFeatures(this.jdField_a_of_type_JavaLangString, paramRequestData.jdField_a_of_type_JavaLangString, paramArrayOfString, paramActivity, null, paramRequestData.jdField_a_of_type_AndroidOsBundle, new GetTokenCallback(paramRequestData), new Handler(ThreadUtils.getUiThreadLooper()));
  }
  
  private void a(Context paramContext, RequestData paramRequestData, String[] paramArrayOfString)
  {
    if (a(paramContext, "android.permission.GET_ACCOUNTS", true))
    {
      Log.e("net_auth", "ERR_MISCONFIGURED_AUTH_ENVIRONMENT: GET_ACCOUNTS permission not granted. Aborting authentication.", new Object[0]);
      nativeSetResult(paramRequestData.jdField_a_of_type_Long, 65193, null);
      return;
    }
    paramRequestData.jdField_a_of_type_AndroidAccountsAccountManager.getAccountsByTypeAndFeatures(this.jdField_a_of_type_JavaLangString, paramArrayOfString, new GetAccountsCallback(paramRequestData), new Handler(ThreadUtils.getUiThreadLooper()));
  }
  
  private void a(Bundle paramBundle, RequestData paramRequestData)
  {
    this.jdField_a_of_type_AndroidOsBundle = paramBundle.getBundle("spnegoContext");
    int k = paramBundle.getInt("spnegoResult", 1);
    int j = -9;
    int i = j;
    switch (k)
    {
    default: 
      i = j;
      break;
    case 9: 
      i = 65207;
      break;
    case 8: 
      i = 65192;
      break;
    case 7: 
      i = 65195;
      break;
    case 6: 
      i = 65197;
      break;
    case 5: 
      i = 65198;
      break;
    case 4: 
      i = 65216;
      break;
    case 3: 
      i = 65194;
      break;
    case 2: 
      i = -3;
      break;
    case 0: 
      i = 0;
    }
    nativeSetResult(paramRequestData.jdField_a_of_type_Long, i, paramBundle.getString("authtoken"));
  }
  
  @VisibleForTesting
  @CalledByNative
  static HttpNegotiateAuthenticator create(String paramString)
  {
    return new HttpNegotiateAuthenticator(paramString);
  }
  
  @VisibleForTesting
  boolean a(Context paramContext, String paramString, boolean paramBoolean)
  {
    boolean bool = false;
    if ((paramBoolean) && (Build.VERSION.SDK_INT >= 23)) {
      return false;
    }
    paramBoolean = bool;
    if (paramContext.checkPermission(paramString, Process.myPid(), Process.myUid()) != 0) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  @VisibleForTesting
  @CalledByNative
  void getNextAuthToken(long paramLong, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (paramString1 == null)) {
      throw new AssertionError();
    }
    Context localContext = ContextUtils.getApplicationContext();
    RequestData localRequestData = new RequestData();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SPNEGO:HOSTBASED:");
    localStringBuilder.append(paramString1);
    localRequestData.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    localRequestData.jdField_a_of_type_AndroidAccountsAccountManager = AccountManager.get(localContext);
    localRequestData.jdField_a_of_type_Long = paramLong;
    paramString1 = new String[1];
    paramString1[0] = "SPNEGO";
    localRequestData.jdField_a_of_type_AndroidOsBundle = new Bundle();
    if (paramString2 != null) {
      localRequestData.jdField_a_of_type_AndroidOsBundle.putString("incomingAuthToken", paramString2);
    }
    if (this.jdField_a_of_type_AndroidOsBundle != null) {
      localRequestData.jdField_a_of_type_AndroidOsBundle.putBundle("spnegoContext", this.jdField_a_of_type_AndroidOsBundle);
    }
    localRequestData.jdField_a_of_type_AndroidOsBundle.putBoolean("canDelegate", paramBoolean);
    paramString2 = ApplicationStatus.getLastTrackedFocusedActivity();
    if (paramString2 == null)
    {
      a(localContext, localRequestData, paramString1);
      return;
    }
    a(localContext, paramString2, localRequestData, paramString1);
  }
  
  @VisibleForTesting
  native void nativeSetResult(long paramLong, int paramInt, String paramString);
  
  @VisibleForTesting
  class GetAccountsCallback
    implements AccountManagerCallback<Account[]>
  {
    private final HttpNegotiateAuthenticator.RequestData jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData;
    
    public GetAccountsCallback(HttpNegotiateAuthenticator.RequestData paramRequestData)
    {
      this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData = paramRequestData;
    }
    
    public void run(AccountManagerFuture<Account[]> paramAccountManagerFuture)
    {
      try
      {
        paramAccountManagerFuture = (Account[])paramAccountManagerFuture.getResult();
        if (paramAccountManagerFuture.length == 0)
        {
          HttpNegotiateAuthenticator.this.nativeSetResult(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_Long, 65195, null);
          return;
        }
        if (paramAccountManagerFuture.length > 1)
        {
          HttpNegotiateAuthenticator.this.nativeSetResult(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_Long, 65195, null);
          return;
        }
        if (HttpNegotiateAuthenticator.this.a(ContextUtils.getApplicationContext(), "android.permission.USE_CREDENTIALS", true))
        {
          Log.e("net_auth", "ERR_MISCONFIGURED_AUTH_ENVIRONMENT: USE_CREDENTIALS permission not granted. Aborting authentication.", new Object[0]);
          HttpNegotiateAuthenticator.this.nativeSetResult(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_Long, 65193, null);
          return;
        }
        HttpNegotiateAuthenticator.RequestData localRequestData = this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData;
        localRequestData.jdField_a_of_type_AndroidAccountsAccount = paramAccountManagerFuture[0];
        localRequestData.jdField_a_of_type_AndroidAccountsAccountManager.getAuthToken(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_AndroidAccountsAccount, this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_AndroidOsBundle, true, new HttpNegotiateAuthenticator.GetTokenCallback(HttpNegotiateAuthenticator.this, this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData), new Handler(ThreadUtils.getUiThreadLooper()));
        return;
      }
      catch (OperationCanceledException|AuthenticatorException|IOException paramAccountManagerFuture)
      {
        for (;;) {}
      }
      HttpNegotiateAuthenticator.this.nativeSetResult(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.jdField_a_of_type_Long, -9, null);
    }
  }
  
  @VisibleForTesting
  class GetTokenCallback
    implements AccountManagerCallback<Bundle>
  {
    private final HttpNegotiateAuthenticator.RequestData jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData;
    
    public GetTokenCallback(HttpNegotiateAuthenticator.RequestData paramRequestData)
    {
      this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData = paramRequestData;
    }
    
    public void run(final AccountManagerFuture<Bundle> paramAccountManagerFuture)
    {
      try
      {
        paramAccountManagerFuture = (Bundle)paramAccountManagerFuture.getResult();
        if (paramAccountManagerFuture.containsKey("intent"))
        {
          paramAccountManagerFuture = ContextUtils.getApplicationContext();
          X5ApiCompatibilityUtils.x5RegisterReceiver(paramAccountManagerFuture, new BroadcastReceiver()new IntentFilter
          {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
              X5ApiCompatibilityUtils.x5UnRegisterReceiver(paramAccountManagerFuture, this);
              HttpNegotiateAuthenticator.GetTokenCallback.a(HttpNegotiateAuthenticator.GetTokenCallback.this).jdField_a_of_type_AndroidAccountsAccountManager.getAuthToken(HttpNegotiateAuthenticator.GetTokenCallback.a(HttpNegotiateAuthenticator.GetTokenCallback.this).jdField_a_of_type_AndroidAccountsAccount, HttpNegotiateAuthenticator.GetTokenCallback.a(HttpNegotiateAuthenticator.GetTokenCallback.this).jdField_a_of_type_JavaLangString, HttpNegotiateAuthenticator.GetTokenCallback.a(HttpNegotiateAuthenticator.GetTokenCallback.this).jdField_a_of_type_AndroidOsBundle, true, new HttpNegotiateAuthenticator.GetTokenCallback(HttpNegotiateAuthenticator.this, HttpNegotiateAuthenticator.GetTokenCallback.a(HttpNegotiateAuthenticator.GetTokenCallback.this)), null);
            }
          }, new IntentFilter("android.accounts.LOGIN_ACCOUNTS_CHANGED"));
          return;
        }
        HttpNegotiateAuthenticator.a(HttpNegotiateAuthenticator.this, paramAccountManagerFuture, this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData);
        return;
      }
      catch (OperationCanceledException|AuthenticatorException|IOException paramAccountManagerFuture)
      {
        for (;;) {}
      }
      HttpNegotiateAuthenticator.this.nativeSetResult(this.jdField_a_of_type_OrgChromiumNetHttpNegotiateAuthenticator$RequestData.a, -9, null);
    }
  }
  
  static class RequestData
  {
    public long a;
    public Account a;
    public AccountManager a;
    public Bundle a;
    public String a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\HttpNegotiateAuthenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */