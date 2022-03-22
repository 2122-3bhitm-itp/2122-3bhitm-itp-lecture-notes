package at.htl.survey.model;

public class Question {

 /*  private int q_id;
   private String q_text;
   private String q_type;
   private int q_qn_id;
*/

    private Long qId;
    private String qText;
    private String qType;
    private Questionnaire questionnaire;


    public Question(Long qId, String qText, String qType, Questionnaire questionnaire) {
        this.qId = qId;
        this.qText = qText;
        this.qType = qType;
        this.questionnaire = questionnaire;
    }


    public Question(){}

    public Long getqId() {
        return qId;
    }


    public void setqId(Long qId) {
        this.qId = qId;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String getqType() {
        return qType;
    }

    public void setqType(String qType) {
        this.qType = qType;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}
