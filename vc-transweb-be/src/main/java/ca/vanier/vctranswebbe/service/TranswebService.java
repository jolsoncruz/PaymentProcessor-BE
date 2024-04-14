package ca.vanier.vctranswebbe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.vanier.vctranswebbe.Card;

@Service
public class TranswebService {
    private final DatabaseReference database;

    public TranswebService(FirebaseApp firebaseApp) {
        this.database = FirebaseDatabase.getInstance(firebaseApp).getReference();
    }

    public void addCard(Card c) {
        Map<String, Card> card = new HashMap<>();
        DatabaseReference cardsRef = database.child("cards");

        try{
            card.put(c.getCardNumber(), c);
            cardsRef.setValueAsync(card);
        } catch(Exception ex){
            System.err.println("Error adding card to database: " + ex.getMessage());
        }
    }

    public CompletableFuture<List<Card>> getAllCardsAsync() {
        CompletableFuture<List<Card>> future = new CompletableFuture<>();
        List<Card> cards = new ArrayList<>();
        DatabaseReference cardsRef = database.child("cards");
    
        cardsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot cardSnapshot : snapshot.getChildren()) {
                    Card card = cardSnapshot.getValue(Card.class);
                    cards.add(card);
                }
                future.complete(cards);
            }
    
            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
    
        return future;
    }

    public List<Card> getAllCards() {
        CompletableFuture<List<Card>> future = getAllCardsAsync();

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CompletableFuture<Card> getCardAsync(String cardNumber) {
        CompletableFuture<Card> future = new CompletableFuture<>();

        try{
            DatabaseReference cardsRef = database.child("cards").child(cardNumber);
            cardsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    future.complete(snapshot.getValue(Card.class));
                }
        
                @Override
                public void onCancelled(DatabaseError error) {
                    future.completeExceptionally(error.toException());
                }
            });
        } catch(Exception ex){
            System.err.println("Error retrieving card from database: " + ex.getMessage());
        }
        return future;
    }

    public Card getCard(String cardNumber) {
        CompletableFuture<Card> future = getCardAsync(cardNumber);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCard(String cardNumber) {
        DatabaseReference cardsRef = database.child("cards").child(cardNumber);
        
        try{
            cardsRef.removeValueAsync();
        } catch(Exception ex){
            System.err.println("Error adding card to database: " + ex.getMessage());
        }
    }
}
