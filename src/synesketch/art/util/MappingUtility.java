package synesketch.art.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import processing.core.PApplet;
import synesketch.SynesketchState;
import synesketch.emotion.Emotion;
import synesketch.emotion.EmotionalState;
import synesketch.emotion.SemanticAnnotation;
import synesketch.util.FileModificationListener;
import synesketch.util.SemanticAnnotationUtility;

public class MappingUtility {

	private static MappingUtility instance;

	private SynesketchVisualization currentVis;

	//private String annotationPath = "ann/viz/visuals/";

	private String visualizationSettingsAnnotation = "ann/viz/settings/visualizationSettingsAnnotation.jsonld";
	
	private SynesketchVisualization[] visualizations;

	private PApplet sketch;

	private MappingUtility(PApplet sketch) throws Exception {
		visualizations = new SynesketchVisualization[7];
		this.sketch = sketch;
		
		String neutralClassName = SemanticAnnotationUtility.getVisualizationClass(visualizationSettingsAnnotation, Emotion.NEUTRAL);
		visualizations[Emotion.NEUTRAL] = createVisualization(neutralClassName);

		//visualizations[Emotion.NEUTRAL] = new SynesketchVisualizationDefaultNeutral(sketch);
		//visualizations[Emotion.NEUTRAL] = createVisualization(fileNames[Emotion.NEUTRAL], sketch);
		
		currentVis = visualizations[Emotion.NEUTRAL];
	}

	/**
	 * Returns the Singleton instance of the {@link MappingUtility}.
	 * 
	 * @return {@link MappingUtility} instance
	 * @throws IOException
	 */
	public static MappingUtility getInstance(PApplet sketch) throws Exception {
		if (instance == null) {
			instance = new MappingUtility(sketch);
		}
		return instance;
	}

	public void loadVisualizations() {
		(new VizBuffer()).start();
		createVisualizationDirectoryListener();
	}

	public void update(SynesketchState state) {
		SemanticAnnotation sa = (SemanticAnnotation) state;

		Map<String, Double> emotions = SemanticAnnotationUtility.getAllEmotions(sa);

		EmotionalState currentEmotionalState = new EmotionalState(sa.getText(), emotions);
		System.out.println(currentEmotionalState);

		Emotion strongest = currentEmotionalState.getStrongestEmotion();
		int currentEmotion = strongest.getType();

		if (visualizations[currentEmotion] == null) {
			try {
				String emotionClassName = SemanticAnnotationUtility.getVisualizationClass(visualizationSettingsAnnotation, currentEmotion);
				visualizations[currentEmotion] = createVisualization(emotionClassName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		currentVis = visualizations[currentEmotion];
		currentVis.setWeight((float) strongest.getWeight());
	}

	public void draw() {
		currentVis.draw();
	}

	/*public void updateModifiedVisualization(String fileName) {		
		try {
			SynesketchVisualization modifiedViz = createVisualization(fileName, sketch);
			visualizations[modifiedViz.getEmotionType()] = modifiedViz;
			if (vis.getEmotionType() == modifiedViz.getEmotionType()) {
				vis = modifiedViz;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public void updateModifiedVisualizations() {
		try {
			Map<String, Integer> modifiedVisualizations = SemanticAnnotationUtility.getAllVisualizations(visualizationSettingsAnnotation);
			
			System.out.println("UPDATING");

			for (Entry<String, Integer> visualization : modifiedVisualizations.entrySet()) {
				
				String className = visualization.getKey();
				int emoID = visualization.getValue().intValue();

				if (!className.equals(visualizations[emoID].getClassName())) {
					visualizations[emoID] = createVisualization(className);
					if (currentVis.getEmotionType() == visualizations[emoID].getEmotionType()) {
						currentVis = visualizations[emoID];
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}

	private void createVisualizationDirectoryListener() {

		final File directory = new File(System.getProperty("user.dir") + "/ann/viz/settings");
		FileAlterationObserver fao = new FileAlterationObserver(directory);
		fao.addListener(new FileModificationListener(this));
		final FileAlterationMonitor monitor = new FileAlterationMonitor(30 * 1000);
		monitor.addObserver(fao);

		System.out.println("Starting monitor. CTRL+C to stop.");
		try {
			monitor.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			public void run() {
				try {
					System.out.println("Stopping monitor.");
					monitor.stop();
				} catch (Exception ignored) {
				}
			}
		}));
	}

	private SynesketchVisualization createVisualization(String className) 
			throws ClassNotFoundException,	NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> visualization = Class.forName(className);
		Constructor<?> vizConstructor = visualization.getConstructor(PApplet.class, String.class);
		return (SynesketchVisualization) vizConstructor.newInstance(sketch, className);
	}

	private void loadAllEmotionVisualizations() 
			throws ClassNotFoundException, 
			NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Map<String, Integer> allVisualizations = SemanticAnnotationUtility.getAllVisualizations(visualizationSettingsAnnotation);
		
		for (Entry<String, Integer> visualization : allVisualizations.entrySet()) {

			String className = visualization.getKey();
			int emoID = (visualization.getValue()).intValue();
			
			System.out.println(className + " " + emoID);
			
			visualizations[emoID] = createVisualization(className);
			if (emoID == Emotion.NEUTRAL) currentVis = visualizations[emoID];
		}
		
	}

	class VizBuffer extends Thread {

		public void run() {
			try {
				loadAllEmotionVisualizations();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}



}
