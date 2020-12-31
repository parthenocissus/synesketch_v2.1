package synesketch.emotion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModel;

import processing.core.PApplet;
import synesketch.Synesthetiator;
import synesketch.util.SemanticAnnotationUtility;

public class SynesthetiatorEmotionSemantic extends Synesthetiator {

	private List<SemanticAnnotation> emotionalStates = new ArrayList<SemanticAnnotation>();
	
	private int count;

	private Empathyscope empathyscope;
	
	private String fileNameBegin = "ann/txt/annotatedText";
	private String extension = ".jsonld";	

	/**
	 * Class constructor that sets parent Processing applet ({@link PApplet}).
	 * Parent applet is to be notified about the text event -- the recognition
	 * of a current emotion in text.
	 * 
	 * @param parent
	 * @throws Exception
	 */
	public SynesthetiatorEmotionSemantic(PApplet parent) throws Exception {
		super(parent);
		empathyscope = Empathyscope.getInstance();
		count = 0;
	}

	/**
	 * Defines behaviour of transferring affective textual information into
	 * visual information (defines the synesthetic ablilites).
	 * 
	 * @param text
	 *            {@link String} containing the text which is to be analyzed
	 * @throws Exception
	 */
	@Override
	public void synesthetise(String text) throws IOException {
		
		EmotionalState current = empathyscope.feel(text);
		
		OntModel model = SemanticAnnotationUtility.createEmotionModel(current);
		
		String fileName = fileNameBegin + count++ + extension;
		OutputStream out = new FileOutputStream(fileName);
		//model.write(System.out, "JSON-LD");
		model.write(out, "JSON-LD");		
		out.close();
		
		SemanticAnnotation sa = new SemanticAnnotation(text, fileName);
		emotionalStates.add(sa);
		
		notifyPApplet(sa);
	}

}
