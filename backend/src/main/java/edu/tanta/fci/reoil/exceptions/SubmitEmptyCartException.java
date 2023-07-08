package edu.tanta.fci.reoil.exceptions;

public class SubmitEmptyCartException extends RuntimeException {
  public SubmitEmptyCartException(String cartIsEmpty) {
    super(cartIsEmpty);
  }
}
