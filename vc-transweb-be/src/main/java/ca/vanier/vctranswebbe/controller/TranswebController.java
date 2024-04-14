package ca.vanier.vctranswebbe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import ca.vanier.vctranswebbe.Card;
import ca.vanier.vctranswebbe.service.TranswebService;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
@RestController
@RequestMapping("/card")
public class TranswebController {
    public TranswebService ts;

    public TranswebController(TranswebService ts) {
        this.ts = ts;
    }

    @PostMapping
    public Response addCard(@RequestBody Card c) {
        Response res = new Response();

        try{
            ts.addCard(c);
            res.setResult(c.toString() + " added.");
            res.setStatus(HttpStatus.CREATED.toString());
        } catch(Exception ex){
            res.setResult(ex.getMessage());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return res;
    }

    @GetMapping()
    public Response getAllCards() {
        Response res = new Response();

        try{
            res.setResult(ts.getAllCards());
            res.setStatus(HttpStatus.OK.toString());
        } catch (Exception ex){
            res.setResult(ex.getMessage());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return res;
    }

    @GetMapping("/find")
    public Response getCard(@RequestParam String cardNumber) {
        Response res = new Response();

        try{
            res.setResult(ts.getCard(cardNumber));
            res.setStatus(HttpStatus.OK.toString());
        } catch (Exception ex){
            res.setResult(ex.getMessage());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return res;
    }
    
    @DeleteMapping("/delete")
    public Response deleteCard(@RequestParam String cardNumber) {
        Response res = new Response();
        
        try{
            ts.deleteCard(cardNumber);
            res.setResult(cardNumber + " deleted.");
            res.setStatus(HttpStatus.OK.toString());
        } catch (Exception ex){
            res.setResult(ex.getMessage());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return res;
    }

    @PutMapping("/update")
    public Response updateCard(@RequestBody Card c) {
        Response res = new Response();

        try{
            ts.updateCard(c);
            res.setResult(c.toString() + " updated.");
            res.setStatus(HttpStatus.OK.toString());
        } catch(Exception ex){
            res.setResult(ex.getMessage());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return res;
    }

}
