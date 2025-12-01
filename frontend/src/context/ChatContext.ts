import { createContext } from 'react';

export interface Message {
    text: string;
    type: 'user' | 'bot' | 'bot error';
}

export interface ChatContextType {
    isOpen: boolean;
    toggleChat: () => void;
    messages: Message[];
    sendMessage: (text: string) => Promise<void>;
    suggestions: string[];
    isLoading: boolean;
}

export const ChatContext = createContext<ChatContextType | undefined>(undefined);