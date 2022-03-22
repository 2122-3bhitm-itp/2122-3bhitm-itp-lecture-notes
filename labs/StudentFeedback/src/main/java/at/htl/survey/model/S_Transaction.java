package at.htl.survey.model;

public class S_Transaction {

/*    private int t_id;
    private String t_transactionscode;
    private String t_password;
    private boolean t_is_used;
    private int t_s_id;*/

    private Long tId;
    private String tTransactionscode;
    private String tPassword;
    private boolean tIsUsed;
    private Survey survey;


    public S_Transaction(Long tId, String tTransactionscode, String tPassword, boolean tIsUsed, Survey survey) {
        this.tId = tId;
        this.tTransactionscode = tTransactionscode;
        this.tPassword = tPassword;
        this.tIsUsed = tIsUsed;
        this.survey = survey;
    }

    public S_Transaction(){}

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String gettTransactionscode() {
        return tTransactionscode;
    }

    public void settTransactionscode(String tTransactionscode) {
        this.tTransactionscode = tTransactionscode;
    }

    public String gettPassword() {
        return tPassword;
    }

    public void settPassword(String tPassword) {
        this.tPassword = tPassword;
    }

    public boolean getIsUsed() {
        return tIsUsed;
    }

    public void settIsUsed(boolean tIsUsed) {
        this.tIsUsed = tIsUsed;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
