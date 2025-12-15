const API_URL = 'http://localhost:7001/ask';

export interface ChatbotResponse {
    answer: string;
    follow_up_suggestions: string[];
}

export const sendMessageToBackend = async (question: string, pageContext: string): Promise<ChatbotResponse> => {
    console.log("SENDING CONTEXT:", pageContext); // debug
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                question: question,
                page_context: pageContext
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        return await response.json();
    } catch (error) {
        console.error("Chatbot Service Error:", error);
        throw error;
    }
};