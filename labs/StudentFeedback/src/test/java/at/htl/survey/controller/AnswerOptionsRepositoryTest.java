package at.htl.survey.controller;

import at.htl.survey.database.SqlRunner;
import at.htl.survey.model.Answer;
import at.htl.survey.model.AnswerOptions;
import at.htl.survey.model.Question;
import at.htl.survey.model.Questionnaire;
import org.assertj.db.api.Assertions;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.db.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AnswerOptionsRepositoryTest {

    QuestionRepository questionRepository = new QuestionRepository();
    AnswerOptionsRepository answerOptionsRepository = new AnswerOptionsRepository();

    @BeforeAll
    private static void init(){
        SqlRunner.dropAndCreateTablesWithExampleData();
    }

    @Test
    @Order(1)
    void save() {

        AnswerOptions answerOptions = new AnswerOptions(2L, "völlig zu", 4, questionRepository.findById(1) );
        answerOptionsRepository.save(answerOptions);

        Table table = new Table(Database.getDataSource(), "answer_option");

        Assertions.assertThat(table).row(0)
                .value("ao_text").isEqualTo("völlig zu")
                .value("ao_value").isEqualTo(4)
                .value("ao_q_id").isEqualTo(1);
    }

   @Test
    @Order(2)
    void insert() {

        AnswerOptions answerOptions = new AnswerOptions(20L, "völlig zu", 4,questionRepository.findById(1) );
        answerOptionsRepository.insert(answerOptions);

        Table table = new Table(Database.getDataSource(), "answer_option");

        int rowsBefore = table.getRowsList().size();
        answerOptionsRepository.insert(answerOptions);
        int rowsAfter = table.getRowsList().size();


        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);
    }

    @Test
    @Order(3)
    void delete() {

        AnswerOptions answerOptions = new AnswerOptions(2L, "völlig zu", 4, questionRepository.findById(1));
        answerOptionsRepository.insert(answerOptions);
        Table table = new Table(Database.getDataSource(), "answer_option");

        int rowsBefore = table.getRowsList().size();
        answerOptionsRepository.delete(answerOptions.getAo_id());
        int rowsAfter = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);

    }

    @Test
    @Order(4)
    void findAll() {


        int findAllRows = answerOptionsRepository.findAll().size();


        Table table = new Table(Database.getDataSource(), "answer_option");

        int tableRows = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(findAllRows).isEqualTo(tableRows);
    }

    @Test
    @Order(5)
    void findById() {

        Table table = new Table(Database.getDataSource(), "answer_option");

        AnswerOptions answerOptions = answerOptionsRepository.findById(2);

        String [] expected = {String.valueOf(answerOptions.getAo_id()), answerOptions.getAoText(), String.valueOf(answerOptions.getAoValue()), String.valueOf(answerOptions.getQuestion().getqId())};
        String [] actual = {
                table.getRow(1).getValuesList().get(0).getValue().toString(),
                table.getRow(1).getValuesList().get(1).getValue().toString(),
                table.getRow(1).getValuesList().get(2).getValue().toString(),
                table.getRow(1).getValuesList().get(3).getValue().toString()
        };

        org.assertj.core.api.Assertions.assertThat(expected).isEqualTo(actual);
    }
}
