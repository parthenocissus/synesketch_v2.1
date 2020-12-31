package synesketch.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import synesketch.SynesketchState;
import synesketch.emotion.EmotionalState;
import synesketch.emotion.Empathyscope;
import synesketch.emotion.SemanticAnnotation;

public class Test {
	
	private static String fileNameBegin = "ann/txt/annotatedText";
	private static String extension = ".jsonld";	
	
	public static void main(String[] args) {
		
		try {
			
			String text = "I am happy and sad.";
			
			//EmotionalState es = Empathyscope.getInstance().feel("Chairs are made of wood");
			SynesketchState es = Empathyscope.getInstance().feel(text);
			OntModel model = SemanticAnnotationUtility.createEmotionModel((EmotionalState) es);
			
			/*String fileName = fileNameBegin + 122 + extension;
			OutputStream out = new FileOutputStream(fileName);
			//model.write(System.out, "JSON-LD");
			model.write(out, "JSON-LD");		
			out.close();
			
			SemanticAnnotation sa = new SemanticAnnotation(text, fileName);
			
			Map<String, Double> results = SemanticAnnotationUtility.getAllEmotions(sa);
			
			System.out.println(results);
			
			for (Entry<String, Double> emotionAnnotation : results.entrySet()) {
				
				String uriEmotion = emotionAnnotation.getKey();
				double emotionalWeight = emotionAnnotation.getValue();
				System.out.println("EMOTION: " + uriEmotion + ", WEIGHT: " + emotionalWeight);
			}
			*/
			
			//OntModel model = ModelFactory.createOntologyModel();
			//model.read("ann/viz/visuals/synemaniaAnger0.jsonld");
			
			model.write(System.out, "TURTLE");			
			//model.write(System.out, "JSON-LD");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
