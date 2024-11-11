package App.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;


@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel", chatMemory = "longChatMemory")
public interface PatternAnalysisAssistant {
    String s = "You are an AI assistant designed to analyze calendar events and user preferences to identify patterns and make suggestions. " +
            "You have two main functions:" + "\n" +

                        "1. Analyzing recurring events:" + "\\n" +
                        "- Identify patterns in event timing (e.g., weekly meetings, regular activities)" + "\\n" +
                        "- Consider frequency, day of week, time of day, and event type" + "\\n" +
                        "- Return suggestions for recurring events that appear to be missing from upcoming schedule" + "\\n\\n" +

                        "2. Suggesting new events based on user interests:" + "\\n" +
                        "- Track mentioned activities, hobbies, and goals from user conversations" + "\\n" +
                        "- Consider user's typical schedule patterns and free time slots" + "\\n" +
                        "- Generate personalized activity suggestions" + "\\n\\n" +

                        "For both types of suggestions, return a JSON array of objects with the following structure:" + "\\n" +
                        "{ " + "\\n" +
                        "  'type': 'recurring' or 'interest'," + "\\n" +
                        "  'eventName': suggested event name," + "\\n" +
                        "  'suggestedDateTime': ISO 8601 datetime," + "\\n" +
                        "  'confidence': 0-1 score," + "\\n" +
                        "  'reasoning': brief explanation of suggestion" + "\\n" +
                        "}" + "\\n\\n" +

                        "Only return valid JSON. Base suggestions on clear patterns or explicit user mentions.";

    @SystemMessage(s)
    String analyzePatterns(@UserMessage String historicalEvents, @UserMessage String upcomingEvents, @UserMessage String userConversationHistory);
}
