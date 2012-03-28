package nl.kennisnet.arena.web.fileserver;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import nl.kennisnet.arena.services.PictureService;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
   
   private final PictureService pictureService;

   private final CompositeConfiguration configuration;

   @Autowired
   public FileController(PictureService questService,CompositeConfiguration configuration) {
       super();
       this.pictureService = questService;
       this.configuration = configuration;
   }

   
   
   @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
   public void processPictureUpload1(@RequestParam("uploadFormElement") MultipartFile image, HttpServletResponse response)
         throws IOException {
      
      try {
         byte[] newUrl=send(image);
         response.setContentType("text/html");
         response.getOutputStream().write(newUrl);
         response.getOutputStream().print("&extension=."+getExtension(image.getOriginalFilename()));
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }

   private String getExtension(String fileName){
      String result=fileName.substring(fileName.lastIndexOf('.')+1);
      return result;
   }
   
   @RequestMapping("/fileDownload")
   public void showPicture(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
       byte[] pictureContent = pictureService.getPictureContent(id);
       response.setContentType(pictureService.getPictureContentType(id));
       FileCopyUtils.copy(pictureContent, response.getOutputStream());
   }

   public byte[] send(MultipartFile image) throws Exception {
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost("http://betelgeuse-filestore.appspot.com/fileupload");

      MultipartEntity reqEntity = new MultipartEntity();
      ContentBody body=new InputStreamBody(image.getInputStream(),image.getContentType(), image.getName());
      reqEntity.addPart("main", body);
      httppost.setEntity(reqEntity);

      HttpResponse response = httpclient.execute(httppost);
      HttpEntity resEntity = response.getEntity();
      return FileCopyUtils.copyToByteArray(resEntity.getContent());
   }

}
