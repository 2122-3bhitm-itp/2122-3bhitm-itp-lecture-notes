package at.htl.survey.controller;

import at.htl.survey.model.AnswerOptions;
import at.htl.survey.model.Question;
import at.htl.survey.model.Questionnaire;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static at.htl.survey.controller.Database.*;


public class QuestionRepository implements Persistent<Question>  {


    private DataSource dataSource = Database.getDataSource();

    QuestionnaireRepository questionnaireRepository = new QuestionnaireRepository();


    @Override
    public void save(Question question) {
        if (question.getqId() == null) {
            insert(question);
        } else {
            update(question);
        }
    }

    public void update(Question question) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE question SET q_text=?, q_type=?, q_qn_id=?  WHERE q_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, question.getqText());
            statement.setString(2, question.getqType());
            statement.setLong(3, question.getQuestionnaire().getQnId());
            statement.setLong(4, question.getqId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of QUESTION failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Question question) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO question (q_text,q_type,q_qn_id) VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, question.getqText());
            statement.setString(2, question.getqType());
            statement.setLong(3, question.getQuestionnaire().getQnId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of QUESTION failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    question.setqId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into QUESTION failed, no ID obtained");
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM question WHERE q_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from QUESTION failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Question> findAll() {

        List<Question> questionList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM question";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long id = result.getLong("Q_ID");
                String text = result.getString("Q_TEXT");
                String type = result.getString("Q_TYPE");
                long q_qn_id = result.getInt("Q_QN_ID");
                Questionnaire questionnaire = questionnaireRepository.findById(q_qn_id);
                questionList.add(new Question(id,text,type,questionnaire));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionList;
    }

    @Override
    public Question findById(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM question WHERE q_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();



            while (result.next()) {
                long q_qn_id = result.getLong("q_qn_id");
                Questionnaire questionnaire = questionnaireRepository.findById(q_qn_id);
                return new Question(
                        result.getLong("Q_ID"),
                        result.getString("Q_TEXT"),
                        result.getString("Q_TYPE"),
                        questionnaire
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}