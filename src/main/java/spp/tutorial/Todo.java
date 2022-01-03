package spp.tutorial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class Todo {

  private String id;
  private final String title;
  private final boolean completed;
  private final Long order;
  private final Instant createDate = Instant.now();

  @JsonCreator
  public Todo(@JsonProperty("id") String id,
              @JsonProperty("title") String title,
              @JsonProperty(value = "completed") boolean completed,
              @JsonProperty(value = "order") long order) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.order = order;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public boolean isCompleted() {
    return completed;
  }

  public long getOrder() {
    return order;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public boolean hasId() {
    return id == null;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", completed=" + completed +
        ", order=" + order +
        '}';
  }
}
