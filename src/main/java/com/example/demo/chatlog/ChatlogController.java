package com.example.demo.chatlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chatlogs")
public class ChatlogController {

    private final ChatlogService chatlogService;

    @Autowired
    public ChatlogController(ChatlogService chatlogService) {
        this.chatlogService = chatlogService;
    }

    @PostMapping("/{user}")
    public ResponseEntity<String> postChatlog(@PathVariable String user, @RequestBody Chatlog chatlog) {
        return new ResponseEntity<>(this.chatlogService.postChatlog(user, chatlog), HttpStatus.OK);
    }

    @GetMapping("/{user}")
    public ResponseEntity<?> getChatlogs(@PathVariable String user, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "") String start) {
        try {
            List<Chatlog> chatlogs = this.chatlogService.getChatlogs(user, limit, start);
            return new ResponseEntity<>(chatlogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{user}")
    public ResponseEntity<String> deleteChatlogs(@PathVariable String user) {
        try {
            this.chatlogService.deleteChatlogs(user);
            return new ResponseEntity<>("Successfully deleted all chat logs of user " + user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{user}/{messageId}")
    public ResponseEntity<String> deleteMessageFromChatlogs(@PathVariable String user, @PathVariable String messageId) {
        try {
            this.chatlogService.deleteMessageFromChatlogs(user, messageId);
            return new ResponseEntity<>("Message successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
