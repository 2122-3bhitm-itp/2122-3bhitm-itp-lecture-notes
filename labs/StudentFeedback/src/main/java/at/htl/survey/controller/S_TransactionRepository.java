package at.htl.survey.controller;

import at.htl.survey.model.Questionnaire;
import at.htl.survey.model.S_Transaction;
import at.htl.survey.model.Survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class S_TransactionRepository implements Persistent<S_Transaction>{
    private DataSource dataSource = Database.getDataSource();


    @Override
    public void save(S_Transaction s_transaction) {
        if (s_transaction.gettId() == null) {
            insert(s_transaction);
        } else {
            update(s_transaction);
        }
    }


    public void update(S_Transaction s_transaction) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE s_transaction SET t_transactionscode=?,t_password=?,t_is_used=?,t_s_id=? WHERE t_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, s_transaction.gettTransactionscode());
            statement.setString(2, s_transaction.gettPassword());
            statement.setBoolean(3, s_transaction.getIsUsed());
            statement.setLong(4, s_transaction.getSurvey().getsId());
            statement.setLong(5,s_transaction.gettId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of S_TRANSACTION failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insert(S_Transaction s_transaction) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO s_transaction (t_transactionscode,t_password,t_is_used,t_s_id) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, s_transaction.gettTransactionscode());
            statement.setString(2, s_transaction.gettPassword());
            statement.setBoolean(3, s_transaction.getIsUsed());
            statement.setLong(4, s_transaction.getSurvey().getsId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of S_TRANSACTION failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    s_transaction.settId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into S_TRANSACTION failed, no ID obtained");
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM s_transaction WHERE t_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from S_TRANSACTION failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<S_Transaction> findAll() {
        List<S_Transaction> s_transactionList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM s_transaction";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long id = Long.valueOf(result.getInt("t_id"));
                String transactionscode = result.getString("t_transactionscode");
                String password = result.getString("t_password");
                Boolean is_used = result.getBoolean("t_is_used");
                int t_s_id = result.getInt("t_s_id");

                SurveyRepository surveyRepository = new SurveyRepository();

                Survey survey = surveyRepository.findById(t_s_id);


                s_transactionList.add(new S_Transaction(id,transactionscode,password,is_used,survey));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s_transactionList;
    }

    @Override
    public S_Transaction findById(long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM s_transaction WHERE t_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                long t_s_id = result.getLong("t_s_id");

                SurveyRepository surveyRepository = new SurveyRepository();
                Survey survey = surveyRepository.findById(t_s_id);

                return new S_Transaction(
                        result.getLong("t_id"),
                        result.getString("t_transactionscode"),
                        result.getString("t_password"),
                        result.getBoolean("t_is_used"),
                        survey
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}


