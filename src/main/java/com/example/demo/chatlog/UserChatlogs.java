package com.example.demo.chatlog;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class UserChatlogs {

    @Id
    private String user;
    private List<Chatlog> chatlogs;

    public UserChatlogs() {
        this.chatlogs = new ArrayList<>();
    }

    public UserChatlogs(String user, List<Chatlog> chatlogs) {
        this.user = user;
        this.chatlogs = chatlogs;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Chatlog> getChatlogs() {
        return chatlogs;
    }

    public void setChatlogs(List<Chatlog> chatlogs) {
        this.chatlogs = chatlogs;
    }
}

