package at.htl.survey.controller;

import at.htl.survey.model.Question;
import at.htl.survey.model.Questionnaire;
import at.htl.survey.model.Survey;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SurveyRepository implements Persistent<Survey> {

    private DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Survey survey) {
        if (survey.getsId() == null) {
            insert(survey);
        } else {
            update(survey);
        }
    }

    public void update(Survey survey) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE survey  SET s_creator=?, s_qn_id=?, s_date=? WHERE s_id=  " + survey.getsId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, survey.getsCreator());
            statement.setLong(2, survey.getQuestionnaire().getQnId());
            statement.setDate(3, java.sql.Date.valueOf(survey.getsDate()));

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of SURVEY failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Survey survey) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = null;
            PreparedStatement statement = null;
            if(survey.getQuestionnaire() != null){
                sql = "INSERT INTO survey (s_creator,s_qn_id,s_date) VALUES (?,?,?)";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, survey.getsCreator());
                statement.setLong(2, survey.getQuestionnaire().getQnId());
                statement.setDate(3, java.sql.Date.valueOf(survey.getsDate()));
            }else{
                sql = "INSERT INTO survey (s_creator,s_date) VALUES (?,?)";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, survey.getsCreator());
                statement.setDate(2, java.sql.Date.valueOf(survey.getsDate()));
            }



            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of SURVEY failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    survey.setsId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into SURVEY failed, no ID obtained");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM survey WHERE s_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from SURVEY failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Survey> findAll() {


        List<Survey> surveyList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM survey";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long s_id = Long.valueOf(result.getInt("s_id"));
                String s_creator = result.getString("s_creator");
                int s_qn_id = result.getInt("s_qn_id");
                Date s_date = result.getDate("s_date");

                QuestionnaireRepository questionnaireRepository = new QuestionnaireRepository();

                Questionnaire questionnaire = questionnaireRepository.findById(s_qn_id);

                surveyList.add(new Survey(s_id, s_creator, questionnaire, s_date.toLocalDate()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return surveyList;
    }

    @Override
    public Survey findById(long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM survey WHERE s_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();


            if (result.next()) {
                long s_qn_id = result.getInt("s_qn_id");

                QuestionnaireRepository questionnaireRepository = new QuestionnaireRepository();
                Questionnaire questionnaire = questionnaireRepository.findById(s_qn_id);


                return new Survey(result.getLong("s_id"), result.getString("s_creator"), questionnaire, result.getDate("s_date").toLocalDate());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
