package com.ai.gemini_chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class QnaService {
    //Access to ApiKey and URL [Gemini]
    @Value("${gemini.api.url}")
    private String geminiApiURL;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public QnaService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }


    public String getAnswer(String question) {
        // Construct the request payload
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

//    Map<String, Object> requestBody = Map.of(
//            "contents", List.of( // ✅ correct key and using List instead of array
//                    Map.of("parts", List.of(
//                            Map.of("text", question)
//                    ))
//            )
//    );



    // make Api Call
   String response = webClient.post()
           .uri(geminiApiURL+"?key="+geminiApiKey)
           .header("Content-Type","application/json")
           .bodyValue(requestBody)
           .retrieve()
           .bodyToMono(String.class)
           .block();

        //Return response

        return response;
    }
}
