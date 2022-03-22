package at.htl.survey.model;

public class AnswerOptions {

//   private int  ao_id;
//   private String ao_text;
//   private int ao_value;
//   private int  ao_q_id;

    private Long aoId;
    private String aoText;
    private int aoValue;
    private Question question;

    public AnswerOptions(Long aoId, String aoText, int aoValue, Question question) {
        this.aoId = aoId;
        this.aoText = aoText;
        this.aoValue = aoValue;
        this.question = question;
    }



    public Long getAo_id() {
       return aoId;
    }

    public Long getAoId() {
        return aoId;
    }

    public String getAoText() {
        return aoText;
    }

    public void setAoText(String aoText) {
        this.aoText = aoText;
    }

    public int getAoValue() {
        return aoValue;
    }

    public void setAoId(Long aoId) {
        this.aoId = aoId;
    }

    public void setAoValue(int aoValue) {
        this.aoValue = aoValue;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
