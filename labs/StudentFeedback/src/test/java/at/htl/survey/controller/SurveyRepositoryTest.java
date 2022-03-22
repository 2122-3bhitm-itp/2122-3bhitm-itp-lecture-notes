package at.htl.survey.controller;

import at.htl.survey.database.SqlRunner;
import at.htl.survey.model.AnswerOptions;
import at.htl.survey.model.Question;
import at.htl.survey.model.Questionnaire;
import at.htl.survey.model.Survey;
import org.assertj.db.api.Assertions;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.db.api.Assertions.assertThat;

import static org.assertj.db.output.Outputs.output;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SurveyRepositoryTest {

    SurveyRepository surveyRepository = new SurveyRepository();
    QuestionnaireRepository questionnaireRepository = new QuestionnaireRepository();

    @BeforeAll
    private static void init(){
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @Test
    @Order(1)
    void save() {
        LocalDate date = LocalDate.now(); //survey.getsDate().getTime());

        Table table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        Questionnaire questionnaire = new Questionnaire(null, "Questionnaire");
        questionnaireRepository.insert(questionnaire);

        Survey survey = new Survey(null, "Thomas Stütz", questionnaireRepository.findById(1), date);
        surveyRepository.save(survey);

        table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        Assertions.assertThat(table).hasNumberOfRows(1);
        Assertions.assertThat(table).row(0)
                .value("s_creator").isEqualTo("Thomas Stütz")
                //.value("s_qn_id").isEqualTo(1)
                .value("s_date").isEqualTo(java.sql.Date.valueOf(date));
    }

    @Test
    @Order(2)
    void insert() {
        LocalDate date = LocalDate.now();

        Table table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        Questionnaire questionnaire = new Questionnaire(null, "Questionnaire");
        questionnaireRepository.insert(questionnaire);

        Survey survey = new Survey(1L, "Thomas Karl", questionnaireRepository.findById(1), date);

        //Table table = new Table(Database.getDataSource(), "Survey");
        table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        int rowsBefore = table.getRowsList().size();
        surveyRepository.insert(survey);
        int rowsAfter = table.getRowsList().size();


        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);
    }

    @Test
    @Order(3)
    void delete() {
        LocalDate date = LocalDate.now();

        Table table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        Questionnaire questionnaire = new Questionnaire(null, "Questionnaire");
        questionnaireRepository.insert(questionnaire);

        Survey survey = new Survey(1L, "Thomas Stütz", questionnaireRepository.findById(1), date);
        surveyRepository.insert(survey);

        // Table table = new Table(Database.getDataSource(), "Survey");
        table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        int rowsBefore = table.getRowsList().size();
        surveyRepository.delete(survey.getsId());
        int rowsAfter = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(rowsBefore).isEqualTo(rowsAfter);

    }

    @Test
    @Order(4)
    void findAll() {

        int findAllRows = surveyRepository.findAll().size();

        Table table = new Table(Database.getDataSource(), "Survey");

        int tableRows = table.getRowsList().size();

        org.assertj.core.api.Assertions.assertThat(findAllRows).isEqualTo(tableRows);
    }

    @Test
    @Order(5)
    void findById() {
        LocalDate date = LocalDate.now(); //survey.getsDate().getTime());

        Questionnaire questionnaire = new Questionnaire(null,"blabla");
        questionnaireRepository.insert(questionnaire);

        Table table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        Survey survey = new Survey(null, "Thomas Stütz", questionnaireRepository.findById(1), date);
        surveyRepository.insert(survey);

        survey = surveyRepository.findById(4);

        table = new Table(Database.getDataSource(), "Survey");
        output(table).toConsole();

        String [] expected = {
                String.valueOf(survey.getsId()),
                survey.getsCreator(),
                String.valueOf(survey.getQuestionnaire().getQnId()),
                String.valueOf(survey.getsDate())

        };
        String [] actual = {
                table.getRow(2).getValuesList().get(0).getValue().toString(),
                table.getRow(2).getValuesList().get(1).getValue().toString(),
                table.getRow(2).getValuesList().get(2).getValue().toString(),
                table.getRow(2).getValuesList().get(3).getValue().toString()
        };

        org.assertj.core.api.Assertions.assertThat(expected).isEqualTo(actual);
    }
}