package com.tencent.common.http;

import java.util.HashMap;
import java.util.regex.Pattern;

public class MimeTypeMap
{
  private static MimeTypeMap jdField_a_of_type_ComTencentCommonHttpMimeTypeMap;
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private HashMap<String, String> b = new HashMap();
  
  private static MimeTypeMap a()
  {
    MimeTypeMap localMimeTypeMap = new MimeTypeMap();
    localMimeTypeMap.a("application/andrew-inset", "ez");
    localMimeTypeMap.a("application/dsptype", "tsp");
    localMimeTypeMap.a("application/futuresplash", "spl");
    localMimeTypeMap.a("application/hta", "hta");
    localMimeTypeMap.a("application/mac-binhex40", "hqx");
    localMimeTypeMap.a("application/mac-compactpro", "cpt");
    localMimeTypeMap.a("application/mathematica", "nb");
    localMimeTypeMap.a("application/msaccess", "mdb");
    localMimeTypeMap.a("application/oda", "oda");
    localMimeTypeMap.a("application/ogg", "ogg");
    localMimeTypeMap.a("application/pdf", "pdf");
    localMimeTypeMap.a("application/pgp-keys", "key");
    localMimeTypeMap.a("application/pgp-signature", "pgp");
    localMimeTypeMap.a("application/pics-rules", "prf");
    localMimeTypeMap.a("application/rar", "rar");
    localMimeTypeMap.a("application/rdf+xml", "rdf");
    localMimeTypeMap.a("application/rss+xml", "rss");
    localMimeTypeMap.a("application/zip", "zip");
    localMimeTypeMap.a("application/vnd.android.package-archive", "apk");
    localMimeTypeMap.a("application/vnd.cinderella", "cdy");
    localMimeTypeMap.a("application/vnd.ms-pki.stl", "stl");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.database", "odb");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.formula", "odf");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.graphics", "odg");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.graphics-template", "otg");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.image", "odi");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.spreadsheet", "ods");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.spreadsheet-template", "ots");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.text", "odt");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.text-master", "odm");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.text-template", "ott");
    localMimeTypeMap.a("application/vnd.oasis.opendocument.text-web", "oth");
    localMimeTypeMap.a("application/msword", "doc");
    localMimeTypeMap.a("application/msword", "dot");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.wordprocessingml.template", "dotx");
    localMimeTypeMap.a("application/vnd.ms-excel", "xls");
    localMimeTypeMap.a("application/vnd.ms-excel", "xlt");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.spreadsheetml.template", "xltx");
    localMimeTypeMap.a("application/vnd.ms-powerpoint", "ppt");
    localMimeTypeMap.a("application/vnd.ms-powerpoint", "pot");
    localMimeTypeMap.a("application/vnd.ms-powerpoint", "pps");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.presentationml.template", "potx");
    localMimeTypeMap.a("application/vnd.openxmlformats-officedocument.presentationml.slideshow", "ppsx");
    localMimeTypeMap.a("application/vnd.rim.cod", "cod");
    localMimeTypeMap.a("application/vnd.smaf", "mmf");
    localMimeTypeMap.a("application/vnd.stardivision.calc", "sdc");
    localMimeTypeMap.a("application/vnd.stardivision.draw", "sda");
    localMimeTypeMap.a("application/vnd.stardivision.impress", "sdd");
    localMimeTypeMap.a("application/vnd.stardivision.impress", "sdp");
    localMimeTypeMap.a("application/vnd.stardivision.math", "smf");
    localMimeTypeMap.a("application/vnd.stardivision.writer", "sdw");
    localMimeTypeMap.a("application/vnd.stardivision.writer", "vor");
    localMimeTypeMap.a("application/vnd.stardivision.writer-global", "sgl");
    localMimeTypeMap.a("application/vnd.sun.xml.calc", "sxc");
    localMimeTypeMap.a("application/vnd.sun.xml.calc.template", "stc");
    localMimeTypeMap.a("application/vnd.sun.xml.draw", "sxd");
    localMimeTypeMap.a("application/vnd.sun.xml.draw.template", "std");
    localMimeTypeMap.a("application/vnd.sun.xml.impress", "sxi");
    localMimeTypeMap.a("application/vnd.sun.xml.impress.template", "sti");
    localMimeTypeMap.a("application/vnd.sun.xml.math", "sxm");
    localMimeTypeMap.a("application/vnd.sun.xml.writer", "sxw");
    localMimeTypeMap.a("application/vnd.sun.xml.writer.global", "sxg");
    localMimeTypeMap.a("application/vnd.sun.xml.writer.template", "stw");
    localMimeTypeMap.a("application/vnd.visio", "vsd");
    localMimeTypeMap.a("application/x-abiword", "abw");
    localMimeTypeMap.a("application/x-apple-diskimage", "dmg");
    localMimeTypeMap.a("application/x-bcpio", "bcpio");
    localMimeTypeMap.a("application/x-bittorrent", "torrent");
    localMimeTypeMap.a("application/x-cdf", "cdf");
    localMimeTypeMap.a("application/x-cdlink", "vcd");
    localMimeTypeMap.a("application/x-chess-pgn", "pgn");
    localMimeTypeMap.a("application/x-cpio", "cpio");
    localMimeTypeMap.a("application/x-debian-package", "deb");
    localMimeTypeMap.a("application/x-debian-package", "udeb");
    localMimeTypeMap.a("application/x-director", "dcr");
    localMimeTypeMap.a("application/x-director", "dir");
    localMimeTypeMap.a("application/x-director", "dxr");
    localMimeTypeMap.a("application/x-dms", "dms");
    localMimeTypeMap.a("application/x-doom", "wad");
    localMimeTypeMap.a("application/x-dvi", "dvi");
    localMimeTypeMap.a("application/x-flac", "flac");
    localMimeTypeMap.a("application/x-font", "pfa");
    localMimeTypeMap.a("application/x-font", "pfb");
    localMimeTypeMap.a("application/x-font", "gsf");
    localMimeTypeMap.a("application/x-font", "pcf");
    localMimeTypeMap.a("application/x-font", "pcf.Z");
    localMimeTypeMap.a("application/x-freemind", "mm");
    localMimeTypeMap.a("application/x-futuresplash", "spl");
    localMimeTypeMap.a("application/x-gnumeric", "gnumeric");
    localMimeTypeMap.a("application/x-go-sgf", "sgf");
    localMimeTypeMap.a("application/x-graphing-calculator", "gcf");
    localMimeTypeMap.a("application/x-gtar", "gtar");
    localMimeTypeMap.a("application/x-gtar", "tgz");
    localMimeTypeMap.a("application/x-gtar", "taz");
    localMimeTypeMap.a("application/x-hdf", "hdf");
    localMimeTypeMap.a("application/x-ica", "ica");
    localMimeTypeMap.a("application/x-internet-signup", "ins");
    localMimeTypeMap.a("application/x-internet-signup", "isp");
    localMimeTypeMap.a("application/x-iphone", "iii");
    localMimeTypeMap.a("application/x-iso9660-image", "iso");
    localMimeTypeMap.a("application/x-jmol", "jmz");
    localMimeTypeMap.a("application/x-kchart", "chrt");
    localMimeTypeMap.a("application/x-killustrator", "kil");
    localMimeTypeMap.a("application/x-koan", "skp");
    localMimeTypeMap.a("application/x-koan", "skd");
    localMimeTypeMap.a("application/x-koan", "skt");
    localMimeTypeMap.a("application/x-koan", "skm");
    localMimeTypeMap.a("application/x-kpresenter", "kpr");
    localMimeTypeMap.a("application/x-kpresenter", "kpt");
    localMimeTypeMap.a("application/x-kspread", "ksp");
    localMimeTypeMap.a("application/x-kword", "kwd");
    localMimeTypeMap.a("application/x-kword", "kwt");
    localMimeTypeMap.a("application/x-latex", "latex");
    localMimeTypeMap.a("application/x-lha", "lha");
    localMimeTypeMap.a("application/x-lzh", "lzh");
    localMimeTypeMap.a("application/x-lzx", "lzx");
    localMimeTypeMap.a("application/x-maker", "frm");
    localMimeTypeMap.a("application/x-maker", "maker");
    localMimeTypeMap.a("application/x-maker", "frame");
    localMimeTypeMap.a("application/x-maker", "fb");
    localMimeTypeMap.a("application/x-maker", "book");
    localMimeTypeMap.a("application/x-maker", "fbdoc");
    localMimeTypeMap.a("application/x-mif", "mif");
    localMimeTypeMap.a("application/x-ms-wmd", "wmd");
    localMimeTypeMap.a("application/x-ms-wmz", "wmz");
    localMimeTypeMap.a("application/x-msi", "msi");
    localMimeTypeMap.a("application/x-ns-proxy-autoconfig", "pac");
    localMimeTypeMap.a("application/x-nwc", "nwc");
    localMimeTypeMap.a("application/x-object", "o");
    localMimeTypeMap.a("application/x-oz-application", "oza");
    localMimeTypeMap.a("application/x-pkcs12", "p12");
    localMimeTypeMap.a("application/x-pkcs7-certreqresp", "p7r");
    localMimeTypeMap.a("application/x-pkcs7-crl", "crl");
    localMimeTypeMap.a("application/x-quicktimeplayer", "qtl");
    localMimeTypeMap.a("application/x-shar", "shar");
    localMimeTypeMap.a("application/x-shockwave-flash", "swf");
    localMimeTypeMap.a("application/x-stuffit", "sit");
    localMimeTypeMap.a("application/x-sv4cpio", "sv4cpio");
    localMimeTypeMap.a("application/x-sv4crc", "sv4crc");
    localMimeTypeMap.a("application/x-tar", "tar");
    localMimeTypeMap.a("application/x-texinfo", "texinfo");
    localMimeTypeMap.a("application/x-texinfo", "texi");
    localMimeTypeMap.a("application/x-troff", "t");
    localMimeTypeMap.a("application/x-troff", "roff");
    localMimeTypeMap.a("application/x-troff-man", "man");
    localMimeTypeMap.a("application/x-ustar", "ustar");
    localMimeTypeMap.a("application/x-wais-source", "src");
    localMimeTypeMap.a("application/x-wingz", "wz");
    localMimeTypeMap.a("application/x-webarchive", "webarchive");
    localMimeTypeMap.a("application/x-x509-ca-cert", "crt");
    localMimeTypeMap.a("application/x-x509-user-cert", "crt");
    localMimeTypeMap.a("application/x-xcf", "xcf");
    localMimeTypeMap.a("application/x-xfig", "fig");
    localMimeTypeMap.a("application/xhtml+xml", "xhtml");
    localMimeTypeMap.a("audio/3gpp", "3gpp");
    localMimeTypeMap.a("audio/amr", "amr");
    localMimeTypeMap.a("audio/basic", "snd");
    localMimeTypeMap.a("audio/midi", "mid");
    localMimeTypeMap.a("audio/midi", "midi");
    localMimeTypeMap.a("audio/midi", "kar");
    localMimeTypeMap.a("audio/midi", "xmf");
    localMimeTypeMap.a("audio/mobile-xmf", "mxmf");
    localMimeTypeMap.a("audio/mpeg", "mpga");
    localMimeTypeMap.a("audio/mpeg", "mpega");
    localMimeTypeMap.a("audio/mpeg", "mp2");
    localMimeTypeMap.a("audio/mpeg", "mp3");
    localMimeTypeMap.a("audio/mpeg", "m4a");
    localMimeTypeMap.a("audio/mpegurl", "m3u");
    localMimeTypeMap.a("audio/prs.sid", "sid");
    localMimeTypeMap.a("audio/x-aiff", "aif");
    localMimeTypeMap.a("audio/x-aiff", "aiff");
    localMimeTypeMap.a("audio/x-aiff", "aifc");
    localMimeTypeMap.a("audio/x-gsm", "gsm");
    localMimeTypeMap.a("audio/x-mpegurl", "m3u");
    localMimeTypeMap.a("audio/x-ms-wma", "wma");
    localMimeTypeMap.a("audio/x-ms-wax", "wax");
    localMimeTypeMap.a("audio/x-pn-realaudio", "ra");
    localMimeTypeMap.a("audio/x-pn-realaudio", "rm");
    localMimeTypeMap.a("audio/x-pn-realaudio", "ram");
    localMimeTypeMap.a("audio/x-realaudio", "ra");
    localMimeTypeMap.a("audio/x-scpls", "pls");
    localMimeTypeMap.a("audio/x-sd2", "sd2");
    localMimeTypeMap.a("audio/x-wav", "wav");
    localMimeTypeMap.a("image/bmp", "bmp");
    localMimeTypeMap.a("image/gif", "gif");
    localMimeTypeMap.a("image/ico", "cur");
    localMimeTypeMap.a("image/ico", "ico");
    localMimeTypeMap.a("image/ief", "ief");
    localMimeTypeMap.a("image/jpeg", "jpeg");
    localMimeTypeMap.a("image/jpeg", "jpg");
    localMimeTypeMap.a("image/jpeg", "jpe");
    localMimeTypeMap.a("image/pcx", "pcx");
    localMimeTypeMap.a("image/png", "png");
    localMimeTypeMap.a("image/svg+xml", "svg");
    localMimeTypeMap.a("image/svg+xml", "svgz");
    localMimeTypeMap.a("image/tiff", "tiff");
    localMimeTypeMap.a("image/tiff", "tif");
    localMimeTypeMap.a("image/vnd.djvu", "djvu");
    localMimeTypeMap.a("image/vnd.djvu", "djv");
    localMimeTypeMap.a("image/vnd.wap.wbmp", "wbmp");
    localMimeTypeMap.a("image/x-cmu-raster", "ras");
    localMimeTypeMap.a("image/x-coreldraw", "cdr");
    localMimeTypeMap.a("image/x-coreldrawpattern", "pat");
    localMimeTypeMap.a("image/x-coreldrawtemplate", "cdt");
    localMimeTypeMap.a("image/x-corelphotopaint", "cpt");
    localMimeTypeMap.a("image/x-icon", "ico");
    localMimeTypeMap.a("image/x-jg", "art");
    localMimeTypeMap.a("image/x-jng", "jng");
    localMimeTypeMap.a("image/x-ms-bmp", "bmp");
    localMimeTypeMap.a("image/x-photoshop", "psd");
    localMimeTypeMap.a("image/x-portable-anymap", "pnm");
    localMimeTypeMap.a("image/x-portable-bitmap", "pbm");
    localMimeTypeMap.a("image/x-portable-graymap", "pgm");
    localMimeTypeMap.a("image/x-portable-pixmap", "ppm");
    localMimeTypeMap.a("image/x-rgb", "rgb");
    localMimeTypeMap.a("image/x-xbitmap", "xbm");
    localMimeTypeMap.a("image/x-xpixmap", "xpm");
    localMimeTypeMap.a("image/x-xwindowdump", "xwd");
    localMimeTypeMap.a("model/iges", "igs");
    localMimeTypeMap.a("model/iges", "iges");
    localMimeTypeMap.a("model/mesh", "msh");
    localMimeTypeMap.a("model/mesh", "mesh");
    localMimeTypeMap.a("model/mesh", "silo");
    localMimeTypeMap.a("text/calendar", "ics");
    localMimeTypeMap.a("text/calendar", "icz");
    localMimeTypeMap.a("text/comma-separated-values", "csv");
    localMimeTypeMap.a("text/css", "css");
    localMimeTypeMap.a("text/html", "htm");
    localMimeTypeMap.a("text/html", "html");
    localMimeTypeMap.a("text/h323", "323");
    localMimeTypeMap.a("text/iuls", "uls");
    localMimeTypeMap.a("text/mathml", "mml");
    localMimeTypeMap.a("text/plain", "txt");
    localMimeTypeMap.a("text/plain", "asc");
    localMimeTypeMap.a("text/plain", "text");
    localMimeTypeMap.a("text/plain", "diff");
    localMimeTypeMap.a("text/plain", "po");
    localMimeTypeMap.a("text/richtext", "rtx");
    localMimeTypeMap.a("text/rtf", "rtf");
    localMimeTypeMap.a("text/texmacs", "ts");
    localMimeTypeMap.a("text/text", "phps");
    localMimeTypeMap.a("text/html", "php");
    localMimeTypeMap.a("text/tab-separated-values", "tsv");
    localMimeTypeMap.a("text/xml", "xml");
    localMimeTypeMap.a("text/x-bibtex", "bib");
    localMimeTypeMap.a("text/x-boo", "boo");
    localMimeTypeMap.a("text/x-c++hdr", "h++");
    localMimeTypeMap.a("text/x-c++hdr", "hpp");
    localMimeTypeMap.a("text/x-c++hdr", "hxx");
    localMimeTypeMap.a("text/x-c++hdr", "hh");
    localMimeTypeMap.a("text/x-c++src", "c++");
    localMimeTypeMap.a("text/x-c++src", "cpp");
    localMimeTypeMap.a("text/x-c++src", "cxx");
    localMimeTypeMap.a("text/x-chdr", "h");
    localMimeTypeMap.a("text/x-component", "htc");
    localMimeTypeMap.a("text/x-csh", "csh");
    localMimeTypeMap.a("text/x-csrc", "c");
    localMimeTypeMap.a("text/x-dsrc", "d");
    localMimeTypeMap.a("text/x-haskell", "hs");
    localMimeTypeMap.a("text/x-java", "java");
    localMimeTypeMap.a("text/x-literate-haskell", "lhs");
    localMimeTypeMap.a("text/x-moc", "moc");
    localMimeTypeMap.a("text/x-pascal", "p");
    localMimeTypeMap.a("text/x-pascal", "pas");
    localMimeTypeMap.a("text/x-pcs-gcd", "gcd");
    localMimeTypeMap.a("text/x-setext", "etx");
    localMimeTypeMap.a("text/x-tcl", "tcl");
    localMimeTypeMap.a("text/x-tex", "tex");
    localMimeTypeMap.a("text/x-tex", "ltx");
    localMimeTypeMap.a("text/x-tex", "sty");
    localMimeTypeMap.a("text/x-tex", "cls");
    localMimeTypeMap.a("text/x-vcalendar", "vcs");
    localMimeTypeMap.a("text/x-vcard", "vcf");
    localMimeTypeMap.a("video/3gpp", "3gpp");
    localMimeTypeMap.a("video/3gpp", "3gp");
    localMimeTypeMap.a("video/3gpp", "3g2");
    localMimeTypeMap.a("video/dl", "dl");
    localMimeTypeMap.a("video/dv", "dif");
    localMimeTypeMap.a("video/dv", "dv");
    localMimeTypeMap.a("video/fli", "fli");
    localMimeTypeMap.a("video/m4v", "m4v");
    localMimeTypeMap.a("video/mpeg", "mpeg");
    localMimeTypeMap.a("video/mpeg", "mpg");
    localMimeTypeMap.a("video/mpeg", "mpe");
    localMimeTypeMap.a("video/mp4", "mp4");
    localMimeTypeMap.a("video/f4v", "f4v");
    localMimeTypeMap.a("video/mpeg", "VOB");
    localMimeTypeMap.a("video/quicktime", "qt");
    localMimeTypeMap.a("video/quicktime", "mov");
    localMimeTypeMap.a("video/vnd.mpegurl", "mxu");
    localMimeTypeMap.a("video/x-la-asf", "lsf");
    localMimeTypeMap.a("video/x-la-asf", "lsx");
    localMimeTypeMap.a("video/x-mng", "mng");
    localMimeTypeMap.a("video/x-ms-asf", "asf");
    localMimeTypeMap.a("video/x-ms-asf", "asx");
    localMimeTypeMap.a("video/x-ms-wm", "wm");
    localMimeTypeMap.a("video/x-ms-wmv", "wmv");
    localMimeTypeMap.a("video/x-ms-wmx", "wmx");
    localMimeTypeMap.a("video/x-ms-wvx", "wvx");
    localMimeTypeMap.a("video/x-msvideo", "avi");
    localMimeTypeMap.a("video/x-sgi-movie", "movie");
    localMimeTypeMap.a("video/x-webex", "wrf");
    localMimeTypeMap.a("x-conference/x-cooltalk", "ice");
    localMimeTypeMap.a("x-epoc/x-sisx-app", "sisx");
    localMimeTypeMap.a("text/vnd.sun.j2me.app-descriptor", "jad");
    localMimeTypeMap.a("application/java-archive", "jar");
    localMimeTypeMap.a("multipart/related", "mhtml");
    localMimeTypeMap.a("multipart/related", "mht");
    return localMimeTypeMap;
  }
  
  private void a(String paramString1, String paramString2)
  {
    if (!this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString1)) {
      this.jdField_a_of_type_JavaUtilHashMap.put(paramString1, paramString2);
    }
    this.b.put(paramString2, paramString1);
  }
  
  public static String getFileExtensionFromUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      int i = paramString.lastIndexOf('?');
      String str = paramString;
      if (i > 0) {
        str = paramString.substring(0, i);
      }
      i = str.lastIndexOf('/');
      paramString = str;
      if (i >= 0) {
        paramString = str.substring(i + 1);
      }
      if ((paramString.length() > 0) && (Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%\\[\\]]+", paramString)))
      {
        i = paramString.lastIndexOf('.');
        if (i >= 0) {
          return paramString.substring(i + 1);
        }
      }
    }
    return "";
  }
  
  public static MimeTypeMap getSingleton()
  {
    if (jdField_a_of_type_ComTencentCommonHttpMimeTypeMap == null) {
      try
      {
        if (jdField_a_of_type_ComTencentCommonHttpMimeTypeMap == null) {
          jdField_a_of_type_ComTencentCommonHttpMimeTypeMap = a();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentCommonHttpMimeTypeMap;
  }
  
  public String getExtensionFromMimeType(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return (String)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    }
    return null;
  }
  
  public String getMimeTypeFromExtension(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = paramString.toLowerCase();
      return (String)this.b.get(paramString);
    }
    return null;
  }
  
  public boolean hasExtension(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return this.b.containsKey(paramString);
    }
    return false;
  }
  
  public boolean hasMimeType(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString);
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MimeTypeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */