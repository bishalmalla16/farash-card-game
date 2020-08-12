package sample.TeenPatti;

public enum Suits {
//    HEART("♥"),
//    SPREAD("♠"),
//    DIAMOND("♦"),
//    CLUB("♣");
    HEART("H"),
    SPREAD("S"),
    DIAMOND("D"),
    CLUB("C");
    private final String value;
    Suits(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

