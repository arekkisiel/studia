public class SBSAnalysisHandler {
    private int currentSeries;
    private int currentWord;
    private int[] currentStates;

    public SBSAnalysisHandler(int series, int word, int[] states){
        this.currentSeries = series;
        this.currentStates = states;
        this.currentWord = word;
    }

    public SBSAnalysisHandler setWord(int word){
        this.currentWord = word;
        return this;
    }

    public SBSAnalysisHandler setSeries(int series){
        this.currentSeries = series;
        return this;
    }

    public SBSAnalysisHandler setStates(int[] states){
        this.currentStates = states;
        return this;
    }

    public int getWord(){
        return this.currentWord;
    }

    public int getSeries(){
        return this.currentSeries;
    }

    public int[] getStates(){
        return this.currentStates;
    }
}
