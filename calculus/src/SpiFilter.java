import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Richard on 24/04/2016.
 */
public class SpiFilter extends FileFilter implements java.io.FileFilter {
    private String extension;
    private String description;

    public SpiFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        } else {
            return f.getName().toLowerCase().endsWith(extension);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}
