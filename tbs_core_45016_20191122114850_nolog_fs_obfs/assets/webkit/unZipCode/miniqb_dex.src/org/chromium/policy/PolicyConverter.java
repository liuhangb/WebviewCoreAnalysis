package org.chromium.policy;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.Iterator;
import java.util.Set;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@JNINamespace("policy::android")
public class PolicyConverter
{
  private long jdField_a_of_type_Long;
  
  private PolicyConverter(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
  
  @TargetApi(21)
  private JSONArray a(Bundle[] paramArrayOfBundle)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    int j = paramArrayOfBundle.length;
    int i = 0;
    while (i < j)
    {
      localJSONArray.put(a(paramArrayOfBundle[i]));
      i += 1;
    }
    return localJSONArray;
  }
  
  @TargetApi(21)
  private JSONObject a(Bundle paramBundle)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject2 = paramBundle.get(str);
      Object localObject1 = localObject2;
      if ((localObject2 instanceof Bundle)) {
        localObject1 = a((Bundle)localObject2);
      }
      localObject2 = localObject1;
      if ((localObject1 instanceof Bundle[])) {
        localObject2 = a((Bundle[])localObject1);
      }
      localJSONObject.put(str, JSONObject.wrap(localObject2));
    }
    return localJSONObject;
  }
  
  @VisibleForTesting
  @CalledByNative
  static PolicyConverter create(long paramLong)
  {
    return new PolicyConverter(paramLong);
  }
  
  @CalledByNative
  private void onNativeDestroyed()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void a(String paramString, Object paramObject)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    if ((paramObject instanceof Boolean))
    {
      nativeSetPolicyBoolean(this.jdField_a_of_type_Long, paramString, ((Boolean)paramObject).booleanValue());
      return;
    }
    if ((paramObject instanceof String))
    {
      nativeSetPolicyString(this.jdField_a_of_type_Long, paramString, (String)paramObject);
      return;
    }
    if ((paramObject instanceof Integer))
    {
      nativeSetPolicyInteger(this.jdField_a_of_type_Long, paramString, ((Integer)paramObject).intValue());
      return;
    }
    if ((paramObject instanceof String[]))
    {
      nativeSetPolicyStringArray(this.jdField_a_of_type_Long, paramString, (String[])paramObject);
      return;
    }
    if (Build.VERSION.SDK_INT >= 21) {
      if ((paramObject instanceof Bundle)) {
        paramObject = (Bundle)paramObject;
      }
    }
    try
    {
      nativeSetPolicyString(this.jdField_a_of_type_Long, paramString, a((Bundle)paramObject).toString());
      return;
    }
    catch (JSONException paramString)
    {
      StringBuilder localStringBuilder;
      return;
    }
    if ((paramObject instanceof Bundle[])) {
      paramObject = (Bundle[])paramObject;
    }
    try
    {
      nativeSetPolicyString(this.jdField_a_of_type_Long, paramString, a((Bundle[])paramObject).toString());
      return;
    }
    catch (JSONException paramString) {}
    if (jdField_a_of_type_Boolean) {
      return;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid setting ");
    localStringBuilder.append(paramObject);
    localStringBuilder.append(" for key ");
    localStringBuilder.append(paramString);
    throw new AssertionError(localStringBuilder.toString());
  }
  
  @VisibleForTesting
  native void nativeSetPolicyBoolean(long paramLong, String paramString, boolean paramBoolean);
  
  @VisibleForTesting
  native void nativeSetPolicyInteger(long paramLong, String paramString, int paramInt);
  
  @VisibleForTesting
  native void nativeSetPolicyString(long paramLong, String paramString1, String paramString2);
  
  @VisibleForTesting
  native void nativeSetPolicyStringArray(long paramLong, String paramString, String[] paramArrayOfString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\policy\PolicyConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */