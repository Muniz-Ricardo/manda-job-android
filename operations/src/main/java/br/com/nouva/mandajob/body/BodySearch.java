package br.com.nouva.mandajob.body;

@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class BodySearch {
    public String sub;
    private String type;

    public BodySearch(String type, String sub) {
        this.type = type;
        this.sub = sub;
    }
}
