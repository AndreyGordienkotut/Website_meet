package com.example.website_meet.bean;



import com.example.website_meet.Models.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope
public class HttpSession {
    @Getter
    @Setter
    private User user;
    private Map<String, Object> attributes = new HashMap<>();

    public boolean isPresent() {
        return user != null;
    }
    public void clear() {
        clearUser();
    }
    public void clearUser() {
        user = null;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }
}

