import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import { useChatbot } from '../../hooks/useChatbot';
import './Chatbot.css';

const ChatbotWidget: React.FC = () => {
    const { isOpen, toggleChat, messages, sendMessage, suggestions, isLoading } = useChatbot();
    const [inputValue, setInputValue] = useState<string>('');
    const historyRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (historyRef.current) {
            historyRef.current.scrollTop = historyRef.current.scrollHeight;
        }
    }, [messages, isOpen]);

    const handleSend = () => {
        if (!inputValue.trim()) return;
        sendMessage(inputValue);
        setInputValue('');
    };

    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') handleSend();
    };

    return (
        <div className="ai-widget-container" id="ai-widget">
            {isOpen && (
                <div className="ai-window" id="ai-window">
                    <div className="ai-header" id="ai-header">
                        <span>Bandit Games Support</span>
                        <span style={{ fontSize: '12px', opacity: 0.7 }}>Online</span>
                    </div>

                    <div className="ai-history" id="ai-history" ref={historyRef}>
                        {messages.map((msg, index) => (
                            <div key={index} className={`msg ${msg.type}`}>
                                <ReactMarkdown>
                                    {msg.text}
                                </ReactMarkdown>
                            </div>
                        ))}

                        {isLoading && (
                            <div className="msg bot">Thinking...</div>
                        )}

                        {suggestions.length > 0 && !isLoading && (
                            <div className="suggestion-container">
                                {suggestions.map((s, idx) => (
                                    <div key={idx} className="suggestion" onClick={() => sendMessage(s)}>
                                        {s}
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>

                    <div className="ai-input-area" id="ai-input-area">
                        <input
                            type="text"
                            className="ai-input"
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onKeyPress={handleKeyPress}
                            placeholder="Ask a question..."
                        />
                        <button className="ai-send-btn" onClick={handleSend}>Send</button>
                    </div>
                </div>
            )}

            <button className="ai-toggle" id="ai-toggle" onClick={toggleChat}>
                💬
            </button>
        </div>
    );
};

export default ChatbotWidget;