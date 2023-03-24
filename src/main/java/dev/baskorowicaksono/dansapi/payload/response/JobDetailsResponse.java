package dev.baskorowicaksono.dansapi.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailsResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;
    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("company")
    private String company;
    @JsonProperty("company_url")
    private String company_url;
    @JsonProperty("location")
    private String location;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("how_to_apply")
    private String how_to_apply;
    @JsonProperty("company_logo")
    private String company_logo;
}
