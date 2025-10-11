package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    //  Task 2: Complete this class
    private int callsMade = 0;
    private HashMap<String,List<String> > cahceMap=new HashMap<>();
    private BreedFetcher fetcherStored;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        fetcherStored=fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if(cahceMap.containsKey(breed)){
            return cahceMap.get(breed);
        }else{
            callsMade++;
            List<String> findList=fetcherStored.getSubBreeds(breed);
            cahceMap.put(breed, findList);
            return findList;
        }
        // return statement included so that the starter code can compile and run.
    }

    public int getCallsMade() {
        return callsMade;
    }
}