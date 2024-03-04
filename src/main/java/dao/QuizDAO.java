package dao;

import dto.QuizDTO;

import java.util.List;

public interface QuizDAO {
    int addQuestion(QuizDTO q);

    int removeQuestion(int questionId);

    int modifyQuestion(QuizDTO q, int questionId);

    int modifyOptions(QuizDTO q, int questionId);

    List<QuizDTO> displayQuestion();

    boolean authentication(String email, String password);

    void createAccount(String email, String password);
}
