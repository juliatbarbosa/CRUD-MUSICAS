package org.libertas;

public class Retorno {
    private boolean success;
    private String message;

    public Retorno(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean issuccess() {
        return success;
    }

    public void setsuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
