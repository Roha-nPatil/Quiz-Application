package dao;

import dto.QuizDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAOImplimentation implements QuizDAO{
    static Connection conn= null;
    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_application_db?user=root&password=sql123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addQuestion(QuizDTO q) {
        PreparedStatement pstmt = null;
        String query = "insert into quiz_app values(?,?,?,?,?,?,?)";
        int count = 0;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 0);
            pstmt.setString(2, q.getQuestion());
            pstmt.setString(3, q.getOption1());
            pstmt.setString(4, q.getOption2());
            pstmt.setString(5, q.getOption3());
            pstmt.setString(6, q.getOption4());
            pstmt.setString(7, q.getAnswer());

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int removeQuestion(int questionId) {
        PreparedStatement pstmt = null;
        String query = "delete from quiz_app where question_id=?";
        int count = 0;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,questionId);

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int modifyQuestion(QuizDTO q, int questionId) {
        PreparedStatement pstmt = null;
        String query = "update quiz_app set question =? where question_id = ?";
        int count = 0;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, q.getQuestion());
            pstmt.setInt(2, questionId);

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int modifyOptions(QuizDTO q, int questionId) {
        PreparedStatement pstmt = null;
        int count = 0;
        String query = "update quiz_app set option1=?, option2=?, option3=?, option4=?, answer=? where question_id=?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, q.getOption1());
            pstmt.setString(2, q.getOption2());
            pstmt.setString(3, q.getOption3());
            pstmt.setString(4, q.getOption4());
            pstmt.setString(5, q.getAnswer());
            pstmt.setInt(6, questionId);

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Invalid Input");
        }
        return count;
    }

    @Override
    public List<QuizDTO> displayQuestion() {
        String query= "select * from quiz_app" ;
        List<QuizDTO> questionList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement() ;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                QuizDTO q = new QuizDTO();
                q.setQuestionId(rs.getInt(1));
                q.setQuestion( rs.getString(2));
                q.setOption1( rs.getString(3));
                q.setOption2( rs.getString(4));
                q.setOption3( rs.getString(5));
                q.setOption4( rs.getString(6));
                q.setAnswer(rs.getString(7));

                questionList.add(q);
            }
        } catch (SQLException e) {

        }

        return questionList ;
    }

    @Override
    public boolean authentication(String email, String password) {
        PreparedStatement pstmt = null;
        String query = "SELECT password FROM user_login WHERE email = ?";
        int count = 0;
        String storedPassword = null;
        boolean authenticationResult = false;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                storedPassword = rs.getString("password");
                authenticationResult = storedPassword != null && storedPassword.equals(password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authenticationResult;
    }

    @Override
    public void createAccount(String email, String password) {
        PreparedStatement pstmt = null;
        String query = "INSERT INTO user_login VALUES(?,?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,email);
            pstmt.setString(2,password);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
