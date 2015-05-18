/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private Result() {

    }

    public static Result getInstance() {
        return new Result();
    }

    private final List<String> errors = new ArrayList();
    private final List<String> message = new ArrayList();

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void clearErrors() {
        errors.clear();
    }

    public List<String> getMessages() {
        return message;
    }

    public boolean hasMessages() {
        return !message.isEmpty();
    }

    public void addMessage(String error) {
        message.add(error);
    }

    public void clearMessage() {
        message.clear();
    }

}
