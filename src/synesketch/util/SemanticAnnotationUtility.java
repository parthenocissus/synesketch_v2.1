package synesketch.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import synesketch.emotion.EmotionalState;
import synesketch.emotion.SemanticAnnotation;

public class SemanticAnnotationUtility {
	
	public static final String PROV_NS = "http://www.w3.org/ns/prov#";
	public static final String ONYX_NS = "http://www.gsi.dit.upm.es/ontologies/onyx/ns#";
	public static final String MARL_NS = "http://www.gsi.dit.upm.es/ontologies/marl#";	
	public static final String NIF_NS = "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#";
	public static final String DC_NS = "http://purl.org/dc/terms/";
	public static final String ITSRDF_NS = "http://www.w3.org/2005/11/its/rdf#";
	public static final String RDFSYNTAX_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String W3_NS = "https://www.w3.org/2001/XMLSchema#";	
	public static final String WNA_NS = "http://www.gsi.dit.upm.es/ontologies/wnaffect/ns#";
	public static final String EMOML = "http://www.gsi.dit.upm.es/ontologies/onyx/vocabularies/emotionml/ns#";
	
	public static final String SYNESKETCH_URL = "http://krcadinac.com/synesketch/";
	
	private static QueryExecutor queryExecutor = new QueryExecutor();
	
