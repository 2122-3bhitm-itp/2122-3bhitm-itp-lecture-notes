package at.htl.survey.controller;

import at.htl.survey.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerRepository implements Persistent<Answer>{

    private DataSource dataSource = Database.getDataSource();
    S_TransactionRepository s_transactionRepository = new S_TransactionRepository();
    QuestionRepository questionRepository = new QuestionRepository();
    SurveyRepository surveyRepository = new SurveyRepository();

    @Override
    public void save(Answer answer) {
        if (answer.getaId() == null) {
            insert(answer);
        } else {
            update(answer);
        }
    }


    public void update(Answer answer) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE answer SET  a_t_id=?, a_q_id=?, a_s_id=?, a_answer_text=? WHERE a_id= " + answer.getaId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,answer.getS_transaction().gettId());
            statement.setLong(2,answer.getQuestion().getqId());
            statement.setLong(3, answer.getSurvey().getsId());
            statement.setString(4, answer.getAnswerText());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of ANSWER failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Answer answer) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO answer  (a_t_id, a_q_id, a_s_id, a_answer_text) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, answer.getS_transaction().gettId());
            statement.setLong(2, answer.getQuestion().getqId());
            statement.setLong(3, answer.getSurvey().getsId());
            statement.setString(4, answer.getAnswerText());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of ANSWER failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    answer.setaId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into ANSWER failed, no ID obtained");
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM answer  WHERE a_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from ANSWER failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Answer> findAll() {
        List<Answer> answerList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM answer";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long id = Long.valueOf(result.getInt("a_id"));

                Long a_t_id = Long.valueOf(result.getInt("a_t_id"));
                S_Transaction s_transaction = s_transactionRepository.findById(a_t_id);

                Long a_q_id = Long.valueOf(result.getInt("a_q_id"));
                Question question = questionRepository.findById(a_q_id);

                Long a_s_id = Long.valueOf(result.getInt("a_s_id"));
                Survey survey = surveyRepository.findById(a_s_id);

                String answer_text = result.getString("a_answer_text");



                answerList.add(new Answer(id,s_transaction,question,survey,answer_text));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answerList;
    }

    @Override
    public Answer findById(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM answer WHERE a_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();



            while (result.next()) {

                S_Transaction s_transaction = s_transactionRepository.findById(result.getLong("a_t_id"));
                Question question = questionRepository.findById(result.getLong("a_q_id"));
                Survey survey = surveyRepository.findById(result.getLong("a_s_id"));


                return new Answer(
                        result.getLong("a_id"),
                        s_transaction,
                        question,
                        survey,
                        result.getString("a_answer_text")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

