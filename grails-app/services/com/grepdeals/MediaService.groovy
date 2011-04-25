package com.grepdeals

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import com.google.gdata.client.photos.PicasawebService
import com.google.gdata.data.PlainTextConstruct
import com.google.gdata.data.media.MediaByteArraySource
import com.google.gdata.data.photos.AlbumEntry
import com.google.gdata.data.photos.PhotoEntry
import com.google.gdata.data.photos.UserFeed
import com.thebuzzmedia.imgscalr.Scalr
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.springframework.web.multipart.MultipartFile

class MediaService {

  static transactional = false
  def springSecurityService;


  public String uploadImage(MultipartFile multiPartFile) {

    PicasawebService myService = new PicasawebService("grepdeals-uploader");
    myService.setUserCredentials(CH.config.grepdeals.user.admin.email, CH.config.grepdeals.user.admin.password);

    String userEmail = springSecurityService.currentUser.email;

    URL feedUrl = new URL(CH.config.grepdeals.image.picasa.feedUrl);

    UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);
    URL albumPostUrl;


    boolean albumFound = false;
    for (AlbumEntry myAlbum: myUserFeed.getAlbumEntries()) {
      if (myAlbum.getTitle().getPlainText().equals(userEmail)) {
        albumFound = true;
        albumPostUrl = new URL(myAlbum.getFeedLink().getHref());
        break;
      }
    }

    if (!albumFound) {
      log.info("Album not found for user ${userEmail} , will create new one");
      AlbumEntry myAlbum = new AlbumEntry();

      myAlbum.setTitle(new PlainTextConstruct(userEmail));

      AlbumEntry insertedAlbum = myService.insert(feedUrl, myAlbum);
      albumPostUrl = new URL(insertedAlbum.getFeedLink().getHref());
    }

    log.info("Album Post Url ${albumPostUrl}");

    PhotoEntry myPhoto = new PhotoEntry();
    String fileName = multiPartFile.getOriginalFilename();


    String imageType = fileName.split("\\.")[1];
    log.info("imageType ${imageType}");
    String[] supportedTypes = ImageIO.getWriterFormatNames();

    if (!(imageType in supportedTypes)) {
      throw new RuntimeException("Unsupported image format ${imageType}");
    }

    myPhoto.setTitle(new PlainTextConstruct(fileName));

    BufferedImage image = resizeImage(multiPartFile.getInputStream());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, imageType, baos);
    baos.flush();
    MediaByteArraySource myMedia = new MediaByteArraySource(baos.toByteArray(), "image/${imageType}");
    baos.close();
    myPhoto.setMediaSource(myMedia);

    PhotoEntry photo = myService.insert(albumPostUrl, myPhoto);

    return photo.getMediaEditLink().href;

  }


  public BufferedImage resizeImage(InputStream is) {

    int maxImageSize = CH.config.grepdeals.image.max.upload.size;

    BufferedImage orgImage = ImageIO.read(is);
    log.info("Original Size ${orgImage.getWidth()} x ${orgImage.getHeight()}");

    if (orgImage.getHeight() < maxImageSize && orgImage.getWidth() < maxImageSize) {
      //input image itself is smaller, no need for resize.
      return orgImage;
    }

    BufferedImage resizedImage = Scalr.resize(orgImage, maxImageSize);
    log.info("Reduced Size ${resizedImage.getWidth()} x ${resizedImage.getHeight()}");
    return resizedImage;
  }


}
