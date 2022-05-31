package at.htl.survey.controller;

import at.htl.survey.model.AnswerOptions;
import at.htl.survey.model.Question;
import at.htl.survey.model.Survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerOptionsRepository implements Persistent<AnswerOptions> {

    private DataSource dataSource = Database.getDataSource();

    QuestionRepository questionRepository = new QuestionRepository();

    @Override
    public void save(AnswerOptions answerOptions) {
        if (answerOptions.getAoId() == null) {
            insert(answerOptions);
        } else {
            update(answerOptions);
        }
    }

    public void update(AnswerOptions answerOptions) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE answer_option SET ao_text=?, ao_value=?, ao_q_id=?  WHERE ao_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, answerOptions.getAoText());
            statement.setInt(2, answerOptions.getAoValue());
            statement.setLong(3, answerOptions.getQuestion().getqId());
            statement.setLong(4, answerOptions.getAoId());



            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of ANSWER_OPTIONS failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(AnswerOptions answerOptions) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO answer_option (ao_text,ao_value,ao_q_id) VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, answerOptions.getAoText());
            statement.setInt(2, answerOptions.getAoValue());
            statement.setLong(3, answerOptions.getQuestion().getqId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of ANSWER_OPTIONS failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    answerOptions.setAoId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into ANSWER_OPTIONS failed, no ID obtained");
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM answer_option WHERE ao_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from answer_option failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<AnswerOptions> findAll() {

        List<AnswerOptions> anwerOptionList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM answer_option";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                long id = result.getLong("AO_ID");
                String text = result.getString("AO_TEXT");
                int value = result.getInt("AO_VALUE");
                int ao_q_id = result.getInt("AO_Q_ID");
                Question question = questionRepository.findById(ao_q_id);
                anwerOptionList.add(new AnswerOptions(id,text,value,question));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return anwerOptionList;
    }

    @Override
    public AnswerOptions findById(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM answer_option WHERE ao_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();



            while (result.next()) {
                long ao_q_id = result.getLong("ao_q_id");
                Question question = questionRepository.findById(ao_q_id);
                return new AnswerOptions(result.getLong("AO_ID"), result.getString("AO_TEXT"), result.getInt("AO_VALUE"), question);
              
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
