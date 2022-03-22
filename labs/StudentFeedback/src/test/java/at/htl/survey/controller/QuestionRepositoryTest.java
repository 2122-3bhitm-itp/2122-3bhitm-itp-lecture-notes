package at.htl.survey.controller;

import at.htl.survey.database.SqlRunner;
import at.htl.survey.model.Question;
import at.htl.survey.model.Questionnaire;
import org.assertj.db.api.Assertions;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.db.api.Assertions.assertThat;

import static org.assertj.db.output.Outputs.output;
import static org.junit.jupiter.api.Assertions.*;

class QuestionRepositoryTest {
    QuestionRepository questionRepository = new QuestionRepository();
    QuestionnaireRepository questionnaireRepository = new QuestionnaireRepository();

    @BeforeAll
    private static void init(){
        SqlRunner.dropTablesAndCreateEmptyTables();
    }
    @Test
    @Order(1)
    void save() {

        Table table = new Table(Database.getDataSource(), "Question");
        output(table).toConsole();

        Questionnaire questionnaire = new Questionnaire(null, "Questionnaire");
        questionnaireRepository.insert(questionnaire);

        Question question = new Question(null, "Wie gefällt dir der Unterricht des Lehrers?", "TEXT",questionnaireRepository.findById(1));
        questionRepository.save(question);

        table = new Table(Database.getDataSource(), "Question");
        output(table).toConsole();

        Assertions.assertThat(table).row(0)
                .value("q_text").isEqualTo("Wie gefällt dir der Unterricht des Lehrers?")
                .value("q_type").isEqualTo("TEXT");
                //.value("q_qn_id").isEqualTo(1L);
    }

    @Test
    @Order(2)
    void insert() {
        Table table = new Table(Database.getDataSource(), "Question");
        output(table).toConsole();

        Questionnaire questionnaire = new Questionnaire(null, "Questionnaire");
        questionnaireRepository.insert(questionnaire);
        Question question = new Question(1L, "Blablabla", "Type", questionnaireRepository.findById(1));

         table = new Table(Database.getDataSource(), "Question");
        output(table).toConsole();

        int rowsBefore = table.getRowsList().size();
        questionRepository.insert(question);
        int rowsAfter = table.getRowsList().size();


        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);
    }


    @Test
    @Order(3)
    void delete() {


        Questionnaire questionnaire = new Questionnaire(1L, "Questionnaire");
        Question question = new Question(9L, "Blablabla", "Text", questionnaire);
        questionRepository.insert(question);
        Table table = new Table(Database.getDataSource(), "Question");

        int rowsBefore = table.getRowsList().size();
        questionRepository.delete(question.getqId());
        int rowsAfter = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);


    }

    @Test
    void findAll() {
        Table table = new Table(Database.getDataSource(), "Question");

        int findAllRows = questionRepository.findAll().size();
        int tableRows = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(findAllRows).isEqualTo(tableRows);
    }

    @Test
    void findById() {
        Table table = new Table(Database.getDataSource(), "Question");

        Question question = questionRepository.findById(2);

        String [] actual = {String.valueOf(question.getqId()), question.getqText(),question.getqType(),String.valueOf(question.getQuestionnaire().getQnId())};
        String [] expected = {
                table.getRow(1).getValuesList().get(0).getValue().toString(),
                table.getRow(1).getValuesList().get(1).getValue().toString(),
                table.getRow(1).getValuesList().get(2).getValue().toString(),
                table.getRow(1).getValuesList().get(3).getValue().toString()
        };

        org.assertj.core.api.Assertions.assertThat(expected).isEqualTo(actual);
    }
}
