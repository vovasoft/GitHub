package sagan.questions.support;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
class Question implements Serializable {

    public String title;
    public String body;
    public List<String> tags;
	public String link;
	public Integer score;

	@JsonProperty("question_id")
	public String id;

    @JsonProperty("is_answered")
    public boolean isAnswered;

	@JsonProperty("answer_count")
	public Integer answerCount;

	@JsonProperty("view_count")
	public Integer viewCount;

	@JsonProperty("creation_date")
	@JsonDeserialize(using = EpochDeserializer.class)
	public LocalDateTime creationDate;

    public User owner;

    public String getBodySynopsis() {
		String synopsis = body.replaceAll("\\<(/?[^\\>]+)\\>", "");
        return synopsis.substring(0, synopsis.length() > 200 ? 200 : synopsis.length());
    }

	@Override
	public String toString() {
		return "Question{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", link='" + link + '\'' +
				'}';
	}
}
