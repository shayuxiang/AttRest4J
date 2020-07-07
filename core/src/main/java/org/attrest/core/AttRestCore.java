package org.attrest.core;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class AttRestCore {

   /**
    * 获取API-DOM结构
    * 
    * @param Host
    * @return
    */
   public static String Schema(String Host) {
      return SpringExt.apiEnumerable.ToSchema(Host);
   }

   /**
    * 获取API-DOM结构网页默认的Css样式
    * 
    * @return
    */
   public static String DefaultCss() {
      return SpringExt.apiEnumerable.GetDefaultCss();
   }

   /**
    * 获取客户端可拉取的代码
    * 
    * @param HttpContext
    */
   public static void ResponseScript(HttpServletResponse response) {
      ResponseScript(response, "gbk");
   }

   /**
    * 获取客户端可拉取的代码
    * 
    * @param HttpContext
    * @param encoding
    */
   public static void ResponseScript(HttpServletResponse response, String encoding) {
      // 生成代码
      String code = SpringExt.apiEnumerable.ToCode();
      // 向浏览器返回HttpResponse
      try {
         // response.getWriter().println(code);
         response.setContentType("text/javascript"); // js类型
         response.setHeader("Content-Disposition",
               "attachment;filename=" + new String("AttRestClient.js".getBytes(encoding), "ISO8859-1"));
         byte[] retcode = code.getBytes("UTF-8");
         // 6.通过response对象获取OutputStream流
         OutputStream out = response.getOutputStream();
         out.write(retcode);
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * 转MakeDown文档
    */
   public static void ToMakeDown() 
   {
   }
}