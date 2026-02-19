package parameters.mlc.pt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import algorithms.mlc.ClassifierChain;
import executeMulan.Utils;
import mulan.classifier.MultiLabelLearner;
import parameters.LearnerParameters;
import parameters.Parameters;

/**
 * Parameters of CC
 * 
 * @author Jose M. Moyano
 *
 */
public class CCParameters extends LearnerParameters{
	
	@Override
	protected HashMap<String, String> defaultParameters(){
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("c", "J48");
		map.put("C", null);
		
		return map;
	}
	
	@Override
	protected LinkedHashMap<String, String> parameterDescription(){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		map.put("c", "Base classifier.");
		map.put("C", "Chain for CC.");
		
		return map;
	}

	@Override
	public MultiLabelLearner createObject(Parameters parameters, int seed) {
		
		parameters.printParameters();
		this.checkDefaultParameters(parameters);
		
		MultiLabelLearner learner = null;
		String chainS = parameters.getParameter("C");
		if (chainS == null) {
			System.out.println("Creating a CC with chain: random");
			learner = new ClassifierChain(
					Utils.getBaseLearner(parameters.getParameter("c")), //Classifier
					seed //Seed for random numbers
					);
		} else {
			String[] c11 = chainS.split("-");
			int[] c11i = new int[c11.length];
			for (int i = 0; i < c11.length; i++) {
				c11i[i] = Integer.parseInt(c11[i]);
			}
			
			System.out.println("Creating a CC with chain: " + Arrays.toString(c11i));
			
			learner = new ClassifierChain(
					Utils.getBaseLearner(parameters.getParameter("c")), //Classifier
					c11i
					);			
		}
		
		return learner;
	}
	
}
