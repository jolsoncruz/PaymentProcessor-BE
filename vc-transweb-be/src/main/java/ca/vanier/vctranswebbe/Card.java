package ca.vanier.vctranswebbe;

public class Card {
    private String name;
    private String cardNumber;
    private String cvc;
    private String expiry;

    // Constructors, getters, and setters
    public Card() {}

    public Card(String name, String cardNumber, String cvc, String expiry) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expiry = expiry;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "Card [name=" + name + ", cardNumber=" + cardNumber + ", cvc=" + cvc + ", expiry="
                + expiry + "]";
    }
}