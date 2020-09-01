package ru.zneik.restaurant.util.exception;

public class ExistVoteException extends RuntimeException {
    public ExistVoteException(String message) {
        super(message);
    }
}
