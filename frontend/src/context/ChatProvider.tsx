import React, { useState, useCallback, type ReactNode } from 'react';
import { sendMessageToBackend } from '../services/chatbotService';
import { getPageContext } from '../utils/pageContext';
import { ChatContext, type Message } from './ChatContext';


export const ChatProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [messages, setMessages] = useState<Message[]>([
        { text: "Hello! I can help you navigate BanditGames or explain game rules.", type: 'bot' }
    ]);
    const [suggestions, setSuggestions] = useState<string[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const toggleChat = () => setIsOpen(prev => !prev);

    const sendMessage = useCallback(async (text: string) => {
        if (!text.trim()) return;

        const userMsg: Message = { text, type: 'user' };
        setMessages(prev => [...prev, userMsg]);
        setIsLoading(true);
        setSuggestions([]);

        try {
            const currentContext = getPageContext();
            const data = await sendMessageToBackend(text, currentContext);
            setMessages(prev => [...prev, { text: data.answer, type: 'bot' }]);
            setSuggestions(data.follow_up_suggestions || []);
        } catch (error) {
            console.error(error);
            setMessages(prev => [...prev, { text: "Error connecting to server.", type: 'bot error' }]);
        } finally {
            setIsLoading(false);
        }
    }, []);

    return (
        <ChatContext.Provider value={{
        isOpen,
            toggleChat,
            messages,
            sendMessage,
            suggestions,
            isLoading
    }}>
    {children}
    </ChatContext.Provider>
);
};