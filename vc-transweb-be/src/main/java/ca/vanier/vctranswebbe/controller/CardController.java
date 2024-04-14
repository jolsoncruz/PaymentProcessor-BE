package ca.vanier.vctranswebbe.controller;

import ca.vanier.vctranswebbe.Card;
import ca.vanier.vctranswebbe.service.FirebaseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final FirebaseService firebaseService;

    public CardController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping
    public ResponseEntity<String> addCard(@RequestBody Card card) {
        firebaseService.addCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card added successfully!");
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable String cardId) {
        firebaseService.deleteCard(cardId);
        return ResponseEntity.status(HttpStatus.OK).body("Card deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = new ArrayList<>();
        firebaseService.getAllCards(retrievedCards -> {
            if (retrievedCards != null) {
                cards.addAll(retrievedCards);
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getSpecificCard(@PathVariable String cardId) {
        Card card = firebaseService.getSpecificCard(cardId);
        if (card != null) {
            return ResponseEntity.status(HttpStatus.OK).body(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
