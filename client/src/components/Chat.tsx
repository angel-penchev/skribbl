import React, { useState } from 'react'
import SockJsClient from 'react-stomp';

interface Props {
    roomId: string;
    username: string;
}

interface Message {
    type: string,
    username: string,
    message: string
}

const Chat: React.FC<Props> = ({ roomId, username }) => {
    const [messages, setMessages] = useState<Array<Message>>([])
    const [socketClient, setSocketClient] = useState<any>()
    const [chatInput, setChatInput] = React.useState('');

    return (
        <div className="chat container">
            {messages.map((message, index) => (
                <div
                    key={index}
                    className={`message-${message.username ? 'chat' : 'system'}`}
                >
                    {!message.username && message.message}
                    {message.username && (
                        <>
                            <b>{message.username}:</b> {message.message}
                        </>
                    )}
                </div>
            ))}

            <form
                id="chatbox-form"
                onSubmit={(e): void => {
                    e.preventDefault();
                    if (chatInput === '') {
                        return;
                    }
                    if (socketClient) socketClient.sendMessage(`/topic/${roomId}/chat`, JSON.stringify({
                        username: username,
                        message: chatInput
                    }));
                    setChatInput('');
                }}
            >
                <input
                    data-testid="chat-input"
                    type="text"
                    value={chatInput}
                    onChange={(ev): void => setChatInput(ev.target.value)}
                />
            </form>
            <SockJsClient url='http://localhost:8080/skribbl/'
                topics={[`/topic/${roomId}/chat`]}
                onMessage={(message: Message) => {
                    setMessages([...messages, message])
                }}
                ref={(socketClient: any) => {
                    setSocketClient(socketClient)
                }} />
        </div>
    )
}

export default Chat
