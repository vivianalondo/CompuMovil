package co.edu.udea.compumovil.gr06_20172.lab1.POJO;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApartmentResponse {


    @SerializedName("")
    private List<Apartment> results;
    @SerializedName("total_results")
    private int totalResults;


    public List<Apartment> getResults() {
        return results;
    }

    public void setResults(List<Apartment> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }


}