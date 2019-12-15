package com.tencent.tbs.common.utils;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.browser.download.engine.DownloadInfo;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.download.BaseDownloadManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ExtendJSRule
{
  public static final String RULE_VIDEO_AUTO_PLAY = "AutoPlayVideo";
  public static final String RULE_VIDEO_FULLSCREEN_EVENT_DETECT = "FullScreenEventDetect";
  static int mDownloadTaskId = -1;
  private static Map<String, String> mRulesMap;
  private static final String sDefaultRuleInAssets = "video/bg_f15.png";
  private static boolean sInited = false;
  private static final String sRuleDelimiter = "//x5mttRuleDelimiter";
  private static final String sRuleName = "bg_f15.png";
  
  public static String getExtendRule(Context paramContext, String paramString)
  {
    initExtendRuleIfNeed(paramContext);
    paramContext = mRulesMap;
    if (paramContext != null) {
      return (String)paramContext.get(paramString);
    }
    return null;
  }
  
  private static void initExtendRuleIfNeed(Context paramContext)
  {
    if (!sInited)
    {
      parseJsRules(paramContext);
      sInited = true;
      if (PublicSettingManager.getInstance().isExtendRuleUpdated())
      {
        Object localObject2 = "";
        ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(62);
        Object localObject1 = localObject2;
        if (localArrayList != null)
        {
          localObject1 = localObject2;
          if (localArrayList.size() > 0) {
            localObject1 = (String)localArrayList.get(0);
          }
        }
        localObject2 = new DownloadInfo();
        ((DownloadInfo)localObject2).url = ((String)localObject1);
        ((DownloadInfo)localObject2).fileFolderPath = FileUtils.getDataDir(paramContext).toString();
        ((DownloadInfo)localObject2).flag |= 0x20;
        ((DownloadInfo)localObject2).deleteTaskIfCompleted = true;
        ((DownloadInfo)localObject2).isPluginTask = true;
        ((DownloadInfo)localObject2).fileName = "bg_f15.png";
        mDownloadTaskId = BaseDownloadManager.getInstance().startDownload((DownloadInfo)localObject2);
        BaseDownloadManager.getInstance().addTaskObserver(new TaskObserver()
        {
          public void onTaskCompleted(Task paramAnonymousTask)
          {
            if ((ExtendJSRule.mDownloadTaskId != -1) && ((paramAnonymousTask instanceof DownloadTask)) && (((DownloadTask)paramAnonymousTask).getDownloadTaskId() == ExtendJSRule.mDownloadTaskId))
            {
              paramAnonymousTask = new File(FileUtils.getDataDir(this.val$context), "bg_f15.png");
              if (TextUtils.equals(Md5Utils.getMD5(paramAnonymousTask), PublicSettingManager.getInstance().getExtendRuleMD5()))
              {
                PublicSettingManager.getInstance().setExtendRuleUpdated(false);
                ExtendJSRule.parseJsRules(this.val$context);
                return;
              }
              paramAnonymousTask.delete();
            }
          }
          
          public void onTaskCreated(Task paramAnonymousTask) {}
          
          public void onTaskExtEvent(Task paramAnonymousTask) {}
          
          public void onTaskFailed(Task paramAnonymousTask) {}
          
          public void onTaskProgress(Task paramAnonymousTask) {}
          
          public void onTaskStarted(Task paramAnonymousTask) {}
        });
      }
    }
  }
  
  /* Error */
  protected static void parseJsRules(Context paramContext)
  {
    // Byte code:
    //   0: new 108	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokestatic 106	com/tencent/common/utils/FileUtils:getDataDir	(Landroid/content/Context;)Ljava/io/File;
    //   8: ldc 31
    //   10: invokespecial 149	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   13: astore_0
    //   14: aload_0
    //   15: invokestatic 155	com/tencent/common/utils/Md5Utils:getMD5	(Ljava/io/File;)Ljava/lang/String;
    //   18: astore_2
    //   19: aload_0
    //   20: invokevirtual 158	java/io/File:exists	()Z
    //   23: istore_1
    //   24: aconst_null
    //   25: astore_3
    //   26: aconst_null
    //   27: astore 5
    //   29: aconst_null
    //   30: astore 4
    //   32: iload_1
    //   33: ifeq +286 -> 319
    //   36: aload_0
    //   37: invokevirtual 161	java/io/File:isFile	()Z
    //   40: ifeq +279 -> 319
    //   43: aload_2
    //   44: invokestatic 64	com/tencent/tbs/common/settings/PublicSettingManager:getInstance	()Lcom/tencent/tbs/common/settings/PublicSettingManager;
    //   47: invokevirtual 164	com/tencent/tbs/common/settings/PublicSettingManager:getExtendRuleMD5	()Ljava/lang/String;
    //   50: invokestatic 170	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   53: ifeq +18 -> 71
    //   56: new 172	java/io/DataInputStream
    //   59: dup
    //   60: aload_0
    //   61: invokestatic 176	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   64: invokespecial 179	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   67: astore_0
    //   68: goto +11 -> 79
    //   71: aload_0
    //   72: invokevirtual 182	java/io/File:delete	()Z
    //   75: pop
    //   76: goto +243 -> 319
    //   79: aload_0
    //   80: astore_2
    //   81: aload_0
    //   82: ifnonnull +18 -> 100
    //   85: invokestatic 188	com/tencent/tbs/common/resources/TBSResources:getResourceContext	()Landroid/content/Context;
    //   88: invokevirtual 194	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   91: invokevirtual 200	android/content/res/Resources:getAssets	()Landroid/content/res/AssetManager;
    //   94: ldc 22
    //   96: invokevirtual 206	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   99: astore_2
    //   100: aload_3
    //   101: astore_0
    //   102: new 208	java/io/BufferedReader
    //   105: dup
    //   106: new 210	java/io/InputStreamReader
    //   109: dup
    //   110: aload_2
    //   111: ldc -44
    //   113: invokespecial 215	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   116: invokespecial 218	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   119: astore_2
    //   120: ldc 70
    //   122: astore_3
    //   123: new 220	java/util/HashMap
    //   126: dup
    //   127: invokespecial 221	java/util/HashMap:<init>	()V
    //   130: putstatic 45	com/tencent/tbs/common/utils/ExtendJSRule:mRulesMap	Ljava/util/Map;
    //   133: aload 4
    //   135: astore_0
    //   136: aload_2
    //   137: invokevirtual 224	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   140: astore 4
    //   142: aload 4
    //   144: ifnull +109 -> 253
    //   147: aload 4
    //   149: ldc 28
    //   151: invokevirtual 228	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   154: ifeq +57 -> 211
    //   157: aload_3
    //   158: ifnull +21 -> 179
    //   161: aload_0
    //   162: ifnull +17 -> 179
    //   165: getstatic 45	com/tencent/tbs/common/utils/ExtendJSRule:mRulesMap	Ljava/util/Map;
    //   168: aload_3
    //   169: aload_0
    //   170: invokevirtual 231	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   173: invokeinterface 235 3 0
    //   178: pop
    //   179: aload 4
    //   181: ldc -19
    //   183: invokevirtual 241	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   186: astore 4
    //   188: aload 4
    //   190: arraylength
    //   191: iconst_2
    //   192: if_icmplt -56 -> 136
    //   195: aload 4
    //   197: iconst_1
    //   198: aaload
    //   199: astore_3
    //   200: new 230	java/lang/StringBuffer
    //   203: dup
    //   204: invokespecial 242	java/lang/StringBuffer:<init>	()V
    //   207: astore_0
    //   208: goto -72 -> 136
    //   211: aload_0
    //   212: ifnull -76 -> 136
    //   215: new 244	java/lang/StringBuilder
    //   218: dup
    //   219: invokespecial 245	java/lang/StringBuilder:<init>	()V
    //   222: astore 5
    //   224: aload 5
    //   226: aload 4
    //   228: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload 5
    //   234: ldc -5
    //   236: invokevirtual 249	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: pop
    //   240: aload_0
    //   241: aload 5
    //   243: invokevirtual 252	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   246: invokevirtual 255	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   249: pop
    //   250: goto -114 -> 136
    //   253: aload_3
    //   254: ifnull +21 -> 275
    //   257: aload_0
    //   258: ifnull +17 -> 275
    //   261: getstatic 45	com/tencent/tbs/common/utils/ExtendJSRule:mRulesMap	Ljava/util/Map;
    //   264: aload_3
    //   265: aload_0
    //   266: invokevirtual 231	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   269: invokeinterface 235 3 0
    //   274: pop
    //   275: aload_2
    //   276: invokestatic 259	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   279: return
    //   280: astore_0
    //   281: goto +30 -> 311
    //   284: astore_3
    //   285: goto +15 -> 300
    //   288: astore_3
    //   289: aload_0
    //   290: astore_2
    //   291: aload_3
    //   292: astore_0
    //   293: goto +18 -> 311
    //   296: astore_3
    //   297: aload 5
    //   299: astore_2
    //   300: aload_2
    //   301: astore_0
    //   302: aload_3
    //   303: invokevirtual 262	java/lang/Exception:printStackTrace	()V
    //   306: aload_2
    //   307: invokestatic 259	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   310: return
    //   311: aload_2
    //   312: invokestatic 259	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   315: aload_0
    //   316: athrow
    //   317: astore_0
    //   318: return
    //   319: aconst_null
    //   320: astore_0
    //   321: goto -242 -> 79
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	324	0	paramContext	Context
    //   23	10	1	bool	boolean
    //   18	294	2	localObject1	Object
    //   25	240	3	str	String
    //   284	1	3	localException1	Exception
    //   288	4	3	localObject2	Object
    //   296	7	3	localException2	Exception
    //   30	197	4	localObject3	Object
    //   27	271	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   123	133	280	finally
    //   136	142	280	finally
    //   147	157	280	finally
    //   165	179	280	finally
    //   179	195	280	finally
    //   200	208	280	finally
    //   215	250	280	finally
    //   261	275	280	finally
    //   123	133	284	java/lang/Exception
    //   136	142	284	java/lang/Exception
    //   147	157	284	java/lang/Exception
    //   165	179	284	java/lang/Exception
    //   179	195	284	java/lang/Exception
    //   200	208	284	java/lang/Exception
    //   215	250	284	java/lang/Exception
    //   261	275	284	java/lang/Exception
    //   102	120	288	finally
    //   302	306	288	finally
    //   102	120	296	java/lang/Exception
    //   0	24	317	java/lang/Exception
    //   36	68	317	java/lang/Exception
    //   71	76	317	java/lang/Exception
    //   85	100	317	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\ExtendJSRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */