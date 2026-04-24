package arnav.springai.springaibasicswithtest.model;

public class InvalidAnswerException extends RuntimeException {
    public InvalidAnswerException(String question, String answer) {
        super("Answer check failed : the answer "+ answer +" is not correct for the question : "+ question + ".");
    }
}
