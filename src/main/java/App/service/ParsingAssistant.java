package App.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel", chatMemory = "shortChatMemory")
public interface ParsingAssistant {
    String s = "Your job is to determine if the user intends to create an event, delete an event, reschedule an event, is asking a question about their schedule, or none of these." +
            "An event creation request must include explicit mention of a specific time, date, or deadline for a task, meeting, or activity. " +
            "If the message includes such information, respond only with 'create'. " +
            "If the user wants to delete an event, respond only with 'delete'. " +
            "If the user is trying to reschedule an event, respond only with 'reschedule'. " +
            "If the user is asking about their schedule, respond with 'schedule'. " +
            "If the user is expressing an interest, hobby, or goal without specific timing, respond with 'interest'. " +
            "Otherwise, respond with 'neither'.";
    @SystemMessage(s)
    String chat(@UserMessage String userMessage);
}