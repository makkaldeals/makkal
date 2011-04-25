package com.grepdeals

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import grails.test.GrailsUnitTestCase
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class MediaServiceTests extends GrailsUnitTestCase {

  def mediaService;
  private static int maxImageSize;
  private static final String testDataDir = System.properties['base.dir'] + "${File.separator}test${File.separator}unit${File.separator}testdata${File.separator}";

  protected void setUp() {
    super.setUp();

    mockLogging(MediaService);
    mediaService = new MediaService();
    mockConfig("""
               grepdeals {
                  image {
                    max.upload.size = 1600;
                  }
               }
           """);

    maxImageSize = CH.config.grepdeals.image.max.upload.size;
  }

  protected void tearDown() {
    super.tearDown()
  }



  void testResizeImagePotrait() {
    BufferedImage image = mediaService.resizeImage(new FileInputStream("${testDataDir}IMG_0374_Rotated.JPG"));
    int max = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();
    assertEquals(maxImageSize, max);
  }

  void testResizeImageLandscape() {
    BufferedImage image = mediaService.resizeImage(new FileInputStream("${testDataDir}IMG_0374.JPG"));
    int max = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();
    assertEquals(maxImageSize, max);

  }

  void testResizeImageSmaller() {
    FileInputStream fis = new FileInputStream("${testDataDir}images1.JPG");
    BufferedImage orgImage = ImageIO.read(fis);
    fis = new FileInputStream("/Users/kavi/Desktop/images1.JPG");
    BufferedImage resizedImage = mediaService.resizeImage(fis);
    assertTrue("testResizeImageSmaller Failed", (resizedImage.getWidth() == orgImage.getWidth()) && resizedImage.getHeight() == orgImage.getHeight());

  }
}
