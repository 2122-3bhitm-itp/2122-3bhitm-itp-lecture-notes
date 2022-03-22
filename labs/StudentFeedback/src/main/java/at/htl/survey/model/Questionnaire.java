package at.htl.survey.model;

public class Questionnaire {

/*    private int qn_id;
    private String qn_descripton;*/

    private Long qnId;
    private String qnDescription;

    public Questionnaire(Long qnId, String qnDescription) {
        this.qnId = qnId;
        this.qnDescription = qnDescription;
    }

    public Questionnaire(){}

    public Long getQnId() {
        return qnId;
    }

    public void setQnId(Long qnId) {
        this.qnId = qnId;
    }

    public String getQnDescription() {
        return qnDescription;
    }

    public void setQnDescription(String qnDescription) {
        this.qnDescription = qnDescription;
    }
}