	public static OntModel createEmotionModel(EmotionalState arg) {

		//creating empty model (graph)
		OntModel graph = ModelFactory.createOntologyModel();
		
		// name spaces
		graph.setNsPrefix("dc", DC_NS);	
		graph.setNsPrefix("marl", MARL_NS);
		graph.setNsPrefix("onyx", ONYX_NS);
		graph.setNsPrefix("nif", NIF_NS);
		graph.setNsPrefix("prov", PROV_NS);
		graph.setNsPrefix("irsrdf", ITSRDF_NS);
		
		// declaring properties
		
		Property rdfType = graph.createProperty(RDFSYNTAX_NS + "type");
		
		Property dcLanguage = graph.createProperty(DC_NS, "language");
		
		//Property onyxMaxEmotionIntensity = graph.createProperty(ONYX_NS, "maxEmotionIntensity");
		//Property onyxMinEmotionIntensity = graph.createProperty(ONYX_NS, "minEmotionIntensity");
		
		Property onyxHasEmotionCategory = graph.createProperty(ONYX_NS, "hasEmotionCategory");
		Property onyxHasEmotionIntensity = graph.createProperty(ONYX_NS, "hasEmotionIntensity");
		//Property onyxHasEmotionSet = graph.createProperty(ONYX_NS, "hasEmotionSet");
		Property onyxHasEmotion = graph.createProperty(ONYX_NS, "hasEmotion");
		Property onyxEmotionSet = graph.createProperty(ONYX_NS, "EmotionSet");
		Property onyxUsesEmotionModel = graph.createProperty(ONYX_NS, "usesEmotionModel");
		Property onyxEmotion = graph.createProperty(ONYX_NS, "Emotion");
		Property onyxEmotionAnalysis = graph.createProperty(ONYX_NS, "EmotionAnalysis");
		Property onyxAlgorithm = graph.createProperty(ONYX_NS, "algorithm");
		Property onyxExtractedFrom = graph.createProperty(ONYX_NS, "extractedFrom");
		
		Property provGenerated = graph.createProperty(PROV_NS + "generated");
		Property provWasAssociatedWith = graph.createProperty(PROV_NS, "wasAssociatedWith");
		Property provAgent = graph.createProperty(PROV_NS + "Agent");
		
		Property wnaOnyxModel = graph.createProperty(WNA_NS + "OnyxModel");
		
		/*Property wnaHappiness = graph.createProperty(WNA_NS + "happiness");
		Property wnaSadness = graph.createProperty(WNA_NS + "sadness");
		Property wnaSurprise = graph.createProperty(WNA_NS + "surprise");
		Property wnaAnger = graph.createProperty(WNA_NS + "anger");
		Property wnaFear = graph.createProperty(WNA_NS + "fear");
		Property wnaDisgust = graph.createProperty(WNA_NS + "disgust");
		Property wnaNeutral = graph.createProperty(WNA_NS + "neutral-emotion");*/
		
		Property emomlModel = graph.createProperty(EMOML + "big6");
		
		Property emomlHappiness = graph.createProperty(EMOML + "happiness");
		Property emomlSadness = graph.createProperty(EMOML + "sadness");
		Property emomlSurprise = graph.createProperty(EMOML + "surprise");
		Property emomlAnger = graph.createProperty(EMOML + "anger");
		Property emomlFear = graph.createProperty(EMOML + "fear");
		Property emomlDisgust = graph.createProperty(EMOML + "disgust");
		
		Property wnaNeutral = graph.createProperty(WNA_NS + "neutral-emotion");
		
		//Property nifIsString = graph.createProperty(NIF_NS + "isString");
		//Property nifContext = graph.createProperty(NIF_NS + "Context");
		
		Property defaultModel = emomlModel;
		
		long idByMillis = System.currentTimeMillis();
		int idByRandom = (new Random()).nextInt(1000);
		String currentId = String.valueOf(idByMillis) + String.valueOf(idByRandom);  

		// create emotions
		double h = arg.getHappinessWeight();
		double sd = arg.getSadnessWeight();
		double a = arg.getAngerWeight();
		double f = arg.getFearWeight();
		double d = arg.getDisgustWeight();
		double su = arg.getSurpriseWeight();
		
		//ArrayList<RDFNode> emotionResourceList = new ArrayList<RDFNode>();
		
		Resource emotionSet = graph.createResource(SYNESKETCH_URL + "EmotionSet/" + currentId)
		.addProperty(rdfType, onyxEmotionSet)
		.addProperty(onyxExtractedFrom, arg.getText());
		int emotionCount = 0;
		
		if (h > 0) {
			Resource happiness = graph.createResource(SYNESKETCH_URL + "Happiness/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlHappiness)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(h), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, happiness);
			emotionCount++;
		}		
		if (sd > 0) {
			Resource sadness = graph.createResource(SYNESKETCH_URL + "Sadness/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlSadness)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(sd), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, sadness);
			emotionCount++;
		}		
		if (a > 0) {
			Resource anger = graph.createResource(SYNESKETCH_URL + "Anger/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlAnger)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(a), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, anger);
			emotionCount++;
		}
		if (f > 0) {
			Resource fear = graph.createResource(SYNESKETCH_URL + "Fear/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlFear)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(f), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, fear);
			emotionCount++;
		}
		if (d > 0) {
			Resource disgust = graph.createResource(SYNESKETCH_URL + "Disgust/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlDisgust)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(d), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, disgust);
			emotionCount++;
		}
		if (su > 0) {
			Resource surprise = graph.createResource(SYNESKETCH_URL + "Surprise/" + currentId)
					.addProperty(rdfType, onyxEmotion)
					.addProperty(onyxHasEmotionCategory, emomlSurprise)
					.addProperty(onyxHasEmotionIntensity, String.valueOf(su), XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, surprise);
			emotionCount++;
		}
		
		if (emotionCount == 0) {
			Resource neutral = graph.createResource(SYNESKETCH_URL + "Neutral/" + currentId)
					.addProperty(rdfType, onyxEmotion)					
					.addProperty(onyxHasEmotionCategory, wnaNeutral)
					.addProperty(onyxHasEmotionIntensity, "1.0", XSDDatatype.XSDdouble);
			emotionSet.addProperty(onyxHasEmotion, neutral);
			defaultModel = wnaOnyxModel;
		}
		
		// create instances
		graph.createResource(SYNESKETCH_URL + "Analysis/" + currentId)
		.addProperty(rdfType, onyxEmotionAnalysis)
		.addProperty(onyxAlgorithm, "Synesketch")
		.addProperty(dcLanguage, "en")
		.addProperty(onyxUsesEmotionModel, defaultModel)
		.addProperty(provWasAssociatedWith, graph.createResource(SYNESKETCH_URL).addProperty(rdfType, provAgent))
		.addProperty(provGenerated, emotionSet);
		
		return graph;
	}
	
	public static Map<String, Double> getAllEmotions(SemanticAnnotation sa) {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(sa.getFileName());
		
		Map<String, Double> emotions = new HashMap<String, Double>();
		
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
				"PREFIX prov: <http://www.w3.org/ns/prov#> " + 
				"PREFIX onyx: <http://www.gsi.dit.upm.es/ontologies/onyx/ns#> " + 
				"SELECT ?emotionName ?intensity " +
				"WHERE { " +
					"?analysis a onyx:EmotionAnalysis ; " +
					"			prov:generated/onyx:hasEmotion ?emotion . " +
					"?emotion onyx:hasEmotionCategory ?emotionName ;" +
					"			 onyx:hasEmotionIntensity ?intensity ." +
				"}";
				
		ResultSet resultSet = queryExecutor.executeSelectSparqlQuery(query, model);
		
		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.nextSolution();
			RDFNode emotionResource = solution.get("emotionName");
			RDFNode intensityProperty = solution.get("intensity");
			
			String emotion = ((Resource) emotionResource).getURI();
			double intensity = Double.parseDouble(((Literal) intensityProperty).getLexicalForm());
			
			System.out.println(emotion + " " + intensity);
			
			emotions.put(emotion, intensity);
		}
		
		return emotions;
	}
	
	public static Map<String, Integer> getAllVisualizations(String fileName) {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(fileName);
		
		Map<String, Integer> visualizations = new HashMap<String, Integer>();
		
		//String query = "SELECT ?x ?vis" +	"WHERE { " + "?x <http://schema.org/name> ?vis . " + "}";
		String query = "SELECT ?x ?vis ?e " +
			"WHERE { " +
				"?x <http://www.gsi.dit.upm.es/ontologies/onyx/ns#hasEmotionCategory> ?e ." +
				"?x <http://schema.org/name> ?vis . " +
			"}";
		
		ResultSet resultSet = queryExecutor.executeSelectSparqlQuery(query, model);
		
		while (resultSet.hasNext()) {			
			QuerySolution solution = resultSet.nextSolution();
			
			//String code = ((Literal) solution.get("x")).getLexicalForm();
			//String code = solution.get("x").asLiteral().getLexicalForm();
			String code = solution.get("x").toString();
			String[] parsed = code.split(SYNESKETCH_URL + "emotion");
			Integer emoId = Integer.parseInt(parsed[1]);
			
			visualizations.put(solution.get("vis").asLiteral().getLexicalForm(), emoId);
		}
		
		return visualizations;
	}
	
	public static String getVisualizationClass(String fileName, int emoType) {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(fileName);
		
		String query = "SELECT ?vis " +
			"WHERE { " +
				"<" + SYNESKETCH_URL + "emotion" + emoType + "> <http://schema.org/name> ?vis . " +
			"}";
		
		String returnValueClassName = "";
		
		ResultSet resultSet = queryExecutor.executeSelectSparqlQuery(query, model);
		
		while (resultSet.hasNext()) {			
			QuerySolution solution = resultSet.nextSolution();
			returnValueClassName = solution.get("vis").asLiteral().getLexicalForm();
			System.out.println("CLASS> " + returnValueClassName);
		}
		
		return returnValueClassName;
	}
	
}