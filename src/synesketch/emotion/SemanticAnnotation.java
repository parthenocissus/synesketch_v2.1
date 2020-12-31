package synesketch.emotion;

import synesketch.SynesketchState;

public class SemanticAnnotation extends SynesketchState {
	
	private String fileName;

	public SemanticAnnotation(String text) {
		super(text);		
	}
	
	public SemanticAnnotation(String text, String fileName) {
		super(text);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
