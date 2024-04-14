package ca.vanier.vctranswebbe.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.vanier.vctranswebbe.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class FirebaseService {
    
    private final DatabaseReference database;

    public FirebaseService(FirebaseApp firebaseApp) {
        this.database = FirebaseDatabase.getInstance(firebaseApp).getReference();
    }


    public void addCard(Card card) {
        Map<String, Object> cardValues = cardToMap(card);

        DatabaseReference cardsRef = database.child("cards");
        cardsRef.push().setValue(cardValues, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Error adding card to database: " + databaseError.getMessage());
            } else {
                System.out.println("Card added successfully!");
            }
        });
    }

    public void deleteCard(String cardId) {
        DatabaseReference cardRef = database.child("cards").child(cardId);
        cardRef.removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Error deleting card from database: " + databaseError.getMessage());
            } else {
                System.out.println("Card deleted successfully!");
            }
        });
    }

    public void getAllCards(Consumer<List<Card>> callback) {
        DatabaseReference cardsRef = database.child("cards");
        List<Card> cards = new ArrayList<>();
    
        cardsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Card card = snapshot.getValue(Card.class);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                callback.accept(cards);
            }
    
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error retrieving cards from database: " + databaseError.getMessage());
                // You can handle error cases here if needed
            }
        });
    }

    public Card getSpecificCard(String cardId) {
        DatabaseReference cardRef = database.child("cards").child(cardId);
        Card card = new Card();
        
        cardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Card c = dataSnapshot.getValue(Card.class);
                //System.out.println(c);
                if (c != null) {
                    card.setCardNumber(c.getCardNumber());
                    card.setName(c.getName());
                    card.setExpiry(c.getExpiry());
                    card.setCvc(c.getCvc());
                }
            }
    
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error retrieving card from database: " + databaseError.getMessage());
            }
        });
        System.out.println(card);
        return card;
    }

    private Map<String, Object> cardToMap(Card card) {
        Map<String, Object> cardValues = new HashMap<>();
        cardValues.put("name", card.getName());
        cardValues.put("cardNumber", card.getCardNumber());
        cardValues.put("cvc", card.getCvc());
        cardValues.put("expiry", card.getExpiry());
        return cardValues;
    }
    
}
