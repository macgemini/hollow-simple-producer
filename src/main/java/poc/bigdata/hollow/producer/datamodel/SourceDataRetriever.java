/*
 *
 *  Copyright 2016 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package poc.bigdata.hollow.producer.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SourceDataRetriever {

	private final List<AlreadyApplied> allAlreadyApplieds;
	private final Random rand;
	
	private int nextAlreadyAppliedId;
	
	public SourceDataRetriever() {
		this.allAlreadyApplieds = new ArrayList<AlreadyApplied>();
		this.rand = new Random();
		bootstrapData();
	}
	
    /**
     * Retrieve all movies from the source of truth.
     */
    public List<AlreadyApplied> retrieveAllAlreadyApplied() {
    	// change a few movie titles
    	int numNamesToChange = rand.nextInt(5);
    	
    	for(int i=0;i<numNamesToChange;i++) {
    		AlreadyApplied alreadyApplied = allAlreadyApplieds.get(rand.nextInt(allAlreadyApplieds.size()));
    	}

    	
    	// modify a few movie cast lists
    	int numCastListsToEdit = rand.nextInt(5);
    	for(int i=0;i<numCastListsToEdit;i++) {
    		AlreadyApplied alreadyApplied = allAlreadyApplieds.get(rand.nextInt(allAlreadyApplieds.size()));

    	}
    	
    	/// remove a few alreadyApplied
    	int numAlreadyAppliedToRemove = rand.nextInt(3);
    	for(int i=0;i<numAlreadyAppliedToRemove;i++)
    		allAlreadyApplieds.remove(rand.nextInt(allAlreadyApplieds.size()));
    	
    	/// add a few AlreadyApplied
    	int numAlreadyAppliedToAdd = rand.nextInt(3);
    	for(int i=0;i<numAlreadyAppliedToAdd;i++)
    		allAlreadyApplieds.add(generateNewRandomAlreadyApplied());
    	
    	return allAlreadyApplieds;
    }
    
    private List<AlreadyApplied> bootstrapData() {
        nextAlreadyAppliedId = 1000000;

        for(int i=0;i<100000;i++)
            allAlreadyApplieds.add(generateNewRandomAlreadyApplied());
        
        return allAlreadyApplieds;
    }

    
    private AlreadyApplied generateNewRandomAlreadyApplied() {
        int numActors = rand.nextInt(25) + 1;

        
        return new AlreadyApplied(++nextAlreadyAppliedId);
    }

    private String generateRandomString() {
        Random rand = new Random();
        
        StringBuilder str = new StringBuilder();
        int nameChars = rand.nextInt(20) + 5;
        
        for(int j=0;j<nameChars;j++) {
            str.append((char)(rand.nextInt(26) + 97));
        }
        return str.toString();
    }
    
    


}
