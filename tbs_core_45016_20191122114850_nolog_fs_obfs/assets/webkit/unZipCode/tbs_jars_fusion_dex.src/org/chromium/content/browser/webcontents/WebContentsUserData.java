package org.chromium.content.browser.webcontents;

import java.util.Map;
import org.chromium.content_public.browser.WebContents;

public final class WebContentsUserData
{
  private final Object jdField_a_of_type_JavaLangObject;
  
  private WebContentsUserData(Object paramObject)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject;
  }
  
  public static <T> T fromWebContents(WebContents paramWebContents, Class<T> paramClass, UserDataFactory<T> paramUserDataFactory)
  {
    WebContentsImpl localWebContentsImpl = (WebContentsImpl)paramWebContents;
    Object localObject1 = localWebContentsImpl.a();
    Object localObject2 = null;
    if (localObject1 == null) {
      return null;
    }
    WebContentsUserData localWebContentsUserData = (WebContentsUserData)((Map)localObject1).get(paramClass);
    localObject1 = localWebContentsUserData;
    if (localWebContentsUserData == null)
    {
      localObject1 = localWebContentsUserData;
      if (paramUserDataFactory != null)
      {
        paramWebContents = paramUserDataFactory.create(paramWebContents);
        if ((!jdField_a_of_type_Boolean) && (!paramClass.isInstance(paramWebContents))) {
          throw new AssertionError();
        }
        localWebContentsImpl.a(paramClass, new WebContentsUserData(paramWebContents));
        localObject1 = localWebContentsImpl.a(paramClass);
      }
    }
    paramWebContents = (WebContents)localObject2;
    if (localObject1 != null) {
      paramWebContents = ((WebContentsUserData)localObject1).jdField_a_of_type_JavaLangObject;
    }
    return paramWebContents;
  }
  
  public static abstract interface UserDataFactory<T>
  {
    public abstract T create(WebContents paramWebContents);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\webcontents\WebContentsUserData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */