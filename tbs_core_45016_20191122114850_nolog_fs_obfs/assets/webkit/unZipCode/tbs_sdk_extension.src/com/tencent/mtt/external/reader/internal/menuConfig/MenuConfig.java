package com.tencent.mtt.external.reader.internal.menuConfig;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.utils.AppEngine;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuConfig
{
  private static String jdField_a_of_type_JavaLangString = "MenuConfig";
  ConfigCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback = null;
  a jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a = null;
  private JSONObject jdField_a_of_type_OrgJsonJSONObject = null;
  private String b = "";
  private final String c = "2015102301";
  
  private StateListDrawable a(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    localStateListDrawable.addState(new int[] { 16842910, 16842908 }, paramDrawable3);
    localStateListDrawable.addState(new int[] { 16842919, 16842910 }, paramDrawable2);
    localStateListDrawable.addState(new int[] { 16842908 }, paramDrawable3);
    localStateListDrawable.addState(new int[] { 16842919 }, paramDrawable2);
    localStateListDrawable.addState(new int[] { 16842910 }, paramDrawable1);
    localStateListDrawable.addState(new int[0], paramDrawable1);
    return localStateListDrawable;
  }
  
  private JSONObject a(String paramString)
  {
    Object localObject1 = jdField_a_of_type_JavaLangString;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("loadConfig ... config file path:");
    ((StringBuilder)localObject2).append(paramString);
    LogUtils.d((String)localObject1, ((StringBuilder)localObject2).toString());
    localObject2 = null;
    localObject1 = localObject2;
    for (;;)
    {
      try
      {
        localObject3 = new StringBuilder();
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(paramString);
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(File.separator);
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append("menuConfig.dat");
        localObject1 = localObject2;
        paramString = ((StringBuilder)localObject3).toString();
        localObject1 = localObject2;
        if (new File(paramString).exists())
        {
          localObject1 = localObject2;
          LogUtils.d(jdField_a_of_type_JavaLangString, "Download conf file exist, loading ...");
          localObject1 = localObject2;
          return b(paramString);
        }
        localObject1 = localObject2;
        paramString = new StringBuilder();
        localObject1 = localObject2;
        paramString.append(AppEngine.getInstance().getTempPath());
        localObject1 = localObject2;
        paramString.append(File.separator);
        localObject1 = localObject2;
        paramString.append("menuConfig.dat");
        localObject1 = localObject2;
        paramString = paramString.toString();
        localObject1 = localObject2;
        localObject3 = new File(paramString);
        localObject1 = localObject2;
        localObject4 = jdField_a_of_type_JavaLangString;
        localObject1 = localObject2;
        localStringBuilder = new StringBuilder();
        localObject1 = localObject2;
        localStringBuilder.append("Download conf file not exist, prepare load local conf:");
        localObject1 = localObject2;
        localStringBuilder.append(paramString);
        localObject1 = localObject2;
        LogUtils.d((String)localObject4, localStringBuilder.toString());
        localObject1 = localObject2;
        localObject2 = b(paramString);
        if (localObject2 != null)
        {
          localObject1 = localObject2;
          LogUtils.d(jdField_a_of_type_JavaLangString, "Load local conf down, check ver ...");
          paramString = "";
        }
      }
      catch (Exception paramString)
      {
        Object localObject3;
        Object localObject4;
        StringBuilder localStringBuilder;
        paramString.printStackTrace();
        localObject2 = jdField_a_of_type_JavaLangString;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("loadConfig Exception:");
        ((StringBuilder)localObject2).append(paramString.toString());
        ((StringBuilder)localObject2).toString();
      }
      try
      {
        localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_VERSION);
        paramString = (String)localObject1;
      }
      catch (Exception localException) {}
    }
    localObject1 = localObject2;
    LogUtils.d(jdField_a_of_type_JavaLangString, "No value for version !");
    localObject1 = localObject2;
    if (Integer.parseInt(paramString) >= Integer.parseInt("2015102301"))
    {
      localObject1 = localObject2;
      localObject3 = jdField_a_of_type_JavaLangString;
      localObject1 = localObject2;
      localObject4 = new StringBuilder();
      localObject1 = localObject2;
      ((StringBuilder)localObject4).append("Local config file verison is OK, ver:");
      localObject1 = localObject2;
      ((StringBuilder)localObject4).append(paramString);
      localObject1 = localObject2;
      LogUtils.d((String)localObject3, ((StringBuilder)localObject4).toString());
      return (JSONObject)localObject2;
    }
    localObject1 = localObject2;
    localObject4 = jdField_a_of_type_JavaLangString;
    localObject1 = localObject2;
    localStringBuilder = new StringBuilder();
    localObject1 = localObject2;
    localStringBuilder.append("Delete expired local config file, exist ver:");
    localObject1 = localObject2;
    localStringBuilder.append(paramString);
    localObject1 = localObject2;
    localStringBuilder.append(" new ver: ");
    localObject1 = localObject2;
    localStringBuilder.append("2015102301");
    localObject1 = localObject2;
    LogUtils.d((String)localObject4, localStringBuilder.toString());
    localObject1 = localObject2;
    ((File)localObject3).delete();
    localObject1 = localObject2;
    LogUtils.d(jdField_a_of_type_JavaLangString, "Gen local config file, ver:2015102301");
    localObject1 = localObject2;
    paramString = a();
    localObject1 = paramString;
    if (paramString != null)
    {
      localObject1 = paramString;
      if (!((File)localObject3).getParentFile().exists())
      {
        localObject1 = paramString;
        ((File)localObject3).getParentFile().mkdirs();
      }
      localObject1 = paramString;
      if (!((File)localObject3).exists())
      {
        localObject1 = paramString;
        ((File)localObject3).createNewFile();
      }
      localObject1 = paramString;
      localObject2 = new PrintStream((File)localObject3);
      localObject1 = paramString;
      ((PrintStream)localObject2).print(paramString.toString());
      localObject1 = paramString;
      ((PrintStream)localObject2).close();
      return paramString;
    }
    return (JSONObject)localObject1;
  }
  
  private JSONObject a(HashMap<String, String> paramHashMap)
  {
    try
    {
      localObject = new JSONObject();
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_BUTTON_INDEX)) {
        return null;
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_BUTTON_NAME)) {
        return null;
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_TEXT)) {
        return null;
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_SHOULD_SHOW)) {
        return null;
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_REQUIRED_QB_VERSION_MIN)) {
        return null;
      }
      a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_QB_VERSION_FILTER);
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_TEXT_SIZE)) {
        ((JSONObject)localObject).put(MenuConstants.MENU_TEXT_SIZE, "reader_fontsize_t3");
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_TEXT_COLOR)) {
        ((JSONObject)localObject).put(MenuConstants.MENU_TEXT_COLOR, "reader_select_text_color");
      }
      JSONObject localJSONObject = new JSONObject();
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_POS_LEFT)) {
        localJSONObject.put(MenuConstants.MENU_POS_LEFT, "reader_menu_btn_padding_left");
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_POS_TOP)) {
        localJSONObject.put(MenuConstants.MENU_POS_TOP, "reader_menu_btn_padding_top");
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_POS_RIGHT)) {
        localJSONObject.put(MenuConstants.MENU_POS_RIGHT, "reader_menu_btn_padding_right");
      }
      if (!a((JSONObject)localObject, paramHashMap, MenuConstants.MENU_POS_BOTTOM)) {
        localJSONObject.put(MenuConstants.MENU_POS_BOTTOM, "reader_menu_btn_padding_bottom");
      }
      ((JSONObject)localObject).put(MenuConstants.MENU_PADDING, localJSONObject);
      return (JSONObject)localObject;
    }
    catch (JSONException paramHashMap)
    {
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("genJsonButton Exception:");
      ((StringBuilder)localObject).append(paramHashMap.toString());
      ((StringBuilder)localObject).toString();
    }
    return null;
  }
  
  private boolean a(JSONObject paramJSONObject, HashMap<String, String> paramHashMap, String paramString)
    throws JSONException
  {
    if (!paramHashMap.containsKey(paramString))
    {
      paramJSONObject = jdField_a_of_type_JavaLangString;
      paramHashMap = new StringBuilder();
      paramHashMap.append("Not found params: ");
      paramHashMap.append(paramString);
      LogUtils.d(paramJSONObject, paramHashMap.toString());
      return false;
    }
    paramJSONObject.put(paramString, paramHashMap.get(paramString));
    return true;
  }
  
  /* Error */
  private JSONObject b(String paramString)
  {
    // Byte code:
    //   0: new 80	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 88	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: invokevirtual 92	java/io/File:exists	()Z
    //   11: ifne +38 -> 49
    //   14: getstatic 59	com/tencent/mtt/external/reader/internal/menuConfig/MenuConfig:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   17: astore_2
    //   18: new 61	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   25: astore_3
    //   26: aload_3
    //   27: ldc -1
    //   29: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: pop
    //   33: aload_3
    //   34: aload_1
    //   35: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_2
    //   40: aload_3
    //   41: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokestatic 78	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   47: aconst_null
    //   48: areturn
    //   49: new 61	java/lang/StringBuilder
    //   52: dup
    //   53: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   56: astore_2
    //   57: new 257	java/io/BufferedReader
    //   60: dup
    //   61: new 259	java/io/FileReader
    //   64: dup
    //   65: aload_1
    //   66: invokespecial 260	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   69: invokespecial 263	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   72: astore_1
    //   73: aload_1
    //   74: invokevirtual 266	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   77: astore_3
    //   78: aload_3
    //   79: ifnull +12 -> 91
    //   82: aload_2
    //   83: aload_3
    //   84: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: goto -15 -> 73
    //   91: new 116	org/json/JSONObject
    //   94: dup
    //   95: aload_2
    //   96: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   99: invokespecial 267	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   102: astore_2
    //   103: aload_1
    //   104: invokevirtual 268	java/io/BufferedReader:close	()V
    //   107: aload_2
    //   108: areturn
    //   109: astore_1
    //   110: aload_1
    //   111: invokevirtual 167	java/lang/Exception:printStackTrace	()V
    //   114: getstatic 59	com/tencent/mtt/external/reader/internal/menuConfig/MenuConfig:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   117: astore_2
    //   118: new 61	java/lang/StringBuilder
    //   121: dup
    //   122: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   125: astore_2
    //   126: aload_2
    //   127: ldc -87
    //   129: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload_2
    //   134: aload_1
    //   135: invokevirtual 170	java/lang/Exception:toString	()Ljava/lang/String;
    //   138: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload_2
    //   143: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: pop
    //   147: aconst_null
    //   148: areturn
    //   149: astore_1
    //   150: aconst_null
    //   151: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	MenuConfig
    //   0	152	1	paramString	String
    //   17	126	2	localObject1	Object
    //   25	59	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   0	47	109	java/lang/Exception
    //   49	73	109	java/lang/Exception
    //   73	78	109	java/lang/Exception
    //   82	88	109	java/lang/Exception
    //   91	107	109	java/lang/Exception
    //   73	78	149	java/lang/OutOfMemoryError
    //   82	88	149	java/lang/OutOfMemoryError
  }
  
  JSONObject a()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put(MenuConstants.MENU_VERSION, "2015102301");
      localObject1 = new JSONObject();
      Object localObject2 = new JSONObject();
      ((JSONObject)localObject2).put(MenuConstants.MENU_POS_LEFT, "reader_menu_btn_margin_left");
      ((JSONObject)localObject2).put(MenuConstants.MENU_POS_TOP, "reader_menu_btn_margin_top");
      ((JSONObject)localObject2).put(MenuConstants.MENU_POS_RIGHT, "reader_menu_btn_margin_right");
      ((JSONObject)localObject2).put(MenuConstants.MENU_POS_BOTTOM, "reader_menu_btn_margin_bottom");
      ((JSONObject)localObject1).put(MenuConstants.MENU_MARGIN, localObject2);
      localObject2 = new JSONObject();
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_LEFT_NORMAL, "reader_menu_btn_left_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_LEFT_PRESSED, "reader_menu_btn_left_pressed");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_LEFT_FOCUSED, "reader_menu_btn_left_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_MIDDLE_NORMAL, "reader_menu_btn_middle_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_MIDDLE_PRESSED, "reader_menu_btn_middle_pressed");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_MIDDLE_FOCUSED, "reader_menu_btn_middle_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_RIGHT_NORMAL, "reader_menu_btn_right_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_RIGHT_PRESSED, "reader_menu_btn_right_pressed");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_RIGHT_FOCUSED, "reader_menu_btn_right_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_SINGLE_NORMAL, "reader_menu_btn_single_normal");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_SINGLE_PRESSED, "reader_menu_btn_single_pressed");
      ((JSONObject)localObject2).put(MenuConstants.MENU_BACKGROUND_SINGLE_FOCUSED, "reader_menu_btn_single_normal");
      ((JSONObject)localObject1).put(MenuConstants.MENU_BACKGROUND, localObject2);
      localJSONObject.put(MenuConstants.MENU_COMMON, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      Object localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_pdf_outline");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_outline");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601550");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PDF outline button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_PDF, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_ppt_play_model");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_model_project");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601550");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_PPT, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_ppt_play_model");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_model_project");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601550");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_PPTX, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_txt_reading_model");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_model_reading");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601550");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_TXT, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_doc_search");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_search2");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601660");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_DOC, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_btn_qq_file_list");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, "reader_model_qq_rencent_file");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "601660");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_DEFAULT, localObject1);
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      localObject2 = new JSONArray();
      localObject3 = new HashMap();
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_INDEX, "0");
      ((HashMap)localObject3).put(MenuConstants.MENU_BUTTON_NAME, "name_snapshot");
      ((HashMap)localObject3).put(MenuConstants.MENU_TEXT, " ");
      ((HashMap)localObject3).put(MenuConstants.MENU_SHOULD_SHOW, "true");
      ((HashMap)localObject3).put(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN, "0");
      localObject3 = a((HashMap)localObject3);
      if (localObject3 != null) {
        ((JSONArray)localObject2).put(localObject3);
      } else {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Gen PPT button failed, invalid params!");
      }
      if (((JSONArray)localObject2).length() > 0) {
        ((JSONObject)localObject1).put(MenuConstants.MENU_BUTTON, localObject2);
      }
      localJSONObject.put(MenuConstants.TYPE_SNAPSHOT, localObject1);
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Object localObject1 = jdField_a_of_type_JavaLangString;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("createConfigFileIfNotExist Exception:");
      ((StringBuilder)localObject1).append(localException.toString());
      ((StringBuilder)localObject1).toString();
    }
    return null;
  }
  
  public void destory()
  {
    a locala = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a;
    if (locala != null)
    {
      locala.cancel(true);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a = null;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback = null;
  }
  
  public Drawable getDrawable(String paramString)
  {
    Object localObject1 = null;
    try
    {
      Object localObject2 = this.jdField_a_of_type_OrgJsonJSONObject.getJSONObject(MenuConstants.MENU_COMMON).getJSONObject(MenuConstants.MENU_BACKGROUND);
      if (paramString.equalsIgnoreCase(MenuConstants.MENU_BACKGROUND_LEFT))
      {
        paramString = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_LEFT_NORMAL);
        localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_LEFT_PRESSED);
        localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_LEFT_FOCUSED);
        return a(MenuResources.getDrawable(paramString), MenuResources.getDrawable((String)localObject1), MenuResources.getDrawable((String)localObject2));
      }
      if (paramString.equalsIgnoreCase(MenuConstants.MENU_BACKGROUND_MIDDLE))
      {
        paramString = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_MIDDLE_NORMAL);
        localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_MIDDLE_PRESSED);
        localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_MIDDLE_FOCUSED);
        return a(MenuResources.getDrawable(paramString), MenuResources.getDrawable((String)localObject1), MenuResources.getDrawable((String)localObject2));
      }
      if (paramString.equalsIgnoreCase(MenuConstants.MENU_BACKGROUND_RIGHT))
      {
        paramString = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_RIGHT_NORMAL);
        localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_RIGHT_PRESSED);
        localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_RIGHT_FOCUSED);
        return a(MenuResources.getDrawable(paramString), MenuResources.getDrawable((String)localObject1), MenuResources.getDrawable((String)localObject2));
      }
      if (paramString.equalsIgnoreCase(MenuConstants.MENU_BACKGROUND_SINGLE))
      {
        paramString = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_SINGLE_NORMAL);
        localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_SINGLE_PRESSED);
        localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_BACKGROUND_SINGLE_FOCUSED);
        localObject1 = a(MenuResources.getDrawable(paramString), MenuResources.getDrawable((String)localObject1), MenuResources.getDrawable((String)localObject2));
      }
      return (Drawable)localObject1;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      localObject1 = jdField_a_of_type_JavaLangString;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getDrawable Exception:");
      ((StringBuilder)localObject1).append(paramString.toString());
      ((StringBuilder)localObject1).toString();
    }
    return null;
  }
  
  public HashMap<String, Integer> getMargin()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Object localObject2 = this.jdField_a_of_type_OrgJsonJSONObject.getJSONObject(MenuConstants.MENU_COMMON).getJSONObject(MenuConstants.MENU_MARGIN);
      localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_LEFT);
      String str1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_TOP);
      String str2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_RIGHT);
      localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_BOTTOM);
      localHashMap.put(MenuConstants.MENU_POS_LEFT, Integer.valueOf(MenuResources.getDimensionPixelSize((String)localObject1)));
      localHashMap.put(MenuConstants.MENU_POS_TOP, Integer.valueOf(MenuResources.getDimensionPixelSize(str1)));
      localHashMap.put(MenuConstants.MENU_POS_RIGHT, Integer.valueOf(MenuResources.getDimensionPixelSize(str2)));
      localHashMap.put(MenuConstants.MENU_POS_BOTTOM, Integer.valueOf(MenuResources.getDimensionPixelSize((String)localObject2)));
      localObject1 = jdField_a_of_type_JavaLangString;
      return localHashMap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject1 = jdField_a_of_type_JavaLangString;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getMargin Exception:");
      ((StringBuilder)localObject1).append(localException.toString());
      ((StringBuilder)localObject1).toString();
    }
    return null;
  }
  
  public MenuType getMenuType(String paramString)
  {
    try
    {
      localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getMenuType:");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).toString();
      localObject = new MenuType(this.jdField_a_of_type_OrgJsonJSONObject.getJSONObject(paramString));
      return (MenuType)localObject;
    }
    catch (Exception localException)
    {
      Object localObject;
      for (;;) {}
    }
    localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getMenuType, no config menu for such file:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    return null;
  }
  
  public boolean initConfig(String paramString, ConfigCallback paramConfigCallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback = paramConfigCallback;
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a = new a();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$a.execute(new Object[] { paramString });
    }
    return true;
  }
  
  public static abstract interface ConfigCallback
  {
    public abstract void onCallback(int paramInt);
  }
  
  class a
    extends AsyncTask<Object, Object, Object[]>
  {
    a() {}
    
    protected Object[] a(Object... paramVarArgs)
    {
      paramVarArgs = (String)paramVarArgs[0];
      paramVarArgs = MenuConfig.a(MenuConfig.this, paramVarArgs);
      if (!isCancelled()) {
        publishProgress(new Object[] { paramVarArgs });
      }
      return null;
    }
    
    protected void onProgressUpdate(Object... paramVarArgs)
    {
      int i = 0;
      paramVarArgs = (JSONObject)paramVarArgs[0];
      MenuConfig.a(MenuConfig.this, paramVarArgs);
      if (MenuConfig.a(MenuConfig.this) == null) {
        i = -1;
      }
      if (MenuConfig.this.a != null) {
        MenuConfig.this.a.onCallback(i);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */