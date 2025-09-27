package com.example.app;

import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

public class TextPrompts {

    public static void main(String[] args) {
        String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
        if (projectId == null) {
            throw new IllegalStateException("Set GOOGLE_CLOUD_PROJECT env var to your GCP project.");
        }

        VertexAiLanguageModel model = VertexAiLanguageModel.builder()
            .endpoint("us-central1-aiplatform.googleapis.com:443")
            .project(projectId)
            .location("us-central1")
            .publisher("google")
            .modelName("text-bison@001") // or another available model
            .maxOutputTokens(300)
            .build();

        // Example 1: simple generation
        Response<String> resp = model.generate("Explain what reinforcement learning is in 2 sentences.");
        System.out.println("Result: " + resp.content());

        // Example 2: using a prompt template
        PromptTemplate template = PromptTemplate.from("""
            Create a recipe for a {{dish}} using: {{ingredients}}.
            Include a name and steps.
            """);
        Prompt prompt = template.apply(java.util.Map.of(
            "dish", "dessert",
            "ingredients", "strawberries, chocolate, whipped cream"
        ));
        Response<String> resp2 = model.generate(prompt);
        System.out.println("Recipe:\n" + resp2.content());
    }
}
