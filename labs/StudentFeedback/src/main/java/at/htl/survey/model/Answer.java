package at.htl.survey.model;

public class Answer {

   /* private int a_id;
    private int a_t_id;
    private int a_q_id;
    private int  a_s_id;
    private String a_answer_text;
*/
    private Long aId;
    private S_Transaction s_transaction;
    private Question question;
    private Survey survey;
    private String answerText;

    public Answer(Long aId, S_Transaction s_transaction, Question question, Survey survey, String answerText) {
        this.aId = aId;
        this.s_transaction = s_transaction;
        this.question = question;
        this.survey = survey;
        this.answerText = answerText;
    }


    public Answer(){}

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public S_Transaction getS_transaction() {
        return s_transaction;
    }

    public void setS_transaction(S_Transaction s_transaction) {
        this.s_transaction = s_transaction;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
