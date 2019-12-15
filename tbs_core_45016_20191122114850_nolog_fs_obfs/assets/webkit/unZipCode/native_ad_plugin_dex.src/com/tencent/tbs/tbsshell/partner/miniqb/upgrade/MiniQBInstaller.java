package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Process;
import android.util.Log;
import com.tencent.common.utils.LinuxToolsJni;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MiniQBInstaller
{
  static final String APK_FILENAME = "miniqb.tbs";
  private static final String DEMO_SIG = "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a";
  private static final boolean ENABLE_COPY_INSTALL = false;
  static final int INCRUPDATE_STATUS_PATCH_SUCCESS = 1;
  static final int INCRUPDATE_STATUS_UNKNOW = -1;
  static final int INSTALL_STATUS_RENAME = 1;
  static final int INSTALL_STATUS_UNKNOWN = -1;
  static final int INSTALL_STATUS_WILLUSE = 2;
  static final String KEY_DIFF_FILE_LOCATION = "diff_file_location";
  static final String KEY_IS_MINIQB = "is_miniqb";
  static final String KEY_NEW_APK_LOCATION = "new_apk_location";
  static final String KEY_NEW_CORE_VERSION = "new_core_ver";
  static final String KEY_OLD_APK_LOCATION = "old_apk_location";
  static final String KEY_OLD_CORE_VERSION = "old_core_ver";
  static final String KEY_OPERATION = "operation";
  private static final String KEY_PATCH_APK_PATH = "apk_path";
  private static final String KEY_PATCH_RESULT = "patch_result";
  private static final String KEY_PATCH_TBSCORE_VER = "tbs_core_ver";
  private static final String MINIQB_LOCAL_INSTALLATION = "miniqb_local_installation";
  private static final String MM_SIG = "308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499";
  private static final String MOBILEQQ_SIG = "30820253308201bca00302010202044bbb0361300d06092a864886f70d0101050500306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b30090603550403130251513020170d3130303430363039343831375a180f32323834303132303039343831375a306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b300906035504031302515130819f300d06092a864886f70d010101050003818d0030818902818100a15e9756216f694c5915e0b529095254367c4e64faeff07ae13488d946615a58ddc31a415f717d019edc6d30b9603d3e2a7b3de0ab7e0cf52dfee39373bc472fa997027d798d59f81d525a69ecf156e885fd1e2790924386b2230cc90e3b7adc95603ddcf4c40bdc72f22db0f216a99c371d3bf89cba6578c60699e8a0d536950203010001300d06092a864886f70d01010505000381810094a9b80e80691645dd42d6611775a855f71bcd4d77cb60a8e29404035a5e00b21bcc5d4a562482126bd91b6b0e50709377ceb9ef8c2efd12cc8b16afd9a159f350bb270b14204ff065d843832720702e28b41491fbc3a205f5f2f42526d67f17614d8a974de6487b2c866efede3b4e49a0f916baa3c1336fd2ee1b1629652049";
  private static final String MTT_SIG = "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a";
  private static final int PATCH_FAIL = 1;
  private static final int PATCH_LOAD_ERROR = 3;
  private static final int PATCH_NONEEDPATCH = 2;
  private static final int PATCH_SUCCESS = 0;
  private static final String QQPIMSECURE_SIG = "30820239308201a2a00302010202044c96f48f300d06092a864886f70d01010505003060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e57753020170d3130303932303035343334335a180f32303635303632333035343334335a3060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e577530819f300d06092a864886f70d010101050003818d0030818902818100b56e79dbb1185a79e52d792bb3d0bb3da8010d9b87da92ec69f7dc5ad66ab6bfdff2a6a1ed285dd2358f28b72a468be7c10a2ce30c4c27323ed4edcc936080e5bedc2cbbca0b7e879c08a631182793f44bb3ea284179b263410c298e5f6831032c9702ba4a74e2ccfc9ef857f12201451602fc8e774ac59d6398511586c83d1d0203010001300d06092a864886f70d0101050500038181002475615bb65b8d8786b890535802948840387d06b1692ff3ea47ef4c435719ba1865b81e6bfa6293ce31747c3cd6b34595b485cc1563fd90107ba5845c28b95c79138f0dec288940395bc10f92f2b69d8dc410999deb38900974ce9984b678030edfba8816582f56160d87e38641288d8588d2a31e20b89f223d788dd35cc9c8";
  private static final String QZONE_SIG = "308202ad30820216a00302010202044c26cea2300d06092a864886f70d010105050030819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d301e170d3130303632373034303830325a170d3335303632313034303830325a30819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d30819f300d06092a864886f70d010101050003818d003081890281810082d6aca037a9843fbbe88b6dd19f36e9c24ce174c1b398f3a529e2a7fe02de99c27539602c026edf96ad8d43df32a85458bca1e6fbf11958658a7d6751a1d9b782bf43a8c19bd1c06bdbfd94c0516326ae3cf638ac42bb470580e340c46e6f306a772c1ef98f10a559edf867f3f31fe492808776b7bd953b2cba2d2b2d66a44f0203010001300d06092a864886f70d0101050500038181006003b04a8a8c5be9650f350cda6896e57dd13e6e83e7f891fc70f6a3c2eaf75cfa4fc998365deabbd1b9092159edf4b90df5702a0d101f8840b5d4586eb92a1c3cd19d95fbc1c2ac956309eda8eef3944baf08c4a49d3b9b3ffb06bc13dab94ecb5b8eb74e8789aa0ba21cb567f538bbc59c2a11e6919924a24272eb79251677";
  static final String TAG = "MiniQBUpgrade";
  private static final Lock mCoreInstallLock = new ReentrantLock();
  private static MiniQBInstaller mInstance;
  
  private Context createHostContext(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.createPackageContext(paramString, 2);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  private boolean doDexOpt(Context paramContext)
  {
    try
    {
      File localFile = MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreTempDir(paramContext);
      File[] arrayOfFile = localFile.listFiles(new FileFilter()
      {
        public boolean accept(File paramAnonymousFile)
        {
          return paramAnonymousFile.getName().endsWith(".jar");
        }
      });
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("doDexOpt jarFile:");
        ((StringBuilder)localObject).append(arrayOfFile[i].getAbsolutePath());
        ((StringBuilder)localObject).toString();
        localObject = paramContext.getClassLoader();
        new DexClassLoader(arrayOfFile[i].getAbsolutePath(), localFile.getAbsolutePath(), null, (ClassLoader)localObject);
        i += 1;
      }
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  /* Error */
  private boolean doUnzip(Context paramContext, File paramFile)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 163	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:isFileValid	(Ljava/io/File;)Z
    //   4: istore 4
    //   6: iconst_0
    //   7: istore_3
    //   8: iload 4
    //   10: ifne +15 -> 25
    //   13: aload_0
    //   14: aload_1
    //   15: sipush 204
    //   18: ldc -91
    //   20: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   23: iconst_0
    //   24: ireturn
    //   25: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   28: aload_1
    //   29: invokevirtual 116	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeCoreTempDir	(Landroid/content/Context;)Ljava/io/File;
    //   32: astore 5
    //   34: aload 5
    //   36: ifnonnull +15 -> 51
    //   39: aload_0
    //   40: aload_1
    //   41: sipush 205
    //   44: ldc -85
    //   46: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   49: iconst_0
    //   50: ireturn
    //   51: aload 5
    //   53: invokestatic 174	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:ensureDirectory	(Ljava/io/File;)Z
    //   56: pop
    //   57: aload_2
    //   58: aload 5
    //   60: invokestatic 178	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:copyTbsFilesIfNeeded	(Ljava/io/File;Ljava/io/File;)Z
    //   63: istore 4
    //   65: iload 4
    //   67: ifne +23 -> 90
    //   70: aload_0
    //   71: aload_1
    //   72: sipush 207
    //   75: ldc -76
    //   77: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   80: goto +10 -> 90
    //   83: astore_1
    //   84: iload 4
    //   86: istore_3
    //   87: goto +192 -> 279
    //   90: iload 4
    //   92: ifne +86 -> 178
    //   95: aload 5
    //   97: ifnull +81 -> 178
    //   100: aload 5
    //   102: invokestatic 184	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:delete	(Ljava/io/File;)V
    //   105: new 127	java/lang/StringBuilder
    //   108: dup
    //   109: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   112: astore_1
    //   113: aload_1
    //   114: ldc -70
    //   116: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload_1
    //   121: aload 5
    //   123: invokevirtual 190	java/io/File:exists	()Z
    //   126: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: ldc 79
    //   132: aload_1
    //   133: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   139: pop
    //   140: iload 4
    //   142: ireturn
    //   143: astore_1
    //   144: new 127	java/lang/StringBuilder
    //   147: dup
    //   148: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   151: astore_2
    //   152: aload_2
    //   153: ldc -55
    //   155: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload_2
    //   160: aload_1
    //   161: invokestatic 205	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   164: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: ldc 79
    //   170: aload_2
    //   171: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   177: pop
    //   178: iload 4
    //   180: ireturn
    //   181: astore_1
    //   182: goto +97 -> 279
    //   185: astore_2
    //   186: aload_0
    //   187: aload_1
    //   188: sipush 206
    //   191: aload_2
    //   192: invokevirtual 208	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/Throwable;)V
    //   195: aload 5
    //   197: ifnull +80 -> 277
    //   200: aload 5
    //   202: invokestatic 184	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:delete	(Ljava/io/File;)V
    //   205: new 127	java/lang/StringBuilder
    //   208: dup
    //   209: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   212: astore_1
    //   213: aload_1
    //   214: ldc -70
    //   216: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: aload_1
    //   221: aload 5
    //   223: invokevirtual 190	java/io/File:exists	()Z
    //   226: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: ldc 79
    //   232: aload_1
    //   233: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   236: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   239: pop
    //   240: iconst_0
    //   241: ireturn
    //   242: astore_1
    //   243: new 127	java/lang/StringBuilder
    //   246: dup
    //   247: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   250: astore_2
    //   251: aload_2
    //   252: ldc -55
    //   254: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload_2
    //   259: aload_1
    //   260: invokestatic 205	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   263: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   266: pop
    //   267: ldc 79
    //   269: aload_2
    //   270: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   273: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   276: pop
    //   277: iconst_0
    //   278: ireturn
    //   279: iload_3
    //   280: ifne +90 -> 370
    //   283: aload 5
    //   285: ifnull +85 -> 370
    //   288: aload 5
    //   290: invokestatic 184	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:delete	(Ljava/io/File;)V
    //   293: new 127	java/lang/StringBuilder
    //   296: dup
    //   297: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   300: astore_2
    //   301: aload_2
    //   302: ldc -70
    //   304: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload_2
    //   309: aload 5
    //   311: invokevirtual 190	java/io/File:exists	()Z
    //   314: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   317: pop
    //   318: ldc 79
    //   320: aload_2
    //   321: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   324: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   327: pop
    //   328: goto +42 -> 370
    //   331: astore_2
    //   332: new 127	java/lang/StringBuilder
    //   335: dup
    //   336: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   339: astore 5
    //   341: aload 5
    //   343: ldc -55
    //   345: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: pop
    //   349: aload 5
    //   351: aload_2
    //   352: invokestatic 205	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   355: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: pop
    //   359: ldc 79
    //   361: aload 5
    //   363: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   366: invokestatic 199	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   369: pop
    //   370: aload_1
    //   371: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	372	0	this	MiniQBInstaller
    //   0	372	1	paramContext	Context
    //   0	372	2	paramFile	File
    //   7	273	3	i	int
    //   4	175	4	bool	boolean
    //   32	330	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   70	80	83	finally
    //   100	140	143	java/lang/Throwable
    //   51	65	181	finally
    //   186	195	181	finally
    //   51	65	185	java/lang/Exception
    //   70	80	185	java/lang/Exception
    //   200	240	242	java/lang/Throwable
    //   288	328	331	java/lang/Throwable
  }
  
  private Context getCoreHostContext(Context paramContext, int paramInt)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getCoreHostContext tbsCoreTargetVer=");
    ((StringBuilder)localObject1).append(paramInt);
    ((StringBuilder)localObject1).toString();
    if (paramInt <= 0) {
      return null;
    }
    localObject1 = MiniQBShareManager.getCoreProviderAppList();
    int i = 0;
    while (i < localObject1.length)
    {
      if ((!paramContext.getPackageName().equalsIgnoreCase(localObject1[i])) && (isHostAppInstalled(paramContext, localObject1[i])))
      {
        Object localObject2 = createHostContext(paramContext, localObject1[i]);
        if (localObject2 != null) {
          if (!vertificateApp((Context)localObject2))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("getCoreHostContext ");
            ((StringBuilder)localObject2).append(localObject1[i]);
            ((StringBuilder)localObject2).append(" illegal signature go on next");
            Log.e("MiniQBUpgrade", ((StringBuilder)localObject2).toString());
          }
          else
          {
            int j = MiniQBUpgradeManager.getInstance().getUpgradeVersion((Context)localObject2);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("getCoreHostContext hostTbsCoreVer=");
            localStringBuilder.append(j);
            localStringBuilder.toString();
            if ((j != 0) && (j == paramInt))
            {
              paramContext = new StringBuilder();
              paramContext.append("getCoreHostContext targetApp=");
              paramContext.append(localObject1[i]);
              paramContext.toString();
              return (Context)localObject2;
            }
          }
        }
      }
      i += 1;
    }
    return null;
  }
  
  static MiniQBInstaller getInstance()
  {
    if (mInstance == null) {
      mInstance = new MiniQBInstaller();
    }
    return mInstance;
  }
  
  private boolean isHostAppInstalled(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    paramContext = null;
    return paramContext != null;
  }
  
  private void prepareInstall(Context paramContext, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("prepareInstall installTargetVersion=");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
    if (paramInt != MiniQBInstallConfig.getInstance(paramContext).getCoreInstallVersion())
    {
      MiniQBInstallConfig.getInstance(paramContext).setCoreInstallRetryTimes(0);
      MiniQBInstallConfig.getInstance(paramContext).setCoreCopyRetryTimes(0);
    }
    MiniQBInstallConfig.getInstance(paramContext).setCoreInstallStatus(paramInt, -1);
    FileUtil.delete(MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreTempDir(paramContext), true);
  }
  
  private void shareAllDirsAndFiles(Context paramContext, LinuxToolsJni paramLinuxToolsJni, File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
    {
      if (!paramFile.isDirectory()) {
        return;
      }
      paramLinuxToolsJni.Chmod(paramFile.getAbsolutePath(), "755");
      paramFile = paramFile.listFiles();
      int j = paramFile.length;
      int i = 0;
      while (i < j)
      {
        File localFile = paramFile[i];
        if (localFile.isFile())
        {
          if (localFile.getAbsolutePath().indexOf(".so") > 0) {
            paramLinuxToolsJni.Chmod(localFile.getAbsolutePath(), "755");
          } else {
            paramLinuxToolsJni.Chmod(localFile.getAbsolutePath(), "644");
          }
        }
        else if (localFile.isDirectory()) {
          shareAllDirsAndFiles(paramContext, paramLinuxToolsJni, localFile);
        } else {
          Log.w("MiniQBUpgrade", "unknown file type.");
        }
        i += 1;
      }
      return;
    }
  }
  
  private boolean vertificateApp(Context paramContext)
  {
    try
    {
      Signature localSignature = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures[0];
      if (paramContext.getPackageName().equals("com.tencent.mtt"))
      {
        if (!localSignature.toCharsString().equals("3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a")) {
          return false;
        }
      }
      else if (paramContext.getPackageName().equals("com.tencent.mm"))
      {
        if (!localSignature.toCharsString().equals("308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499")) {
          return false;
        }
      }
      else if (paramContext.getPackageName().equals("com.tencent.mobileqq"))
      {
        if (!localSignature.toCharsString().equals("30820253308201bca00302010202044bbb0361300d06092a864886f70d0101050500306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b30090603550403130251513020170d3130303430363039343831375a180f32323834303132303039343831375a306d310e300c060355040613054368696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b300906035504031302515130819f300d06092a864886f70d010101050003818d0030818902818100a15e9756216f694c5915e0b529095254367c4e64faeff07ae13488d946615a58ddc31a415f717d019edc6d30b9603d3e2a7b3de0ab7e0cf52dfee39373bc472fa997027d798d59f81d525a69ecf156e885fd1e2790924386b2230cc90e3b7adc95603ddcf4c40bdc72f22db0f216a99c371d3bf89cba6578c60699e8a0d536950203010001300d06092a864886f70d01010505000381810094a9b80e80691645dd42d6611775a855f71bcd4d77cb60a8e29404035a5e00b21bcc5d4a562482126bd91b6b0e50709377ceb9ef8c2efd12cc8b16afd9a159f350bb270b14204ff065d843832720702e28b41491fbc3a205f5f2f42526d67f17614d8a974de6487b2c866efede3b4e49a0f916baa3c1336fd2ee1b1629652049")) {
          return false;
        }
      }
      else if (paramContext.getPackageName().equals("com.tencent.tbs"))
      {
        if (!localSignature.toCharsString().equals("3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a")) {
          return false;
        }
      }
      else if (paramContext.getPackageName().equals("com.qzone"))
      {
        if (!localSignature.toCharsString().equals("308202ad30820216a00302010202044c26cea2300d06092a864886f70d010105050030819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d301e170d3130303632373034303830325a170d3335303632313034303830325a30819a310b3009060355040613023836311530130603550408130c4265696a696e672043697479311530130603550407130c4265696a696e67204369747931263024060355040a131d515a6f6e65205465616d206f662054656e63656e7420436f6d70616e7931183016060355040b130f54656e63656e7420436f6d70616e79311b301906035504031312416e64726f696420515a6f6e65205465616d30819f300d06092a864886f70d010101050003818d003081890281810082d6aca037a9843fbbe88b6dd19f36e9c24ce174c1b398f3a529e2a7fe02de99c27539602c026edf96ad8d43df32a85458bca1e6fbf11958658a7d6751a1d9b782bf43a8c19bd1c06bdbfd94c0516326ae3cf638ac42bb470580e340c46e6f306a772c1ef98f10a559edf867f3f31fe492808776b7bd953b2cba2d2b2d66a44f0203010001300d06092a864886f70d0101050500038181006003b04a8a8c5be9650f350cda6896e57dd13e6e83e7f891fc70f6a3c2eaf75cfa4fc998365deabbd1b9092159edf4b90df5702a0d101f8840b5d4586eb92a1c3cd19d95fbc1c2ac956309eda8eef3944baf08c4a49d3b9b3ffb06bc13dab94ecb5b8eb74e8789aa0ba21cb567f538bbc59c2a11e6919924a24272eb79251677")) {
          return false;
        }
      }
      else if (paramContext.getPackageName().equals("com.tencent.qqpimsecure"))
      {
        boolean bool = localSignature.toCharsString().equals("30820239308201a2a00302010202044c96f48f300d06092a864886f70d01010505003060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e57753020170d3130303932303035343334335a180f32303635303632333035343334335a3060310b300906035504061302434e310b300906035504081302474431123010060355040713094775616e677a686f753110300e060355040a130754656e63656e74310b3009060355040b130233473111300f0603550403130857696c736f6e577530819f300d06092a864886f70d010101050003818d0030818902818100b56e79dbb1185a79e52d792bb3d0bb3da8010d9b87da92ec69f7dc5ad66ab6bfdff2a6a1ed285dd2358f28b72a468be7c10a2ce30c4c27323ed4edcc936080e5bedc2cbbca0b7e879c08a631182793f44bb3ea284179b263410c298e5f6831032c9702ba4a74e2ccfc9ef857f12201451602fc8e774ac59d6398511586c83d1d0203010001300d06092a864886f70d0101050500038181002475615bb65b8d8786b890535802948840387d06b1692ff3ea47ef4c435719ba1865b81e6bfa6293ce31747c3cd6b34595b485cc1563fd90107ba5845c28b95c79138f0dec288940395bc10f92f2b69d8dc410999deb38900974ce9984b678030edfba8816582f56160d87e38641288d8588d2a31e20b89f223d788dd35cc9c8");
        if (!bool) {
          return false;
        }
      }
      return true;
    }
    catch (Exception paramContext) {}
    return false;
  }
  
  void clearAllInstallFiles(Context paramContext)
  {
    MiniQBInstallConfig.getInstance(paramContext).clearConfig();
    FileUtil.delete(MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreTempDir(paramContext), true);
    FileUtil.delete(MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreDir(paramContext), true);
  }
  
  void copyCore(Context paramContext1, Context paramContext2, int paramInt)
  {
    if (isMiniQBLocalInstalled(paramContext1)) {
      return;
    }
    try
    {
      int i = MiniQBUpgradeManager.getInstance().getMiniQBVersion();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("installLocalCore miniQBVersion=");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).toString();
      if (i == paramInt) {
        return;
      }
      prepareInstall(paramContext1, paramInt);
      i = MiniQBInstallConfig.getInstance(paramContext1).getCoreCopyRetryTimes();
      if (i > 10)
      {
        logReport(paramContext1, 222, "EXCEED_RETRY_NUM");
        return;
      }
      MiniQBInstallConfig.getInstance(paramContext1).setCoreCopyRetryTimes(i + 1);
      localObject = MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreDir(paramContext2);
      File localFile = MiniQBUpgradeManager.getInstance().getMiniQBUpgradeCoreTempDir(paramContext1);
      if ((localObject != null) && (localFile != null))
      {
        MiniQBCopyVerify localMiniQBCopyVerify = new MiniQBCopyVerify();
        localMiniQBCopyVerify.generateReferenceValue((File)localObject);
        if (FileUtil.copyFiles((File)localObject, localFile))
        {
          localMiniQBCopyVerify.generateVerifyValue((File)localObject);
          if (!localMiniQBCopyVerify.verify())
          {
            FileUtil.delete(localFile, true);
            logReport(paramContext1, 213, "CopyVerify failed");
            return;
          }
          paramContext2 = MiniQBApkDownloader.backupApkPath(paramContext2);
          if ((paramContext2 != null) && (paramContext2.exists())) {
            MiniQBApkDownloader.backupApk(new File(paramContext2, "miniqb.tbs.org"), paramContext1);
          }
          MiniQBInstallConfig.getInstance(paramContext1).setCoreInstallStatus(paramInt, 1);
          logReport(paramContext1, 220, "success");
          return;
        }
        FileUtil.delete(localFile, false);
        logReport(paramContext1, 212, "copy failed!");
        return;
      }
      if (localObject == null) {
        logReport(paramContext1, 213, "src-dir is null");
      }
      if (localFile == null)
      {
        logReport(paramContext1, 214, "dst-dir is null");
        return;
      }
    }
    catch (Exception paramContext2)
    {
      logReport(paramContext1, 215, paramContext2);
    }
  }
  
  void installCore(Context paramContext, String paramString, int paramInt)
  {
    if (isMiniQBLocalInstalled(paramContext)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore installApkPath=");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore installTargetVersion=");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore currentProcessName=");
    localStringBuilder.append(paramContext.getApplicationInfo().processName);
    localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore currentProcessId=");
    localStringBuilder.append(Process.myPid());
    localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore currentThreadName=");
    localStringBuilder.append(Thread.currentThread().getName());
    localStringBuilder.toString();
    boolean bool = mCoreInstallLock.tryLock();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("installCore locked =");
    localStringBuilder.append(bool);
    localStringBuilder.toString();
    if (bool) {
      try
      {
        prepareInstall(paramContext, paramInt);
        int i = MiniQBInstallConfig.getInstance(paramContext).getCoreInstallRetryTimes();
        if (i > 10)
        {
          logReport(paramContext, 222, "EXCEED_RETRY_NUM");
          return;
        }
        MiniQBInstallConfig.getInstance(paramContext).setCoreInstallRetryTimes(i + 1);
        if (paramString == null)
        {
          logReport(paramContext, 202, "APK_PATH_ERROR");
          return;
        }
        bool = doUnzip(paramContext, new File(paramString));
        if (!bool) {
          return;
        }
        if (!doDexOpt(paramContext))
        {
          logReport(paramContext, 209, "DEXOPT_EXCEPTION");
          return;
        }
        MiniQBInstallConfig.getInstance(paramContext).setCoreInstallStatus(paramInt, 1);
        return;
      }
      finally
      {
        mCoreInstallLock.unlock();
      }
    }
  }
  
  boolean installLocalCore(Context paramContext, int paramInt)
  {
    if (isMiniQBLocalInstalled(paramContext)) {
      return false;
    }
    paramContext = new StringBuilder();
    paramContext.append("installLocalCore start targetVersion=");
    paramContext.append(paramInt);
    paramContext.toString();
    return false;
  }
  
  /* Error */
  void installLocalMiniqbCoreExInThread(Context paramContext, android.os.Bundle paramBundle)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 367	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:isMiniQBLocalInstalled	(Landroid/content/Context;)Z
    //   5: ifeq +4 -> 9
    //   8: return
    //   9: aload_2
    //   10: ifnull +477 -> 487
    //   13: aload_1
    //   14: ifnonnull +4 -> 18
    //   17: return
    //   18: aconst_null
    //   19: astore 8
    //   21: aconst_null
    //   22: astore 9
    //   24: aconst_null
    //   25: astore 7
    //   27: aload 8
    //   29: astore 5
    //   31: aload 9
    //   33: astore 6
    //   35: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   38: invokevirtual 370	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBVersion	()I
    //   41: ifgt +4 -> 45
    //   44: return
    //   45: aload 8
    //   47: astore 5
    //   49: aload 9
    //   51: astore 6
    //   53: aload_1
    //   54: invokestatic 488	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   57: invokevirtual 491	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMiniQBResponseCode	()I
    //   60: istore_3
    //   61: iload_3
    //   62: iconst_1
    //   63: if_icmpeq +431 -> 494
    //   66: iload_3
    //   67: iconst_2
    //   68: if_icmpne +420 -> 488
    //   71: goto +423 -> 494
    //   74: aload 8
    //   76: astore 5
    //   78: aload 9
    //   80: astore 6
    //   82: new 127	java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   89: astore 10
    //   91: aload 8
    //   93: astore 5
    //   95: aload 9
    //   97: astore 6
    //   99: aload 10
    //   101: ldc_w 493
    //   104: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload 8
    //   110: astore 5
    //   112: aload 9
    //   114: astore 6
    //   116: aload 10
    //   118: iload_3
    //   119: invokevirtual 215	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload 8
    //   125: astore 5
    //   127: aload 9
    //   129: astore 6
    //   131: aload 10
    //   133: ldc_w 495
    //   136: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload 8
    //   142: astore 5
    //   144: aload 9
    //   146: astore 6
    //   148: aload 10
    //   150: iload 4
    //   152: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload 8
    //   158: astore 5
    //   160: aload 9
    //   162: astore 6
    //   164: aload 10
    //   166: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   169: pop
    //   170: iload 4
    //   172: ifne +89 -> 261
    //   175: aload 8
    //   177: astore 5
    //   179: aload 9
    //   181: astore 6
    //   183: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   186: aload_1
    //   187: invokevirtual 498	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeDir	(Landroid/content/Context;)Ljava/io/File;
    //   190: astore 10
    //   192: aload 10
    //   194: ifnull +67 -> 261
    //   197: aload 8
    //   199: astore 5
    //   201: aload 9
    //   203: astore 6
    //   205: new 121	java/io/File
    //   208: dup
    //   209: aload 10
    //   211: ldc 10
    //   213: invokespecial 406	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   216: invokevirtual 190	java/io/File:exists	()Z
    //   219: ifeq +42 -> 261
    //   222: aload 8
    //   224: astore 5
    //   226: aload 9
    //   228: astore 6
    //   230: aload_1
    //   231: aload_2
    //   232: invokestatic 504	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQbSdk:incrUpdate	(Landroid/content/Context;Landroid/os/Bundle;)Landroid/os/Bundle;
    //   235: astore_2
    //   236: aload_2
    //   237: ifnonnull +8 -> 245
    //   240: iconst_1
    //   241: istore_3
    //   242: goto +24 -> 266
    //   245: aload_2
    //   246: astore 5
    //   248: aload_2
    //   249: astore 6
    //   251: aload_2
    //   252: ldc 52
    //   254: invokevirtual 509	android/os/Bundle:getInt	(Ljava/lang/String;)I
    //   257: istore_3
    //   258: goto +8 -> 266
    //   261: iconst_2
    //   262: istore_3
    //   263: aload 7
    //   265: astore_2
    //   266: iload_3
    //   267: ifne +51 -> 318
    //   270: aload_2
    //   271: ifnull +47 -> 318
    //   274: aload_1
    //   275: invokestatic 276	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig;
    //   278: iconst_0
    //   279: iconst_m1
    //   280: invokevirtual 291	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:setCoreInstallStatus	(II)V
    //   283: aload_2
    //   284: ldc 49
    //   286: invokevirtual 513	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   289: astore 5
    //   291: new 121	java/io/File
    //   294: dup
    //   295: aload 5
    //   297: invokespecial 471	java/io/File:<init>	(Ljava/lang/String;)V
    //   300: aload_1
    //   301: invokestatic 410	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:backupApk	(Ljava/io/File;Landroid/content/Context;)V
    //   304: aload_0
    //   305: aload_1
    //   306: aload 5
    //   308: aload_2
    //   309: ldc 55
    //   311: invokevirtual 509	android/os/Bundle:getInt	(Ljava/lang/String;)I
    //   314: invokevirtual 515	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:installCore	(Landroid/content/Context;Ljava/lang/String;I)V
    //   317: return
    //   318: iload_3
    //   319: iconst_2
    //   320: if_icmpne +39 -> 359
    //   323: return
    //   324: astore 6
    //   326: aload 5
    //   328: astore_2
    //   329: iconst_2
    //   330: istore_3
    //   331: aload 6
    //   333: astore 5
    //   335: goto +58 -> 393
    //   338: astore_2
    //   339: aload 6
    //   341: astore 5
    //   343: aload_2
    //   344: invokevirtual 153	java/lang/Exception:printStackTrace	()V
    //   347: aload_0
    //   348: aload_1
    //   349: sipush 218
    //   352: aload_2
    //   353: invokevirtual 516	java/lang/Exception:toString	()Ljava/lang/String;
    //   356: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   359: aload_1
    //   360: invokestatic 488	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   363: iconst_1
    //   364: invokevirtual 520	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setNeedDownload	(Z)V
    //   367: aload_1
    //   368: invokestatic 488	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   371: invokevirtual 523	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:commit	()V
    //   374: aload_0
    //   375: aload_1
    //   376: sipush 217
    //   379: ldc_w 525
    //   382: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   385: return
    //   386: astore 5
    //   388: aload 6
    //   390: astore_2
    //   391: iconst_1
    //   392: istore_3
    //   393: iload_3
    //   394: ifne +56 -> 450
    //   397: aload_2
    //   398: ifnonnull +6 -> 404
    //   401: goto +49 -> 450
    //   404: aload_1
    //   405: invokestatic 276	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig;
    //   408: iconst_0
    //   409: iconst_m1
    //   410: invokevirtual 291	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:setCoreInstallStatus	(II)V
    //   413: aload_2
    //   414: ldc 49
    //   416: invokevirtual 513	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   419: astore 6
    //   421: new 121	java/io/File
    //   424: dup
    //   425: aload 6
    //   427: invokespecial 471	java/io/File:<init>	(Ljava/lang/String;)V
    //   430: aload_1
    //   431: invokestatic 410	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:backupApk	(Ljava/io/File;Landroid/content/Context;)V
    //   434: aload_0
    //   435: aload_1
    //   436: aload 6
    //   438: aload_2
    //   439: ldc 55
    //   441: invokevirtual 509	android/os/Bundle:getInt	(Ljava/lang/String;)I
    //   444: invokevirtual 515	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:installCore	(Landroid/content/Context;Ljava/lang/String;I)V
    //   447: goto +37 -> 484
    //   450: iload_3
    //   451: iconst_2
    //   452: if_icmpne +6 -> 458
    //   455: goto +29 -> 484
    //   458: aload_1
    //   459: invokestatic 488	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   462: iconst_1
    //   463: invokevirtual 520	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setNeedDownload	(Z)V
    //   466: aload_1
    //   467: invokestatic 488	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   470: invokevirtual 523	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:commit	()V
    //   473: aload_0
    //   474: aload_1
    //   475: sipush 217
    //   478: ldc_w 525
    //   481: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   484: aload 5
    //   486: athrow
    //   487: return
    //   488: iconst_0
    //   489: istore 4
    //   491: goto -417 -> 74
    //   494: iconst_1
    //   495: istore 4
    //   497: goto -423 -> 74
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	500	0	this	MiniQBInstaller
    //   0	500	1	paramContext	Context
    //   0	500	2	paramBundle	android.os.Bundle
    //   60	393	3	i	int
    //   150	346	4	bool	boolean
    //   29	313	5	localObject1	Object
    //   386	99	5	localObject2	Object
    //   33	217	6	localObject3	Object
    //   324	65	6	localObject4	Object
    //   419	18	6	str	String
    //   25	239	7	localObject5	Object
    //   19	204	8	localObject6	Object
    //   22	205	9	localObject7	Object
    //   89	121	10	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   35	44	324	finally
    //   53	61	324	finally
    //   82	91	324	finally
    //   99	108	324	finally
    //   116	123	324	finally
    //   131	140	324	finally
    //   148	156	324	finally
    //   164	170	324	finally
    //   183	192	324	finally
    //   205	222	324	finally
    //   230	236	324	finally
    //   251	258	324	finally
    //   343	347	324	finally
    //   35	44	338	java/lang/Exception
    //   53	61	338	java/lang/Exception
    //   82	91	338	java/lang/Exception
    //   99	108	338	java/lang/Exception
    //   116	123	338	java/lang/Exception
    //   131	140	338	java/lang/Exception
    //   148	156	338	java/lang/Exception
    //   164	170	338	java/lang/Exception
    //   183	192	338	java/lang/Exception
    //   205	222	338	java/lang/Exception
    //   230	236	338	java/lang/Exception
    //   251	258	338	java/lang/Exception
    //   347	359	386	finally
  }
  
  /* Error */
  boolean isMiniQBLocalInstalled(Context paramContext)
  {
    // Byte code:
    //   0: new 121	java/io/File
    //   3: dup
    //   4: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   7: aload_1
    //   8: invokevirtual 362	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeCoreDir	(Landroid/content/Context;)Ljava/io/File;
    //   11: ldc_w 529
    //   14: invokespecial 406	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   17: astore_1
    //   18: aload_1
    //   19: invokevirtual 190	java/io/File:exists	()Z
    //   22: istore 4
    //   24: iconst_0
    //   25: istore_2
    //   26: iconst_0
    //   27: istore_3
    //   28: iload 4
    //   30: ifne +5 -> 35
    //   33: iconst_0
    //   34: ireturn
    //   35: new 531	java/util/Properties
    //   38: dup
    //   39: invokespecial 532	java/util/Properties:<init>	()V
    //   42: astore 8
    //   44: aconst_null
    //   45: astore 6
    //   47: aconst_null
    //   48: astore 9
    //   50: new 534	java/io/FileInputStream
    //   53: dup
    //   54: aload_1
    //   55: invokespecial 536	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   58: astore_1
    //   59: aload_1
    //   60: astore 5
    //   62: new 538	java/io/BufferedInputStream
    //   65: dup
    //   66: aload_1
    //   67: invokespecial 541	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   70: astore 7
    //   72: iload_3
    //   73: istore_2
    //   74: aload 8
    //   76: aload 7
    //   78: invokevirtual 544	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   81: iload_3
    //   82: istore_2
    //   83: aload 8
    //   85: ldc 58
    //   87: ldc_w 546
    //   90: invokevirtual 550	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   93: invokestatic 556	java/lang/Boolean:valueOf	(Ljava/lang/String;)Ljava/lang/Boolean;
    //   96: invokevirtual 559	java/lang/Boolean:booleanValue	()Z
    //   99: istore_3
    //   100: iload_3
    //   101: istore_2
    //   102: new 127	java/lang/StringBuilder
    //   105: dup
    //   106: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   109: astore 5
    //   111: iload_3
    //   112: istore_2
    //   113: aload 5
    //   115: ldc_w 561
    //   118: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: iload_3
    //   123: istore_2
    //   124: aload 5
    //   126: iload_3
    //   127: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: iload_3
    //   132: istore_2
    //   133: aload 5
    //   135: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   138: pop
    //   139: aload 7
    //   141: invokevirtual 564	java/io/BufferedInputStream:close	()V
    //   144: goto +10 -> 154
    //   147: astore 5
    //   149: aload 5
    //   151: invokevirtual 565	java/io/IOException:printStackTrace	()V
    //   154: iload_3
    //   155: istore 4
    //   157: aload_1
    //   158: invokevirtual 566	java/io/FileInputStream:close	()V
    //   161: iload_3
    //   162: ireturn
    //   163: astore_1
    //   164: aload_1
    //   165: invokevirtual 565	java/io/IOException:printStackTrace	()V
    //   168: iload 4
    //   170: ireturn
    //   171: astore 6
    //   173: aload 7
    //   175: astore 5
    //   177: goto +96 -> 273
    //   180: astore 8
    //   182: goto +31 -> 213
    //   185: astore 8
    //   187: aload 9
    //   189: astore 7
    //   191: goto +22 -> 213
    //   194: astore 6
    //   196: aconst_null
    //   197: astore 5
    //   199: aload 5
    //   201: astore_1
    //   202: goto +71 -> 273
    //   205: astore 8
    //   207: aconst_null
    //   208: astore_1
    //   209: aload 9
    //   211: astore 7
    //   213: aload 7
    //   215: astore 6
    //   217: aload_1
    //   218: astore 5
    //   220: aload 8
    //   222: invokevirtual 567	java/lang/Throwable:printStackTrace	()V
    //   225: aload 7
    //   227: ifnull +18 -> 245
    //   230: aload 7
    //   232: invokevirtual 564	java/io/BufferedInputStream:close	()V
    //   235: goto +10 -> 245
    //   238: astore 5
    //   240: aload 5
    //   242: invokevirtual 565	java/io/IOException:printStackTrace	()V
    //   245: aload_1
    //   246: ifnull +10 -> 256
    //   249: iload_2
    //   250: istore 4
    //   252: aload_1
    //   253: invokevirtual 566	java/io/FileInputStream:close	()V
    //   256: iload_2
    //   257: ireturn
    //   258: astore_1
    //   259: aload 6
    //   261: astore 7
    //   263: aload_1
    //   264: astore 6
    //   266: aload 5
    //   268: astore_1
    //   269: aload 7
    //   271: astore 5
    //   273: aload 5
    //   275: ifnull +18 -> 293
    //   278: aload 5
    //   280: invokevirtual 564	java/io/BufferedInputStream:close	()V
    //   283: goto +10 -> 293
    //   286: astore 5
    //   288: aload 5
    //   290: invokevirtual 565	java/io/IOException:printStackTrace	()V
    //   293: aload_1
    //   294: ifnull +15 -> 309
    //   297: aload_1
    //   298: invokevirtual 566	java/io/FileInputStream:close	()V
    //   301: goto +8 -> 309
    //   304: astore_1
    //   305: aload_1
    //   306: invokevirtual 565	java/io/IOException:printStackTrace	()V
    //   309: aload 6
    //   311: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	312	0	this	MiniQBInstaller
    //   0	312	1	paramContext	Context
    //   25	232	2	bool1	boolean
    //   27	135	3	bool2	boolean
    //   22	229	4	bool3	boolean
    //   60	74	5	localObject1	Object
    //   147	3	5	localIOException1	java.io.IOException
    //   175	44	5	localObject2	Object
    //   238	29	5	localIOException2	java.io.IOException
    //   271	8	5	localObject3	Object
    //   286	3	5	localIOException3	java.io.IOException
    //   45	1	6	localObject4	Object
    //   171	1	6	localObject5	Object
    //   194	1	6	localObject6	Object
    //   215	95	6	localObject7	Object
    //   70	200	7	localObject8	Object
    //   42	42	8	localProperties	java.util.Properties
    //   180	1	8	localThrowable1	Throwable
    //   185	1	8	localThrowable2	Throwable
    //   205	16	8	localThrowable3	Throwable
    //   48	162	9	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   139	144	147	java/io/IOException
    //   157	161	163	java/io/IOException
    //   252	256	163	java/io/IOException
    //   74	81	171	finally
    //   83	100	171	finally
    //   102	111	171	finally
    //   113	122	171	finally
    //   124	131	171	finally
    //   133	139	171	finally
    //   74	81	180	java/lang/Throwable
    //   83	100	180	java/lang/Throwable
    //   102	111	180	java/lang/Throwable
    //   113	122	180	java/lang/Throwable
    //   124	131	180	java/lang/Throwable
    //   133	139	180	java/lang/Throwable
    //   62	72	185	java/lang/Throwable
    //   50	59	194	finally
    //   50	59	205	java/lang/Throwable
    //   230	235	238	java/io/IOException
    //   62	72	258	finally
    //   220	225	258	finally
    //   278	283	286	java/io/IOException
    //   297	301	304	java/io/IOException
  }
  
  void logReport(Context paramContext, int paramInt, String paramString)
  {
    MiniQBLogReport localMiniQBLogReport = new MiniQBLogReport(paramContext);
    localMiniQBLogReport.setDownloadMiniQBVersion(MiniQBInstallConfig.getInstance(paramContext).getCoreInstallVersion());
    localMiniQBLogReport.setInstallErrorCode(paramInt, paramString);
  }
  
  void logReport(Context paramContext, int paramInt, Throwable paramThrowable)
  {
    MiniQBLogReport localMiniQBLogReport = new MiniQBLogReport(paramContext);
    localMiniQBLogReport.setDownloadMiniQBVersion(MiniQBInstallConfig.getInstance(paramContext).getCoreInstallVersion());
    localMiniQBLogReport.setInstallErrorCode(paramInt, paramThrowable);
  }
  
  /* Error */
  boolean renameCoreIfNeeded(Context paramContext)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: iconst_0
    //   4: istore_3
    //   5: iload 5
    //   7: istore 4
    //   9: getstatic 92	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:mCoreInstallLock	Ljava/util/concurrent/locks/Lock;
    //   12: invokeinterface 458 1 0
    //   17: istore 6
    //   19: iload 5
    //   21: istore 4
    //   23: new 127	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   30: astore 7
    //   32: iload 5
    //   34: istore 4
    //   36: aload 7
    //   38: ldc_w 584
    //   41: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: iload 5
    //   47: istore 4
    //   49: aload 7
    //   51: iload 6
    //   53: invokevirtual 193	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: iload 5
    //   59: istore 4
    //   61: aload 7
    //   63: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   66: pop
    //   67: iload 6
    //   69: ifeq +245 -> 314
    //   72: aload_1
    //   73: invokestatic 276	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig;
    //   76: invokevirtual 587	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallStatus	()I
    //   79: istore_2
    //   80: new 127	java/lang/StringBuilder
    //   83: dup
    //   84: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   87: astore 7
    //   89: aload 7
    //   91: ldc_w 589
    //   94: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: aload 7
    //   100: iload_2
    //   101: invokevirtual 215	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: aload 7
    //   107: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   110: pop
    //   111: iload_2
    //   112: iconst_1
    //   113: if_icmpne +169 -> 282
    //   116: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   119: aload_1
    //   120: invokevirtual 116	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeCoreTempDir	(Landroid/content/Context;)Ljava/io/File;
    //   123: astore 7
    //   125: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   128: aload_1
    //   129: invokevirtual 362	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeCoreDir	(Landroid/content/Context;)Ljava/io/File;
    //   132: astore 8
    //   134: aload 7
    //   136: ifnull +91 -> 227
    //   139: aload 7
    //   141: invokevirtual 309	java/io/File:listFiles	()[Ljava/io/File;
    //   144: ifnull +83 -> 227
    //   147: aload 7
    //   149: invokevirtual 309	java/io/File:listFiles	()[Ljava/io/File;
    //   152: arraylength
    //   153: ifle +74 -> 227
    //   156: aload 8
    //   158: iconst_0
    //   159: invokestatic 294	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:delete	(Ljava/io/File;Z)V
    //   162: aload 7
    //   164: ifnull +49 -> 213
    //   167: aload 8
    //   169: ifnonnull +6 -> 175
    //   172: goto +41 -> 213
    //   175: aload 7
    //   177: aload 8
    //   179: invokevirtual 592	java/io/File:renameTo	(Ljava/io/File;)Z
    //   182: ifeq +17 -> 199
    //   185: aload_0
    //   186: aload_1
    //   187: sipush 200
    //   190: ldc_w 594
    //   193: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   196: goto +31 -> 227
    //   199: aload_0
    //   200: aload_1
    //   201: sipush 219
    //   204: ldc_w 596
    //   207: invokevirtual 169	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:logReport	(Landroid/content/Context;ILjava/lang/String;)V
    //   210: goto +17 -> 227
    //   213: iload 5
    //   215: istore 4
    //   217: getstatic 92	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:mCoreInstallLock	Ljava/util/concurrent/locks/Lock;
    //   220: invokeinterface 466 1 0
    //   225: iconst_0
    //   226: ireturn
    //   227: aload_0
    //   228: aload_1
    //   229: invokestatic 112	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   232: aload_1
    //   233: invokevirtual 498	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeDir	(Landroid/content/Context;)Ljava/io/File;
    //   236: invokevirtual 600	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:shareCore	(Landroid/content/Context;Ljava/io/File;)V
    //   239: aload_0
    //   240: aload_1
    //   241: aload 8
    //   243: invokevirtual 600	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:shareCore	(Landroid/content/Context;Ljava/io/File;)V
    //   246: aload_1
    //   247: invokestatic 276	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig;
    //   250: iconst_0
    //   251: invokevirtual 284	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:setCoreInstallRetryTimes	(I)V
    //   254: aload_1
    //   255: invokestatic 276	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig;
    //   258: iconst_0
    //   259: iconst_2
    //   260: invokevirtual 291	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:setCoreInstallStatus	(II)V
    //   263: goto +17 -> 280
    //   266: astore_1
    //   267: aload_1
    //   268: invokevirtual 153	java/lang/Exception:printStackTrace	()V
    //   271: ldc 79
    //   273: ldc_w 602
    //   276: invokestatic 327	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   279: pop
    //   280: iconst_1
    //   281: istore_3
    //   282: iload_3
    //   283: istore 4
    //   285: getstatic 92	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:mCoreInstallLock	Ljava/util/concurrent/locks/Lock;
    //   288: invokeinterface 466 1 0
    //   293: iload_3
    //   294: ireturn
    //   295: astore_1
    //   296: iload 5
    //   298: istore 4
    //   300: getstatic 92	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:mCoreInstallLock	Ljava/util/concurrent/locks/Lock;
    //   303: invokeinterface 466 1 0
    //   308: iload 5
    //   310: istore 4
    //   312: aload_1
    //   313: athrow
    //   314: iconst_0
    //   315: ireturn
    //   316: astore_1
    //   317: iload 4
    //   319: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	320	0	this	MiniQBInstaller
    //   0	320	1	paramContext	Context
    //   79	35	2	i	int
    //   4	290	3	bool1	boolean
    //   7	311	4	bool2	boolean
    //   1	308	5	bool3	boolean
    //   17	51	6	bool4	boolean
    //   30	146	7	localObject	Object
    //   132	110	8	localFile	File
    // Exception table:
    //   from	to	target	type
    //   116	134	266	java/lang/Exception
    //   139	162	266	java/lang/Exception
    //   175	196	266	java/lang/Exception
    //   199	210	266	java/lang/Exception
    //   227	263	266	java/lang/Exception
    //   72	111	295	finally
    //   116	134	295	finally
    //   139	162	295	finally
    //   175	196	295	finally
    //   199	210	295	finally
    //   227	263	295	finally
    //   267	280	295	finally
    //   9	19	316	java/lang/Exception
    //   23	32	316	java/lang/Exception
    //   36	45	316	java/lang/Exception
    //   49	57	316	java/lang/Exception
    //   61	67	316	java/lang/Exception
    //   217	225	316	java/lang/Exception
    //   285	293	316	java/lang/Exception
    //   300	308	316	java/lang/Exception
    //   312	314	316	java/lang/Exception
  }
  
  void shareCore(Context paramContext, File paramFile)
  {
    try
    {
      shareAllDirsAndFiles(paramContext, new LinuxToolsJni(), paramFile);
      return;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */