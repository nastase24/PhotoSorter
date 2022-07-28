import javax.swing.*;
import java.nio.file.Path;
import java.awt.event.*;

public class BasicFileGui {
    // Use a JFileChooser to obtain a Path to a file to be read.
 //If user cancels the dialog window, return null.
 // Parameter s can be a path on the hard drive or null.
 // If s is not* null, start the JFileChooser at the path given by s. */
/*
 * credit: https://www.cs.cornell.edu/courses/JavaAndDS/files/io6JFileChooser.pdf
 */
public static Path getInputPath(String s) {
        JFileChooser jd= s == null ? new JFileChooser() : new JFileChooser(s);
        /*JButton clearResultsBox = new JButton("Clear Results");
        clearResultsBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a instanceof PhotoSorter){
                    
                    a.clearResults();

                }
            };
        } ); */
        
        //JCheckBox openResults = new JCheckBox("Open Results")
        jd.setDialogTitle("Choose input file");
            //change below if you want to select files
        jd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal= jd.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) return null;
        return jd.getSelectedFile().toPath();
}



public static void main(String[] args){
    Path directory = getInputPath("C:\\Users\\narga\\downloads");
}

}
