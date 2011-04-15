package com.grepdeals

import java.io.File;
import java.net.URL;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.web.multipart.MultipartFile

class MediaService {

  static transactional = false
  def springSecurityService;

  def String uploadImage(MultipartFile multiPartFile) {

    PicasawebService myService = new PicasawebService("grepdeals-uploader");
    myService.setUserCredentials(CH.config.grepdeals.user.admin.email, CH.config.grepdeals.user.admin.password);
    URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/admin@grepdeals.com");


    //FIXME: Should create one album per user. First check if album already exists and create if required.
    // All users photos should be uploaded to user's album
    
    /*UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);


for (AlbumEntry myAlbum: myUserFeed.getAlbumEntries()) {
log.info(myAlbum.getTitle().getPlainText());
}

    String userEmail = springSecurityService.currentUser.email;
    AlbumEntry myAlbum = new AlbumEntry();

    myAlbum.setTitle(new PlainTextConstruct("grepdeals-${userEmail}"));

    AlbumEntry insertedAlbum = myService.insert(feedUrl, myAlbum);
    URL albumPostUrl = new URL(insertedAlbum.getFeedLink().getHref());
    */

    URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/admin@grepdeals.com/albumid/default");

    PhotoEntry myPhoto = new PhotoEntry();

    myPhoto.setTitle(new PlainTextConstruct(multiPartFile.getOriginalFilename()));

    //FIXME: should reduce size of photo on uploading.
    myPhoto.setSize(100);
    MediaStreamSource myMedia = new MediaStreamSource(multiPartFile.getInputStream(), "image/jpeg");
    myPhoto.setMediaSource(myMedia);
    
    PhotoEntry photo = myService.insert(feedUrl, myPhoto);

    return  photo.getMediaEditLink().href;

  }
}
