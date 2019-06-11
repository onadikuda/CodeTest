package com.test.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCompare {
	
    public static boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
     for (int x = 0; x < img1.getWidth(); x++) {
      for (int y = 0; y < img1.getHeight(); y++) {
    	  if (img1.getRGB(x, y) != img2.getRGB(x, y))
        return false;
       
       }
      }
     } else {
    	 return false;
     } 
    return true;
     
    }

    public static void main(String args[]) throws IOException {
    	BufferedImage imgA = ImageIO.read(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg"));
        BufferedImage imgB = ImageIO.read(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg"));   
        bufferedImagesEqual(imgA, imgB);
    	
    }
}
