import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.lang.String;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FilenameFilter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Desktop;
import java.awt.Desktop.Action;

public class PhotoSorter
{
    public Path path;
    public File[] fileList;
    public BufferedImage[] imageArray;
    public File dir,parent,results;
    public int[][] minMaxRGB;
    int[] redArr;
    int[] greenArr;
    int[] blueArr;
    int[] alphaArr;
    public PhotoSorter(){
        //dir = new File(System.getProperty("user.dir"));
        parent = new File(BasicFileGui.getInputPath("C:\\Users").toString());
        results = new File(parent,"results");
        results.mkdir();
        
        fileList = parent.listFiles(new FilenameFilter()
            {
                //Creates an anonymous class, that overrides the accept method to search for file extensions.
                @Override
                public boolean accept(File directory, String fileName) {
                    if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                        return true;
                    }
                    return false;
                }

            });

        imageArray = new BufferedImage[fileList.length];
        try{
            for(int i = 0; i < fileList.length; i++){
                imageArray[i] = ImageIO.read(fileList[i]);
            } 
            int[] redArr = new int[this.imageArray.length];
            int[] greenArr = new int[this.imageArray.length];
            int[] blueArr = new int[this.imageArray.length];
            int[] alphaArr = new int[this.imageArray.length];
        }catch(IOException e){

        }
    }
    /*
    public  boolean clearResults(){
        if(this.results == null){
            System.out.println("No Results Folder");
            return false;
        }else{
            results.delete();
            results.mkdirs();
        }
    }
    
    Computes the average color of parameter BufferedImage, returns the color
     */
    public static Color averageColor(BufferedImage bi){
        if(bi == null){
            return null;
        }
        int w,h,i,j;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        w = bi.getWidth();
        h = bi.getHeight();
        int sum = w * h;
        for(i = 0; i < w; i++){
            for(j = 0; j < h; j++){
                Color pix = new Color(bi.getRGB(i,j));
                r+=pix.getRed();
                g+=pix.getGreen();
                b+=pix.getBlue(); 
                a+=pix.getAlpha();
            }
        }
        r/=sum;
        g/=sum;
        b/=sum;
        a/=sum;
        if(a < 0){
            a*=-1;
        }
        if(r < 0){
            r*=-1;
        }
        if(g < 0){
            g*=-1;
        }
        if(b < 0){
            b*=-1;
        }
        return new Color(r, g, b,255);
    }

    public void createImages(){
        if(this.imageArray.length == 0){
            System.out.println("No images found in directory " + parent.getAbsolutePath());
            return;     
        }
        for(int i = 0; i < this.imageArray.length; i++){
            Color tempColor = averageColor(this.imageArray[i]);
            int borderWidth = this.imageArray[i].getWidth()/8;
            int borderHeight = this.imageArray[i].getHeight()/8;
            BufferedImage result = new BufferedImage(2 * borderWidth + this.imageArray[i].getWidth(), 2 * borderHeight + this.imageArray[i].getHeight() ,BufferedImage.TYPE_INT_ARGB);
            Graphics g = result.createGraphics();
            g.setColor(tempColor);
            g.fillRect(0,0,result.getWidth(), result.getHeight());
            g.setColor(Color.white); 
            g.setFont(new Font("HighTowerText", Font.PLAIN, 72));
            String text = ("COLOR: #" + Integer.toHexString(tempColor.getRGB())).toUpperCase();
            g.drawString(text, borderWidth,this.imageArray[i].getHeight() + borderHeight + borderHeight/2 );
            g.drawImage(imageArray[i],borderWidth,borderHeight, null);
            try{
                ImageIO.write(result,"PNG", new File(parent.getAbsolutePath() + "\\results\\" + i + ".png"));
            }catch(IOException e){
                System.out.println("Error Writing Image.");
            }
        }
    }

    public static void main(String[] args){
        System.out.println("Initialized.");
        
        PhotoSorter sorter = new PhotoSorter();
        sorter.createImages();
        try{
            Desktop.getDesktop().open(sorter.results);

        }catch(IOException e){
            System.out.println("Error Opening Explorer.");
        }
        System.out.println("Done.");

    }
}