public class Pronunciation {
    protected String word;
    protected String pronunciation;

    public Pronunciation(String word, String pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
    }

    public void SetWord(String word){
        this.word = word;
    }
    public void SetPronunciation(String word){
        this.word = word;
    }
    public String GetWord(String word){
        return this.word;
    }
    public String GetPronunciation(String word){
        return this.word;
    }

    public int NumOfMatchingKeys(OALDictionary<String, Pronunciation> dict) {
        int i = 0;
        for (Pronunciation p : dict.findAll(this.pronunciation)) {
            i++;
        }
        return i;
    }
}
