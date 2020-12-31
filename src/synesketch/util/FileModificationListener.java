package synesketch.util;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import synesketch.art.util.MappingUtility;
 
/**
 *
 * @author John Yeary
 * @version 1.0
 */
public class FileModificationListener implements FileAlterationListener {
	
	private MappingUtility mapper;
	
	public FileModificationListener(MappingUtility mapper) {
		super();
		this.mapper = mapper;
	}

	/**
     * {@inheritDoc}
     */
    public void onStart(final FileAlterationObserver observer) {
        System.out.println("The WindowsFileListener has started on " + observer.getDirectory().getAbsolutePath());
    }
 
    /**
     * {@inheritDoc}
     */
    public void onDirectoryCreate(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was created.");
    }
 
    /**
     * {@inheritDoc}
     */
    public void onDirectoryChange(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was modified");
    }
 
    /**
     * {@inheritDoc}
     */
    public void onDirectoryDelete(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was deleted.");
    }
 
    /**
     * {@inheritDoc}
     */
    public void onFileCreate(final File file) {
        System.out.println(file.getAbsoluteFile() + " was created.");
        System.out.println("----------> length: " + file.length());
        System.out.println("----------> last modified: " + new Date(file.lastModified()));
        System.out.println("----------> readable: " + file.canRead());
        System.out.println("----------> writable: " + file.canWrite());
        System.out.println("----------> executable: " + file.canExecute());
    }
 
    /**
     * {@inheritDoc}
     */
    public void onFileChange(final File file) {
    	mapper.updateModifiedVisualizations();        
    }
 
    /**
     * {@inheritDoc}
     */
    public void onFileDelete(final File file) {
        System.out.println(file.getAbsoluteFile() + " was deleted.");
    }
 
    /**
     * {@inheritDoc}
     */
    public void onStop(final FileAlterationObserver observer) {
        System.out.println("The WindowsFileListener has stopped on " + observer.getDirectory().getAbsolutePath());
    }
}