package org.chromium.content_public.browser;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.services.service_manager.InterfaceRegistry;

public abstract interface InterfaceRegistrar<ParamType>
{
  public abstract void registerInterfaces(InterfaceRegistry paramInterfaceRegistry, ParamType paramParamType);
  
  public static class Registry<ParamType>
  {
    private static Registry<Context> jdField_a_of_type_OrgChromiumContent_publicBrowserInterfaceRegistrar$Registry;
    private static Registry<WebContents> b;
    private static Registry<RenderFrameHost> c;
    private List<InterfaceRegistrar<ParamType>> jdField_a_of_type_JavaUtilList = new ArrayList();
    
    public static void a(InterfaceRegistrar<Context> paramInterfaceRegistrar)
    {
      if (jdField_a_of_type_OrgChromiumContent_publicBrowserInterfaceRegistrar$Registry == null) {
        jdField_a_of_type_OrgChromiumContent_publicBrowserInterfaceRegistrar$Registry = new Registry();
      }
      jdField_a_of_type_OrgChromiumContent_publicBrowserInterfaceRegistrar$Registry.b(paramInterfaceRegistrar);
    }
    
    public static void a(InterfaceRegistry paramInterfaceRegistry)
    {
      Registry localRegistry = jdField_a_of_type_OrgChromiumContent_publicBrowserInterfaceRegistrar$Registry;
      if (localRegistry == null) {
        return;
      }
      localRegistry.a(paramInterfaceRegistry, ContextUtils.getApplicationContext());
    }
    
    private void a(InterfaceRegistry paramInterfaceRegistry, ParamType paramParamType)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext()) {
        ((InterfaceRegistrar)localIterator.next()).registerInterfaces(paramInterfaceRegistry, paramParamType);
      }
    }
    
    public static void a(InterfaceRegistry paramInterfaceRegistry, RenderFrameHost paramRenderFrameHost)
    {
      Registry localRegistry = c;
      if (localRegistry == null) {
        return;
      }
      localRegistry.a(paramInterfaceRegistry, paramRenderFrameHost);
    }
    
    public static void a(InterfaceRegistry paramInterfaceRegistry, WebContents paramWebContents)
    {
      Registry localRegistry = b;
      if (localRegistry == null) {
        return;
      }
      localRegistry.a(paramInterfaceRegistry, paramWebContents);
    }
    
    private void b(InterfaceRegistrar<ParamType> paramInterfaceRegistrar)
    {
      this.jdField_a_of_type_JavaUtilList.add(paramInterfaceRegistrar);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\InterfaceRegistrar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */