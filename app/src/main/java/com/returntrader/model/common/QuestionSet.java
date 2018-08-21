package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by moorthy on 12/7/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class QuestionSet {

    @JsonField(name = "questionList")
    private List<QuestionItem> questionList;


    public List<QuestionItem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionItem> questionList) {
        this.questionList = questionList;
    }
}
