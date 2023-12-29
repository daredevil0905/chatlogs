package com.example.demo.chatlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ChatlogService {

    private final ChatlogRepository chatlogRepository;

    @Autowired
    public ChatlogService(ChatlogRepository chatlogRepository) {
        this.chatlogRepository = chatlogRepository;
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    private int getIndexOfChatlogBasedOnMessageId(List<Chatlog> chatlogs, String messageId) {
        for (int i = 0; i < chatlogs.size(); i++) {
            Chatlog chatlog = chatlogs.get(i);
            if (chatlog.getMessageId().equals(messageId)) {
                return i + 1;
            }
        }
        return -1;
    }

    private int deleteMessageFromChatlogsBasedOnMessageId(List<Chatlog> chatlogs, String messageId) {
        for (int i = 0; i < chatlogs.size(); i++) {
            Chatlog chatlog = chatlogs.get(i);
            if (chatlog.getMessageId().equals(messageId)) {
                return i;
            }
        }
        return -1;
    }

    private void saveChatlog(String user, Chatlog chatlog) {
        UserChatlogs userChatlogs = chatlogRepository.findById(user).orElse(new UserChatlogs());
        userChatlogs.setUser(user);

        userChatlogs.getChatlogs().add(chatlog);

        Collections.sort(userChatlogs.getChatlogs());

        chatlogRepository.save(userChatlogs);
    }

    public String postChatlog(String user, Chatlog chatlog) {
        String messageId = generateMessageId();
        chatlog.setMessageId(messageId);

        saveChatlog(user, chatlog);

        return messageId;
    }

    public List<Chatlog> getChatlogs(String user, int limit, String start) throws Exception {
        UserChatlogs userChatlogs = chatlogRepository.findById(user).orElseThrow(() -> new Exception("Invalid input: User not found in database."));

        int indexOfOldestChatlog = limit > 0 ? limit : 10;
        if (!start.isEmpty()) {
            int startIndex = getIndexOfChatlogBasedOnMessageId(userChatlogs.getChatlogs(), start);
            if (startIndex != -1) {
                indexOfOldestChatlog = Math.min(indexOfOldestChatlog, startIndex);
            }
        }

        indexOfOldestChatlog = Math.min(indexOfOldestChatlog, userChatlogs.getChatlogs().size());

        return userChatlogs.getChatlogs().subList(0, indexOfOldestChatlog);
    }

    public void deleteChatlogs(String user) throws Exception {
        UserChatlogs userChatlogs = chatlogRepository.findById(user).orElseThrow(() -> new Exception("Invalid input: User not found in database."));
        List<Chatlog> chatlogs = userChatlogs.getChatlogs();
        chatlogs.clear();
        userChatlogs.setChatlogs(chatlogs);
        chatlogRepository.save(userChatlogs);
    }

    public void deleteMessageFromChatlogs(String user, String messageId) throws Exception {
        UserChatlogs userChatlogs = chatlogRepository.findById(user).orElseThrow(() -> new Exception("Invalid input: User not found in database."));

        int indexOfMessageToDelete = deleteMessageFromChatlogsBasedOnMessageId(userChatlogs.getChatlogs(), messageId);

        if (indexOfMessageToDelete == -1) {
            throw new Exception("Invalid input: incorrect messageId entered.");
        }

        List<Chatlog> chatlogs = userChatlogs.getChatlogs();
        chatlogs.remove(indexOfMessageToDelete);
        userChatlogs.setChatlogs(chatlogs);

        chatlogRepository.save(userChatlogs);
    }

}
