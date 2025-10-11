package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;


/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private static final String API_URL ="https://dog.ceo/api";
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String SUCCESS = "success";


    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws  BreedNotFoundException{
        //  Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("%s/breed/%s/list", API_URL,breed))
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (Objects.equals(responseBody.getString(STATUS), SUCCESS)) {
                final JSONArray subBreed = responseBody.getJSONArray(MESSAGE);
                List<String> subBreedList = new ArrayList<>();
                for (int i = 0; i < subBreed.length(); i++) {
                    subBreedList.add(subBreed.getString(i));
                }
                return subBreedList;
            }
            else{
                    throw new BreedNotFoundException(breed);
                }

        }
        catch(IOException | JSONException event){
                throw new BreedNotFoundException(breed);
            }


    }
    }