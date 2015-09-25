package example.com.itune.Network;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * This POJO/Model class
 */
public class ItunePojo {

    @Expose
    private Integer resultCount;
    @Expose
    private List<ResultsData> results = new ArrayList<ResultsData>();

    /**
     *
     * @return
     * The resultCount
     */
    public Integer getResultCount() {
        return resultCount;
    }

    /**
     *
     * @param resultCount
     * The resultCount
     */
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    /**
     *
     * @return
     * The results
     */
    public List<ResultsData> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ResultsData> results) {
        this.results = results;
    }


}
