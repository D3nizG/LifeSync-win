import React, {useState, useEffect} from "react";
import { ChatFeed, Message } from "react-chat-ui";
import Linkify from "react-linkify";
import "./App.css";
import "./ChatUI.css";

function ChatUI({ fetchCalendar }) {
    const [userInput, setUserInput] = useState('');
    const [messages, setMessages] = React.useState([
        new Message({ id: 1, message: "Hello! I am your calendar assistant, how can I help you today?" }),
    ]);


    const checkForSuggestions = async () => {
        try{
            const response = await fetch("http://localhost:8080/api/v1/event/suggestions", {method: "GET"});

            if (response.ok){
                const suggestions = await response.json();
                if(suggestions && suggestions.length > 0){
                    suggestions.foreach(suggestion => {
                        const message = formatSuggestionMessage(suggestion);
                        setMessages(prevMessages => [...prevMessages, new Message({
                            id:1, message: message
                        })]);
                    });
                }
            }
        }catch (e) {
            console.error("Error fetching suggestions: ", e)
        }
    }

    const formatSuggestionMessage = (suggestion) => {
        if (suggestion.type === 'recurring') {
            return `I noticed you usually have "${suggestion.eventName}" at this time. Would you like to schedule it for ${new Date(suggestion.suggestedDateTime).toLocaleString()}?`;
        } else {
            return `Based on your interests in ${suggestion.reasoning}, would you like to schedule ${suggestion.eventName} for ${new Date(suggestion.suggestedDateTime).toLocaleString()}?`;
        }
    };
/*
    useEffect(() => {
        const suggestionInterval = setInterval(checkForSuggestions, 30*60)

        checkForSuggestions()

        return () => clearInterval(suggestionInterval);
    }, []);*/

    async function handleSubmit(event) {
        event.preventDefault();
        console.log(userInput);
        setMessages([...messages, new Message({ id: 0, message: userInput })]);

        const lastbotMessage = messages[messages.length - 1];
        const isResponseToSuggestion = lastbotMessage.id === 1 &&
            lastbotMessage.message.includes('Would you like to schedule') &&
            userInput.toLowerCase().includes('yes');

        if(isResponseToSuggestion){
            try{
                const eventDetails = extractEventFromSuggestion(lastbotMessage.message);

                const formattedInput = `Schedule ${eventDetails.eventName} on ${eventDetails.dateTime}`;

                const apiResponse = await fetch(
                    `http://localhost:8080/api/v1/event?userInput=${encodeURIComponent(formattedInput)}`,
                    { method: 'POST' }
                );

                const text = await apiResponse.text();
                setMessages(prevMessages => [...prevMessages, new Message({id: 1, message: text})]);

            }catch(error){
                console.error('Error handling suggestion:', error)
                setMessages(prevMessages => [...prevMessages, new Message({id:1, message:"Sorry, I couldn't process that suggestion"})]);
            }
        }else{
            const apiResponse = await fetch(`http://localhost:8080/api/v1/event?userInput=${encodeURIComponent(userInput)}`, {
                method: 'POST',
            });
            const text = await apiResponse.text();
            setMessages(prevMessages => [...prevMessages, new Message({ id : 1, message: text })]);
            fetchCalendar();
        }
        setUserInput("");

    }

    function extractEventFromSuggestion(message){
        const eventNameMatch = message.match(/"([^"]+)"/) || message.match(/schedule ([^for]+) for/);
        const dateTimeMatch = message.match(/schedule it for (.+?)\?/) || message.match(/schedule .+ for (.+?)\?/);

        return{
            eventName: eventNameMatch ? eventNameMatch[1].trim() : "",
            dateTime: dateTimeMatch ? dateTimeMatch[1].trim() : "",
        }
    }

    return (
        <div className="chat-container">
            <ChatFeed
                messages={messages.map((msg) => ({
                    ...msg,
                    message: (
                        <Linkify>{msg.message}</Linkify>
                    )
                }))}
                bubbleStyles={{
                    chatbubble: {
                        backgroundColor: '#3788d8'
                    },
                    userBubble: {
                        backgroundColor: '#44b332'
                    }
                }}
            />
            <div className="input-container">
                <form onSubmit={handleSubmit}>
                    <input id="textInput"
                           value={userInput}
                           onChange={(event) => setUserInput(event.target.value)}
                    />
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
}

export default ChatUI;